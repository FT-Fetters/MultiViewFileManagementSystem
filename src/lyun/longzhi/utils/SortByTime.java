package lyun.longzhi.utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * 设计一个能获取指定目录下的所有文件,并且按照文件的修改时间分类的方法
 */
public class SortByTime {
    /**
     * 获取该目录下所有文件的年份分类
     * @param path 要获取的目录
     * @return 分类树
     */
   public static TreeMap<String,List<Map.Entry<File, Image>>> sortByYear(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        TreeMap<String, List<Map.Entry<File, Image>>> result = new TreeMap<>((o1, o2) -> {
            int t1 = Integer.parseInt(o1.replace("年",""));
            int t2 = Integer.parseInt(o2.replace("年",""));
            return t1 - t2;
        });
       for (File cur : files != null ? files : new File[0]) {
           long lastModified = cur.lastModified();
           Date date = new Date(lastModified);
           /*
           dates[0] week
           dates[1] month
           dates[2] day
           dates[3] clock
           dates[4] CST
           dates[5] year
           */
           String[] dates = String.valueOf(date).split(" ");
           String time = dates[5] + "年";
           addToMap(result, cur, time);
       }
        return result;
    }

    /**
     * 获取该目录下所有文件的月份分类
     * @param path 要获取的目录
     * @return 分类树
     */
    public static TreeMap<String,List<Map.Entry<File, Image>>> sortByMonth(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        TreeMap<String, List<Map.Entry<File, Image>>> result = new TreeMap<>((o1, o2) -> {
            int year1 = Integer.parseInt(o1.split("年")[0]);
            int year2 = Integer.parseInt(o2.split("年")[0]);
            int month1 = Integer.parseInt(o1.split("年")[1].replace("月",""));
            int month2 = Integer.parseInt(o2.split("年")[1].replace("月",""));
            if (year1 == year2){
                return month1 - month2;
            }else {
                return year1 - year2;
            }
        });
        for (File cur : files != null ? files : new File[0]) {
            long lastModified = cur.lastModified();
            Date date = new Date(lastModified);
           /*
           dates[0] week
           dates[1] month
           dates[2] day
           dates[3] clock
           dates[4] CST
           dates[5] year
           */
            String[] dates = String.valueOf(date).split(" ");
            String time = dates[5] + "年" + monthToNum(dates[1]) + "月";
            addToMap(result, cur, time);
        }
        return result;
    }

    /**
     * 获取该目录下所有文件的月份分类
     * @param path 要获取的目录
     * @return 分类树
     */
    public static TreeMap<String,List<Map.Entry<File, Image>>> sortByDay(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        TreeMap<String, List<Map.Entry<File, Image>>> result = new TreeMap<>((o1, o2) -> {
            int year1 = Integer.parseInt(o1.split("年")[0]);
            int year2 = Integer.parseInt(o2.split("年")[0]);
            int month1 = Integer.parseInt(o1.split("年")[1].split("月")[0]);
            int month2 = Integer.parseInt(o2.split("年")[1].split("月")[0]);
            int day1 = Integer.parseInt(o1.split("月")[1].replace("日",""));
            int day2 = Integer.parseInt(o2.split("月")[1].replace("日",""));
            if (year1 == year2){
                if (month1 == month2){
                    return day1 - day2;
                }else return month1 - month2;
            }else {
                return year1 - year2;
            }
        });
        for (File cur : files != null ? files : new File[0]) {
            long lastModified = cur.lastModified();
            Date date = new Date(lastModified);
           /*
           dates[0] week
           dates[1] month
           dates[2] day
           dates[3] clock
           dates[4] CST
           dates[5] year
           */
            String[] dates = String.valueOf(date).split(" ");
            String time = dates[5] + "年" + monthToNum(dates[1]) + "月"+dates[2] + "日";
            addToMap(result, cur, time);
        }
        return result;
    }

    private static void addToMap(TreeMap<String, List<Map.Entry<File, Image>>> result, File cur, String time) {
        if (result.containsKey(time)) {
            Map.Entry<File,Image> tmpEntry = new AbstractMap.SimpleEntry<>(cur, ((ImageIcon) ImageTools.getSmallIcon(cur)).getImage());
            result.get(time).add(tmpEntry);
        } else {
            List<Map.Entry<File,Image>> tmp = new ArrayList<>();
            Map.Entry<File,Image> tmpEntry = new AbstractMap.SimpleEntry<>(cur, ((ImageIcon) ImageTools.getSmallIcon(cur)).getImage());
            tmp.add(tmpEntry);
            result.put(time, tmp);
        }
    }

    private static int monthToNum(String month){
        String[] months = {"Jua","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (int i = 0; i < months.length; i++) {
            if (month.equals(months[i]))return i+1;
        }
        return 0;
    }


}
