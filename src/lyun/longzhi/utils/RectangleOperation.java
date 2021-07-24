package lyun.longzhi.utils;

public class RectangleOperation {
    /**
     * 判断点是否在某个矩形内
     * @param x 点的x坐标
     * @param y 点的y坐标
     * @param x1 矩形左上x
     * @param y1 矩形左上y
     * @param x2 矩形右下x
     * @param y2 矩形右下y
     * @return 如果点在矩形内则返回true,不在则返回false
     */
    public static boolean pointInRectangle(int x,int y,int x1,int y1,int x2,int y2){
        if (x2 < x1) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y2 < y1){
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        return x > x1 && x < x2 && y > y1 && y < y2;
    }
}
