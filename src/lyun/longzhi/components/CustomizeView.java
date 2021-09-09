package lyun.longzhi.components;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.imageio.plugins.common.ImageUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lyun.longzhi.Main;
import lyun.longzhi.utils.ImageTools;
import lyun.longzhi.utils.RectangleOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

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
    private int borderWidth;
    private static int y1 = 0;
    private static int x1 = 0;
    private static int click = 0;
    private static int dclick = 0;
    private static int size = 0;
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

    Image image1 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\xinjian.png");
    Image image2 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\dakai.png");
    Image image3 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\baocun.png");
    Image image4 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\fuzhi.png");
    Image image5 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\niantie.png");
    Image image6 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\jianqie.png");
    Image image7 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\charutupian.png");
    Image image8 = Toolkit.getDefaultToolkit().getImage("D:\\app\\MultiViewFileManagementSystem\\src\\lyun\\longzhi\\images\\charulianjie.png");


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
        //画右边的一个加号
        //graphics2D.drawLine(this.x + 5 + 300, this.y + 25, this.x + 30 + 300, this.y + 25);
        //graphics2D.drawLine(this.x + 5 + 300 + 25 / 2, this.y + 15, this.x + 5 + 300 + 25 / 2, this.y + 15 + 20);
        //画一条横线
        graphics2D.drawLine(this.x+70, this.y + 50, this.x + Main.mainFrame.getWidth() - 72, this.y + 50);
        //画一条竖线
        graphics2D.drawLine(this.x + 70, this.y, this.x + 70, this.y + 550);


        if (click == 1) {

            file = new File(filePath);
            //绘制文件名
            graphics2D.setColor(Color.white);
            graphics2D.setFont(new Font("微软雅黑", Font.PLAIN, 15));
            graphics2D.drawString(file.getName(), this.x + 40, this.y + 70);
            //绘制图标
            FileSystemView fsv = FileSystemView.getFileSystemView();
            ImageIcon imageIcon = (ImageIcon) fsv.getSystemIcon(file);
            fileicon = imageIcon.getImage();
            graphics2D.drawImage(fileicon, this.x + 20, this.y + 55, null);
            changeFile(g);
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
        if (RectangleOperation.pointInRectangle(x, y, 0, 10, 30, 20 + 20)) {
            size = 0;
            JFileChooser jFileChooser = new JFileChooser(filePath);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = jFileChooser.showOpenDialog(jFileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                filePath = jFileChooser.getSelectedFile().getAbsolutePath();
            }
            click = 1;
            //draw(g);
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
        //画一个改变的箭头
        graphics2D.drawLine(this.x + 30 + 20, this.y + 25, this.x + 50 + 20, this.y + 25);
        graphics2D.drawLine(this.x + 30 + 20, this.y + 25, this.x + 50 + 5, this.y + 25 - 5);
        graphics2D.drawLine(this.x + 30 + 20, this.y + 25, this.x + 50 + 5, this.y + 25 + 5);
        graphics2D.drawLine(this.x + 50 + 20, this.y + 25, this.x + 50 + 15, this.y + 25 + 5);
        graphics2D.drawLine(this.x + 50 + 20, this.y + 25, this.x + 50 + 15, this.y + 25 - 5);
    }

    /**
     * 新建项目
     */
    private static void newProject() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "项目文件(*.udp)", "udp");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = jFileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File projectFile = jFileChooser.getSelectedFile();
            String fileName = jFileChooser.getName(projectFile);
            if (!fileName.endsWith(".udp")) {
                projectFile = new File(jFileChooser.getCurrentDirectory(), projectFile.getName() + ".udp");
            }
            JSONObject initData = new JSONObject();
            initData.put("projectName", fileName);
            initData.put("files", new JSONArray());
            initData.put("path", projectFile.getParent().replace("\\", "/"));
            String tmp = initData.toJSONString();
            try {
                FileOutputStream outputStream = new FileOutputStream(projectFile);
                outputStream.write(tmp.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载或添加项目
     */
    private void loadProject() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "项目文件(*.udp)", "udp");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = jFileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File projectFile = jFileChooser.getSelectedFile();
            if (projectFile.exists()) {
                StringBuilder tmp = new StringBuilder();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(projectFile));
                    String tmpStr;
                    while ((tmpStr = bufferedReader.readLine()) != null) {
                        tmp.append(tmpStr);
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject data = JSONObject.parseObject(tmp.toString());
                if (data.containsKey("projectName")) {
                    projectMap.put(data.getString("projectName"), data);
                }
            } else {
                //文件不存在
            }
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
            fileDFS(0, files, project.getString("path"));
            return true;
        } else return false;
    }

    /**
     * 深度搜索json获取所有的文件并添加到相应的list中
     *
     * @param level 层级
     * @param files 文件表
     * @param path  项目路径
     */
    private void fileDFS(int level, JSONArray files, String path) {
        for (Object o : files) {
            JSONObject file = (JSONObject) o;
            if (file.getString("type").equals("file")) {
                fileList.add(new File(path + "/" + file.getString("md5") + "." + file.getString("suffix")));
                filesName.add(file.getString("name") + "." + file.getString("suffix"));
                levels.add(level);
            } else if (file.getString("type").equals("dir")) {
                fileDFS(level + 1, file.getJSONArray("contain"), path);
            }
        }
    }

    /**
     * 添加文件到指定项目的目录下
     *
     * @param projectName 项目名称
     * @param file        文件
     * @param path        路径
     * @return 如果项目存在且添加陈工则返回true, 否则返回false, 如果路径错误也会返回false
     */
    private boolean addFileToProject(String projectName, File file, String[] path) throws IOException {
        if (projectMap.containsKey(projectName)) {
            JSONObject project = projectMap.get(projectName);
            String suffix = file.getName().split("\\.")[file.getName().split("\\.").length - 1];
            String md5 = DigestUtils.md5Hex(file.getName() + new Random().nextInt(999));
            FileUtils.copyFile(file, new File(project.getString("path") + "/files/" + md5 + "." + suffix));
            JSONArray targetDir = project.getJSONArray("files");
            for (String p : path) {
                for (Object o : targetDir) {
                    JSONObject tmpFile = (JSONObject) o;
                    if (tmpFile.getString("type").equals("dir") && tmpFile.getString("name").equals(p)) {
                        targetDir = tmpFile.getJSONArray("contain");
                        break;
                    }
                }
            }
            JSONObject targetFile = new JSONObject();
            targetFile.put("name", file.getName().split("\\.")[0]);
            targetFile.put("type", "file");
            targetFile.put("md5", md5);
            targetFile.put("suffix", suffix);
            targetDir.add(targetFile);
            FileOutputStream outputStream = new FileOutputStream(new File(project.getString("path") + "/" + project.getString("projectName") + ".udp"));
            outputStream.write(project.toJSONString().getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从项目中删除指定的文件
     *
     * @param projectName 项目名称
     * @param path        要删除的文件再项目中的路径
     * @param fileName    文件名称
     * @return 从指定的项目中删除指定路径下的文件, 如果文件不存在则返回false, 如果删除成功则返回true
     */
    private boolean rmFileOfProject(String projectName, String[] path, String fileName) {
        if (projectMap.containsKey(projectName)) {
            JSONObject project = projectMap.get(projectName);
            JSONArray targetDir = project.getJSONArray("files");
            for (String p : path) {
                for (Object o : targetDir) {
                    JSONObject tmpFile = (JSONObject) o;
                    if (tmpFile.getString("type").equals("dir") && tmpFile.getString("name").equals(p)) {
                        targetDir = tmpFile.getJSONArray("contain");
                        break;
                    }
                }
            }
            for (int i = 0; i < targetDir.size(); i++) {
                JSONObject tmpFile = (JSONObject) targetDir.get(i);
                if (tmpFile.getString("name").equals(fileName) && tmpFile.getString("type").equals("file")) {
                    targetDir.remove(i);
                    return true;
                }
            }
            return false;
        } else return false;
    }


}
