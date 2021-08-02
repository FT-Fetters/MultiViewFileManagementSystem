package lyun.longzhi.components;

import lyun.longzhi.Main;
import lyun.longzhi.utils.RectangleOperation;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TypeClassifier implements Component{
    private int x,y;
    private int width,height;
    private int checked = 0;

    private boolean enable = true;

    private List<File> imageFiles = new ArrayList<>();
    private List<File> videoFiles = new ArrayList<>();
    private List<File> otherFiles = new ArrayList<>();

    private final Color OTHER_BACKGROUND = new Color(32,32,32);
    private final Color VIDEO_BACKGROUND= new Color(40,40,40);
    private final Color IMAGE_BACKGROUND = new Color(48,48,48);




    private String path;

    public TypeClassifier(int x, int y, int width, int height, String path){
        this.x = x;
        this.y = y;
        this.width = Math.max(width, 100);
        this.height = Math.max(height,100);
        setPath(path);
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void resize(int width, int height) {
        this.width = Math.max(width, 100);
        this.height = Math.max(height,100);
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
        if (!enable)return;
        Graphics2D g2d = (Graphics2D) g;
        //增加抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //draw tag
        g2d.setColor(OTHER_BACKGROUND);
        g2d.fillRect(this.x + this.width - 30 - 80,this.y,80,60);
        g2d.fillRect(this.x + this.width - 15,this.y + 60,15,this.height - 60);
        g2d.setColor(VIDEO_BACKGROUND);
        g2d.fillRect(this.x + this.width - 30 - 80 - 25 - 80,this.y,80,60);
        g2d.fillRect(this.x + this.width -30,this.y + 60,15,this.height - 60);
        g2d.setColor(IMAGE_BACKGROUND);
        g2d.fillRect(this.x + this.width - 30 - 80*3 - 25*2,this.y,80,60);
        //draw text
        g2d.setColor(Color.white);
        Font font = new Font("微软雅黑",Font.BOLD,18);
        g2d.setFont(font);
        g2d.drawString("图片",this.x + this.width - 30 - 80*3 - 25*2 + 22,this.y + 30 + 6);
        g2d.drawString("视频",this.x + this.width - 30 - 80 - 25 - 80 + 22,this.y + 30 + 6);
        g2d.drawString("其它",this.x + this.width - 30 - 80 + 22,this.y + 30 + 6);
        //draw board
        switch (checked){
            case 0:
                g2d.setColor(IMAGE_BACKGROUND);
                g2d.fillRect(this.x,this.y+60,this.width - 30,this.height - 60);
                break;
            case 1:
                g2d.setColor(VIDEO_BACKGROUND);
                g2d.fillRect(this.x,this.y+60,this.width - 15,this.height - 60);
                break;
            case 2:
                g2d.setColor(OTHER_BACKGROUND);
                g2d.fillRect(this.x,this.y + 60,this.width,this.height - 60);
        }


    }

    @Override
    public void mouseClick(int x, int y) {
        if (RectangleOperation.pointInRectangle(x,y, this.width - 30 - 80,0, this.width - 30,60))checked = 2;
        else if (RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80 - 25 - 80,0,this.width - 30 - 80 - 25,60))checked = 1;
        else if (RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80*3 - 25*2,0,this.width - 30 - 80*3 - 25*2 + 80,60))checked = 0;
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {
        Main.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseMove(int x, int y) {
        if (
                RectangleOperation.pointInRectangle(x,y, this.width - 30 - 80,0, this.width - 30,60) ||
                        RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80 - 25 - 80,0,this.width - 30 - 80 - 25,60) ||
                        RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80*3 - 25*2,0,this.width - 30 - 80*3 - 25*2 + 80,60)
        ){
            Main.mainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else Main.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDoubleClick(int x, int y) {

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
