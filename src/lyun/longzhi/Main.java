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
    public static boolean connectWeb = false;

    public static void main(String[] args) {
        LoadConfigure.loading();
        initMainView();
        loadThread();
    }

    /**
     * 初始化主视图方法
     */
    static void initMainView(){
        //请不要修改属性设置的顺序以及线程创建和new的顺寻,不然会有奇葩的bug
        mainFrame.setSize(LoadConfigure.getScreenWidth()*2/3, LoadConfigure.getScreenHeight()*2/3);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainView = new MainView();
        Thread mainViewThread = new Thread(mainView);
        mainViewThread.start();
        mainFrame.add(mainView);
    }

    static void loadThread(){
        ThreadPool.setAllThread();
        for (Thread thread : ThreadPool.threads) {
            thread.start();
        }
    }

}
