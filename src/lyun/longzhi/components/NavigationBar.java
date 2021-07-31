package lyun.longzhi.components;

import sun.font.FontDesignMetrics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NavigationBar implements Component {
    private int x, y;
    private int width, height;
    private int borderWidth;
    private int capacity;
    private int mouseIn = -1;
    private int choose = -1;

    private Color backgroundColor;
    private Color borderColor;

    private boolean border = true;

    private List<String> contents = new ArrayList<>();


    public NavigationBar(int capacity, int x, int y, int width, int height) {
        this.capacity = capacity;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        borderColor = new Color(43,43,43);
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
        this.borderWidth = width;
        this.borderColor = color;
    }

    @Override
    public void removeBorder() {
        this.border = false;
    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public void removeBackground() {

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = ((Graphics2D) g);
        //增加抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int singleContentWidth = width / capacity;
        g2d.setColor(Color.white);
        Font font = new Font("微软雅黑",Font.PLAIN,18);
        g2d.setFont(font);

        for (int i = 0; i < contents.size(); i++) {
            g2d.drawString(
                    contents.get(i),
                    this.x + i * singleContentWidth + (singleContentWidth - getWordWidth(font,contents.get(i)))/2,
                    this.y + this.height - (this.height - getWordHeight(font))*2/3
            );
        }

        if (border){g2d.setColor(this.borderColor);g2d.setStroke(new BasicStroke(0.8f));g2d.drawLine(0,this.y + height,Integer.MAX_VALUE,this.y+height);}

        if (mouseIn != -1){
            g2d.setColor(new Color(51,51,51));
            g2d.fillRect(
                    this.x + mouseIn * singleContentWidth + singleContentWidth/3,
                    this.y + this.height - this.height /10,singleContentWidth/3,
                    this.height/10);
        }

    }

    @Override
    public void mouseClick(int mouseX, int mouseY) {

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
        int singleContentWidth = width / capacity;
        this.mouseIn = x/singleContentWidth;
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


    /**
     * 添加新的内容
     *
     * @param content 目录名
     * @return 若添加成功则返回true若添加失败则返回false
     */
    public boolean addContent(String content) {
        if (contents.size() < capacity && content.length() <= 6) {
            contents.add(content);
            return true;
        } else return false;
    }


    /**
     * 删除指定内容
     *
     * @param content 目录名
     * @return 若目录存在并删除成功则返回true, 若删除失败则返回false
     */
    public boolean removeContent(String content) {
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).equals(content)) {
                contents.remove(i);
                return true;
            }
        }
        return false;
    }


    private static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    private static int getWordHeight(Font font){
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.getHeight();
    }
}