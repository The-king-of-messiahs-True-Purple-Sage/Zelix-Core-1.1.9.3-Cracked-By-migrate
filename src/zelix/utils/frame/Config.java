/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package zelix.utils.frame;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import zelix.hack.Hack;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.system.WebUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class Config
extends JFrame {
    public static String Cloudurl;
    public static String ReturnData;
    private static JsonParser jsonParser;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    Config frame = new Config();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Config() {
        this.setTitle("Cloud-Config-Loader");
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setToolTipText("Fill With Config-Author");
        textArea.setBounds(0, 10, 434, 24);
        this.contentPane.add(textArea);
        final JTextArea textArea2 = new JTextArea();
        textArea2.setBackground(Color.WHITE);
        textArea2.setToolTipText("Fill With Config-Name");
        textArea2.setBounds(0, 40, 434, 24);
        this.contentPane.add(textArea2);
        JButton btnNewButton = new JButton("Load");
        btnNewButton.setBounds(10, 74, 103, 25);
        this.contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cloudurl = "http://checker.dpro.site/" + textArea.getText() + "/HWID.txt";
                    ReturnData = WebUtils.get(Cloudurl);
                    if (!ReturnData.contains(Utils.getHWID(false))) {
                        JOptionPane.showMessageDialog(null, "Did Not Bind Your HWID");
                        return;
                    }
                    Cloudurl = "http://checker.dpro.site/" + textArea.getText() + "/" + textArea2.getText() + ".json";
                    ReturnData = WebUtils.get(Cloudurl);
                    Config.loadHacks(ReturnData);
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Null Point");
                    return;
                }
            }
        });
    }

    public static void loadHacks(String parse) {
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject)jsonParser.parse(parse);
            for (Map.Entry entry : jsonObject.entrySet()) {
                Hack hack = HackManager.getHack((String)entry.getKey());
                if (hack == null) continue;
                JsonObject jsonObjectHack = (JsonObject)entry.getValue();
                hack.setKey(jsonObjectHack.get("key").getAsInt());
                hack.setToggled(jsonObjectHack.get("toggled").getAsBoolean());
                if (hack.getValues().isEmpty()) continue;
                for (Value value : hack.getValues()) {
                    if (value instanceof BooleanValue) {
                        value.setValue(jsonObjectHack.get(value.getName()).getAsBoolean());
                    }
                    if (value instanceof NumberValue) {
                        value.setValue(jsonObjectHack.get(value.getName()).getAsDouble());
                    }
                    if (!(value instanceof ModeValue)) continue;
                    ModeValue modeValue = (ModeValue)value;
                    for (Mode mode2 : modeValue.getModes()) {
                        mode2.setToggled(jsonObjectHack.get(mode2.getName()).getAsBoolean());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        jsonParser = new JsonParser();
    }
}

