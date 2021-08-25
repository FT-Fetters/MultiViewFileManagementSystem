package lyun.longzhi.utils;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class MessageBox {
    private static final Queue<String> messageQueue = new LinkedList<>();

    private static int timer = 15;

    private static final Font font = new Font("微软雅黑",Font.PLAIN,18);

    public static void addMessage(String msg){
        messageQueue.add(msg);
    }

    public static void messageHandle(){
        if (messageQueue.isEmpty())return;
        if (timer > 0){
            timer--;
        }else {
            messageQueue.poll();
            timer = 15;
        }
    }

    public static void drawMessage(Graphics g,int width,int height){
        Graphics2D g2d = (Graphics2D) g;
        //增加抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        float alpha = 1;
        if (timer <= 10)alpha = (float)timer/10.0f;
        Color color = new Color(0.08f,0.08f,0.08f, alpha);
        g2d.setColor(color);
        if (messageQueue.isEmpty())return;
        String msg = messageQueue.peek();
        g2d.fillRoundRect(
                width/2 - FontUtils.getWordWidth(font,msg)/2 - 20,
                height - FontUtils.getWordHeight(font) - 40,
                FontUtils.getWordWidth(font,msg) + 40,
                30,
                8,8
        );
        color = new Color(1,1,1,alpha);
        g2d.setColor(color);
        g2d.drawString(msg,
                width/2 - FontUtils.getWordWidth(font,msg)/2,
                height - FontUtils.getWordHeight(font)/5 - 40
                );
    }
}
