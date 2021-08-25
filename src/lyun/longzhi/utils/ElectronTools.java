package lyun.longzhi.utils;

import com.alibaba.fastjson.JSONObject;
import lyun.longzhi.Main;

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
            System.out.println(res.getString("commands"));
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

    public static String getSignal(){
        return signal;
    }

}
