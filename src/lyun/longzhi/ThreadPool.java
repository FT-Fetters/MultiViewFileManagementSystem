package lyun.longzhi;

import lyun.longzhi.components.FileListColumn;
import lyun.longzhi.utils.ElectronTools;
import lyun.longzhi.utils.MessageBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    static List<Thread> threads = new ArrayList<>();

    public static void setAllThread(){
        //电子shock线程
        Thread webThread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(200);
                    if (Main.connectWeb){
                        FileListColumn fileListColumn = (FileListColumn) Main.mainView.getComponentList().get(0);
                        List<File> files = fileListColumn.getFiles();
                        String path =fileListColumn.getPath();
                        if (!ElectronTools.shock(files,path)){
                            Main.connectWeb = false;
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threads.add(webThread);

        //消息队列线程
        Thread messageThread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MessageBox.messageHandle();
            }
        });
        threads.add(messageThread);
    }

}
