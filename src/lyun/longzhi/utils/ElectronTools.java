package lyun.longzhi.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ElectronTools {

    static boolean connected = false;

    static String signal;

    public static boolean connect() throws IOException {
        JSONObject res = urlConnect("http://127.0.0.1:8080/appConnect");
        if (res != null && res.containsKey("signal")){
            signal = res.getString("signal");
            connected = true;
            return true;
        }else return false;
    }

    public static boolean shock(List<File> files,String path) throws IOException {
        if (signal == null || signal.equals(""))return false;
        String url = "http://127.0.0.1:8080/shock?";
        url += "signal="+signal+"&";
        StringBuilder filesStr = new StringBuilder();
        for (int i = 0; i < files.size(); i++) {
            if (i==0) filesStr.append(files.get(i).getName());
            else filesStr.append("/").append(files.get(i).getName());
        }
        url += "files=" + filesStr + "&";
        url += "path=" + path;
        JSONObject res = urlConnect(url);
        if (res != null){
            return res.getBoolean("alive");
        }
        return false;
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

}
