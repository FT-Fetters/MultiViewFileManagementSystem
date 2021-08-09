package lyun.longzhi.utils;

import sun.font.FontDesignMetrics;

import java.awt.*;

public class FontUtils {
    /**
     * 计算文字宽度
     * @param font 字体
     * @param content 文字
     * @return 字体宽度
     */
    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    /**
     * 计算文字高度
     * @param font 字体
     * @return 高度
     */
    public static int getWordHeight(Font font){
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.getHeight();
    }
}
