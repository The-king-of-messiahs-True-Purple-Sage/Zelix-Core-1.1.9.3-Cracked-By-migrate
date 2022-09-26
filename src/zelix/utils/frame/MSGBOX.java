/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.frame;

import java.awt.EventQueue;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import zelix.utils.system.Check;
import zelix.utils.system.ClipBoardUtils;

public class MSGBOX
extends JFrame {
    private JPanel contentPane;
    private JTextField txthwid;
    private JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    MSGBOX frame = new MSGBOX();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MSGBOX() {
        this.setTitle("Zelix\u9a8c\u8bc1\u7cfb\u7edf");
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        this.txthwid = new JTextField();
        this.txthwid.setText("\u60a8\u5c1a\u672a\u83b7\u53d6\u51ed\u8bc1\uff0c\u8bf7\u52a0\u7fa4975302793\uff0c\u60a8\u7684HWID\u4e3a\uff1a");
        this.txthwid.setBounds(27, 79, 378, 59);
        this.contentPane.add(this.txthwid);
        this.txthwid.setColumns(10);
        this.textField = new JTextField();
        try {
            this.textField.setText(Check.getHWID());
            ClipBoardUtils.setSysClipboardText(Check.getHWID());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.textField.setBounds(27, 153, 378, 29);
        this.contentPane.add(this.textField);
        this.textField.setColumns(10);
    }
}

