package lyun.longzhi.Frame;

import lyun.longzhi.configure.LoadConfigure;
import lyun.longzhi.utils.FontUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageBoxFrame extends JDialog {

    public boolean confirm = false;

    public MessageBoxFrame(JFrame father,String title,String msg){
        super(father);
        setModal(true);
        this.setSize(LoadConfigure.getScreenWidth() / 5, LoadConfigure.getScreenWidth() / 12);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(title);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(36, 36, 36));
        panel.setLayout(null);
        //msg
        JLabel label = new JLabel(msg);
        label.setForeground(Color.white);
        label.setBounds(this.getWidth()/2 - FontUtils.getWordWidth(this.getFont(),msg)/2,
                this.getHeight()/8,
                FontUtils.getWordWidth(this.getFont(),msg)*2,
                FontUtils.getWordHeight(this.getFont()));
        panel.add(label);
        //confirm button
        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(100, 60, 80, 35);
        confirmButton.setBackground(new Color(83, 83, 83));
        confirmButton.setForeground(Color.white);
        confirmButton.addActionListener(e -> {
            this.confirm = true;
            setVisible(false);
        });
        panel.add(confirmButton);
        //cancel button
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 60, 80, 35);
        cancelButton.setBackground(new Color(83, 83, 83));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setForeground(Color.white);
        panel.add(cancelButton);
        this.add(panel);
        this.setVisible(true);
    }



}
