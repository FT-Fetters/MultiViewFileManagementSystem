package lyun.longzhi.view;

import lyun.longzhi.Main;
import lyun.longzhi.components.Button;
import lyun.longzhi.components.Component;
import lyun.longzhi.components.TextLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 视图的主视图
 */
public class MainView extends JPanel implements Runnable {

    private List<Component> componentList = new ArrayList<>();


    /**
     * 视图类创建的初始化
     */
    public MainView(){
        addComponents();
        addListener();
    }

    //添加组件
    public void addComponents(){
        this.setBackground(new Color(32,32,32));
        TextLabel textLabel = new TextLabel("C:/", Main.mainFrame.getWidth() -65,35,25,25);
        textLabel.setBorder(new Color(83,83,83),1);
        Font font = new Font("微软雅黑",Font.PLAIN,15);
        textLabel.setTextFont(font,new Color(170,170,170));
        componentList.add(textLabel);

    }

    //添加监听器
    public void addListener(){

    }



    @Override
    public void run() {
        while (true){
            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Component component : componentList) {
            component.draw(g);
        }
    }


    /**
     * 判断点是否在某个矩形内
     * @param x 点的x坐标
     * @param y 点的y坐标
     * @param x1 矩形左上x
     * @param y1 矩形左上y
     * @param x2 矩形右下x
     * @param y2 矩形右下y
     * @return 如果点在矩形内则返回true,不在则返回false
     */
    boolean pointInRectangle(int x,int y,int x1,int y1,int x2,int y2){
        if (x2 < x1) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y2 < y1){
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        return x > x1 && x < x2 && y > y1 && y < y2;
    }
}
