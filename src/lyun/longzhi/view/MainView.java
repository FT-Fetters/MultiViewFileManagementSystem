package lyun.longzhi.view;

import lyun.longzhi.Main;
import lyun.longzhi.components.Component;
import lyun.longzhi.components.FileListColumn;
import lyun.longzhi.components.TextLabel;
import lyun.longzhi.utils.RectangleOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
        this.setBackground(new Color(25,25,25));

        TextLabel textLabel = new TextLabel("C:/", Main.mainFrame.getWidth() -65,35,25,25);
        textLabel.setBorder(new Color(83,83,83),1);
        Font font = new Font("微软雅黑",Font.PLAIN,15);
        textLabel.setTextFont(font,new Color(170,170,170));
        componentList.add(textLabel);

        FileListColumn fileListColumn = new FileListColumn("D:\\",25,75,Main.mainFrame.getWidth() -65,320,10);
        componentList.add(fileListColumn);

    }

    //添加监听器
    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (Component component : componentList) {
                    if (RectangleOperation.pointInRectangle(
                            e.getX(),
                            e.getY(),
                            component.getX(),
                            component.getY(),
                            component.getX()+component.getWidth(),
                            component.getY()+component.getHeight())
                    ){
                        component.mouseClick(e.getX()-component.getX(),e.getY()-component.getY());
                    }
                }
            }


        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                for (Component component : componentList) {
                    if (RectangleOperation.pointInRectangle(
                            e.getX(),
                            e.getY(),
                            component.getX(),
                            component.getY(),
                            component.getX()+component.getWidth(),
                            component.getY()+component.getHeight())
                    ){
                        component.mouseMove(e.getX()-component.getX(),e.getY()-component.getY());
                    }else {
                        component.mouseLeave();
                    }
                }
            }
        });
    }



    @Override
    public void run() {
        while (true){
            this.repaint();
            try {
                Thread.sleep(10);
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



}
