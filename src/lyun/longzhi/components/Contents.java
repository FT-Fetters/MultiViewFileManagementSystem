package lyun.longzhi.components;

import lyun.longzhi.utils.RectangleOperation;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Contents implements Component{
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow;
    private boolean enable = true;
    private Color backgroundColor = new Color(165, 163, 163, 17);

    private FileListColumn fileListColumn;
    private TextLabel textLabel;
    private PathSelector pathSelector;
    private TypeClassifier typeClassifier;
    private NavigationBar navigationBar;

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
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
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
    public void mouseClick(int x, int y) {
        if (!enable)return;
        System.out.println("6666");
        if(RectangleOperation.pointInRectangle(x,y,0,15,30,15+20)){
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
    public void mouseDoubleClick(int x, int y) throws IOException {

    }

    @Override
    public void mousePress(int x, int y) {

    }

    @Override
    public void mouseRelease(int x, int y) {

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
