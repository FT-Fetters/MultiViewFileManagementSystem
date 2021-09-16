package lyun.longzhi.components;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lyun.longzhi.Frame.NewProjectFrame;
import lyun.longzhi.Main;
import lyun.longzhi.utils.ImageTools;
import lyun.longzhi.utils.RectangleOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
//import sun.awt.windows.WPathGraphics;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomizeView implements Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow = 360;
    private int roller = 0;//滚轮
    private boolean enable = true;
    private boolean border = true;
    private boolean change = false;
    private boolean isClick = false;
    private int borderWidth;
    private static int y1 = 0;
    private static int x1 = 0;
    private static int click = 0;
    private static int dclick = 0;
    private static int size = 0;
    private static int count = -1;
    private static final int MAXSHOW = 5;//最大显示
    private static int show = 0;//显示
    private File file;
    private String filePath = "C:\\";
    private Image fileicon;
    private Graphics g;
    private Color borderColor;
    private final HashMap<String, JSONObject> projectMap = new HashMap<>();//项目图
    private final List<File> fileList = new ArrayList<>();
    private final List<String> filesName = new ArrayList<>();
    private final List<Image> icons = new ArrayList<>();
    private final List<Integer> levels = new ArrayList<>();
    private final List<String> showTitle = new ArrayList<>();
    List<String> ListStr = new ArrayList<>();

    Image image1 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\xinjian.png");
    Image image2 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\dakai.png");
    Image image3 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\baocun.png");
    Image image4 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\fuzhi.png");
    Image image5 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\niantie.png");
    Image image6 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\jianqie.png");
    Image image7 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\charutupian.png");
    Image image8 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\charulianjie.png");
    Image image9 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\guanbi.png");


    private Color backgroundColor = new Color(57, 57, 57, 91);

    private int mouseClick;

    public static int mcount = 0;
    private FileListColumn fileListColumn;
    private TextLabel textLabel;
    private PathSelector pathSelector;
    private TypeClassifier typeClassifier;
    private NavigationBar navigationBar;


    public CustomizeView(int x, int y, int width, int height, int maxShow) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxShow = maxShow;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setBorder(Color color, int width) {
        this.borderWidth = width;
        this.borderColor = color;
    }

    @Override
    public void removeBorder() {
        this.border = false;
    }

    @Override
    public void setBackground(Color color) {

    }

    @Override
    public void removeBackground() {

    }

    @Override
    public void draw(Graphics g) {
        if (!enable) return;
        Graphics2D graphics2D = (Graphics2D) g;
        g.setColor(backgroundColor);
        g.fillRect(this.x, this.y, this.width, this.height);

        graphics2D.setColor(new Color(154, 151, 151, 188));
        graphics2D.setStroke(new BasicStroke(2f));
        //画左边的一个加号

        //graphics2D.drawLine(this.x + 5, this.y + 25, this.x + 5 + 25, this.y + 25);
        //graphics2D.drawLine(this.x + 5 + 25 / 2, this.y + 15, this.x + 5 + 25 / 2, this.y + 15 + 20);

        //画图片
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawImage(image1, this.x + 10, this.y + 5 + 10, null);
        graphics2D.drawImage(image2, this.x + 10, this.y + 5 + 50 + 20, null);
        graphics2D.drawImage(image3, this.x + 10, this.y + 5 + 100 + 40, null);
        graphics2D.drawImage(image4, this.x + 10, this.y + 5 + 150 + 60, null);
        graphics2D.drawImage(image5, this.x + 10, this.y + 5 + 200 + 80, null);
        graphics2D.drawImage(image6, this.x + 10, this.y + 5 + 250 + 100, null);
        graphics2D.drawImage(image7, this.x + 10, this.y + 5 + 300 + 120, null);
        graphics2D.drawImage(image8, this.x + 10, this.y + 5 + 350 + 140, null);
        //
        //画一个改变的箭头
        graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 20 + 25 + 10, this.y + 25);
        graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 5 + 25 + 10, this.y + 25 - 5);
        graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 5 + 25 + 10, this.y + 25 + 5);
        graphics2D.drawLine(this.x + 120, this.y + 0, this.x + 120, this.y + 50);

        graphics2D.drawLine(this.x + 1145 + 20 + 25 + 5, this.y + 25, this.x + 1125 + 20 + 25 + 5, this.y + 25);
        graphics2D.drawLine(this.x + 1170 + 20 + 5, this.y + 25, this.x + 1150 + 5 + 25 + 5 + 5, this.y + 25 + 5);
        graphics2D.drawLine(this.x + 1170 + 20 + 5, this.y + 25, this.x + 1150 + 5 + 25 + 5 + 5, this.y + 25 - 5);
        graphics2D.drawLine(this.x + 1160, this.y + 0, this.x + 1160, this.y + 48);
        graphics2D.drawLine(this.x + 1208, this.y + 0, this.x + 1208, this.y + 48);

        graphics2D.drawRect(this.x,this.y,this.x+this.width-27,this.y+this.height-100);

        //graphics2D.drawLine(this.x + 50 + 20, this.y + 25, this.x + 50 + 15, this.y + 25 + 5);
        //graphics2D.drawLine(this.x + 50 + 20, this.y + 25, this.x + 50 + 15, this.y + 25 - 5);

        //graphics2D.drawImage(image9, this.x + 270, this.y + 16, null);
        //画右边的一个加号
        //graphics2D.drawLine(this.x + 5 + 300, this.y + 25, this.x + 30 + 300, this.y + 25);
        //graphics2D.drawLine(this.x + 5 + 300 + 25 / 2, this.y + 15, this.x + 5 + 300 + 25 / 2, this.y + 15 + 20);
        //画一条横线
        graphics2D.drawLine(this.x + 70, this.y + 50, this.x + Main.mainFrame.getWidth() - 72, this.y + 50);
        //画一条竖线
        graphics2D.drawLine(this.x + 70, this.y, this.x + 70, this.y + 550);
        if (ListStr.size() > MAXSHOW) {
            show = ListStr.size() - MAXSHOW;
            for (String Str : showTitle) {
                if (!ListStr.contains(Str)) {
                    ListStr.add(Str);
                }
            }
            for (int i = show; i < Math.max(MAXSHOW, ListStr.size()); i++) {
                graphics2D.drawString(ListStr.get(i), (i + 5 - ListStr.size()) * 228 + this.x + 100, this.y + 30);
                graphics2D.drawImage(image9, (i + 5 - ListStr.size()) * 228 + this.x + 270, this.y + 16, null);
                graphics2D.drawString(projectMap.toString(), this.x + 75, this.y + 100);
            }
        } else if (ListStr.size() <= MAXSHOW) {
            for (String Str : showTitle) {
                if (!ListStr.contains(Str)) {
                    ListStr.add(Str);
                }
            }
            for (int i = show; i < Math.min(MAXSHOW, ListStr.size()); i++) {
                graphics2D.drawString(ListStr.get(i), i * 228 + this.x + 100, this.y + 30);
                graphics2D.drawImage(image9, i * 228 + this.x + 270, this.y + 16, null);
                graphics2D.drawString(projectMap.toString(), this.x + 75, this.y + 100);
            }
        }

        if (dclick == 1) {
            x1 = 0;
            y1 = 0;
            //size = 0;
            printFile(g, filePath, 0);
            //如果双击项目则展示树形结构，并且要使得右边的加号变为白色,没变白色就说明没有指明哪个项目
            changeWhite(g);
        }
        g.setColor(new Color(77, 77, 77));
        if (size > maxShow)
            g.fillRect(this.x + 10 + this.width - 15, this.y + roller * this.height / size, 10, this.height * maxShow / size);

    }

    @Override
    public void mouseClick(int x, int y, int key) {
        if (!enable) return;
        if (RectangleOperation.pointInRectangle(x, y, 5, 0, 65, 65)) {
            newProject();
            for (String projectName : projectMap.keySet()) {
                showTitle.add(projectName);
            }
        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 65, 65, 130)) {
            loadProject();
            for (String projectName : projectMap.keySet()) {
                showTitle.add(projectName);
            }
        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 130, 65, 195)) {
            System.out.println(3);

        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 195, 65, 260)) {
            System.out.println(4);


        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 260, 65, 325)) {
            System.out.println(5);

        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 325, 65, 410)) {

            System.out.println(666);
        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 410, 65, 485)) {
            System.out.println(999);

        }
        if (RectangleOperation.pointInRectangle(x, y, 5, 485, 65, 550)) {
            System.out.println(1000);

        }
        if (change) {
            if (RectangleOperation.pointInRectangle(x, y, 305, 10, 30 + 305, 20 + 20)) {
                JFileChooser jFileChooser = new JFileChooser(filePath);
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = jFileChooser.showOpenDialog(jFileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filePath = jFileChooser.getSelectedFile().getAbsolutePath();
                }
            }
        }
        if (click == 1) {
            if (RectangleOperation.pointInRectangle(x, y, 50, 10, 50 + 20, 40)) {
                JFileChooser jFileChooser = new JFileChooser("C:\\");
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = jFileChooser.showOpenDialog(jFileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filePath = jFileChooser.getSelectedFile().getAbsolutePath();
                }
            }
        }
    }


    @Override
    public void mouseEnter() {

    }

    @Override
    public void mouseLeave() {

    }

    @Override
    public void mouseMove(int x, int y) {
        if (!enable) return;
    }

    @Override
    public void mouseDoubleClick(int x, int y, int key) throws IOException {
        if (!enable) return;
        if (RectangleOperation.pointInRectangle(x, y, 0, 50, 300, 50 + 15)) {
            dclick = 1;

        }
    }

    @Override
    public void mousePress(int x, int y, int key) {

    }

    @Override
    public void mouseRelease(int x, int y, int key) {

    }


    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable) return;
        /*if (wheel == 1){
            if (roller < size - maxShow){
                roller++;
            }
        }else if (wheel == -1){
            if (roller > 0)roller--;
        }*/
    }

    @Override
    public void mouseDrag(int x, int y, int key) {

    }

    public void connect(FileListColumn fileListColumn, TextLabel textLabel, PathSelector pathSelector, TypeClassifier typeClassifier, NavigationBar navigationBar) {
        this.fileListColumn = fileListColumn;
        this.textLabel = textLabel;
        this.pathSelector = pathSelector;
        this.typeClassifier = typeClassifier;
        this.navigationBar = navigationBar;
    }


    //生成树形结构的方法，递归调用printFile（）时，参数level为0
    public void printFile(Graphics g, String path, int lever) {

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.white);
        graphics2D.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        File f = new File(path);
        for (int i = 0; i < lever; i++) {
            graphics2D.drawString("   ", this.x + 300 + 40, this.y + 70 + y1);

        }
        graphics2D.drawString(f.getName(), this.x + 40 + 300 + lever, this.y + 70 + y1);
        //size = size + 15;
        y1 = y1 + 20;
        if (f.isFile()) {
        } else {
            String[] s = f.list();
            for (int i = 0; i < s.length; i++) {
                String path1;
                path1 = f.getPath() + File.separator + s[i];
                printFile(g, path1, lever + 50);
            }
        }
    }

    public void changeWhite(Graphics g) {
        change = true;
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.white);
        graphics2D.drawLine(this.x + 5 + 300, this.y + 25, this.x + 30 + 300, this.y + 25);
        graphics2D.drawLine(this.x + 5 + 300 + 25 / 2, this.y + 15, this.x + 5 + 300 + 25 / 2, this.y + 15 + 20);
    }

    public void changeFile(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.white);

    }

    public List<String> getFilesName() {
        return filesName;
    }

    /**
     * 新建项目
     */
    private static void newProject() {
        NewProjectFrame frame = new NewProjectFrame(Main.mainFrame);
        String projectName = frame.getProjectName();
        JSONObject filesJson = new JSONObject();
        filesJson.put("files", new JSONArray());
        File dataFile = new File(System.getProperty("user.dir") + File.separator + "data.udp");
        if (!dataFile.exists()) {
            try {
                if (!dataFile.createNewFile()) return;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        StringBuilder tmp = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
            String tmpStr;
            while ((tmpStr = bufferedReader.readLine()) != null) {
                tmp.append(tmpStr);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject data = JSONObject.parseObject(tmp.toString());
        data.put(projectName, filesJson);

        try {
            FileOutputStream outputStream = new FileOutputStream(dataFile);
            outputStream.write(data.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载或添加项目
     */
    private void loadProject() {
        File dataFile = new File(System.getProperty("user.dir") + File.separator + "data.udp");
        if (dataFile.exists()) {
            StringBuilder tmp = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
                String tmpStr;
                while ((tmpStr = bufferedReader.readLine()) != null) {
                    tmp.append(tmpStr);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject data = JSONObject.parseObject(tmp.toString());
            for (String key : data.keySet()) {
                projectMap.put(key, data.getJSONObject(key));
            }
        } else {
            //文件不存在
        }
    }


    /**
     * 切换项目
     *
     * @param projectName 项目名称
     * @return 如果项目已导入并切换成功则返回true, 否则返回false
     */
    private boolean switchProject(String projectName) {
        if (projectMap.containsKey(projectName)) {
            JSONObject project = projectMap.get(projectName);
            JSONArray files = project.getJSONArray("files");
            for (Object o : files) {
                File tmpFile = new File((String) o);
                if (tmpFile.exists()) {
                    fileList.add(tmpFile);
                    icons.add((Image) ImageTools.getSmallIcon(tmpFile));
                }
            }
            return true;
        } else return false;
    }


    /**
     * 添加文件到指定项目的目录下
     *
     * @param projectName 项目名称
     * @param file        文件
     * @return 如果项目存在且添加陈工则返回true, 否则返回false, 如果路径错误也会返回false
     */
    private boolean addFileToProject(String projectName, File file) {
        if (projectMap.containsKey(projectName)) {
            JSONObject project = projectMap.get(projectName);
            JSONArray files = project.getJSONArray("files");
            files.add(file.getAbsolutePath());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从项目中删除指定的文件
     *
     * @param projectName 项目名称
     * @param fileName    文件名称
     * @return 从指定的项目中删除指定路径下的文件, 如果文件不存在则返回false, 如果删除成功则返回true
     */
    private boolean rmFileOfProject(String projectName, String fileName) {
        if (projectMap.containsKey(projectName)) {
            JSONObject project = projectMap.get(projectName);
            JSONArray targetDir = project.getJSONArray("files");
            targetDir.remove(fileName);
            return true;
        } else return false;
    }

    //复制项目文件
    private boolean copyFile(File file, String filePath, String fileName) {
        File f = new File(filePath, fileName);
        try {
            FileUtils.copyFile(file, f);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //移动项目文件
    private boolean moveFile(File file, String filePath, String fileName) {
        File f = new File(filePath, fileName);
        try {
            FileUtils.moveFile(file, f);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
