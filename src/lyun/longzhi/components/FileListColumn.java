package lyun.longzhi.components;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileListColumn implements Component{
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow;//最大显示数量
    private int borderWidth = 1;//边框宽度
    private int roller = 0;//滚轮
    private int choose = -1;//所选择的文件行
    private int mouseIn = -1;

    private String path;

    private Color backgroundColor = new Color(32,32,32);
    private Color borderColor = new Color(32,32,32);

    private boolean border = false;
    private boolean background = true;

    private List<File> files = new ArrayList<>();
    private List<Image> filesIcon = new ArrayList<>();

    public FileListColumn(String path, int x, int y,int maxShow){
        this.path = path;
        this.x = x;
        this.y = y;
        this.maxShow = maxShow;
        this.setPath(this.path);
    }

    public FileListColumn(String path, int x, int y, int width, int height, int maxShow){
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxShow = maxShow;
        this.setPath(this.path);
    }


    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
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
        this.borderColor = color;
        this.borderWidth = width;
    }

    @Override
    public void removeBorder() {
        this.border = false;
    }

    @Override
    public void setBackground(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public void removeBackground() {
        this.background = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(this.x,this.y,this.width,this.height);




        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);//增加抗锯齿

        //绘制被选择的行
        g2d.setColor(new Color(119,119,119));
        if (choose != -1  && choose >= roller && choose<roller+maxShow)
            g2d.fillRoundRect(this.x + 10,this.y + 30+30*(choose-roller)-20,this.width-20,30,3,3);

        //绘制鼠标经过
        g2d.setColor(new Color(51,51,51));
        if (this.mouseIn > -1 && this.mouseIn != (choose-roller))g2d.fillRoundRect(this.x + 10,this.y + 30+30*mouseIn-20,this.width-20,30,3,3);

        g2d.setColor(Color.white);
        for (int i = roller; i < Math.min(roller + maxShow, files.size()); i++) {
            //绘制文件名
            g2d.drawString(files.get(i).getName(),this.x+40,this.y+30+(i-roller)*30);

            //绘制图标
            g2d.drawImage(filesIcon.get(i),this.x+20,this.y+15+(i-roller)*30,null);
        }

        //绘制滚动条
        g.setColor(new Color(77,77,77));
        g.fillRect(this.x+10+this.width-20,this.y+roller*this.height/files.size(),10,this.height*maxShow/files.size());
    }

    @Override
    public void mouseClick(int x,int y) {
        if (x > 10 && x < this.width-20 && y > 10 && y < this.height - 10){
            choose = (y-10)/30 + roller;
        }
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {
        this.mouseIn = -1;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (x > 10 && x < this.width-20 && y > 10 && y < this.height - 10){
            mouseIn = (y-10)/30;
        }
    }

    @Override
    public void mouseDoubleClick() {

    }

    @Override
    public void mousePress() {

    }

    @Override
    public void mouseRelease() {

    }

    @Override
    public void mouseWheelMoved(int wheel) {
        //模拟鼠标滚滚动
        if (wheel == 1){
            if (roller < files.size() - maxShow){
                roller++;
            }
        }else if (wheel == -1){
            if (roller > 0)roller--;
        }
    }

    /**
     * 设定索要显示的文件的目录
     * @param path 目录
     */
    public void setPath(String path){
        this.path = path;
        new Thread(() -> {//此段应当用一个新的线程进行加载,否则会出现堵塞现象导致无法paint()
            File file = new File(path);
            files.clear();
            filesIcon.clear();
            if (file.isDirectory()){
                File[] readFiles = file.listFiles();
                if (readFiles != null) {
                    files.addAll(Arrays.asList(readFiles));
                    for (File readFile : readFiles) {
                        ImageIcon imageIcon = (ImageIcon) getSmallIcon(readFile);
                        filesIcon.add(imageIcon.getImage());
                    }
                }
            }
        }).start();
    }

    /**
     * 获取小图标
     * @param f 要获取的文件
     * @return 图标
     */
    private Icon getSmallIcon(File f) {
        if (f != null && f.exists()) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            return fsv.getSystemIcon(f);
        }
        return null;
    }
}
