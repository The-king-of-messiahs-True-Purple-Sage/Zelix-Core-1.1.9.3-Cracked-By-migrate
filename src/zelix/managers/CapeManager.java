/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.util.ResourceLocation
 */
package zelix.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import zelix.command.Config;
import zelix.gui.cloudconfig.CloudConfig;
import zelix.utils.Cr4sh;
import zelix.utils.Utils;

public class CapeManager {
    private HashMap<String, ResourceLocation> CapeUsers = new HashMap();
    private HashMap<String, ResourceLocation> Capes = new HashMap();

    public CapeManager() {
        if (Minecraft.getMinecraft().thePlayer != null) {
            this.verify();
        }
    }

    public void verify() {
        String PacketS = CloudConfig.verify + "[TARGET][CAPE][gameid][" + Minecraft.getMinecraft().thePlayer.getName() + "]";
        String str = Config.Send("121.62.61.198", 9990, PacketS);
        switch (str.hashCode()) {
            case 483552411: {
                this.LoadCapes();
                break;
            }
            case 1298389123: {
                new Cr4sh();
                break;
            }
            default: {
                new Cr4sh();
            }
        }
    }

    public void LoadCapes() {
        try {
            String l_Line;
            URL l_URL = null;
            URLConnection l_Connection = null;
            BufferedReader l_Reader = null;
            l_URL = new URL("http://121.62.61.198/cape/cape.txt");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            while ((l_Line = l_Reader.readLine()) != null) {
                String[] l_Split = l_Line.split(" ");
                if (l_Split.length != 2) continue;
                this.DownloadCapeFromLocationWithName(l_Split[0], l_Split[1]);
            }
            l_Reader.close();
            l_URL = new URL("http://121.62.61.198/cape/capes.txt");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            while ((l_Line = l_Reader.readLine()) != null) {
                this.ProcessCapeString(l_Line);
            }
            l_Reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            String l_Line;
            URL l_URL = null;
            URLConnection l_Connection = null;
            BufferedReader l_Reader = null;
            l_URL = new URL("http://121.62.61.198/cape/cape.txt");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            while ((l_Line = l_Reader.readLine()) != null) {
                String[] l_Split = l_Line.split(" ");
                if (l_Split.length != 2) continue;
                this.DownloadCapeFromLocationWithName(l_Split[0], l_Split[1]);
            }
            l_Reader.close();
            l_URL = new URL("http://121.62.61.198/cape/capes.txt");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            while ((l_Line = l_Reader.readLine()) != null) {
                this.ProcessCapeString(l_Line);
            }
            l_Reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ProcessCapeString(String p_String) {
        ResourceLocation l_Cape;
        String[] l_Split = p_String.split(" ");
        if (l_Split.length == 2 && (l_Cape = this.GetCapeFromName(l_Split[1])) != null) {
            this.CapeUsers.put(Utils.getSubString(l_Split[0], "{", "}"), l_Cape);
        }
    }

    private final ResourceLocation GetCapeFromName(String p_Name) {
        if (!this.Capes.containsKey(p_Name)) {
            return null;
        }
        return this.Capes.get(p_Name);
    }

    public void DownloadCapeFromLocationWithName(String p_Link, String p_Name) throws IOException {
        DynamicTexture l_Texture = new DynamicTexture(ImageIO.read(new URL(p_Link)));
        this.Capes.put(p_Name, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("modlogo", l_Texture));
    }

    public ResourceLocation getCapeByName(String name) {
        if (!this.CapeUsers.containsKey(name)) {
            return null;
        }
        return this.CapeUsers.get(name);
    }
}

