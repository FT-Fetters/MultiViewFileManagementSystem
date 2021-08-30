package lyun.longzhi.components;

import lyun.longzhi.utils.RectangleOperation;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

public  class Contents implements Component{
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow;
    private boolean enable = true;
    private boolean border = true;
    private Color backgroundColor = new Color(165, 163, 163, 17);
    private int borderWidth;
    private Color borderColor;
    private FileListColumn fileListColumn;
    private TextLabel textLabel;
    private PathSelector pathSelector;
    private TypeClassifier typeClassifier;
    private NavigationBar navigationBar;
    private ScrollPane scrollPane=null;
    private MouseWheelListener sysWheel;
    public class event extends MouseAdapter{
        public void mouseWheelMove(MouseWheelEvent e){
            scrollPane.addMouseWheelListener(sysWheel);
            sysWheel.mouseWheelMoved(e);
            scrollPane.removeMouseWheelListener(sysWheel);
        }

    }


    public Contents( int x, int y, int width, int height, int maxShow){
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
        //graphics2D.setColor(new Color(97, 94, 94, 188));
        graphics2D.setStroke(new BasicStroke(2f));
        //画一条横线
        graphics2D.drawLine(this.x,this.y+50,this.x+300,this.y+50);

        //画左边的一个加号

        graphics2D.setColor(new Color(97, 94, 94, 188));
        graphics2D.drawLine(this.x+5,this.y+25,this.x+5+25,this.y+25);
        graphics2D.drawLine(this.x+5+25/2,this.y+15,this.x+5+25/2,this.y+15+20);


    }

    @Override
    public void mouseClick(int x, int y,int key) {
        if (!enable)return;
        if(RectangleOperation.pointInRectangle(x,y,0,10,30,20+20)){
            TextEdit note=new TextEdit();
            note.launchFrame();
            System.out.println("999");
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
}
