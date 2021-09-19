package lyun.longzhi.Frame;

import lyun.longzhi.Main;
import lyun.longzhi.components.FileListColumn;
import lyun.longzhi.configure.LoadConfigure;
import lyun.longzhi.view.CopyFileView;

import javax.swing.*;

public class CopyFileFrame extends JFrame {

    public CopyFileFrame(JFrame father){
        this.setSize(LoadConfigure.getScreenWidth()*2/3, LoadConfigure.getScreenHeight()*2/3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("相同拷贝文件");
        FileListColumn fileListColumn = (FileListColumn) Main.mainView.getComponentList().get(0);
        CopyFileView copyFileView = new CopyFileView(fileListColumn.getPath(),this);
        copyFileView.copyFile.startSearch(fileListColumn.getPath());
        copyFileView.setLayout(null);
        Thread copyFileViewThread = new Thread(copyFileView);
        this.add(copyFileView);
        copyFileViewThread.setPriority(10);
        copyFileViewThread.start();
        this.setVisible(true);
    }

}
