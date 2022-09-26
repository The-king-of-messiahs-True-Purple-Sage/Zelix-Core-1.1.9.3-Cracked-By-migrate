/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.Display
 */
package zelix.hack.hacks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.Display;
import zelix.gui.clickguis.caesium.components.ConfigFrame;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;

public class CloudConfig
extends Hack {
    public CloudConfig() {
        super("CloudConfig", HackCategory.ANOTHER);
    }

    @Override
    public void onEnable() {
        String IP = "121.62.61.198";
        int Port = 9990;
        String Message = zelix.gui.cloudconfig.CloudConfig.verify + "[TARGET][GETCFGLIST]";
        try {
            Socket socket = new Socket(IP, Port);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops);
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(Message);
            bw.flush();
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String s = null;
            while ((s = br.readLine()) != null) {
                String[] var1 = s.split("@-@-@");
                zelix.gui.cloudconfig.CloudConfig.NEW = new ConfigFrame[var1.length];
                for (int i = 0; i < var1.length; ++i) {
                    String[] var2 = var1[i].split("=");
                    if (var2.length == 3) {
                        try {
                            zelix.gui.cloudconfig.CloudConfig.NEW[i] = new ConfigFrame(var2[0], var2[1], var2[2]);
                        }
                        catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                    int page = i / 6;
                    zelix.gui.cloudconfig.CloudConfig.pages = page + 1;
                }
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new zelix.gui.cloudconfig.CloudConfig());
                this.setToggled(false);
            }
            socket.close();
        }
        catch (Exception e) {
            Display.setTitle((String)"Failed Connect to The Server(0x66FF)");
            e.printStackTrace();
        }
    }
}

