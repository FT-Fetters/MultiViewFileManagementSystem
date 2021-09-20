package lyun.longzhi.components;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lyun.longzhi.utils.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyFile implements Component{

    int x,y,width,height;
    int lRoller = 0,rRoller = 0;
    int lMouseIn = -1,rMouseIn=-1;
    int lChoose=-1,rChoose=-1;
    boolean searching = true;
    int maxShow = 10;
    int searchAnimation = 0;

    List<File> lFiles = new ArrayList<>();
    List<File> rFiles = new ArrayList<>();
    List<Image> lIcons = new ArrayList<>();
    List<Image> rIcons = new ArrayList<>();
    List<String> keys = new ArrayList<>();

    Map<String, List<File>> copyFiles;

    JFrame father;

    public CopyFile(int x, int y, int width, int height,JFrame frame){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.father = frame;
    }

    @Override
    public void setEnable(boolean enable) {

    }

    @Override
    public void resize(int width, int height) {

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

    }

    @Override
    public void setY(int y) {

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
        Graphics2D g2d = (Graphics2D) g;
        //增加抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(new Color(32, 32, 32));
        g.fillRect(this.x,this.y,this.width/2-4,this.height);
        g.fillRect(this.x + this.width/2 +4,this.y,this.width/2-4,this.height);
        //text
        g2d.setColor(Color.white);
        Font font = new Font("微软雅黑",Font.PLAIN,15);
        g2d.setFont(font);
        g2d.drawString("待选择的拷贝文件",this.x + 8,this.y + FontUtils.getWordHeight(font));
        g2d.drawString("相同的拷贝文件",this.x + this.width/2 + 8 ,this.y + FontUtils.getWordHeight(font));
        //line
        g2d.setColor(Color.gray);
        g2d.drawLine(
                this.x + 8 + FontUtils.getWordWidth(font,"待选择的拷贝文件") + 12,
                this.y + FontUtils.getWordHeight(font)*2/3,
                this.x + this.width/2 - 12,
                this.y + FontUtils.getWordHeight(font)*2/3
                );
        g2d.drawLine(
                this.x + this.width/2 + 8 + FontUtils.getWordWidth(font,"相同的拷贝文件") + 12,
                this.y + FontUtils.getWordHeight(font)*2/3,
                this.x + this.width - 12,
                this.y + FontUtils.getWordHeight(font)*2/3
        );
        //绘制被选择的行
        g2d.setColor(new Color(119, 119, 119));
        if (lChoose != -1 && lChoose >= lRoller && lChoose < lRoller + maxShow && ((lFiles.size() - maxShow) >= 0 || lFiles.size() > lChoose))
            g2d.fillRoundRect(this.x + 10, this.y + 58 + 30 * (lChoose - lRoller) - 20, this.width/2 -6 - 20, 30, 3, 3);
        //绘制鼠标经过
        g2d.setColor(new Color(51, 51, 51));
        if (this.lMouseIn > -1 && this.lMouseIn != (lChoose - lRoller) && ((lFiles.size() - maxShow) >= 0 || lFiles.size() > lMouseIn))
            g2d.fillRoundRect(this.x + 10, this.y + 58 + 30 * lMouseIn - 20, this.width/2 -6 - 20, 30, 3, 3);
        if (this.rMouseIn > -1 && this.rMouseIn != (rChoose - rRoller) && ((rFiles.size() - maxShow) >= 0 || rFiles.size() > rMouseIn))
            g2d.fillRoundRect(this.x + this.width/2 + 10, this.y + 58 + 30 * rMouseIn - 20, this.width/2 -6 - 20, 30, 3, 3);
        //files
        if (searching){
            searchAnimation+=1;
            if (searchAnimation > 10000)searchAnimation=0;
            g2d.setColor(Color.gray);
            g2d.drawString("搜索中"+ StringTools.repeat(".",searchAnimation%100),this.x + 20,this.y + 60);
        }else {
            //left
            g2d.setColor(Color.white);
            for (int i = lRoller; i < Math.min(lRoller + maxShow, lFiles.size());i++){
                String name = lFiles.get(i).getName();
                if (name.length() > 70) {
                    name = name.substring(0, 70);
                    name += "...";
                }
                g2d.drawString(name,this.x + 40,this.y + 60 + (i -lRoller)*30);
                g2d.drawImage(lIcons.get(i), this.x + 20, this.y + 45 + (i - lRoller) * 30, null);
            }
            //right
            for (int i = rRoller;i < Math.min(rRoller + maxShow, rFiles.size());i++){
                String name = rFiles.get(i).getName();
                if (name.length() > 70){
                    name = name.substring(0, 70);
                    name += "...";
                }
                g2d.drawString(name,this.x + this.width/2 + 40,this.y + 60 + (i -rRoller)*30);
                g2d.drawImage(rIcons.get(i), this.x + this.width/2 + 20, this.y + 45 + (i - rRoller) * 30, null);
            }
        }
    }

    @Override
    public void mouseClick(int x, int y, int key) {
        if (x > 10 && x < this.width/2 && y > 10 && y < this.height - 10) {
            lChoose = (y - 45) / 30;
            if (lChoose >= lFiles.size())return;
            String chooseKey = keys.get(lChoose);
            rFiles = copyFiles.get(chooseKey);
            rIcons.clear();
            for (File rFile : rFiles) {
                ImageIcon icon = (ImageIcon) ImageTools.getSmallIcon(rFile);
                rIcons.add(icon.getImage());
            }
        }
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {

    }

    @Override
    public void mouseMove(int x, int y) {
        //System.out.println(233);
        if (x > 10 && x < this.width/2 && y > 10 && y < this.height - 10) {
            lMouseIn = (y - 45) / 30;
        }
        if (x > this.width/2 + 10 && x < this.width && y > 10 && y < this.height - 10) {
            rMouseIn = (y - 45) / 30;
        }
    }

    @Override
    public void mouseDoubleClick(int x, int y, int key) throws IOException {
        if (x > this.width/2 + 10 && x < this.width && y > 10 && y < this.height - 10) {
            int click = (y - 45) / 30;
            if (MessageBox.showMessageBox("消息框", "是否选择该为唯一文件", father)){
                for (int i = 0; i < rFiles.size(); i++) {
                    if (click + rRoller != i){
                        System.out.println(rFiles.get(i).delete());
                    }
                }
                lFiles.remove(lChoose);
                lIcons.remove(lChoose);
                keys.remove(lChoose);
                lChoose = -1;
                lRoller = 0;
                rFiles.clear();
                rIcons.clear();
                rChoose = -1;
                rRoller = 0;
                MessageBox.addMessage("已删除其它拷贝文件!");
            }
        }
    }

    @Override
    public void mousePress(int x, int y, int key) {

    }

    @Override
    public void mouseRelease(int x, int y, int key) {

    }

    @Override
    public void mouseWheelMoved(int wheel) {

    }

    public void mouseWheelMoved(int wheel,int x,int y) {

    }

    @Override
    public void mouseDrag(int x, int y, int key) {

    }

    public void startSearch(String path){
        new Thread(() -> {
            searching = true;
            copyFiles = FindDuplicateFiles.search(path);
            lFiles.clear();
            lIcons.clear();
            for (String key : copyFiles.keySet()) {
                lFiles.add(copyFiles.get(key).get(0));
                ImageIcon icon = (ImageIcon) ImageTools.getSmallIcon(copyFiles.get(key).get(0));
                lIcons.add(icon.getImage());
                keys.add(key);
            }
            searching = false;
        }).start();
    }
}
