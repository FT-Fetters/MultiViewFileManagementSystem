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
    private int choose = 0;
    private int slideRatio = 0;
    private int slideTo = -1;

    private Color backgroundColor;
    private Color borderColor;

    private boolean border = true;
    private boolean enable = true;
    private boolean slide = false;

    private List<String> contents = new ArrayList<>();

    private Component[][] components;


    public NavigationBar(int capacity, int x, int y, int width, int height,Component[][] components) {
        this.capacity = capacity;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        borderColor = new Color(43,43,43);
        this.components = components;
        for (int i = 1;i < capacity;i++){
            if (components[i] != null)
                for (Component component : components[i]) {
                    component.setEnable(false);
                }
        }
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
        if (!enable)return;
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

        if (mouseIn != -1 && mouseIn != choose){
            g2d.setColor(new Color(51, 51, 51));
            g2d.fillRect(
                    this.x + mouseIn * singleContentWidth + singleContentWidth/3,
                    this.y + this.height - this.height /10,singleContentWidth/3,
                    this.height/10);
        }

        if (choose != -1){
            if (slide){
                int sub = slideTo - choose;
                float rate = ((float) slideRatio)/100.00f;
                float moveStepLen = ((float) singleContentWidth)*sub*rate;
                g2d.setColor(new Color(119, 119, 119));
                g2d.fillRect(
                        (int) (this.x + choose * singleContentWidth + singleContentWidth / 3 + moveStepLen),
                        this.y + this.height - this.height / 10, singleContentWidth / 3,
                        this.height / 10);
            }else {
                g2d.setColor(new Color(119, 119, 119));
                g2d.fillRect(
                        this.x + choose * singleContentWidth + singleContentWidth / 3,
                        this.y + this.height - this.height / 10, singleContentWidth / 3,
                        this.height / 10);
            }
        }

    }

    @Override
    public void mouseClick(int x, int y,int key) {
        if (!enable || slide)return;
        int singleContentWidth = width / capacity;
        navSwitch(x/singleContentWidth);


    }

    @Override
    public void mouseEnter() {
        if (!enable)return;
    }

    @Override
    public void mouseLeave() {
        if (!enable)return;this.mouseIn = -1;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (!enable)return;
        int singleContentWidth = width / capacity;
        this.mouseIn = x/singleContentWidth;
    }

    @Override
    public void mouseDoubleClick(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mousePress(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mouseRelease(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable)return;
    }

    @Override
    public void mouseDrag(int x, int y, int key) {

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

    public boolean setContent(int i,Component[] component){
        if (i < capacity -1){
            components[i] = component;
            return true;
        }else return false;
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

    private void navSwitch(int to){
        Thread thread = new Thread(() -> {
            try {
                this.slide = true;
                this.slideRatio = 0;
                this.slideTo = to;
                for (int i = 0; i < 50; i+=2){
                    Thread.sleep((50 - i)/4);
                    slideRatio = i;
                }
                for (int i = 50; i <= 100;i+=2) {
                    Thread.sleep((i - 50) / 4);
                    slideRatio = i;
                }

                if (components[choose] != null){
                    for (Component component : components[choose]) {
                        if (component!= null)component.setEnable(false);
                    }
                }
                if (components[to] != null){
                    for (Component component : components[to]) {
                        if (component!= null)component.setEnable(true);
                    }
                }
                slide = false;
                choose =to;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setPriority(10);
        thread.start();
    }

    public int getChoose() {
        return choose;
    }
}