package lyun.longzhi.configure;

import java.awt.*;

public class LoadConfigure {
    private static boolean loaded = false;
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    public static boolean loading(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_HEIGHT = (int) screenSize.getHeight();
        SCREEN_WIDTH = (int) screenSize.getWidth();
        loaded = true;
        return true;
    }

    public static int getScreenWidth(){
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight(){
        return SCREEN_HEIGHT;
    }


}
