package lyun.longzhi.components;

import java.awt.*;

public class TextLabel implements Component{
    private int width;
    private int height;
    private int x;
    private int y;

    private boolean background = false;
    private boolean border = false;

    private Color backgroundColor;
    private Color borderColor;

    private Font textFont;

    private int borderWidth;

    String text;

    TextLabel(String text,int width,int height){
        this.text = text;
        this.width = width;
        this.height = height;
    }

    public TextLabel(String text, int width, int height, int x, int y){
        this.text = text;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
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
        this.border = true;
    }

    @Override
    public void removeBorder() {
        this.border = false;
    }

    @Override
    public void setBackground(Color color) {
        this.backgroundColor = color;
        this.background = true;
    }

    @Override
    public void removeBackground() {
        this.background = false;
    }

    @Override
    public void draw(Graphics g) {
        //绘制背景
        if (background){
            g.setColor(backgroundColor);
            g.fillRect(this.x,this.y,this.width,this.height);
        }
        //绘制边框
        if (border){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRect(this.x,this.y,this.width,this.height);
        }
        //绘制文字
        g.setFont(this.textFont);
        g.drawString(this.text,this.x,this.y);
    }
}
