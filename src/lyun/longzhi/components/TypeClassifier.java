package lyun.longzhi.components;

import lyun.longzhi.Main;
import lyun.longzhi.utils.DetermineFileType;
import lyun.longzhi.utils.RectangleOperation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TypeClassifier implements Component{
    private int x,y;
    private int width,height;
    private int checked = 0;
    private int page = 0;
    private int maxPage = 0;

    private boolean enable = true;

    private List<File> imageFiles = new ArrayList<>();
    private List<File> videoFiles = new ArrayList<>();
    private List<File> otherFiles = new ArrayList<>();

    private final Color OTHER_BACKGROUND = new Color(32,32,32);
    private final Color VIDEO_BACKGROUND= new Color(42,42,42);
    private final Color IMAGE_BACKGROUND = new Color(52,52,52);


    private static Image IMAGE_ICON;
    private static Image VIDEO_ICON;
    private static Image OTHER_ICON;
    private static Image WHEEL_ICON;


    private String path;

    public TypeClassifier(int x, int y, int width, int height, String path){
        this.x = x;
        this.y = y;
        this.width = Math.max(width, 100);
        this.height = Math.max(height,100);
        setPath(path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                IMAGE_ICON = new ImageIcon("src/lyun/longzhi/images/image.png").getImage();
                VIDEO_ICON = new ImageIcon("src/lyun/longzhi/images/video.png").getImage();
                OTHER_ICON = new ImageIcon("src/lyun/longzhi/images/other.png").getImage();
                WHEEL_ICON = new ImageIcon("src/lyun/longzhi/images/wheel.png").getImage();
            }
        }).start();
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void resize(int width, int height) {
        this.width = Math.max(width, 100);
        this.height = Math.max(height,100);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setBorder(Color color, int width) {

    }

    @Override
    public void removeBorder() {

    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public void removeBackground() {

    }

    @Override
    public void draw(Graphics g) {
        if (!enable)return;
        Graphics2D g2d = (Graphics2D) g;
        //增加抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //draw tag
        g2d.setColor(OTHER_BACKGROUND);
        g2d.fillRect(this.x + this.width - 30 - 80,this.y,80,60);
        g2d.fillRect(this.x + this.width - 15,this.y + 60,15,this.height - 60);
        g2d.setColor(VIDEO_BACKGROUND);
        g2d.fillRect(this.x + this.width - 30 - 80 - 25 - 80,this.y,80,60);
        g2d.fillRect(this.x + this.width -30,this.y + 60,15,this.height - 60);
        g2d.setColor(IMAGE_BACKGROUND);
        g2d.fillRect(this.x + this.width - 30 - 80*3 - 25*2,this.y,80,60);
        //draw text
        g2d.setColor(Color.white);

        Font plain = new Font("微软雅黑",Font.PLAIN,18);
        Font bold = new Font("微软雅黑",Font.BOLD,18);
        if (checked == 0)g2d.setFont(bold);
        else g2d.setFont(plain);
        g2d.drawString("图片",this.x + this.width - 30 - 80*3 - 25*2 + 22,this.y + 30 + 6);
        if (checked == 1)g2d.setFont(bold);
        else g2d.setFont(plain);
        g2d.drawString("视频",this.x + this.width - 30 - 80 - 25 - 80 + 22,this.y + 30 + 6);
        if (checked == 2)g2d.setFont(bold);
        else g2d.setFont(plain);
        g2d.drawString("其它",this.x + this.width - 30 - 80 + 22,this.y + 30 + 6);
        //draw board
        switch (checked){
            case 0:
                g2d.setColor(IMAGE_BACKGROUND);
                g2d.fillRect(this.x,this.y+60,this.width - 30,this.height - 60);
                drawFiles(g2d, imageFiles, IMAGE_ICON);
                break;
            case 1:
                g2d.setColor(VIDEO_BACKGROUND);
                g2d.fillRect(this.x,this.y+60,this.width - 15,this.height - 60);
                drawFiles(g2d, videoFiles, VIDEO_ICON);
                break;
            case 2:
                g2d.setColor(OTHER_BACKGROUND);
                g2d.fillRect(this.x,this.y + 60,this.width,this.height - 60);
                drawFiles(g2d, otherFiles, OTHER_ICON);
        }
        //draw page
        g2d.setColor(Color.white);
        g2d.setFont(new Font("微软雅黑",Font.PLAIN,16));
        g2d.drawString(page+1 + "/"+maxPage,this.x + (this.width - 30)/2,this.y + this.height - 10);
        g2d.drawImage(WHEEL_ICON,this.x + (this.width - 30)/2 + 25,this.y + this.height - 24,null);

    }

    private void drawFiles(Graphics2D g2d, List<File> videoFiles, Image videoIcon) {
        int begin = page * 40;
        int end = (page * 40 + 40)>videoFiles.size() ? videoFiles.size() - begin+ begin:page * 40 + 40;
        for (int i = begin; i < end; i++) {
            String filename = videoFiles.get(i).getName();
            g2d.drawImage(videoIcon,this.x+112*((i-begin)%10) + 20 + 40,this.y + 60 + 10 + 120*((i-begin)/10),null);
            g2d.setColor(Color.white);
            g2d.setFont(new Font("微软雅黑",Font.PLAIN,16));
            if (filename.length() > 6)filename = filename.substring(0,6) + "..";
            g2d.drawString(filename,this.x+112*((i-begin)%10) + 20 + 40,this.y + 60 + 64 + 10 + 120*((i-begin)/10) + 15);
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        if (RectangleOperation.pointInRectangle(x,y, this.width - 30 - 80,0, this.width - 30,60)){
            checked = 2;page = 0;maxPage = otherFiles.size()%40 == 0 ? otherFiles.size()/40:otherFiles.size()/40+1;}
        else if (RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80 - 25 - 80,0,this.width - 30 - 80 - 25,60)){
            checked = 1;page = 0;maxPage = videoFiles.size()%40 == 0 ? videoFiles.size()/40:videoFiles.size()/40+1;}
        else if (RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80*3 - 25*2,0,this.width - 30 - 80*3 - 25*2 + 80,60)){
            checked = 0;page = 0;maxPage = ((imageFiles.size()%40 == 0) ? (imageFiles.size()/40):(imageFiles.size()/40+1));}
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {
        Main.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseMove(int x, int y) {
        if (
                RectangleOperation.pointInRectangle(x,y, this.width - 30 - 80,0, this.width - 30,60) ||
                        RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80 - 25 - 80,0,this.width - 30 - 80 - 25,60) ||
                        RectangleOperation.pointInRectangle(x,y,this.width - 30 - 80*3 - 25*2,0,this.width - 30 - 80*3 - 25*2 + 80,60)
        ){
            Main.mainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else Main.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDoubleClick(int x, int y) {

    }

    @Override
    public void mousePress(int x, int y) {

    }

    @Override
    public void mouseRelease() {

    }

    @Override
    public void mouseWheelMoved(int wheel) {

        if (wheel == 1)//向下
        {
            int fileSize = 0;
            switch (checked){
                case 0:
                    fileSize = imageFiles.size();
                    break;
                case 1:
                    fileSize = videoFiles.size();
                    break;
                case 2:
                    fileSize = otherFiles.size();
            }
            if ((page + 1)*40<fileSize){
                page++;
            }
        }else if (wheel == -1){
            int fileSize = 0;
            switch (checked){
                case 0:
                    fileSize = imageFiles.size();
                    break;
                case 1:
                    fileSize = videoFiles.size();
                    break;
                case 2:
                    fileSize = otherFiles.size();
            }
            if (page > 0){
                page--;
            }
        }
    }

    public void setPath(String path){
        new Thread(() -> {
            this.path = path;
            File dir = new File(path);
            if (dir.isDirectory()){
                File[] files = dir.listFiles();
                if (files == null)return;
                otherFiles.clear();
                videoFiles.clear();
                imageFiles.clear();
                for (File file : files) {
                    if (file.isDirectory())continue;
                    int type = DetermineFileType.getType(file);
                    switch (type){
                        case DetermineFileType.IMAGE:
                            imageFiles.add(file);
                            break;
                        case DetermineFileType.VIDEO:
                            videoFiles.add(file);
                            break;
                        case DetermineFileType.OTHER:
                            otherFiles.add(file);
                    }
                }
            }
            page = 0;maxPage = ((imageFiles.size()%40 == 0) ? (imageFiles.size()/40):(imageFiles.size()/40+1));
        }).start();

    }

    /**
     * 获取大图标
     * @param f 要获取的文件
     * @return 图标
     */
    private Icon getBigIcon(File f) {
        if (f != null && f.exists()) {
            try {
                sun.awt.shell.ShellFolder sf = sun.awt.shell.ShellFolder.getShellFolder(f);
                return new ImageIcon(sf.getIcon(true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
