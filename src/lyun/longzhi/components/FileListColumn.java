package lyun.longzhi.components;

import lyun.longzhi.utils.FontUtils;
import lyun.longzhi.utils.MessageBox;
import lyun.longzhi.utils.RectangleOperation;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileListColumn implements Component {
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxShow;//最大显示数量
    private int borderWidth = 1;//边框宽度
    private int roller = 0;//滚轮
    private int choose = -1;//所选择的文件行
    private int mouseIn = -1;
    private int diskX = -1;
    private int diskY = -1;
    private int diskMouseX = -1;
    private int diskMouseY = -1;

    private String path;

    private Color backgroundColor = new Color(32, 32, 32);
    private Color borderColor = new Color(32, 32, 32);

    private boolean border = false;
    private boolean background = true;
    private boolean enable = true;
    private boolean disk = false;//右键圆盘
    private boolean shear = false;
    private boolean isMousePress = false;
    private boolean isDrag = false;

    private int LocationY = -1;


    private File shearFile = null;
    private File copyFile = null;

    private List<File> files = new ArrayList<>();

    private List<Image> filesIcon = new ArrayList<>();

    private TextLabel textLabel;

    private PathSelector pathSelector;

    private TypeClassifier typeClassifier;

    private TimeAxis timeAxis;

    public FileListColumn(String path, int x, int y, int maxShow) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.maxShow = maxShow;
        this.setPath(this.path);
    }

    public FileListColumn(String path, int x, int y, int width, int height, int maxShow) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxShow = maxShow;
        this.setPath(this.path);
    }


    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
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
        this.borderColor = color;
        this.borderWidth = width;
    }

    @Override
    public void removeBorder() {
        this.border = false;
    }

    @Override
    public void setBackground(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public void removeBackground() {
        this.background = false;
    }

    @Override
    public void draw(Graphics g) {
        if (!enable) return;
        g.setColor(backgroundColor);
        g.fillRect(this.x, this.y, this.width, this.height);


        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("微软雅黑", Font.PLAIN, 15);
        g2d.setFont(font);
        //增加抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (files.size() == 0) {
            g2d.setColor(Color.white);
            g2d.drawString("此文件夹为空", this.x + this.width / 2 - 30, this.y + 20);
            return;
        }

        //绘制被选择的行
        g2d.setColor(new Color(119, 119, 119));
        if (choose != -1 && choose >= roller && choose < roller + maxShow && ((files.size() - maxShow) >= 0 || files.size() > choose))
            g2d.fillRoundRect(this.x + 10, this.y + 30 + 30 * (choose - roller) - 20, this.width - 20, 30, 3, 3);

        //绘制鼠标经过
        g2d.setColor(new Color(51, 51, 51));
        if (this.mouseIn > -1 && this.mouseIn != (choose - roller) && ((files.size() - maxShow) >= 0 || files.size() > mouseIn))
            g2d.fillRoundRect(this.x + 10, this.y + 30 + 30 * mouseIn - 20, this.width - 20, 30, 3, 3);

        g2d.setColor(Color.white);
        for (int i = roller; i < Math.min(roller + maxShow, files.size()); i++) {
            //绘制文件名
            if (i < filesIcon.size()) {
                String name = files.get(i).getName();
                if (name.length() > 70) {
                    name = name.substring(0, 70);
                    name += "...";
                }
                g2d.drawString(name, this.x + 40, this.y + 30 + (i - roller) * 30);
            }

            //绘制图标
            if (i < filesIcon.size())
                g2d.drawImage(filesIcon.get(i), this.x + 20, this.y + 15 + (i - roller) * 30, null);
        }
        //绘制滚动条
        g.setColor(new Color(77, 77, 77));
        if (files.size() > maxShow)
            g.fillRect(this.x + 10 + this.width - 20, this.y + roller * this.height / files.size(), 10, this.height * maxShow / files.size());
        //绘制功能圆盘(如果右键的话
        if (disk) {
            Color diskColor = new Color(0.9f, 0.9f, 0.9f, 0.1f);
            g2d.setColor(diskColor);
            g2d.fillArc(this.x + diskX - 80, this.y + diskY - 80 - 1, 160, 160, 45, 90);
            g2d.fillArc(this.x + diskX - 80 - 1, this.y + diskY - 80, 160, 160, 135, 90);
            g2d.fillArc(this.x + diskX - 80, this.y + diskY - 80 + 1, 160, 160, 225, 90);
            g2d.fillArc(this.x + diskX - 80 + 1, this.y + diskY - 80, 160, 160, 315, 90);
            int tx = diskMouseX - diskX;
            int ty = diskMouseY - diskY;
            diskColor = new Color(0.9f, 0.9f, 0.9f, 0.15f);
            g2d.setColor(diskColor);
            if (ty > tx && ty > -tx) {//down
                g2d.fillArc(this.x + diskX - 80, this.y + diskY - 80 + 1, 160, 160, 225, 90);
            } else if (ty < -tx && ty > tx) {//left
                g2d.fillArc(this.x + diskX - 80 - 1, this.y + diskY - 80, 160, 160, 135, 90);
            } else if (ty < tx && ty < -tx) {//up
                g2d.fillArc(this.x + diskX - 80, this.y + diskY - 80 - 1, 160, 160, 45, 90);
            } else {//right
                g2d.fillArc(this.x + diskX - 80 + 1, this.y + diskY - 80, 160, 160, 315, 90);
            }
            g2d.setColor(Color.white);
            font = new Font("微软雅黑", Font.PLAIN, 18);
            g2d.setFont(font);
            g2d.drawString("删除", this.x + diskX - FontUtils.getWordWidth(font, "删除") / 2, this.y + diskY - 40);
            g2d.drawString("复制", this.x + diskX - FontUtils.getWordWidth(font, "复制") - 30, this.y + diskY + FontUtils.getWordHeight(font) / 4);
            g2d.drawString("粘贴", this.x + diskX - FontUtils.getWordWidth(font, "粘贴") / 2, this.y + diskY + 40);
            g2d.drawString("剪切", this.x + diskX + 30, this.y + diskY + FontUtils.getWordHeight(font) / 4);
            //g2d.drawLine();
        }
    }

    @Override
    public void mouseClick(int x, int y, int key) {
        if (!enable) return;
        if (key == 1) {
            if (disk) {
                if ((x - diskX) * (x - diskX) + (y - diskY) * (y - diskY) > 6400) {
                    disk = false;
                    return;
                }
                int tx = x - diskX;
                int ty = y - diskY;
                File chooseFile = files.get(mouseIn);
                if (ty > tx && ty > -tx) {//down
                    File tmp;
                    if (shear) {
                        tmp = shearFile;
                    } else tmp = copyFile;
                    try {
                        if (copyFile == null) {
                            MessageBox.addMessage("暂无可粘贴的文件!");
                            return;
                        }
                        File to = new File(path + "\\" + copyFile.getName());
                        if (tmp.equals(to)) to = new File(path + "\\" + copyFile.getName() + "_");
                        FileUtils.copyFile(tmp, to);
                        MessageBox.addMessage("已粘贴");
                    } catch (IOException e) {
                        e.printStackTrace();
                        MessageBox.addMessage("粘贴失败");
                    }
                    if (shear) {
                        tmp.delete();
                        shear = false;
                    }
                    refresh();
                } else if (ty < -tx && ty > tx) {//left
                    copyFile = chooseFile;
                    MessageBox.addMessage("已复制");
                } else if (ty < tx && ty < -tx) {//up
                    if (chooseFile.delete()) {
                        MessageBox.addMessage("删除成功!");
                    } else MessageBox.addMessage("删除失败!");
                    refresh();
                } else {//right
                    copyFile = chooseFile;
                    shearFile = chooseFile;
                    shear = true;
                    MessageBox.addMessage("已剪切");
                }
                disk = false;

            } else {
                if (x > 10 && x < this.width - 20 && y > 10 && y < this.height - 10) {
                    choose = (y - 10) / 30 + roller;
                }
            }
        } else if (key == 3) {
            if (!(this.mouseIn > -1 && ((files.size() - maxShow) >= 0 || files.size() > mouseIn))) return;
            if (y < 10 || y > this.height - 10 || x < 10 || x > this.width - 20) return;
            if (mouseIn != -1 && !disk) {
                diskX = x;
                diskY = y;
                diskMouseX = x;
                diskMouseY = y;
                disk = true;
            } else if (disk) {
                disk = false;
            }
        }
    }

    @Override
    public void mouseEnter() {
        if (!enable) return;
    }

    @Override
    public void mouseLeave() {
        if (!enable) return;
        this.mouseIn = -1;
    }

    @Override
    public void mouseMove(int x, int y) {
        if (!enable) return;
        if (x > 10 && x < this.width - 20 && y > 10 && y < this.height - 10 && !disk) {
            mouseIn = (y - 10) / 30;
        }
        if (disk) {
            diskMouseX = x;
            diskMouseY = y;
        }
    }

    @Override
    public void mouseDoubleClick(int x, int y, int key) throws IOException {
        if (!enable) return;
        if (disk) return;
        if (x > 10 && x < this.width - 20 && y > 10 && y < this.height - 10) {
            int tmp = (y - 10) / 30 + roller;
            if ((y - 10) / 30 > files.size() - roller) return;
            if (files.get(tmp).isDirectory()) {
                String newPath = files.get(tmp).getPath();
                if (pathSelector != null) {
                    pathSelector.enterNewPath(newPath);
                }
                if (textLabel != null) {
                    textLabel.text = newPath;
                }
                if (typeClassifier != null) typeClassifier.setPath(newPath);
                if (timeAxis != null) timeAxis.setPath(newPath);
                setPath(newPath);
                this.choose = -1;
                this.roller = 0;
            } else {
                String newPath = files.get(tmp).getPath();
                if (pathSelector != null) {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", newPath});
                }
            }
        }

    }

    @Override
    public void mousePress(int x, int y, int key) {
        if (!enable) return;
        if (files.size() == 0) return;
        isMousePress = true;
        if (isMousePress) {
            LocationY = y;
        }
    }

    @Override
    public void mouseRelease(int x, int y, int key) {
        if (!enable) return;
        if (files.size() == 0) return;
        isMousePress = false;
        LocationY = -1;
        isDrag=false;
    }

    @Override
    public void mouseWheelMoved(int wheel) {
        if (!enable) return;
        //模拟鼠标滚滚动
        if (wheel == 1) {
            if (roller < files.size() - maxShow) {
                roller++;
            }
        } else if (wheel == -1) {
            if (roller > 0) roller--;
        }
    }

    @Override
    public void mouseDrag(int x, int y, int key) {
        if (!enable)return;
        if (RectangleOperation.pointInRectangle(x, y,
                this.width - 10,
                roller * this.height / files.size(),
                this.width,
                roller * this.height / files.size() + this.height * maxShow / files.size())) {
            isDrag = true;
            if (roller * this.height / files.size() + this.height * maxShow / files.size() < this.height - maxShow / 2 && roller * this.height / files.size() > 0) {
                if ((LocationY - y) / files.size() > (this.height / files.size()) * files.size()) {
                    roller--;
                } else if ((y - LocationY) / files.size() > (this.height / files.size()) * files.size()) {
                    roller++;
                }
            }
            if (roller * this.height / files.size() == 0 && (y - LocationY) / 2 > (this.height / files.size()) * 2) {
                roller++;
            }
            if (roller * this.height / files.size() + this.height * maxShow / files.size() >= this.height - maxShow / 2 && (LocationY - y) / 2 > (this.height / files.size()) * 2) {
                roller--;
            }

        }
        if (!RectangleOperation.pointInRectangle(x, y,
                this.width - 10,
                roller * this.height / files.size(),
                this.width,
                roller * this.height / files.size() + this.height * maxShow / files.size()) && isDrag){
            if (roller * this.height / files.size() + this.height * maxShow / files.size() < this.height - maxShow  && roller * this.height / files.size() > 0) {
                if ((LocationY - y)*files.size() > (this.height / files.size()*files.size()) ) {
                    roller--;
                    LocationY=y;
                } else if ((y - LocationY) *files.size() > (this.height / files.size()) * files.size()) {
                    roller++;
                    LocationY=y;
                }

            }
            if (roller * this.height / files.size() == 0 && (y - LocationY) > (this.height / files.size()) ) {
                roller++;
            }
            if (roller * this.height / files.size() + this.height * maxShow / files.size() >= this.height - maxShow  && (LocationY - y) > (this.height / files.size()) ) {
                roller--;
            }

        }

    }

    /**
     * 设定索要显示的文件的目录
     *
     * @param path 目录
     */
    public void setPath(String path) {
        this.path = path;
        new Thread(() -> {//此段应当用一个新的线程进行加载,否则会出现堵塞现象导致无法paint()
            File file = new File(path);
            files.clear();
            filesIcon.clear();
            if (file.isDirectory()) {
                File[] readFiles = file.listFiles();
                if (readFiles != null) {
                    files.addAll(Arrays.asList(readFiles));
                    for (File readFile : readFiles) {
                        ImageIcon imageIcon = (ImageIcon) getSmallIcon(readFile);
                        filesIcon.add(imageIcon.getImage());
                    }
                }
            }
            this.roller = 0;
        }).start();
    }

    /**
     * 获取小图标
     *
     * @param f 要获取的文件
     * @return 图标
     */
    private Icon getSmallIcon(File f) {
        if (f != null && f.exists()) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            return fsv.getSystemIcon(f);
        }
        return null;
    }


    public void connect(TextLabel textLabel, PathSelector pathSelector, TypeClassifier typeClassifier, TimeAxis timeAxis) {
        this.textLabel = textLabel;
        this.pathSelector = pathSelector;
        this.typeClassifier = typeClassifier;
        this.timeAxis = timeAxis;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public String getPath() {
        return this.path;
    }

    public void refresh() {
        if (textLabel != null) {
            textLabel.text = path;
        }
        if (typeClassifier != null) typeClassifier.setPath(path);
        if (timeAxis != null) timeAxis.setPath(path);
        setPath(path);
        this.choose = -1;
        this.roller = 0;
    }
}
