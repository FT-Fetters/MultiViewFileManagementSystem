package lyun.longzhi.components;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lyun.longzhi.Frame.NewProjectFrame;
import lyun.longzhi.Main;
import lyun.longzhi.utils.ImageTools;
import lyun.longzhi.utils.MessageBox;
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
    private boolean isload = false;
    private boolean isMoveTitle = false;
    private boolean isClickTitle = false;
    private boolean isClickFile = false;
    private boolean isSwitch = false;
    private int borderWidth;
    private static int y1 = 0;
    private static int x1 = 0;
    private static int click = 0;
    private static int dclick = 0;
    private static int size = 0;
    private static int count = 0;
    private static final int MAXSHOW = 5;//标题最大显示
    private static int show = 0;//显示标题
    private static int showMovenum = 0;//标题移动交互计数
    private static int showClicknum = 0;//标题点击交互计数
    private static int choose = -1;//选择文件
    private static int num = -1;
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
    // private final List<String> ListTitleStr = new ArrayList<>();//存放标题名称的列表
    //private final List<String> showTitle = new ArrayList<>();//展示标题名称

    //添加图片
    Image image1 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\xinjian.png");
    Image image2 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\dakai.png");
    Image image3 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\fuzhi.png");
    Image image4 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\niantie.png");
    Image image5 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\jianqie.png");
    Image image6 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\shanchu.png");
    Image image7 = Toolkit.getDefaultToolkit().getImage("src\\lyun\\longzhi\\images\\guanbi.png");


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
        this.loadProject();
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
        List<String> listTitleStr = new ArrayList<>(projectMap.keySet());
        //画功能块
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawImage(image1, this.x + 10, this.y + 5 + 10 + 15, null);
        graphics2D.drawImage(image2, this.x + 10, this.y + 5 + 50 + 20 + 20 + 15, null);
        graphics2D.drawImage(image3, this.x + 10, this.y + 5 + 100 + 40 + 40 + 15, null);
        graphics2D.drawImage(image4, this.x + 10, this.y + 5 + 150 + 60 + 60 + 15, null);
        graphics2D.drawImage(image5, this.x + 10, this.y + 5 + 200 + 80 + 80 + 15, null);
        graphics2D.drawImage(image6, this.x + 10, this.y + 5 + 250 + 100 + 100 + 15, null);

        //画一个鼠标移动到标题的交互效果
        if (listTitleStr.size() < MAXSHOW && isMoveTitle && isMoveTitle && showMovenum > 0 &&
                showMovenum <= MAXSHOW && showMovenum != 0 && showMovenum <= listTitleStr.size()) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(this.x + (showMovenum - 1) * 208 + 130, this.y + 45, 188, 2);
        } else if (listTitleStr.size() >= MAXSHOW && isMoveTitle && showMovenum > 0 &&
                showMovenum <= MAXSHOW && showMovenum != 0) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(this.x + (showMovenum - 1) * 208 + 120, this.y + 47, 208, 3);
        }

        //画一个点击交互效果
        if (isClickTitle && showClicknum > 0 && showClicknum <= 5 && showClicknum != -1) {
            graphics2D.setColor(Color.DARK_GRAY);
            graphics2D.fillRect(this.x + (showClicknum - 1) * 208 + 120, this.y, 208, 50);
        }

        //箭头交互，可以点击为白色，不可点击为灰色（左箭头）
        if (show == 0) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 20 + 25 + 10, this.y + 25);
            graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 5 + 25 + 10, this.y + 25 - 5);
            graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 5 + 25 + 10, this.y + 25 + 5);
        } else {
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 20 + 25 + 10, this.y + 25);
            graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 5 + 25 + 10, this.y + 25 - 5);
            graphics2D.drawLine(this.x + 30 + 20 + 25 + 10, this.y + 25, this.x + 50 + 5 + 25 + 10, this.y + 25 + 5);
        }

        //箭头交互，可以点击为白色，不可点击为灰色（右箭头）
        if (listTitleStr.size() > MAXSHOW && show < listTitleStr.size() - MAXSHOW && isload) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawLine(this.x + 1145 + 20 + 25 + 5, this.y + 25, this.x + 1125 + 20 + 25 + 5, this.y + 25);
            graphics2D.drawLine(this.x + 1170 + 20 + 5, this.y + 25, this.x + 1150 + 5 + 25 + 5 + 5, this.y + 25 + 5);
            graphics2D.drawLine(this.x + 1170 + 20 + 5, this.y + 25, this.x + 1150 + 5 + 25 + 5 + 5, this.y + 25 - 5);

        } else {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawLine(this.x + 1145 + 20 + 25 + 5, this.y + 25, this.x + 1125 + 20 + 25 + 5, this.y + 25);
            graphics2D.drawLine(this.x + 1170 + 20 + 5, this.y + 25, this.x + 1150 + 5 + 25 + 5 + 5, this.y + 25 + 5);
            graphics2D.drawLine(this.x + 1170 + 20 + 5, this.y + 25, this.x + 1150 + 5 + 25 + 5 + 5, this.y + 25 - 5);

        }

        //画一个箭头框和整个界面的外框
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(this.x + 120, this.y + 0, this.x + 120, this.y + 48);
        graphics2D.drawLine(this.x + 1160, this.y + 0, this.x + 1160, this.y + 48);
        graphics2D.drawRect(this.x, this.y, this.x + this.width - 27, this.y + this.height - 100);

        //画一条横线（分割标题块和内容块）
        graphics2D.drawLine(this.x + 70, this.y + 50, this.x + Main.mainFrame.getWidth() - 72, this.y + 50);

        //画一条竖线（分割功能块和标题内容块）
        graphics2D.drawLine(this.x + 70, this.y, this.x + 70, this.y + 550);

        //画项目标题块和内容块

        if (listTitleStr.size() > MAXSHOW) {
            show = listTitleStr.size() - MAXSHOW + count;

            for (String Str : listTitleStr) {
                if (!listTitleStr.contains(Str)) {
                    listTitleStr.add(Str);
                }
            }
            for (int i = show; i < show + 5; i++) {
                graphics2D.drawString(listTitleStr.get(i), (i + MAXSHOW - listTitleStr.size() - count) * 208 + this.x + 150, this.y + 30);
                graphics2D.drawImage(image7, (i + MAXSHOW - listTitleStr.size() - count) * 208 + this.x + 300, this.y + 16, null);
            }
        } else {
            show = 0;
            for (int i = 0; i < listTitleStr.size(); i++) {
                graphics2D.drawString(listTitleStr.get(i), i * 208 + this.x + 150, this.y + 30);
                graphics2D.drawImage(image7, i * 208 + this.x + 300, this.y + 16, null);
            }
        }//画选择文件
        if (isClickFile && num - roller >= 0 && num - roller <= 15 && fileList.size() != 0 && num - roller < fileList.size()) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRoundRect(this.x + 72 + 10, this.y + 60 + 30 * (num - roller), this.width - 95, 30, 3, 3);
        }
        if (choose != -1 && isClickTitle && fileList.size() != 0 && choose < fileList.size()) {
            if (choose != num - roller) {
                graphics2D.setColor(Color.DARK_GRAY);
                graphics2D.fillRoundRect(this.x + 72 + 10, this.y + 60 + 30 * choose, this.width - 95, 30, 3, 3);
            }

        }

        //画文件
        Font font = new Font("微软雅黑", Font.PLAIN, 15);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        if (fileList.size() != 0&&listTitleStr.size()!=0) {
            for (int i = roller; i < Math.min(fileList.size(), roller + maxShow); i++) {
                if (i < fileList.size()) {
                    graphics2D.drawString(fileList.get(i).getName(), this.x + 100 + 10 + 5, this.y + 80 + (i - roller) * 30);
                }
                if (i < icons.size()) {
                    graphics2D.drawImage(icons.get(i), this.x + 83 + 10, this.y + 65 + (i - roller) * 30, null);
                }
            }
        }

        //画滚动条
        if (fileList.size() > maxShow) {
            g.setColor(new Color(77, 77, 77));
            g.fillRect(this.x + 10 + this.width - 23, this.y + 52 + roller * this.height / fileList.size(), 10, this.height * maxShow / fileList.size() - 52);
        }


    }

    @Override
    public void mouseClick(int x, int y, int key) {
        if (!enable) return;
        //点击左边的箭头
        List<String> listTitleStr = new ArrayList<>(projectMap.keySet());
        if (RectangleOperation.pointInRectangle(x, y, 70, 0, 120, 50)) {
            if (show > 0 && listTitleStr.size() > MAXSHOW) {
                count--;
                showClicknum++;
            }
        }
        //点击右边的箭头
        if (RectangleOperation.pointInRectangle(x, y, 1160, 0, 1210, 50)) {
            if (show < listTitleStr.size() - MAXSHOW) {
                count++;

                showClicknum--;

            }
        }
        //点击标题
        if (listTitleStr.size() < MAXSHOW) {
            for (int i = 0; i < listTitleStr.size(); i++) {
                if (RectangleOperation.pointInRectangle(x, y, i * 208 + 120, 0, i * 208 + 328, 50)) {
                    isClickTitle = true;
                    showClicknum = i + 1;
                    if (i != showClicknum) {
                        switchProject(listTitleStr.get(i));
                    }
                    System.out.println(showClicknum);
                }
                if (RectangleOperation.pointInRectangle(x, y, i * 208 + 300, +16, i * 208 + 300 + 16, +16 + 16)) {
                    if (isClickTitle && showClicknum > i + 1) {
                        showClicknum--;
                        System.out.println(showClicknum);
                        break;

                    } else if (isClickTitle && showClicknum == i + 1) {
                        showClicknum = -1;
                        isClickTitle = false;
                        System.out.println(showClicknum);
                        break;

                    }
                }
            }

        } else {
            for (int i = 0; i < MAXSHOW; i++) {
                if (RectangleOperation.pointInRectangle(x, y, i * 208 + 120, 0, i * 208 + 328, 50)) {
                    isClickTitle = true;
                    showClicknum = i + 1;
                    if (i != showClicknum) {
                        switchProject(listTitleStr.get(i));
                    }
                    System.out.println(showClicknum);

                }
                if (RectangleOperation.pointInRectangle(x, y, i * 208 + 300, +16, i * 208 + 300 + 16, +16 + 16)) {
                    if (isClickTitle && showClicknum > i + 1) {
                        showClicknum--;
                        System.out.println(showClicknum);
                        break;
                    } else if (isClickTitle && showClicknum == i + 1) {
                        showClicknum = -1;
                        isClickTitle = false;
                        System.out.println(showClicknum);
                        break;
                    }
                }
            }
        }
        //点击文件
        for (int i = 0; i < 16; i++) {
            if (isClickTitle && fileList.size() != 0) {
                if (RectangleOperation.pointInRectangle(x, y, 70, i * 30 + 55, 1165, i * 30 + 85)) {
                    num = i + roller;
                    isClickFile = true;
                    break;
                }
            }

        }

        //点击标题中的删除符号
        if (listTitleStr.size() <= MAXSHOW) {
            for (int i = 0; i < listTitleStr.size(); i++) {
                if (RectangleOperation.pointInRectangle(x, y, i * 208 + 300, +16, i * 208 + 300 + 16, +16 + 16)) {
                    projectMap.remove(listTitleStr.get(i));
                    saveProject();
                    loadProject();
                    break;

                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                if (RectangleOperation.pointInRectangle(x, y, (i) * 208 + 300, +16, (i) * 208 + 300 + 16, +16 + 16)) {
                    projectMap.remove(listTitleStr.get(show + i));
                    saveProject();
                    loadProject();
                    show--;
                }
            }
        }

        //点击新建项目
        if (RectangleOperation.pointInRectangle(x, y, 5, 20, 65, 85)) {
            newProject();
            loadProject();
            isload = true;
        }
        //点击添加文件
        if (RectangleOperation.pointInRectangle(x, y, 5, 100, 65, 165)) {
            if (isClickTitle) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(null);
                File file = jFileChooser.getSelectedFile();
                if (file != null) {
                    addFileToProject(listTitleStr.get(showClicknum - 1), file);
                }
            }
            saveProject();
            loadProject();
            switchProject(listTitleStr.get(showClicknum - 1));

        }
        //点击复制项目文件
        if (RectangleOperation.pointInRectangle(x, y, 5, 195, 65, 250)) {
            if (isClickTitle && fileList.size() != 0) {
                copyFileFromProject(listTitleStr.get(showClicknum - 1), fileList.get(num).getAbsolutePath());
                saveProject();
                loadProject();
                switchProject(listTitleStr.get(showClicknum - 1));
            }

        }
        //点击粘贴项目文件
        if (RectangleOperation.pointInRectangle(x, y, 5, 285, 65, 340)) {
            if (isClickTitle && fileList.size() != 0) {
                pasteFileFromCopyFile(listTitleStr.get(showClicknum - 1));
                saveProject();
                loadProject();
                switchProject(listTitleStr.get(showClicknum - 1));
            }
        }
        //点击剪切项目文件
        if (RectangleOperation.pointInRectangle(x, y, 5, 370, 65, 435)) {
            if (isClickTitle && fileList.size() != 0) {
                shearFileFromProject(listTitleStr.get(showClicknum - 1), fileList.get(num).getAbsolutePath());
                saveProject();
                loadProject();
                switchProject(listTitleStr.get(showClicknum - 1));
            }


        }
        //点击删除项目文件
        if (RectangleOperation.pointInRectangle(x, y, 5, 470, 65, 535)) {
            if (isClickTitle && fileList.size() != 0) {
                rmFileFromProject(listTitleStr.get(showClicknum - 1), fileList.get(num).getAbsolutePath());
                saveProject();
                loadProject();
                switchProject(listTitleStr.get(showClicknum - 1));
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
        for (int i = 0; i < MAXSHOW; i++) {
            if (RectangleOperation.pointInRectangle(x, y, i * 208 + 120, 0, i * 208 + 328, 50)) {
                isMoveTitle = true;
                showMovenum = i + 1;
            }
        }
        for (int i = 0; i < Math.min(16, fileList.size()); i++) {
            if (RectangleOperation.pointInRectangle(x, y, 70, i * 30 + 55, 1165, i * 30 + 85)) {
                choose = i;

            }
            if (!RectangleOperation.pointInRectangle(x, y, 72, 52, 1200, 550)) {
                choose = -1;
            }
        }
        if (!RectangleOperation.pointInRectangle(x, y, 120, 0, 1165, 50)) {
            {
                isMoveTitle = false;
            }
        }
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
        if (wheel == 1) {
            if (roller < fileList.size() - maxShow) {
                roller++;
            }
        } else if (wheel == -1) {
            if (roller > 0) roller--;
        }
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

    public List<String> getFilesName() {
        return filesName;
    }

    /**
     * 新建项目
     */
    private static void newProject() {
        NewProjectFrame frame = new NewProjectFrame(Main.mainFrame);
        String projectName = frame.getProjectName();
        if (projectName == null || projectName.equals("")) {
            MessageBox.addMessage("已取消创建或名称无效");
            return;
        }
        JSONObject filesJson = new JSONObject();
        filesJson.put("files", new JSONArray());
        File dataFile = new File(System.getProperty("user.dir") + File.separator + "data.udp");
        if (!dataFile.exists()) {
            try {
                if (!dataFile.createNewFile()) return;
                else {
                    try {
                        FileOutputStream outputStream = new FileOutputStream(dataFile);
                        outputStream.write("{}".getBytes(StandardCharsets.UTF_8));
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
        MessageBox.addMessage("新建项目成功!");
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
            fileList.clear();
            icons.clear();
            for (Object o : files) {
                File tmpFile = new File((String) o);
                if (tmpFile.exists()) {
                    fileList.add(tmpFile);
                    ImageIcon icon = (ImageIcon) ImageTools.getSmallIcon(tmpFile);
                    icons.add(icon.getImage());
                }
            }
            num = -1;
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
            saveProject();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从项目中删除文件
     *
     * @param projectName 项目名
     * @param fileName    文件路径
     * @return 是否删除成功
     */
    private boolean rmFileFromProject(String projectName, String fileName) {
        if (projectMap.containsKey(projectName)) {
            JSONObject project = projectMap.get(projectName);
            JSONArray files = project.getJSONArray("files");
            if (files.contains(fileName)) {
                files.remove(fileName);
                saveProject();
                return true;
            } else return false;
        } else return false;
    }

    File copyFile;

    /**
     * 从项目中复制文件
     *
     * @param projectName 项目名称
     * @param fileName    要复制的文件路径
     * @return 是否复制成功
     */
    private boolean copyFileFromProject(String projectName, String fileName) {
        if (projectMap.containsKey(projectName)) {
            File tmpFile = new File(fileName);
            if (tmpFile.exists()) {
                copyFile = tmpFile;
                isShear = false;
                return true;
            } else return false;
        } else return false;
    }


    /**
     * 从复制文件中把文件复制到指定项目中
     *
     * @param projectName 项目名称
     * @return 是否复制成功
     */
    private boolean pasteFileFromCopyFile(String projectName) {
        if (projectMap.containsKey(projectName)) {
            if (copyFile == null || !copyFile.exists()) {
                return false;
            } else {
                if (isShear) {
                    if (projectMap.containsKey(shearProject)) {
                        JSONObject preProject = projectMap.get(shearProject);
                        JSONArray preFiles = preProject.getJSONArray("files");
                        if (preFiles.contains(copyFile.getAbsolutePath())) {
                            preFiles.remove(copyFile.getAbsolutePath());
                            JSONObject project = projectMap.get(projectName);
                            JSONArray files = project.getJSONArray("files");
                            files.add(copyFile.getAbsolutePath());
                            saveProject();
                            return true;
                        } else return false;

                    } else return false;
                } else {
                    JSONObject project = projectMap.get(projectName);
                    JSONArray files = project.getJSONArray("files");
                    files.add(copyFile.getAbsolutePath());
                    saveProject();
                    return true;
                }
            }
        } else return false;
    }

    boolean isShear = false;
    String shearProject;

    /**
     * 从指定项目中剪切文件
     *
     * @param projectName 项目名称
     * @param fileName    文件路径
     * @return 是否剪切成功
     */
    private boolean shearFileFromProject(String projectName, String fileName) {
        if (projectMap.containsKey(projectName)) {
            File tmpFile = new File(fileName);
            if (tmpFile.exists()) {
                copyFile = tmpFile;
                shearProject = projectName;
                isShear = true;
                return true;
            } else return false;
        } else return false;
    }


    /**
     * 将文件从指定文件夹移动到另一个文件夹
     *
     * @param projectName    项目名称
     * @param fileName       要移动的文件的路径
     * @param newProjectName 要移动到的项目名称
     * @return 是否移动成功
     */
    private boolean moveFileToNewProject(String projectName, String fileName, String newProjectName) {
        if (projectMap.containsKey(projectName) && projectName.contains(newProjectName)) {
            File tmpFile = new File(fileName);
            if (tmpFile.exists()) {
                JSONObject project = projectMap.get(projectName);
                JSONObject newProject = projectMap.get(newProjectName);
                JSONArray files = project.getJSONArray("files");
                JSONArray newFiles = newProject.getJSONArray("files");
                if (files.contains(fileName)) {
                    files.remove(fileName);
                    newFiles.add(fileName);
                    saveProject();
                    return true;
                } else return false;
            }
        }
        return false;
    }

    /**
     * 删除项目
     *
     * @param projectName 要删除的项目名称
     * @return 是否删除成功
     */
    private boolean rmProject(String projectName) {
        if (projectMap.containsKey(projectName)) {
            projectMap.remove(projectName);
            saveProject();
            return true;
        } else return false;
    }

    /**
     * 保存项目信息
     */
    private void saveProject() {
        JSONObject allProject = new JSONObject();
        File dataFile = new File(System.getProperty("user.dir") + File.separator + "data.udp");
        for (String key : projectMap.keySet()) {
            allProject.put(key, projectMap.get(key));
        }
        String tmp = allProject.toString();
        try {
            FileOutputStream outputStream = new FileOutputStream(dataFile);
            outputStream.write(tmp.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadProject();
    }

    public void putFile(List<File> files) {
        for (File file1 : files) {
            List<String> listTitleStr = new ArrayList<>(projectMap.keySet());
            addFileToProject(listTitleStr.get(showClicknum - 1), file1);
        }
    }


}
