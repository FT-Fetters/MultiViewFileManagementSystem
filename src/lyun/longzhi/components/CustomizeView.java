package lyun.longzhi.components;

import lyun.longzhi.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CustomizeView implements Component{
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow;
    private boolean enable = true;
    private boolean border = true;
    private int borderWidth;
    private Color borderColor;

    private Color backgroundColor = new Color(57, 57, 57, 91);

    private int mouseClick;


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
    public void draw(Graphics g) {
        if (!enable)return;
        Graphics2D graphics2D = (Graphics2D) g;
        g.setColor(backgroundColor);
        g.fillRect(this.x,this.y,this.width,this.height);
        graphics2D.setColor(new Color(135, 133, 133, 92));
        graphics2D.setStroke(new BasicStroke(2f));
        //画一条横线
        graphics2D.drawLine( this.x,this.y+50, this.x+Main.mainFrame.getWidth() -370,this.y+50);
        //画一条竖线
        graphics2D.drawLine( this.x,this.y,this.x,this.y+550);

        //画右边的一个加号
        graphics2D.drawLine(this.x+5,this.y+25,this.x+30,this.y+25);
        graphics2D.drawLine(this.x+5+25/2,this.y+15,this.x+5+25/2,this.y+15+20);
    }

    @Override
    public void mouseClick(int x, int y,int key) {
        if (!enable)return;
        System.out.println("6666");
        
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {

    }

    @Override
    public void mouseMove(int x, int y) {

    }

    @Override
    public void mouseDoubleClick(int x, int y,int key) throws IOException {

    }

    @Override
    public void mousePress(int x, int y,int key) {

    }

    @Override
    public void mouseRelease(int x, int y,int key) {

    }

    @Override
    public void mouseWheelMoved(int wheel) {

    }
    public void connect(FileListColumn fileListColumn,TextLabel textLabel,PathSelector pathSelector,TypeClassifier typeClassifier,NavigationBar navigationBar){
        this.fileListColumn = fileListColumn;
        this.textLabel = textLabel;
        this.pathSelector = pathSelector;
        this.typeClassifier = typeClassifier;
        this.navigationBar = navigationBar;
    }
    //生成树形结构的方法，递归调用printFile（）时，参数level为0
    public static void printFile(String path, int lever){
        File f = new File(path);
        for(int i=0;i<lever;i++){
            System.out.print("   ");
        }
        System.out.println(f.getName());
        if(f.isFile()){
            return ;
        }else{
            String[] s=f.list();
            for(int i=0;i<s.length;i++){
                String path1;
                path1 = f.getPath()+File.separator+s[i];
                printFile(path1,lever+1);
            }
        }
        return ;
    }

}
