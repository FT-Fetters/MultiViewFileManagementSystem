package lyun.longzhi.components;


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
    private int selectedTime = 0;

    private boolean enable = true;

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
        File t1 = new File("D:\\xz\\ts.txt");
        File t2 = new File("D:\\xz\\HEU23_Debug.txt");
        List<File> tl1 = new ArrayList<>();
        tl1.add(t1);
        List<File> tl2 = new ArrayList<>();
        tl2.add(t2);
        yearMap.put("2021年",tl1);
        yearMap.put("2022年",tl2);
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
            //time
            TreeMap<String,List<File>> cur = claType > 0 ? (claType > 1 ? dayMap:monthMap):yearMap;
            String[] curSet = new String[cur.size()];
            int i = 0;
            for (String s : cur.keySet()) {
                curSet[i] = s;
                i++;
            }
            g2d.setColor(Color.white);
            g2d.setFont(new Font("微软雅黑",Font.PLAIN,16));
            g2d.drawString(curSet[selectedTime],this.x + this.width/2,this.y);
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

    }

    public void setPath(String path){

    }
}
