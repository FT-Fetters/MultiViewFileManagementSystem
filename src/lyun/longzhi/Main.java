package lyun.longzhi;


import javax.swing.*;

import lyun.longzhi.components.CustomizeView;
import lyun.longzhi.components.NavigationBar;
import lyun.longzhi.configure.LoadConfigure;
import lyun.longzhi.view.MainView;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
        mainView = new MainView();
        Thread mainViewThread = new Thread(mainView);
        mainViewThread.setPriority(10);
        mainFrame.add(mainView);
        mainViewThread.start();
        new DropTarget(mainFrame, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                NavigationBar nav = (NavigationBar) mainView.getComponentList().get(3);
                if (nav.getChoose() == 3){
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        try {
                            List<File> list = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                            CustomizeView customizeView = (CustomizeView) mainView.getComponentList().get(7);
                            customizeView.putFile(list);
                        } catch (IOException | UnsupportedFlavorException e) {
                            e.printStackTrace();
                        }
                    }else dtde.rejectDrop();
                }else dtde.rejectDrop();
            }
        });
        mainFrame.setVisible(true);
    }

    static void loadThread(){
        ThreadPool.setAllThread();
        for (Thread thread : ThreadPool.threads) {
            thread.start();
        }
    }

}
