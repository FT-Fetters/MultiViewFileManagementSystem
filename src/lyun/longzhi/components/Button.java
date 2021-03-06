package lyun.longzhi.components;

import javax.swing.*;
import java.awt.*;

public class Button  implements Component{
    private int x;
    private int y;
    private int width;
    private int height;

    private String buttonText;

    private boolean mouseInside;

    private Color backgroundColor = new Color(67,149,255);
    private Color textColor = Color.white;

    Button(String text,int width,int height){

        this.buttonText = text;
        this.width = width;
        this.height = height;
    }

    public Button(String text, int width, int height, int x, int y){

        this.buttonText = text;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }


    @Override
    public void setEnable(boolean enable) {

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
        //背景(还有颜色等)
        g.fillRoundRect(this.x,this.y,this.width,this.height,this.width/10,this.height/10);
        //边框(颜色)
        g.drawRoundRect(this.x,this.y,this.width,this.height,this.width/10,this.height/10);
        //圆角
        g.setColor(backgroundColor);
        //super.paintComponent(g);
        g.drawRoundRect(0, 0, this.width,this.height ,15, 15);

    }

    @Override
    public void mouseClick(int mouseX,int mouseY,int key) {

    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {

    }

    @Override
    public void mouseMove(int x,int y) {

    }

    @Override
    public void mouseDoubleClick(int x,int y,int key) {

    }

    @Override
    public void mousePress(int x, int y,int key) {

    }


    @Override
    public void mouseRelease(int x, int y,int key) {

    }

    @Override
    public void mouseWheelMoved(int wheel) {

    }

    @Override
    public void mouseDrag(int x, int y, int key) {

    }

}
