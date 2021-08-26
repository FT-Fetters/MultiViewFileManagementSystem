package lyun.longzhi.view;

import lyun.longzhi.Main;
import lyun.longzhi.components.*;
import lyun.longzhi.components.Button;
import lyun.longzhi.components.Component;
import lyun.longzhi.components.actions.BarAction;
import lyun.longzhi.utils.ElectronTools;
import lyun.longzhi.utils.MessageBox;
import lyun.longzhi.utils.RectangleOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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

        //TextLable
        TextLabel textLabel = new TextLabel(path, Main.mainFrame.getWidth() -65-165,35,25,95);
        textLabel.setBorder(new Color(83,83,83),1);
        Font font = new Font("微软雅黑",Font.PLAIN,15);
        textLabel.setTextFont(font,new Color(170,170,170));

        //FileListColumn
        FileListColumn fileListColumn = new FileListColumn(path,25,155,Main.mainFrame.getWidth() -65,500,16);

        //PathSelector
        PathSelector selector = new PathSelector(path,25 + Main.mainFrame.getWidth() -65 - 165+10,95);

        //TypeClassifier
        TypeClassifier typeClassifier = new TypeClassifier(25,95,Main.mainFrame.getWidth() -65,550,"");
        typeClassifier.setPath(path);

        //ThreeStageSwitch
        ThreeStageSwitch threeStageSwitch = new ThreeStageSwitch(25 + Main.mainFrame.getWidth() - 290,95);

        //TimeAxis
        TimeAxis timeAxis = new TimeAxis(25,135,Main.mainFrame.getWidth() -65,520,"");
        timeAxis.setPath(path);//我也不知为何不在new完后如果不调用setpath就会报错,明明构造函数里面set了

        //CustomizeView
        CustomizeView customizeView = new CustomizeView(25,100,Main.mainFrame.getWidth() -65,550,16);
        //Contents
        Contents contents = new Contents(25,100,300,550,16);

        //SettingBar
        SettingBar settingBar1 = new SettingBar(25,95,Main.mainFrame.getWidth() -65,80,SettingBar.SWITCH,"云管理","");
        SettingBar settingBar2 = new SettingBar(25, 95 + 80 + 5,Main.mainFrame.getWidth() -65,80,SettingBar.BUTTON,"将身份信号复制到剪切板","复制");
        settingBar1.setBarAction(new BarAction() {
            @Override
            public void switchChane(boolean open) {
                if (open){
                    try {
                        ElectronTools.connect();
                        MessageBox.addMessage("已连接到服务器");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    ElectronTools.disConnect();
                    MessageBox.addMessage("已断开连接");
                }
            }

            @Override
            public void buttonClick() {

            }
        });
        settingBar2.setBarAction(new BarAction() {
            @Override
            public void switchChane(boolean open) {

            }

            @Override
            public void buttonClick() {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable trans = new StringSelection(ElectronTools.getSignal());
                clipboard.setContents(trans, null);
                MessageBox.addMessage("已复制到剪切板");
            }
        });


        //NavigationBar
        Component[][] components = {
                {
                    fileListColumn,selector,textLabel
                },
                {
                    typeClassifier
                },
                {
                    threeStageSwitch,timeAxis
                },{
                    customizeView,contents
        },
                {
                    settingBar1, settingBar2
                }
        };
        NavigationBar nav = new NavigationBar(5,25,15,Main.mainFrame.getWidth() -65,60,components);
        nav.addContent("文件列表");
        nav.addContent("分类图表");
        nav.addContent("时间轴图");
        nav.addContent("自定义视图");
        nav.addContent("设置");


        fileListColumn.connect(textLabel,selector,typeClassifier,timeAxis);
        selector.connect(fileListColumn,textLabel,typeClassifier,timeAxis);
        threeStageSwitch.connect(timeAxis);
        timeAxis.connect(fileListColumn,textLabel,selector,typeClassifier,nav);
        customizeView.connect(fileListColumn,textLabel,selector,typeClassifier,nav);

        componentList.add(fileListColumn);
        componentList.add(textLabel);
        componentList.add(selector);
        componentList.add(nav);
        componentList.add(typeClassifier);
        componentList.add(threeStageSwitch);
        componentList.add(timeAxis);
        componentList.add(customizeView);
        componentList.add(contents);
        componentList.add(settingBar1);
        componentList.add(settingBar2);

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
                            try {
                                component.mouseDoubleClick(e.getX()-component.getX(),e.getY()-component.getY());
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
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
                        component.mouseRelease(e.getX()-component.getX(),e.getY()-component.getY());
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
        MessageBox.drawMessage(g,this.getWidth(),this.getHeight());
    }

    public List<Component> getComponentList(){
        return this.componentList;
    }



}
