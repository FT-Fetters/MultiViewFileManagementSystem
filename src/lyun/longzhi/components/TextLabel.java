package lyun.longzhi.components;


import java.awt.*;

/**
 * 文本标签控件
 * 主要用于展示文字内容
 */
public class TextLabel implements Component{
    private int width;
    private int height;
    private int x;
    private int y;

    private boolean background = false;
    private boolean border = false;
    private boolean enable = true;

    private Color backgroundColor;
    private Color borderColor;
    private Color textColor;

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
    public void setEnable(boolean enable) {
        this.enable = enable;
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
        if (!enable)return;
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
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);//增加抗锯齿
        g2d.setColor(textColor);
        g2d.setFont(this.textFont);
        g2d.drawString(this.text,this.x+10,this.y+(this.height+textFont.getSize())/2);//xy未文字的左下角
    }

    @Override
    public void mouseClick(int mouseX,int mouseY) {
        if (!enable)return;
        System.out.println("textLabel被点击");
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
    public void mouseDoubleClick(int x,int y) {
        if (!enable)return;
    }

    @Override
    public void mousePress(int x,int y) {
        if (!enable)return;
    }

    @Override
    public void mouseRelease(int x, int y) {
        if (!enable)return;
    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable)return;
    }

    /**
     * 设置文本的字体格式
     * @param font 字体格式
     */
    public void setTextFont(Font font,Color color){
        this.textFont = font;
        this.textColor = color;
    }
}
