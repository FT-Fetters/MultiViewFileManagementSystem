package lyun.longzhi.components;

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
    private int maxShow;
    private int borderWidth = 1;
    private int roller = 0;

    private String path;

    private Color backgroundColor = new Color(32,32,32);
    private Color borderColor = new Color(32,32,32);

    private boolean border = false;
    private boolean background = true;

    private List<File> files = new ArrayList<>();

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
        g2d.setColor(Color.white);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);//增加抗锯齿
        for (int i = roller; i < Math.min(roller + maxShow, files.size()); i++) {
            g2d.drawString(files.get(i).getName(),this.x+20,this.y+30+i*30);
        }
    }

    @Override
    public void mouseClick(int mouseX,int mouseY) {
        System.out.println("FileListColumn 被点击");
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {

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

    public void setPath(String path){
        this.path = path;
        File file = new File(path);
        files.clear();
        if (file.isDirectory()){
            File[] readFiles = file.listFiles();
            if (readFiles != null)
                files.addAll(Arrays.asList(readFiles));
        }
    }
}
