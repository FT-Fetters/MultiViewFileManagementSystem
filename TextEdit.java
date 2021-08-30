package lyun.longzhi.components;

import lyun.longzhi.components.actions.tools;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class TextEdit extends JFrame {

    private boolean isChanged = false;
    private File file = null;

    private JMenuBar bar = null;
    private JMenu dJMenu = null;
    private JMenu eJMenu = null;
    private JMenu aJMenu = null;
    private JMenu cJMenu = null;
    private JMenu encodeItem = null;
    private JMenu decodeItem = null;
    private JMenuItem openItem = null;
    private JMenuItem saveItem = null;
    private JMenuItem saveAsIem = null;
    private JMenuItem closeItem = null;
    private JMenuItem undoItem = null;
    private JMenuItem redoItem = null;
    private JMenuItem cutItem = null;
    private JMenuItem copyItem = null;
    private JMenuItem pasteItem = null;
    private JRadioButton en_UTF_8_Item = null;
    private JRadioButton en_gbk_Item = null;
    private JRadioButton de_UTF_8_Item = null;
    private JRadioButton de_gbk_Item = null;
    private JMenuItem AboutItem = null;

    private JFileChooser jFileChooser = null;
    private JTextArea ta = null;
    private JScrollPane scrollPane = null;
    private JPanel jdown = null;
    private JLabel lableLeft = null;
    private JLabel lableCenter = null;
    private JLabel lableRight = null;
    //编码的格式
    private String encode = "UTF-8";
    private String decode = "UTF-8";
    //按下为真，判断是否按下
    private boolean key_ctrl = false;
    //是否打开文件
    private boolean isOpen = false;
    //滚动条
    private MouseWheelListener sysWheel;
    //文本字体大小
    Font f = new Font("Serif", 0, 28);
    //撤销的监听器
    UndoManager um = new UndoManager();

    public TextEdit() {
        //初始化
        jFileChooser = new JFileChooser();
    }

    private class change implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        public void removeUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        public void changedUpdate(DocumentEvent e) {
            changed();
        }

        public void changed() {
            if (!isOpen) {
                isChanged = true;
                lableLeft.setText("修改状态:已修改");
                //统计字数
                lableRight.setText("字数" + tools.replaceBlank(ta.getText()));
            }
        }

    }

    private class event extends MouseAdapter {
        public void mouseWheelMove(MouseWheelEvent e) {
            if (e.isControlDown()) {
                if (e.getWheelRotation() < 0 && f.getSize() < 60) {
                    f = new Font(f.getFamily(), f.getStyle(), f.getSize() + 1);
                    ta.setFont(f);
                } else if (e.getWheelRotation() > 0 && f.getSize() > 0) {
                    f = new Font(f.getFamily(), f.getStyle(), f.getSize() - 1);
                    ta.setFont(f);
                }
                lableCenter.setText("字体大小" + f.getSize());
            } else {
                scrollPane.addMouseWheelListener(sysWheel);
                sysWheel.mouseWheelMoved(e);
                scrollPane.removeMouseWheelListener(sysWheel);
            }
        }
    }

    //重新打开读取
    public void openagainDialog() {
        isOpen = true;
        BufferedReader bufferedReader = null;
        if (file != null) ;
        {
            String str = "";

            try {
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(file), decode);
                bufferedReader = new BufferedReader(fReader);
                ta.setText(" ");
                str = bufferedReader.readLine();
                while (str != null) {
                    ta.append(str + '\n');
                    str = bufferedReader.readLine();
                }

            } catch (FileNotFoundException e1) {
                ta.setText(ta.getText() + '\n' + "文件未找到");
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                isOpen = true;
                isChanged = false;
                lableLeft.setText("状态：以打开");
                this.setTitle(file.getName());
                lableLeft.setText("字数：" + tools.replaceBlank(ta.getText()));
            }
        }
    }

    //打开文件读对话框，打开控件调用这个方法
    public void openOpenDialog() {
        isOpen = true;
        int statu = jFileChooser.showOpenDialog(TextEdit.this);
        BufferedReader bufferedReader = null;
        if (statu == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser.getSelectedFile();
            String str = "";
            try {
                InputStreamReader fReader = new InputStreamReader(new FileInputStream(file), decode);
                bufferedReader = new BufferedReader(fReader);
                ta.setText(" ");
                str = bufferedReader.readLine();
                while (str != null) {
                    ta.append(str + '\n');
                    str = bufferedReader.readLine();
                }
            } catch (FileNotFoundException E1) {
                ta.setText(ta.getText() + '\n' + "文件未找到");
                E1.printStackTrace();
            } catch (IOException E1) {
                E1.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException E1) {
                        E1.printStackTrace();
                    }
                }
                isOpen = false;
                isChanged = false;
                lableLeft.setText("状态：已打开");
                this.setTitle(file.getName());
                lableRight.setText("字数:" + tools.replaceBlank(ta.getText()));
            }
        }
    }

    //新建或另存为的文件保存
    public void openSaveDialog() {
        int status = jFileChooser.showSaveDialog(TextEdit.this);
        BufferedWriter bufferedReader2 = null;
        if (status == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser.getSelectedFile();
            try {
                OutputStreamWriter fWriter = new OutputStreamWriter(new FileOutputStream(file), encode);
                bufferedReader2 = new BufferedWriter(fWriter);
                String[] strings = ta.getText().split("\n");
                for (String str : strings) {
                    bufferedReader2.write(str);
                    bufferedReader2.newLine();
                    bufferedReader2.flush();
                }
            } catch (FileNotFoundException E1) {
                ta.setText(ta.getText() + '\n' + "文件未找到");
                E1.printStackTrace();
            } catch (IOException E1) {
                E1.printStackTrace();
            } finally {
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException E1) {
                        E1.printStackTrace();
                    }
                }
                isOpen = false;
                isChanged = false;
                lableLeft.setText("状态：已保存");
                this.setTitle(file.getName());
            }
        }
    }

    //文件已有的普通保存ctrl+s调用
    public void onlySave() {
        BufferedWriter bufferedReader2 = null;
        try {
            OutputStreamWriter fWriter = new OutputStreamWriter(new FileOutputStream(file), encode);
            bufferedReader2 = new BufferedWriter(fWriter);
            String[] strings = ta.getText().split("\n");
            for (String str : strings) {
                bufferedReader2.write(str);
                bufferedReader2.newLine();
                bufferedReader2.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isOpen = false;
            isChanged = false;
            lableLeft.setText("状态：已保存");
            this.setTitle(file.getName());
        }
    }

    //判断按钮是否可点击
    public void ifclick() {
        if (um.canUndo()) {
            undoItem.setEnabled(true);
        } else {
            undoItem.setEnabled(false);
        }
        if (um.canRedo()) {
            redoItem.setEnabled(true);
        } else {
            redoItem.setEnabled(false);
        }
    }

    //按键监听
    public void addKeyListener() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (((KeyEvent) event).getID() == KeyEvent.KEY_RELEASED) {
                    int key = ((KeyEvent) event).getKeyCode();
                    System.out.println(key);
                    if (key == 17) {
                        key_ctrl = false;
                    }
                }
                if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
                    int key = ((KeyEvent) event).getKeyCode();
                    System.out.println(key);
                    if (key == 17) {
                        key_ctrl = true;
                    }
                    if (key == 83 && key_ctrl == true) {
                        System.out.println("ctrl+s");
                        if (file == null) {
                            openSaveDialog();
                        } else {
                            onlySave();
                        }
                    }
                    if (key == 90 && key_ctrl == true) {
                        System.out.println("ctrl+z");
                        if (um.canUndo()) {
                            um.undo();
                        }
                    }
                    if (key == 89 && key_ctrl == true) {
                        System.out.println("ctrl+y");
                        if (um.canRedo()) {
                            um.redo();
                        }
                    }
                }

            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    //加bar组件监听器
    public void addBarListener() {
        //菜单栏焦点事件
        eJMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {

                ifclick();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

                menuSelected(e);
            }

            @Override
            public void menuCanceled(MenuEvent e) {

                menuSelected(e);
            }
        });

        //关于Note触发监听器
        AboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TextEdit.this, "自定义视图介绍...", e.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
            }
        });
        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println((um.canUndo() ? "可" : "不可") + "撤销");
                if (um.canUndo()) {
                    um.undo();
                }
                ifclick();
            }
        });
        redoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println((um.canUndo() ? "可" : "不可") + "恢复");
                if (um.canRedo()) {
                    um.redo();
                }
                ifclick();
            }
        });
        cutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.cut();
            }
        });
        copyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.copy();
            }
        });
        pasteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.paste();
            }
        });

        //格式栏编码解码
        en_UTF_8_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encode = "UTF-8";
            }
        });
        en_gbk_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encode = "GBK";
            }
        });
        de_UTF_8_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("utf-8");
                decode = "UTF-8";
                openagainDialog();
            }
        });
        de_gbk_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("gbk");
                decode = "GBK";
                openagainDialog();
            }
        });

        //打开按键
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isChanged == false) {
                    openOpenDialog();
                } else if (file == null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEdit.this, "文件未保存，是否保存", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        openSaveDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        openOpenDialog();
                    }
                } else if (file != null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEdit.this, "文件未保存，是否保存到:" + file.getPath(), "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        onlySave();
                    } else if (result == JOptionPane.NO_OPTION) {
                        openSaveDialog();
                    }

                }
            }
        });
        //另存为按键
        saveAsIem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSaveDialog();
            }
        });
        //保存按键
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    openSaveDialog();
                } else {
                    onlySave();
                }
            }
        });
        //关闭按键
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isChanged == false) {
                    System.exit(0);
                } else if (file == null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEdit.this, "文件未保存，是否保存!", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        openOpenDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else if (file != null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEdit.this, "文件未保存，是否保存到" + file.getPath(), "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        openSaveDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });
        //
    }

    //窗口监听器
    public void myaddWinListener() {
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                //如果修改未保存弹出对话框，保存就直接退出
                if (isChanged == false) {
                    System.exit(0);
                } else if (file == null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEdit.this, "文件未保存，是否保存!", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        openSaveDialog();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else if (file != null) {
                    new JOptionPane();
                    int result = JOptionPane.showConfirmDialog(TextEdit.this, "文件还未保存，是否保存!" + file.getPath(), "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        onlySave();
                    } else if (result == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });
    }

    ///加载文件菜单
    public void lauchBar() {
        bar = new JMenuBar();
        dJMenu = new JMenu("文本");
        eJMenu = new JMenu("编辑");
        cJMenu = new JMenu("格式");
        aJMenu = new JMenu("关于");

        openItem = new JMenuItem("打开");
        saveItem = new JMenuItem("保存");
        saveAsIem = new JMenuItem("另存为");
        closeItem = new JMenuItem("关闭");

        copyItem = new JMenuItem("复制（ctrl+C）");
        pasteItem = new JMenuItem("粘贴（ctrl+V）");
        cutItem = new JMenuItem("剪切（ctrl+X）");
        undoItem = new JMenuItem("撤销（ctrl+Z）");
        redoItem = new JMenuItem("恢复（ctrl+Y）");

        encodeItem = new JMenu("编码");
        decodeItem = new JMenu("解码");
        en_UTF_8_Item = new JRadioButton("UTF-8", true);
        en_gbk_Item = new JRadioButton("GBK", false);
        de_UTF_8_Item = new JRadioButton("UTF-8", true);
        de_gbk_Item = new JRadioButton("GBK", false);

        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();
        group1.add(en_UTF_8_Item);
        group1.add(en_gbk_Item);
        group2.add(de_UTF_8_Item);
        group2.add(de_gbk_Item);

        AboutItem = new JMenuItem("关于");

        setJMenuBar(bar);
        bar.add(dJMenu);
        bar.add(eJMenu);
        bar.add(cJMenu);
        bar.add(aJMenu);

        dJMenu.add(openItem);
        dJMenu.add(saveItem);
        dJMenu.add(saveAsIem);
        dJMenu.add(closeItem);

        eJMenu.add(copyItem);
        eJMenu.add(pasteItem);
        eJMenu.add(cutItem);
        eJMenu.add(undoItem);
        eJMenu.add(redoItem);

        cJMenu.add(encodeItem);
        cJMenu.add(decodeItem);

        aJMenu.add(AboutItem);

        encodeItem.add(en_UTF_8_Item);
        encodeItem.add(en_gbk_Item);
        decodeItem.add(de_UTF_8_Item);
        decodeItem.add(de_gbk_Item);
    }

    //加载文本域
    public void launchTextArea() {
        ta = new JTextArea();
        ta.setFont(f);
        //监听文本变化改变change
        ta.getDocument().addDocumentListener(new change());
        //监听取消
        ta.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                um.addEdit(e.getEdit());
            }
        });
        scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sysWheel = scrollPane.getMouseWheelListeners()[0];//获得系统滚动事件
        scrollPane.removeMouseWheelListener(sysWheel);
        scrollPane.addMouseWheelListener(new event());
        add(scrollPane, BorderLayout.CENTER);
    }

    //label板块
    public void launchLabel() {
        jdown = new JPanel();//Label板块
        lableLeft = new JLabel("修改状态:未修改");
        lableCenter = new JLabel("字体大小:" + tools.replaceBlank(ta.getText()));
        lableRight = new JLabel("字数:" + tools.replaceBlank(ta.getText()));//漏了一个这个launchFrame一直报错！
        jdown.setLayout(new GridLayout(1, 5));
        jdown.add(lableLeft);
        jdown.add(lableCenter);
        jdown.add(lableRight);
        add(jdown, BorderLayout.SOUTH);

    }

    //加载整个窗口
    public void launchFrame() {
        setBounds(340, 290, 1210, 555);
        setTitle("自定义视图");
        setLayout(new BorderLayout());

        //加载文件菜单
        this.lauchBar();
        //加载文本域
        this.launchTextArea();
        //加载label模块
        this.launchLabel();
        //按钮组件监听
        this.addBarListener();
        //全局按键事件监听
        this.addKeyListener();
        //窗口的监听器
        this.myaddWinListener();
        setVisible(true);
        //设置JFram不默认关闭，为了用对话框触发退出
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


}


