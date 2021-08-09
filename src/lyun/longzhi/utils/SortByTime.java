package lyun.longzhi.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 设计一个能获取指定目录下的所有文件,并且按照文件的修改时间分类的方法
 */
public class SortByTime {
    /**
     * 获取该目录下所有文件的时间分类图
     * @param path 要获取的目录
     * @return 分类图图
     */
   public static TreeMap<Integer,List<File>> sortByYear(String path) {
        File file = new File(path);
        File[] fs = file.listFiles();
        TreeMap<Integer, List<File>> treeMap = new TreeMap<Integer, List<File>>();
        List list = new ArrayList();
        for (File f : fs) {

            Date date = new Date(f.lastModified());
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            int year = ca.get(Calendar.YEAR);
            if (!treeMap.containsKey(year)) {
                list.add(file);
                treeMap.put(year, list);
            }
            if (treeMap.containsKey(year)) {
                Iterator it = treeMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    ArrayList value = (ArrayList) entry.getValue();
                    if (key.equals(year)) {
                        value.add(f);
                    }
                }
            }

        }
        return treeMap;
    }
    private static TreeMap<Integer, List<File>> getIntegerListTreeMap(String path) {
        File file = new File(path);
        File[] fs = file.listFiles();
        TreeMap<Integer, List<File>> treeMap = new TreeMap<Integer, List<File>>();
        List list = new ArrayList();
        for (File f : fs) {
            Date date = new Date(f.lastModified());
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            int month = ca.get(Calendar.MONTH) + 1;
            if (!treeMap.containsKey(month)) {
                list.add(file);
                treeMap.put(month, list);
            }
            if (treeMap.containsKey(month)) {
                Iterator it = treeMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    ArrayList value = (ArrayList) entry.getValue();
                    if (key.equals(month)) {
                        value.add(f);
                    }
                }
            }

        }
        return treeMap;
    }
    public static TreeMap<Integer, List<File>> sortByDay (String path){
        File file = new File(path);
        File[] fs = file.listFiles();
        TreeMap<Integer, List<File>> treeMap = new TreeMap<Integer, List<File>>();
        List list = new ArrayList();
        for (File f : fs) {
            Date date = new Date(f.lastModified());
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            int day = ca.get(Calendar.DAY_OF_MONTH);
            if (!treeMap.containsKey(day)) {
                list.add(file);
                treeMap.put(day, list);
            }
            if (treeMap.containsKey(day)) {
                Iterator it = treeMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    ArrayList value = (ArrayList) entry.getValue();
                    if (key.equals(day)) {
                        value.add(f);
                    }
                }
            }

        }
        return treeMap;
    }
}
