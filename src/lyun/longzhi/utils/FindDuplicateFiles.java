package lyun.longzhi.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FindDuplicateFiles {
    /**
     * 用于搜索所有重复拷贝的文件
     * @param path 要搜索的目录
     * @return 返回所有重复的文件的图
     */
    public static Map<String, List<File>> search(String path){
        File folder = new File(path);
        if (folder.isDirectory()){
            Map<String, List<File>> res = new HashMap<>();
            List<File> files = new ArrayList<>();
            getFiles(folder,files);
            for (File file : files) {
                try {
                    String tmpMd5 = DigestUtils.md5Hex(new FileInputStream(file));
                    if (!res.containsKey(tmpMd5)) res.put(tmpMd5,new ArrayList<>());
                    res.get(tmpMd5).add(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }else return null;
    }

    private static void getFiles(File path,List<File> res){
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory())getFiles(file,res);
                else res.add(file);
        }
    }


}
