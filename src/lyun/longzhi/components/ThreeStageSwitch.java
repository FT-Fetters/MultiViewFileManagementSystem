package lyun.longzhi.components;

import lyun.longzhi.Main;

import java.awt.*;

public class ThreeStageSwitch implements Component{
    private final int width = 144,height = 24;
    private int x,y;
    private int checked = 0;
    private int slideRatio = 0;

    private boolean enable;
    private boolean slide = false;

    private Color borderColor = new Color(170,170,170);

    private TimeAxis timeAxis;

    public ThreeStageSwitch(int x,int y){
        this.x = x;
        this.y = y;
    }


    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;

    }

    /**
     * 固定大小不可修改
     * @param width 组件宽度
     * @param height 组件高度
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     *
     * @return 宽度
     */
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
        //边框
        g2d.setColor(borderColor);
        g2d.drawRect(this.x,this.y,this.width,this.height);
        //checked
        g2d.setColor(new Color(65,65,65));
        if (slide){
            g2d.fillRect(this.x + 48*checked + slideRatio,this.y + 1,48-2,this.height-2);
        }else g2d.fillRect(this.x + 1 + 48*checked,this.y + 1,48 -2,this.height - 2);
        //文字
        String[] data = new String[]{"年份","月份","日期"};
        g2d.setColor(Color.white);
        g2d.setFont(new Font("微软雅黑",Font.PLAIN,16));
        for (int i = 0; i < 3; i++)
            g2d.drawString(data[i], this.x + 48 *i + 8,this.y + this.height - 6);
    }

    @Override
    public void mouseClick(int x, int y,int key) {
        if (!enable)return;
        int check = x/48;
        if (!slide)slideTo(check);
        if (timeAxis != null){timeAxis.setClaType(check);}
    }

    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {
        if (!enable)return;
        Main.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseMove(int x, int y) {
        if (!enable)return;
        Main.mainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseDoubleClick(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mousePress(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mouseRelease(int x, int y,int key) {
        if (!enable)return;
    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable)return;
    }

    //滑动动画
    private void slideTo(int to){
        new Thread(() -> {
            try {
                this.slide =true;
                this.slideRatio = 0;
                int abs = Math.abs(checked - to);
                int ans = 0;
                if (abs == 2){
                    for (int i = 0; i < 24; i++) {
                        Thread.sleep((32-i)/4);
                        ans += 2;
                        if (checked - to > 0)slideRatio=-ans;
                        else slideRatio = ans;
                    }
                    for (int i = 24;i > 0;i--){
                        Thread.sleep((32-i)/4);
                        ans += 2;
                        if (checked - to > 0)slideRatio=-ans;
                        else slideRatio = ans;
                    }
                }else if (abs == 1){
                    for (int i = 0; i < 24; i++) {
                        Thread.sleep((32-i)/4);
                        ans += 1;
                        if (checked - to > 0)slideRatio=-ans;
                        else slideRatio = ans;
                    }
                    for (int i = 24;i > 0;i--){
                        Thread.sleep((32-i)/4);
                        ans += 1;
                        if (checked - to > 0)slideRatio=-ans;
                        else slideRatio = ans;
                    }
                }
                slide = false;
                checked = to;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void connect(TimeAxis timeAxis){
        this.timeAxis = timeAxis;
    }
}
