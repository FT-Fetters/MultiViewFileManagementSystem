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

    private boolean enable = true;
    private boolean move = false;
    private boolean loading = true;

    private String path;

    private TreeMap<String,List<Map.Entry<File, Image>>> yearMap = new TreeMap<>();
    private TreeMap<String,List<Map.Entry<File, Image>>> monthMap = new TreeMap<>();
    private TreeMap<String,List<Map.Entry<File, Image>>> dayMap = new TreeMap<>();

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
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        if (!enable)return;
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
    }

    @Override
    public void mouseDoubleClick(int x, int y) throws IOException {
        if (!enable)return;
    }

    @Override
    public void mousePress(int x, int y) {
        if (!enable)return;
    }

    @Override
    public void mouseRelease() {
        if (!enable)return;
    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable)return;
        if (move)return;
        if (wheel == 1 && selectedTime > 0){//to right
            timeSwitch(false);
        }else if (wheel == -1 && selectedTime < (claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap).size() - 1){//to left
            timeSwitch(true);
        }
    }

    /**
     * 设置路径
     * @param path 路径
     */
    public void setPath(String path){
        Runnable runnable = () -> {
            loading = true;
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
    }


}
