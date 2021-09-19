package lyun.longzhi.view;

import lyun.longzhi.Main;
import lyun.longzhi.components.Component;
import lyun.longzhi.components.CopyFile;
import lyun.longzhi.components.FileListColumn;
import lyun.longzhi.utils.MessageBox;
import lyun.longzhi.utils.RectangleOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyFileView  extends JPanel implements Runnable {


    public CopyFile copyFile;

    public CopyFileView(String path,JFrame frame){
        this.setBackground(new Color(25,25,25));
        copyFile = new CopyFile(25,15,Main.mainFrame.getWidth() -65,Main.mainFrame.getHeight() -70,frame);
        addListener();
    }


    //添加监听器
    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
                    if (RectangleOperation.pointInRectangle(
                            e.getX(),
                            e.getY(),
                            copyFile.getX(),
                            copyFile.getY(),
                            copyFile.getX()+copyFile.getWidth(),
                            copyFile.getY()+copyFile.getHeight())
                    ) {
                        try {
                            copyFile.mouseDoubleClick(e.getX()-copyFile.getX(),e.getY()-copyFile.getY(),0);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }else if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)){
                    if (RectangleOperation.pointInRectangle(
                            e.getX(),
                            e.getY(),
                            copyFile.getX(),
                            copyFile.getY(),
                            copyFile.getX()+copyFile.getWidth(),
                            copyFile.getY()+copyFile.getHeight())
                    ){
                        copyFile.mouseClick(e.getX()-copyFile.getX(),e.getY()-copyFile.getY(),0);
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (RectangleOperation.pointInRectangle(
                        e.getX(),
                        e.getY(),
                        copyFile.getX(),
                        copyFile.getY(),
                        copyFile.getX()+copyFile.getWidth(),
                        copyFile.getY()+copyFile.getHeight())
                ){
                    copyFile.mousePress(e.getX()-copyFile.getX(),e.getY()-copyFile.getY(),0);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                copyFile.mouseRelease(e.getX()-copyFile.getX(),e.getY()-copyFile.getY(),0);
            }

        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (RectangleOperation.pointInRectangle(
                        e.getX(),
                        e.getY(),
                        copyFile.getX(),
                        copyFile.getY(),
                        copyFile.getX()+copyFile.getWidth(),
                        copyFile.getY()+copyFile.getHeight())
                ){
                    copyFile.mouseMove(e.getX()-copyFile.getX(),e.getY()-copyFile.getY());
                }else {
                    copyFile.mouseLeave();
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (RectangleOperation.pointInRectangle(
                        e.getX(),
                        e.getY(),
                        copyFile.getX(),
                        copyFile.getY(),
                        copyFile.getX()+copyFile.getWidth(),
                        copyFile.getY()+copyFile.getHeight())
                ){
                    if (SwingUtilities.isLeftMouseButton(e)){
                        copyFile.mouseDrag(e.getX()-copyFile.getX(),e.getY()-copyFile.getY(),0);
                    }else if (SwingUtilities.isRightMouseButton(e)){
                        copyFile.mouseDrag(e.getX()-copyFile.getX(),e.getY()-copyFile.getY(),2);
                    }
                }
            }
        });



        this.addMouseWheelListener(e -> {
            if (RectangleOperation.pointInRectangle(
                    e.getX(),
                    e.getY(),
                    copyFile.getX(),
                    copyFile.getY(),
                    copyFile.getX()+copyFile.getWidth(),
                    copyFile.getY()+copyFile.getHeight())
            ){
                copyFile.mouseWheelMoved(e.getWheelRotation(),e.getX(),e.getY());
            }
        });
    }


    @Override
    public void run() {
        while (true){
            try {
                this.repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        copyFile.draw(g);
        MessageBox.drawMessage(g,this.getWidth(),this.getHeight());
    }
}
