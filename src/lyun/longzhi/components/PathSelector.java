package lyun.longzhi.components;

import lyun.longzhi.utils.RectangleOperation;

import java.awt.*;

public class PathSelector implements Component{
    private String path;

    private int x,y;
    private int mouseEnter = -1;
    private int mouseClick;




    /**
     * 固定宽度高度无法修改
     * @param width 组件宽度
     * @param height 组件高度
     */
    @Override
    public void resize(int width, int height) {}

    /**
     * 固定宽度无法获取
     * @return -1
     */
    @Override
    public int getWidth() {
        return 165;
    }

    /**
     * 固定高度无法获取
     * @return -1
     */
    @Override
    public int getHeight() {
        return 35;
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

    /**
     * 固定样式,无法修改
     * @param color 边框颜色
     * @param width 边框宽度
     */
    @Override
    public void setBorder(Color color, int width) {}

    @Override
    public void removeBorder() {}

    /**
     * 固定样式无法修改
     * @param color 背景颜色
     */
    @Override
    public void setBackground(Color color) { }

    @Override
    public void removeBackground() {

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        //消除画图和文字抗锯齿
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (this.mouseClick != -1)graphics2D.setColor(new Color(40,40,40));
        else graphics2D.setColor(new Color(45,45,45));
        switch (mouseEnter){
            case 0:graphics2D.fillRoundRect(this.x,this.y,45,35,5,5);break;
            case 1:graphics2D.fillRoundRect(this.x + 40 + 10,this.y,45,35,5,5);break;
            case 2:graphics2D.fillRoundRect(this.x + 35 + 15 + 35 + 20 + 10,this.y,40,35,5,5);break;
        }



        //艰难的画出的两个箭头,总算调好看了,花了快半个小时
        //箭头一
        graphics2D.setStroke(new BasicStroke(2f));
        if (mouseClick == 0)graphics2D.setColor(new Color(200,200,200));
        else graphics2D.setColor(Color.white);
        graphics2D.drawLine(this.x + 10,this.y+35/2,this.x+20 -2,this.y+5 + 4);
        graphics2D.drawLine(this.x+10,this.y+35/2,this.x+20 -2,this.y+30 - 4);
        graphics2D.drawLine(this.x+10 + 2,this.y+35/2,this.x+35,this.y+35/2);
        //箭头二
        if (mouseClick == 1)graphics2D.setColor(new Color(200,200,200));
        else graphics2D.setColor(Color.white);
        graphics2D.drawLine(this.x+10 + 15 + 35 ,this.y+35/2,this.x+35 + 15 + 35 - 2,this.y+35/2);
        graphics2D.drawLine(this.x + 10 + 15 + 35 + 25,this.y + 35/2,this.x + 10 + 15+35 + 15 + 2,this.y + 5 + 4);
        graphics2D.drawLine(this.x + 10 + 15 + 35 + 25,this.y + 35/2,this.x + 10 + 15+35 + 15 + 2,this.y +30 -4);


        //分割线
        graphics2D.setColor(new Color(50,50,50));
        graphics2D.setStroke(new BasicStroke(0.8f));
        graphics2D.drawLine(this.x + 35 + 15 + 35 + 20,this.y - 5 , this.x + 35 + 15 + 35 + 20,this.y + 35 +5);

        //选择地址

        if (mouseClick == 2)graphics2D.setColor(new Color(200,200,200));
        else graphics2D.setColor(Color.white);
        graphics2D.fillOval(this.x + 35 + 15 + 35 + 20 + 20,this.y + 35/2 - 2,4,4);
        graphics2D.fillOval(this.x + 35 + 15 + 35 + 20 + 20 + 8,this.y + 35/2-2,4,4);
        graphics2D.fillOval(this.x + 35 + 15 + 35 + 20 + 20 + 16,this.y + 35/2-2,4,4);

    }

    @Override
    public void mouseClick(int x, int y) {
        if (RectangleOperation.pointInRectangle(x,y,0,0,40,35))backOff();
        else if (RectangleOperation.pointInRectangle(x,y,40 + 5,0, 40 + 5 + 40, 35))forward();
        else if (RectangleOperation.pointInRectangle(x,y,35 + 15 + 35 + 20 + 10,0,35 + 15 + 35 + 20 + 10 + 35,35))selectPath();
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {
        this.mouseEnter = -1;
        this.mouseClick = -1;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (RectangleOperation.pointInRectangle(x,y,0,0,40,35))this.mouseEnter = 0;
        else if (RectangleOperation.pointInRectangle(x,y,40 + 5,0, 40 + 5 + 40, 35))this.mouseEnter = 1;
        else if (RectangleOperation.pointInRectangle(x,y,35 + 15 + 35 + 20 + 10,0,35 + 15 + 35 + 20 + 10 + 35,35))this.mouseEnter = 2;
    }

    @Override
    public void mouseDoubleClick() {

    }

    @Override
    public void mousePress(int x,int y) {
        if (RectangleOperation.pointInRectangle(x,y,0,0,40,35))this.mouseClick = 0;
        else if (RectangleOperation.pointInRectangle(x,y,40 + 5,0, 40 + 5 + 40, 35))this.mouseClick = 1;
        else if (RectangleOperation.pointInRectangle(x,y,35 + 15 + 35 + 20 + 10,0,35 + 15 + 35 + 20 + 10 + 35,35))this.mouseClick = 2;
    }

    @Override
    public void mouseRelease() {
        this.mouseClick = -1;
    }

    @Override
    public void mouseWheelMoved(int wheel) {

    }

    private void backOff(){

    }

    private void forward(){

    }

    private void selectPath(){

    }
}
