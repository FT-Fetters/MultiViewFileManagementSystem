package lyun.longzhi.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DetermineFileType {
    public static final int IMAGE = 0;
    public static final int VIDEO = 1;
    public static final int OTHER = 2;

    private static final List<String> imageSuffix = Arrays.asList("xbm","tif","pjp","svgz","jpg","jpeg","ico","tiff","gif","svg","jfif","webp","png","bmp","pjpeg","avif");
    private static final List<String> videoSuffix = Arrays.asList(
            "avi","flv","mpg","mpeg","mpe","m1v","m2v","mpv2","mp2v","dat","ts","tp","tpr","pva","pss","mp4","m4v",
            "m4p","m4b","3gp","3gpp","3g2","3gp2","ogg","mov","qt","amr","rm","ram","rmvb","rpm");

    public static int getType(File file){
        String filename = file.getName().toLowerCase();
        String[] tmp = filename.split("\\.");
        if (tmp.length == 1)return OTHER;
        String suffix = tmp[1];
        if (imageSuffix.contains(suffix)) return IMAGE;
        else if (videoSuffix.contains(suffix)) return VIDEO;
        else return OTHER;

    }
}
