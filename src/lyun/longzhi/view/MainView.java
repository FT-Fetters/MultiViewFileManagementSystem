package lyun.longzhi.view;

import lyun.longzhi.Main;
import lyun.longzhi.components.Component;
import lyun.longzhi.components.FileListColumn;
import lyun.longzhi.components.PathSelector;
import lyun.longzhi.components.TextLabel;
import lyun.longzhi.utils.RectangleOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 视图的主视图
 */
public class MainView extends JPanel implements Runnable {

    //用于存放控件列表
    private final List<Component> componentList = new ArrayList<>();

    private String path = "D:\\";


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

        TextLabel textLabel = new TextLabel(path, Main.mainFrame.getWidth() -65-165,35,25,25);
        textLabel.setBorder(new Color(83,83,83),1);
        Font font = new Font("微软雅黑",Font.PLAIN,15);
        textLabel.setTextFont(font,new Color(170,170,170));

        FileListColumn fileListColumn = new FileListColumn(path,25,75,Main.mainFrame.getWidth() -65,320,10);

        PathSelector selector = new PathSelector(path,25 + Main.mainFrame.getWidth() -65 - 165+10,25);

        fileListColumn.connect(textLabel,selector);
        selector.connect(fileListColumn,textLabel);

        componentList.add(textLabel);
        componentList.add(fileListColumn);
        componentList.add(selector);

    }

    //添加监听器
    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
                    for (Component component : componentList) {
                        if (RectangleOperation.pointInRectangle(
                                e.getX(),
                                e.getY(),
                                component.getX(),
                                component.getY(),
                                component.getX()+component.getWidth(),
                                component.getY()+component.getHeight())
                        ) {
                            component.mouseDoubleClick(e.getX()-component.getX(),e.getY()-component.getY());
                        }
                    }
                }else if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)){
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

            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                for (Component component : componentList) {
                    if (RectangleOperation.pointInRectangle(
                            e.getX(),
                            e.getY(),
                            component.getX(),
                            component.getY(),
                            component.getX()+component.getWidth(),
                            component.getY()+component.getHeight())
                    ){
                        component.mousePress(e.getX()-component.getX(),e.getY()-component.getY());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                for (Component component : componentList) {
                        component.mouseRelease();
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

        this.addMouseWheelListener(e -> {
            for (Component component : componentList) {
                if (RectangleOperation.pointInRectangle(
                        e.getX(),
                        e.getY(),
                        component.getX(),
                        component.getY(),
                        component.getX()+component.getWidth(),
                        component.getY()+component.getHeight())
                ){
                    component.mouseWheelMoved(e.getWheelRotation());
                }
            }
        });
    }



    @Override
    public void run() {
        while (true){
            this.repaint();
            try {
                Thread.sleep(10);//设置刷新延迟,防止cpu占用过高
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
