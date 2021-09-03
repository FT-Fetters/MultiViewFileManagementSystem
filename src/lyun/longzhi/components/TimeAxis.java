package lyun.longzhi.components;


import lyun.longzhi.utils.FontUtils;
import lyun.longzhi.utils.SortByTime;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TimeAxis implements Component{

    public static final int CLA_BY_YEAR = 0;//按照年分类
    public static final int CLA_BY_MONTH = 1;//按照月份分类
    public static final int CLA_BY_DAY = 2;//按照日期分类


    private int claType = CLA_BY_YEAR;
    private int x,y;
    private int width,height;
    private int selectedTime = 0;
    private int moveState = 0;
    private int roller = 0;
    private int choose = -1;
    private int mouseIn = -1;

    private boolean enable = true;
    private boolean move = false;
    private boolean loading = true;

    private String path;

    private TreeMap<String,List<Map.Entry<File, Image>>> yearMap = new TreeMap<>();
    private TreeMap<String,List<Map.Entry<File, Image>>> monthMap = new TreeMap<>();
    private TreeMap<String,List<Map.Entry<File, Image>>> dayMap = new TreeMap<>();

    private FileListColumn fileListColumn;
    private TextLabel textLabel;
    private PathSelector pathSelector;
    private TypeClassifier typeClassifier;
    private NavigationBar navigationBar;

    public TimeAxis(int x,int y,int width,int height,String path){
        this.x = x;
        this.y = y;
        this.resize(width,height);
        this.setPath(path);//不知为何这句没用,必须在new完之后再调用setpath
    }


    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void resize(int width, int height) {
        this.width = Math.max(width,400);
        this.height = Math.max(height,400);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setBorder(Color color, int width) {

    }

    @Override
    public void removeBorder() {

    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public void removeBackground() {

    }

    @Override
    public void draw(Graphics g) {
        if (enable){
            Graphics2D g2d = (Graphics2D) g;
            //增加抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //background
            g2d.setColor(new Color(32,32,32));
            g2d.fillRect(this.x,this.y,this.width,this.height);
            //time
            if (loading)return;
            TreeMap<String,List<Map.Entry<File, Image>>> cur = claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap;
            String[] curSet = new String[cur.size()];
            int i = 0;
            for (String s : cur.keySet()) {
                curSet[i] = s;
                i++;
            }
            if (move)moveDrawTime(g2d,curSet);
            else normalDrawTime(g2d,curSet);
            //border
            g2d.setColor(new Color(43,43,43));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawLine(this.x,this.y+50,this.x + this.width,this.y + 50);
            if (cur.size() == 0){
                g2d.setColor(Color.white);
                Font font = new Font("微软雅黑",Font.PLAIN,15);
                g2d.setFont(font);
                g2d.drawString("此文件夹为空",this.x + this.width/2 - FontUtils.getWordWidth(font,"此文件夹为空")/2,this.y + 20 + 50);
                return;
            }
            if (selectedTime >= curSet.length)return;
            //draw file
            drawFiles(g2d, cur.get(curSet[selectedTime]));
            //绘制滚动条
            g.setColor(new Color(77,77,77));
            int tl = cur.get(curSet[selectedTime]).size();
            if (tl > 15)g.fillRect(this.x+10+this.width-20,this.y + 50 +roller*(this.height-50)/tl,10,(this.height - 50)*15/tl);
        }
    }

    @Override
    public void mouseClick(int x, int y,int key) {
        if (!enable)return;
        if (x > 10 && x < this.width-20 && y > 10+50 && y < this.height - 10){
            choose = ((y - 50)-10)/30 + roller;
        }
    }

    @Override
    public void mouseEnter() {
        if (!enable)return;
    }

    @Override
    public void mouseLeave() {
        if (!enable)return;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (!enable)return;
        if (x > 10 && x < this.width-20 && y > 10 + 50 && y < this.height - 10){
            mouseIn = ((y - 50)-10)/30;
        }
    }

    @Override
    public void mouseDoubleClick(int x, int y,int key) throws IOException {
        if (!enable)return;
        if (x > 10 && x < this.width-20 && y > 10 + 50 && y < this.height - 10){
            TreeMap<String,List<Map.Entry<File, Image>>> cur = claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap;
            String[] curSet = new String[cur.size()];
            int i = 0;
            for (String s : cur.keySet()) {
                curSet[i] = s;
                i++;
            }
            int tl = cur.get(curSet[selectedTime]).size();
            int tmp = ((y-50)-10)/30 + roller;
            System.out.println(tmp);
            if (((y-50)-10)/30 >= tl - roller)return;
            File file = cur.get(curSet[selectedTime]).get(tmp).getKey();
            if (file.isDirectory()){
                String newPath = file.getPath();
                if (pathSelector != null){
                    pathSelector.enterNewPath(newPath);
                }
                if (textLabel != null){
                    textLabel.text = newPath;
                }
                if (typeClassifier != null)typeClassifier.setPath(newPath);
                if (fileListColumn != null)fileListColumn.setPath(newPath);
                if (navigationBar != null)navigationBar.mouseClick(0,0,0);//模拟切换
                this.setPath(newPath);
                this.choose = -1;
                this.roller = 0;
            }else{
                String newPath = file.getPath();
                if (pathSelector != null){
                    Runtime.getRuntime().exec(new String[]{ "cmd", "/c", newPath});
                }
            }
        }
    }

    @Override
    public void mousePress(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mouseRelease(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable)return;
        if (move)return;
        TreeMap<String,List<Map.Entry<File, Image>>> cur = claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap;
        String[] curSet = new String[cur.size()];
        int i = 0;
        for (String s : cur.keySet()) {
            curSet[i] = s;
            i++;
        }
        if (selectedTime > curSet.length - 1)return;
        int tl = cur.get(curSet[selectedTime]).size();
        if (wheel == 1){//down
            if (roller == Math.max(tl - 15, 0) && selectedTime < (claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap).size() - 1){
                timeSwitch(true);
                this.choose = -1;
                this.roller = 0;
                this.mouseIn = -1;
            }else if (roller < Math.max(tl - 15, 0)){
                roller++;
            }

        }else if (wheel == -1){//up
            if (roller == 0 && selectedTime > 0){
                timeSwitch(false);
                this.choose = -1;
                this.roller = 0;
                this.mouseIn = -1;
            }else if (roller > 0){
                roller--;
            }

        }
    }

    /**
     * 设置路径
     * @param path 路径
     */
    public void setPath(String path){
        loading = true;
        roller = 0;
        selectedTime = 0;
        choose = -1;
        mouseIn = -1;
        Runnable runnable = () -> {
            selectedTime = 0;
            this.yearMap = SortByTime.sortByYear(path);
            this.monthMap = SortByTime.sortByMonth(path);
            this.dayMap = SortByTime.sortByDay(path);
            loading = false;
        };
        Thread thread = new Thread(runnable);
        if (enable){
            thread.setPriority(8);
        }else thread.setPriority(2);
        thread.start();
    }

    private void normalDrawTime(Graphics2D g2d,String[] curSet){
        if (curSet.length == 0)return;
        g2d.setColor(Color.white);
        Font font = new Font("微软雅黑", Font.PLAIN, 18);
        g2d.setFont(font);
        int selectWidth = FontUtils.getWordWidth(font,curSet[selectedTime]);
        g2d.drawString(
                curSet[selectedTime],
                this.x + (this.width - selectWidth)/2,
                this.y + FontUtils.getWordHeight(font) + 8
        );
        g2d.setColor(new Color(121,121,121));
        font = new Font("微软雅黑", Font.PLAIN, 18);
        g2d.setFont(font);
        if (selectedTime > 0){
            g2d.drawString(
                    curSet[selectedTime-1],
                    this.x + (this.width - selectWidth)/2 - FontUtils.getWordWidth(font,curSet[selectedTime-1]) - 20,
                    this.y + FontUtils.getWordHeight(font) + 8
            );
        }
        if (selectedTime < curSet.length - 1 ){
            g2d.drawString(
                    curSet[selectedTime+1],
                    this.x + (this.width - selectWidth)/2 + selectWidth + 20,
                    this.y + FontUtils.getWordHeight(font) + 8
            );
        }
    }

    private void moveDrawTime(Graphics2D g2d,String[] curSet){
        //center move
        Font font = new Font("微软雅黑", Font.PLAIN, 18);
        int c = 255 - (255 - 121)*Math.abs(moveState)/100;
        int selectWidth = FontUtils.getWordWidth(font,curSet[selectedTime]);
        g2d.setColor(new Color(c,c,c));
        g2d.setFont(font);
        g2d.drawString(
                curSet[selectedTime],
                this.x + (this.width - selectWidth)/2 + (moveState > 0 ? selectWidth + 20 : FontUtils.getWordWidth(font,curSet[selectedTime]))*moveState/100,
                this.y + FontUtils.getWordHeight(font) + 8
                );
        c = 121 + (255 - 121)*Math.abs(moveState)/100;
        g2d.setColor(new Color(c,c,c));
        if (moveState > 0 && selectWidth >0){
            g2d.drawString(
                    curSet[selectedTime - 1],
                    this.x + (this.width - selectWidth)/2 - (FontUtils.getWordWidth(font,curSet[selectedTime-1]) + 20)*(100 - moveState)/100,
                    this.y + FontUtils.getWordHeight(font) + 8
                    );
        }else if (moveState < 0 && selectedTime < curSet.length - 1){
            g2d.drawString(
                    curSet[selectedTime + 1],
                    this.x + (this.width - selectWidth)/2 + (selectWidth + 20) + (selectWidth + 20)*moveState/100 ,
                    this.y + FontUtils.getWordHeight(font) + 8
                    );
        }
    }

    private void timeSwitch(boolean left){
        new Thread(() -> {
            try {
                move = true;
                for (int i = 1;i <= 100;++i){
                    Thread.sleep(1);
                    if (left)moveState = - i;
                        else moveState = i;
                }
                move = false;
                moveState = 0;
                if (left)selectedTime++;
                else selectedTime--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setClaType(int claType){
        this.claType = claType;
        this.selectedTime = 0;
        this.roller = 0;
        this.mouseIn = -1;
        this.choose = -1;
    }

    private void drawFiles(Graphics2D g2d, List<Map.Entry<File,Image>> files){
        int baseX = this.x;
        int baseY = this.y + 50;
        final int maxShow = 15;
        //绘制被选择的行
        g2d.setColor(new Color(119,119,119));
        if (choose != -1  && choose >= roller && choose<roller+maxShow && ((files.size() - maxShow) >= 0 || files.size() > choose))
            g2d.fillRoundRect(baseX + 10,baseY + 30 + 30*(choose - roller) - 20,this.width-20,30,3,3);

        //绘制鼠标经过
        g2d.setColor(new Color(51,51,51));
        if (this.mouseIn > -1 && this.mouseIn != (choose-roller) && ((files.size() - maxShow) >= 0 || files.size() > mouseIn))
            g2d.fillRoundRect(baseX + 10,baseY + 30+30*mouseIn-20,this.width-20,30,3,3);

        for (int i = roller; i < Math.min(roller + maxShow, files.size()); i++){
            File file = files.get(i).getKey();
            Image icon = files.get(i).getValue();
            Font font = new Font("微软雅黑",Font.PLAIN,15);
            g2d.setFont(font);
            g2d.setColor(Color.white);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);//增加抗锯齿
            //绘制文件名
            g2d.drawString(file.getName(),baseX + 40,baseY + 30 +(i - roller)*30);
            //绘制图标
            g2d.drawImage(icon,baseX + 20,baseY + 15 + (i -roller)*30,null);
        }
    }

    public void connect(FileListColumn fileListColumn,TextLabel textLabel,PathSelector pathSelector,TypeClassifier typeClassifier,NavigationBar navigationBar){
        this.fileListColumn = fileListColumn;
        this.textLabel = textLabel;
        this.pathSelector = pathSelector;
        this.typeClassifier = typeClassifier;
        this.navigationBar = navigationBar;
    }

}
