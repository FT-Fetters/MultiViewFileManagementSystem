package lyun.longzhi;


import javax.swing.*;

import lyun.longzhi.configure.LoadConfigure;
import lyun.longzhi.view.MainView;

/**
 * 主类
 */
public class Main {


    public static MainView mainView;
    public static JFrame mainFrame = new JFrame("多视图文件管理系统");

    public static void main(String[] args) {
        LoadConfigure.loading();
        initMainView();

    }

    /**
     * 初始化主视图方法
     */
    static void initMainView(){
        Thread mainViewThread = new Thread(mainView);
        mainFrame.setSize(LoadConfigure.getScreenWidth()*2/3, LoadConfigure.getScreenHeight()*2/3);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainView = new MainView();
        mainFrame.add(mainView);
        mainViewThread.start();
    }
}
