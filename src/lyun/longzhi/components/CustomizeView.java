package lyun.longzhi.components;

import lyun.longzhi.Main;
import lyun.longzhi.utils.RectangleOperation;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CustomizeView implements Component{
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow = 360;
    private int roller = 0;//滚轮
    private boolean enable = true;
    private boolean border = true;
    private boolean change = false;
    private int borderWidth;
    private  static int y1 = 0;
    private  static int x1 = 0;
    private static int click = 0;
    private static int dclick = 0;
    private static int size = 0;
    private File file;
    private String filePath = "C:\\";
    private Image fileicon;
    private Graphics g;
    private Color borderColor;


    private Color backgroundColor = new Color(57, 57, 57, 91);

    private int mouseClick;

    public static int mcount = 0;
    private FileListColumn fileListColumn;
    private TextLabel textLabel;
    private PathSelector pathSelector;
    private TypeClassifier typeClassifier;
    private NavigationBar navigationBar;
    public CustomizeView( int x, int y, int width, int height, int maxShow){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxShow = maxShow;
    }
    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
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
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setBorder(Color color, int width) {
        this.borderWidth = width;
        this.borderColor = color;
    }

    @Override
    public void removeBorder() {
        this.border = false;
    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public void removeBackground() {

    }

    @Override
    public void draw(Graphics g){
        if (!enable)return;
        Graphics2D graphics2D = (Graphics2D) g;
        g.setColor(backgroundColor);
        g.fillRect(this.x, this.y, this.width, this.height);

            graphics2D.setColor(new Color(154, 151, 151, 188));
            graphics2D.setStroke(new BasicStroke(2f));
            //画左边的一个加号

            graphics2D.drawLine(this.x + 5, this.y + 25, this.x + 5 + 25, this.y + 25);
            graphics2D.drawLine(this.x + 5 + 25 / 2, this.y + 15, this.x + 5 + 25 / 2, this.y + 15 + 20);
            //画右边的一个加号
            graphics2D.drawLine(this.x + 5 + 300, this.y + 25, this.x + 30 + 300, this.y + 25);
            graphics2D.drawLine(this.x + 5 + 300 + 25 / 2, this.y + 15, this.x + 5 + 300 + 25 / 2, this.y + 15 + 20);
            //画一条横线
            graphics2D.drawLine(this.x, this.y + 50, this.x + Main.mainFrame.getWidth() - 70, this.y + 50);
            //画一条竖线
            graphics2D.drawLine(this.x + 300, this.y, this.x + 300, this.y + 550);


        if (click == 1) {

                file = new File(filePath);
                //绘制文件名
                graphics2D.setColor(Color.white);
                graphics2D.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                graphics2D.drawString(file.getName(), this.x + 40, this.y + 70);
                //绘制图标
                FileSystemView fsv = FileSystemView.getFileSystemView();
                ImageIcon imageIcon = (ImageIcon) fsv.getSystemIcon(file);
                fileicon = imageIcon.getImage();
                graphics2D.drawImage(fileicon, this.x + 20, this.y + 55, null);
                changeFile(g);
        }
        if(dclick == 1){
            x1 = 0 ;
            y1 = 0 ;
            //size = 0;
            printFile(g,filePath,0);
            //如果双击项目则展示树形结构，并且要使得右边的加号变为白色,没变白色就说明没有指明哪个项目
            changeWhite(g);
        }
        g.setColor(new Color(77,77,77));
        if (size > maxShow)g.fillRect(this.x+10+this.width-15,this.y+roller*this.height/size,10,this.height*maxShow/size);

    }

    @Override
    public void mouseClick(int x, int y, int key) {
        if (!enable) return;
        if (RectangleOperation.pointInRectangle(x, y, 0, 10, 30, 20 + 20)) {
            size = 0;
            JFileChooser jFileChooser = new JFileChooser(filePath);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = jFileChooser.showOpenDialog(jFileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                filePath = jFileChooser.getSelectedFile().getAbsolutePath();
            }
            click = 1;
            //draw(g);
        }
        if (change) {
            if (RectangleOperation.pointInRectangle(x, y, 305, 10, 30 + 305, 20 + 20)) {
                JFileChooser jFileChooser = new JFileChooser(filePath);
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = jFileChooser.showOpenDialog(jFileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filePath = jFileChooser.getSelectedFile().getAbsolutePath();
                }
            }
        }
        if(click == 1){
            if (RectangleOperation.pointInRectangle(x, y, 50, 10, 50+20, 40)){
                JFileChooser jFileChooser = new JFileChooser("C:\\");
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = jFileChooser.showOpenDialog(jFileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filePath = jFileChooser.getSelectedFile().getAbsolutePath();
                }
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
    public void mouseMove(int x,int y) {
        if (!enable)return;
    }

    @Override
    public void mouseDoubleClick(int x, int y, int key) throws IOException {
        if (!enable) return;
        if (RectangleOperation.pointInRectangle(x, y, 0, 50, 300,50+15 )){
            dclick = 1;

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
        if (!enable)return;
        /*if (wheel == 1){
            if (roller < size - maxShow){
                roller++;
            }
        }else if (wheel == -1){
            if (roller > 0)roller--;
        }*/
    }
    public void connect(FileListColumn fileListColumn,TextLabel textLabel,PathSelector pathSelector,TypeClassifier typeClassifier,NavigationBar navigationBar){
        this.fileListColumn = fileListColumn;
        this.textLabel = textLabel;
        this.pathSelector = pathSelector;
        this.typeClassifier = typeClassifier;
        this.navigationBar = navigationBar;
    }



    //生成树形结构的方法，递归调用printFile（）时，参数level为0
    public   void   printFile(Graphics g,String path, int lever){

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        File f = new File(path);
        for(int i=0;i<lever;i++){
            graphics2D.drawString("   ",this.x+300+40,this.y+70+y1);

        }
        graphics2D.drawString(f.getName(),this.x+40+300+lever,this.y+70+y1);
        //size = size + 15;
        y1  = y1 + 20;
        if(f.isFile()){
            return ;
        }else{
            String[] s=f.list();
            for(int i=0;i<s.length;i++){
                String path1;
                path1 = f.getPath()+File.separator+s[i];
                printFile(g,path1,lever+50);
            }
        }
        return ;
    }
   public void changeWhite(Graphics g){
        change = true;
       Graphics2D graphics2D = (Graphics2D) g;
       graphics2D.setColor(Color.white);
       graphics2D.drawLine(this.x + 5 + 300, this.y + 25, this.x + 30 + 300, this.y + 25);
       graphics2D.drawLine(this.x + 5 + 300 + 25 / 2, this.y + 15, this.x + 5 + 300 + 25 / 2, this.y + 15 + 20);
   }

   public void changeFile(Graphics g){
       Graphics2D graphics2D = (Graphics2D) g;
       graphics2D.setColor(Color.white);
       //画一个改变的箭头
       graphics2D.drawLine(this.x+30+20,this.y+25,this.x+50+20,this.y+25);
       graphics2D.drawLine(this.x+30+20,this.y+25,this.x+50+5,this.y+25-5);
       graphics2D.drawLine(this.x+30+20,this.y+25,this.x+50+5,this.y+25+5);
       graphics2D.drawLine(this.x+50+20,this.y+25,this.x+50+15,this.y+25+5);
       graphics2D.drawLine(this.x+50+20,this.y+25,this.x+50+15,this.y+25-5);
   }


}
