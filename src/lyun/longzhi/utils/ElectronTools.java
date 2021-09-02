package lyun.longzhi.utils;

import com.alibaba.fastjson.JSONObject;
import lyun.longzhi.Main;
import lyun.longzhi.components.FileListColumn;
import lyun.longzhi.components.PathSelector;
import lyun.longzhi.view.MainView;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ElectronTools {

    static boolean connected = false;

    static String signal;

    public static boolean connect() throws IOException {
        JSONObject res = urlConnect("http://ldqc.xyz:7555/appConnect");
        if (res != null && res.containsKey("signal")){
            signal = res.getString("signal");
            connected = true;
            Main.connectWeb = true;
            return true;
        }else{Main.connectWeb = false; return false;}
    }

    public static boolean shock(List<File> files,String path) throws IOException {
        if (signal == null || signal.equals(""))return false;
        String url = "http://ldqc.xyz:7555/shock?";
        url += "signal="+signal+"&";
        StringBuilder filesStr = new StringBuilder();
        for (int i = 0; i < files.size(); i++) {
            if (i==0) filesStr.append(files.get(i).getName());
            else filesStr.append("/").append(files.get(i).getName());
        }

        url += "files=" + string2Unicode(filesStr.toString()) + "&";
        url += "path=" + string2Unicode(path);
        JSONObject res = urlConnect(url);
        if (res != null){
            if (res.getString("command") != null && !res.getString("command").equals("")){
                res.put("res",handleCommand(res.getString("command").split(" ")));
            }
            return res.getBoolean("alive");
        }
        return false;
    }

    public static void disConnect(){
        String url = "http://ldqc.xyz:7555/appDisconnect?signal=" + signal;
        try {
            urlConnect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.connectWeb = false;
        signal = null;
    }

    private static JSONObject urlConnect(String urlStr) throws IOException {
        String str;
        JSONObject res = null;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

        connection.connect();

        try(InputStream is = connection.getInputStream()) {
            // 构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                res = JSONObject.parseObject(str);
            }
        } finally {
            // 关闭连接
            connection.disconnect();
        }
        return res;
    }

    /**
     * 字符串转换unicode
     * @param string 要转化的字符串
     * @return unicode string
     */
    public static String string2Unicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("/u").append(Integer.toHexString(c));
        }

        return unicode.toString();
    }

    private static JSONObject handleCommand(String[] cmd){
        PathSelector pathSelector = (PathSelector) Main.mainView.getComponentList().get(2);
        String path;
        JSONObject res = new JSONObject();
        switch (cmd[0]){
            case "bk":
                pathSelector.backOff();
                res.put("suc",true);
                break;
            case "fr":
                pathSelector.forward();
                res.put("suc",true);
                break;
            case "op":
                path = cmd[1].replace('"',' ');
                if (new File(path).isDirectory()){
                    pathSelector.openFolder(path,false);
                    res.put("suc",true);
                    res.put("path",pathSelector.getPath());
                }else {
                    res.put("suc",false);
                }
                break;
            case "del":
                path = cmd[1].replace('"',' ');
                if (new File(path).exists()){
                    boolean delete = new File(path).delete();
                    if (delete)res.put("suc",true);
                        else res.put("suc",false);
                }
                break;
            case "cp":
                File cpPath = new File(cmd[1].replace('"',' '));
                File toPath = new File(cmd[2].replace('"',' '));
                if (cpPath.exists() && toPath.exists()){
                    if (cpPath.isDirectory()){
                        if (toPath.isDirectory()){
                            try {
                                FileUtils.copyDirectory(cpPath,toPath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            res.put("suc",true);
                        }else res.put("suc",false);
                    }else{
                        if (!toPath.isDirectory()){
                            try {
                                FileUtils.copyFile(cpPath,toPath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            res.put("suc",true);
                        }else res.put("suc",false);
                    }
                }else res.put("suc",false);
                break;
            case "rn":
                path = cmd[1].replace('"',' ');
                String oldName = cmd[2].replace('"',' ');
                String newName = cmd[3].replace('"',' ');
                renameFile(path,oldName,newName);
                res.put("suc",true);
        }
        return res;
    }

    public static String getSignal(){
        return signal;
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static String renameFile(String path, String oldname, String newname) {
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldname);
            File newfile = new File(path + "/" + newname);
            //重命名文件不存在
            if (!oldfile.exists()) {
                return "not exists";
            }
            if (newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                return "already exists";
            else {
                oldfile.renameTo(newfile);
                return "success";
            }
        } else {
            return "same name";
        }
    }
}
