package lyun.longzhi.components;


import lyun.longzhi.utils.FontUtils;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TimeAxis implements Component{

    public final int CLA_BY_YEAR = 0;//按照年分类
    public final int CLA_BY_MONTH = 1;//按照月份分类
    public final int CLA_BY_DAY = 2;//按照日期分类


    private int claType = CLA_BY_YEAR;
    private int x,y;
    private int width,height;
    private int selectedTime = 1;
    private int moveState = 0;

    private boolean enable = true;
    private boolean move = false;

    private String path;

    private final TreeMap<String,List<File>> yearMap = new TreeMap<>((o1, o2) -> {
        int t1 = Integer.parseInt(o1.replace("年",""));
        int t2 = Integer.parseInt(o2.replace("年",""));
        return t1 - t2;
    });
    private final TreeMap<String,List<File>> monthMap = new TreeMap<>();
    private final TreeMap<String, List<File>> dayMap = new TreeMap<>();

    public TimeAxis(int x,int y,int width,int height,String path){
        this.x = x;
        this.y = y;
        this.resize(width,height);
        this.setPath(path);
        //测试样例代码
        File t1 = new File("D:\\xz\\ts.txt");
        File t2 = new File("D:\\xz\\HEU23_Debug.txt");
        File t3 = new File("C:\\迅雷下载\\20977.zip");
        List<File> tl1 = new ArrayList<>();
        tl1.add(t1);
        List<File> tl2 = new ArrayList<>();
        tl2.add(t2);
        List<File> tl3 = new ArrayList<>();
        tl3.add(t3);
        yearMap.put("2021年",tl1);
        yearMap.put("2022年",tl2);
        yearMap.put("2020年",tl3);
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
            TreeMap<String,List<File>> cur = claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap;
            String[] curSet = new String[cur.size()];
            int i = 0;
            for (String s : cur.keySet()) {
                curSet[i] = s;
                i++;
            }
            if (move)moveDrawTime(g2d,curSet);
                else normalDrawTime(g2d,curSet);
        }
    }

    @Override
    public void mouseClick(int x, int y) {

    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {

    }

    @Override
    public void mouseMove(int x, int y) {

    }

    @Override
    public void mouseDoubleClick(int x, int y) throws IOException {

    }

    @Override
    public void mousePress(int x, int y) {

    }

    @Override
    public void mouseRelease() {

    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (wheel == 1){//to right
            timeSwitch(false);
        }else if (wheel == -1){//to left
            timeSwitch(true);
        }
    }

    /**
     * 设置路径
     * @param path 路径
     */
    public void setPath(String path){

    }

    private void normalDrawTime(Graphics2D g2d,String[] curSet){
        g2d.setColor(Color.white);
        Font font = new Font("微软雅黑", Font.PLAIN, 18);
        g2d.setFont(font);
        g2d.drawString(
                curSet[selectedTime],
                this.x + (this.width - FontUtils.getWordWidth(font,curSet[selectedTime]))/2,
                this.y + FontUtils.getWordHeight(font) + 8
        );
        g2d.setColor(new Color(171,171,171));
        font = new Font("微软雅黑", Font.PLAIN, 18);
        g2d.setFont(font);
        if (selectedTime > 0){
            g2d.drawString(
                    curSet[selectedTime-1],
                    this.x + (this.width - FontUtils.getWordWidth(font,curSet[selectedTime]))/2 - FontUtils.getWordWidth(font,curSet[selectedTime-1]) - 20,
                    this.y + FontUtils.getWordHeight(font) + 8
            );
        }
        if (selectedTime < curSet.length - 1 ){
            g2d.drawString(
                    curSet[selectedTime+1],
                    this.x + (this.width - FontUtils.getWordWidth(font,curSet[selectedTime]))/2 + FontUtils.getWordWidth(font,curSet[selectedTime]) + 20,
                    this.y + FontUtils.getWordHeight(font) + 8
            );
        }
    }

    private void moveDrawTime(Graphics2D g2d,String[] curSet){

    }

    private void timeSwitch(boolean left){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }


}
