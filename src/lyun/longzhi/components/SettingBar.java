package lyun.longzhi.components;

import lyun.longzhi.components.actions.BarAction;
import lyun.longzhi.utils.FontUtils;
import lyun.longzhi.utils.RectangleOperation;

import java.awt.*;
import java.io.IOException;

public class SettingBar implements Component{

    public static final int SWITCH = 0;
    public static final int BUTTON = 1;

    private int x,y,width,height;

    private int type = -1;

    private boolean enable = true;
    private boolean switchState = false;
    private boolean mouseIn = false;

    private String text;
    private String typeDescribe;

    private BarAction barAction;

    public SettingBar(int x,int y,int width,int height,int type,String text,String typeDescribe){
        this.x = x;
        this.y = y;
        this.width = Math.max(width, 300);
        this.height = height;
        if (type >= 0 && type <= 1){
            this.type =type;
        }
        this.text = text;
        this.typeDescribe = typeDescribe;
    }


    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void resize(int width, int height) {
        this.width = Math.max(width, 300);
        this.height = height;
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
        //background
        Color background = new Color(43,43,43);
        g2d.setColor(background);
        g2d.fillRoundRect(this.x,this.y,this.width,this.height,8,8);
        //draw text
        g2d.setColor(Color.white);
        Font font = new Font("微软雅黑",Font.PLAIN,18);
        g2d.setFont(font);
        g2d.drawString(text,this.x + 25,this.y + this.height/2 + FontUtils.getWordHeight(font)/3);
        //Functionality
        if (type == SWITCH){
            if (switchState){
                Color border = new Color(158,158,158);
                g2d.setColor(border);
                g2d.fillRoundRect(this.x + this.width - 25 - 48,this.y + this.height/2 - 15,48,24,25,30);
                g2d.setColor(Color.black);
                g2d.fillOval(this.x + this.width - 25 - 20,this.y + this.height/2 - 11,16,16);
            }else {
                Color border = new Color(158,158,158);
                g2d.setColor(border);
                g2d.drawRoundRect(this.x + this.width - 25 - 48,this.y + this.height/2 - 15,48,24,25,30);
                g2d.fillOval(this.x + this.width - 25 - 48 + 4,this.y + this.height/2 - 15 +4,16,16);
            }
        }else {
            Color button = new Color(55,55,55);
            g2d.setColor(button);
            g2d.fillRoundRect(this.x + this.width - this.width/15 - 25,this.y + this.height*2/7,this.width/15,this.height*2/5,8,8);
            g2d.setColor(Color.white);
            font = new Font("微软雅黑",Font.PLAIN,12);
            g2d.drawString(typeDescribe,
                    this.x + this.width - this.width/15 - 25 + (this.width/15 - FontUtils.getWordWidth(font,typeDescribe))*3/7,
                    this.y + this.height*2/7+FontUtils.getWordHeight(font) + this.height/12
                    );
        }
    }

    @Override
    public void mouseClick(int x, int y) {

    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {
        if (!enable)return;
        mouseIn = false;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (!enable)return;
        mouseIn = RectangleOperation.pointInRectangle(
                x, y,
                this.width - 25 - 48,
                this.height / 2 - 15,
                this.width - 25,
                this.height / 2 + 15
        );

    }

    @Override
    public void mouseDoubleClick(int x, int y) throws IOException {

    }

    @Override
    public void mousePress(int x, int y) {

    }

    @Override
    public void mouseRelease(int x, int y) {
        if (RectangleOperation.pointInRectangle(
                x, y,
                this.width - 25 - 48,
                this.height / 2 - 15,
                this.width - 25,
                this.height / 2 + 15
        )){
            switchState = !switchState;
            if (barAction!=null){
                if (type == SWITCH){
                    barAction.switchChane(switchState);
                }else {
                    barAction.buttonClick();
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(int wheel) {

    }

    public void setBarAction(BarAction barAction){
        this.barAction = barAction;
    }
}
