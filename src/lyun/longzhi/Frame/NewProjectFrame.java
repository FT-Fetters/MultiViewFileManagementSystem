package lyun.longzhi.Frame;

import lyun.longzhi.Main;
import lyun.longzhi.configure.LoadConfigure;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewProjectFrame extends JDialog {
    private String projectName;

    public NewProjectFrame(JFrame father){
        super(father);
        setModal(true);
        this.setSize(LoadConfigure.getScreenWidth()/5,LoadConfigure.getScreenWidth()/12);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("新建项目");
        JPanel view = new View();
        view.setLayout(null);
        Thread viewThread = new Thread((Runnable) view);
        viewThread.start();
        //label
        JLabel label = new JLabel("项目名:");
        label.setForeground(Color.white);
        label.setBounds(25,15,50,25);
        view.add(label);
        //text
        JTextArea name = new JTextArea(1,10);
        name.setBackground(new Color(38,38,38));
        name.setBorder(BorderFactory.createLineBorder(new Color(83,83,83)));
        name.setBounds(70,15 ,260,25);
        name.setForeground(Color.white);
        Font textFont = new Font("微软雅黑",Font.PLAIN,16);
        name.setFont(textFont);
        view.add(name);
        this.add(view);
        //confirm button
        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(100,60,80,35);
        confirmButton.setBackground(new Color(83,83,83));
        confirmButton.setForeground(Color.white);
        confirmButton.addActionListener(e -> {
            projectName = name.getText();
            setVisible(false);
        });
        view.add(confirmButton);
        //cancel button
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(200,60,80,35);
        cancelButton.setBackground(new Color(83,83,83));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setForeground(Color.white);

        view.add(cancelButton);
        //Lin
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose();
            }
        });

        this.setVisible(true);
    }

    public String getProjectName(){
        return this.projectName;
    }



    private static class View extends JPanel implements Runnable{

        public View(){
            this.setBackground(new Color(36,36,36));
        }

        @Override
        public void run() {
            while (true){
                this.repaint();
                try {
                    Thread.sleep(10);//设置刷新延迟,防止cpu占用过高
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

        }
    }


}