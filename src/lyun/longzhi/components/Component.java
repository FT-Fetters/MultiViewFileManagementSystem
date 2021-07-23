package lyun.longzhi.components;

import java.awt.*;

public interface Component {



    /**
     * 设置组件大小
     * @param width 组件宽度
     * @param height 组件高度
     */
    void resize(int width,int height);

    /**
     * 获取组件宽度
     * @return 组件的宽度
     */
    int getWidth();

    /**
     * 获取组件的高度
     * @return 组件的高度
     */
    int getHeight();

    /**
     * 获取组件的位置x
     * @return 位置x
     */
    int getX();

    /**
     * 获取组件的位置y
     * @return 位置y
     */
    int getY();

    /**
     * 设置组件的位置x
     * @param x 位置x
     */
    void setX(int x);

    /**
     * 设置组件的位置y
     * @param y 位置y
     */
    void setY(int y);

    /**
     * 设置组件边框的颜色和线条宽度
     * @param color 边框颜色
     * @param width 边框宽度
     */
    void setBorder(Color color,int width);
    /**
     * 删除组件边框
     */
    void removeBorder();

    /**
     * 设置组件的背景颜色
     * @param color 背景颜色
     */
    void setBackground(Color color);
    /**
     * 删除组件的背景颜色
     */
    void removeBackground();

    /**
     * 绘制组件方法
     * 由所在的面板调用该方法
     * @param g 绘制的画板
     */
    void draw(Graphics g);

    /**
     * 鼠标点击事件
     */
    void mouseClick(int mouseX,int mouseY);

    /**
     * 鼠标进入事件
     */
    void mouseEnter();

    /**
     * 鼠标离开事件
     */
    void mouseLeave();

    /**
     * 鼠标双击事件
     */
    void mouseDoubleClick();

    /**
     * 鼠标按下事件
     */
    void mousePress();

    /**
     * 鼠标释放事件
     */
    void mouseRelease();


}
