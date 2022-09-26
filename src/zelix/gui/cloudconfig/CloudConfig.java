/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.cloudconfig;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import zelix.command.Config;
import zelix.gui.clickguis.caesium.components.ConfigFrame;
import zelix.hack.Hack;
import zelix.managers.FontManager;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.HGLUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class CloudConfig
extends GuiScreen {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ScaledResolution sr = new ScaledResolution(mc);
    public static float startX = sr.getScaledWidth() / 2 - 225;
    public static float startY = sr.getScaledHeight() / 2 - 150;
    public float moveX = 0.0f;
    public float moveY = 0.0f;
    public static FontManager font = new FontManager();
    public static ConfigFrame[] NEW;
    public static ConfigFrame[] OWN;
    public static String Mode;
    public static String verify;
    public boolean LoadButton_isHovered = false;
    public String str = "";
    public static int pages;
    public int nowpage = 1;
    int ConfigNumbers;
    private static JsonParser jsonParser;

    public void initGui() {
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int i;
        for (i = 0; i < NEW.length; ++i) {
            if (NEW[i] == null || i / 6 + 1 != this.nowpage) continue;
            int x = i - (this.nowpage - 1) * 6;
            if (x > 2) {
                NEW[i].mouseClicked(mouseX, mouseY, mouseButton);
                continue;
            }
            NEW[i].mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (CloudConfig.isHovered(startX + 10.0f, startY + 280.0f, startX + 100.0f, startY + 300.0f, mouseX, mouseY) && mouseButton == 0) {
            int page;
            this.nowpage = 1;
            if (Mode == "All") {
                Mode = "Own";
                OWN = new ConfigFrame[NEW.length];
                for (i = 0; i < OWN.length; ++i) {
                    if (OWN[i] == null) continue;
                    page = i / 6;
                    pages = page + 1;
                }
            } else {
                Mode = "All";
                for (i = 0; i < NEW.length; ++i) {
                    if (NEW[i] == null) continue;
                    page = i / 6;
                    pages = page + 1;
                }
            }
        }
        if (pages > 1) {
            if (this.nowpage == 1) {
                if (CloudConfig.isHovered(startX + 210.0f, startY + 280.0f, startX + 230.0f, startY + 300.0f, mouseX, mouseY) && mouseButton == 0) {
                    ++this.nowpage;
                }
            } else if (this.nowpage == pages) {
                if (CloudConfig.isHovered(startX + 170.0f, startY + 280.0f, startX + 190.0f, startY + 300.0f, mouseX, mouseY) && mouseButton == 0) {
                    --this.nowpage;
                }
            } else {
                if (CloudConfig.isHovered(startX + 210.0f, startY + 280.0f, startX + 230.0f, startY + 300.0f, mouseX, mouseY) && mouseButton == 0) {
                    ++this.nowpage;
                }
                if (CloudConfig.isHovered(startX + 170.0f, startY + 280.0f, startX + 190.0f, startY + 300.0f, mouseX, mouseY) && mouseButton == 0) {
                    --this.nowpage;
                }
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int x;
        int i;
        RenderUtils.drawRect(startX, startY, startX + 400.0f, startY + 310.0f, ColorUtils.getColor(96, 96, 96));
        font.getFont("SFR 14").drawString("Zelix Cloud Config Loader", startX + 10.0f, startY + 10.0f, ColorUtils.rainbow().getRGB());
        HGLUtils.drawGradientRect(startX + 10.0f, startY + 30.0f, startX + 300.0f, startY + 40.0f + font.getFont("SFR 14").getHeight("HHHHHHH"), ColorUtils.getColor(224, 224, 224));
        font.getFont("SFR 14").drawString(this.str, startX + 10.0f, startY + 35.0f, ColorUtils.getColor(32, 32, 32));
        RenderUtils.drawRect(startX + 320.0f, startY + 30.0f, startX + 390.0f, startY + 40.0f + font.getFont("SFR 14").getHeight("HHHHHHH"), this.LoadButton_isHovered ? ColorUtils.getColor(240, 240, 240) : ColorUtils.getColor(224, 224, 224));
        font.getFont("SFR 14").drawString("Load", startX + 345.0f, startY + 35.0f, ColorUtils.getColor(32, 32, 32));
        font.getFont("SFR 11").drawString("Click [LOAD] Button To Choose Your Private Config Verification File And Load It!", startX + 10.0f, startY + 50.0f, ColorUtils.getColor(32, 32, 32));
        if ((CloudConfig.isHovered(startX, startY, startX + 450.0f, startY + 50.0f, mouseX, mouseY) || CloudConfig.isHovered(startX, startY + 315.0f, startX + 450.0f, startY + 350.0f, mouseX, mouseY) || CloudConfig.isHovered(startX + 430.0f, startY, startX + 450.0f, startY + 350.0f, mouseX, mouseY)) && Mouse.isButtonDown((int)0)) {
            if (this.moveX == 0.0f && this.moveY == 0.0f) {
                this.moveX = (float)mouseX - startX;
                this.moveY = (float)mouseY - startY;
            } else {
                startX = (float)mouseX - this.moveX;
                startY = (float)mouseY - this.moveY;
            }
        } else if (this.moveX != 0.0f || this.moveY != 0.0f) {
            this.moveX = 0.0f;
            this.moveY = 0.0f;
        }
        if (CloudConfig.isHovered(startX + 320.0f, startY + 30.0f, startX + 390.0f, startY + 40.0f + font.getFont("SFR 14").getHeight("HHHHHHH"), mouseX, mouseY)) {
            JFrame frame;
            JFileChooser chooser;
            int i2;
            this.LoadButton_isHovered = true;
            if (Mouse.isButtonDown((int)0) && (i2 = (chooser = new JFileChooser(".")).showOpenDialog(frame = new JFrame())) == 0) {
                this.str = Utils.readFile(chooser.getSelectedFile().getAbsolutePath());
            }
        } else {
            this.LoadButton_isHovered = false;
        }
        int y = 0;
        for (i = 0; i < NEW.length; ++i) {
            if (NEW[i] == null) continue;
            if (Mode == "Own") {
                if (!NEW[i].isUserDev()) continue;
                CloudConfig.OWN[y] = NEW[i];
                ++y;
                continue;
            }
            if (i / 6 + 1 != this.nowpage) continue;
            x = i - (this.nowpage - 1) * 6;
            if (x > 2) {
                NEW[i].render((int)startX + 10 + (x - 3) * 10 + (x - 3) * 120, (int)startY + 65 + 110, 0, mouseX, mouseY);
                continue;
            }
            NEW[i].render((int)startX + 10 + x * 10 + x * 120, (int)startY + 65, 0, mouseX, mouseY);
        }
        if (Mode == "Own") {
            for (i = 0; i < OWN.length; ++i) {
                if (OWN[i] == null || i / 6 + 1 != this.nowpage) continue;
                x = i - (this.nowpage - 1) * 6;
                if (x > 2) {
                    OWN[i].render((int)startX + 10 + (x - 3) * 10 + (x - 3) * 120, (int)startY + 65 + 110, 0, mouseX, mouseY);
                    continue;
                }
                OWN[i].render((int)startX + 10 + x * 10 + x * 120, (int)startY + 65, 0, mouseX, mouseY);
            }
        }
        font.getFont("SFR 14").drawString(String.valueOf(this.nowpage), startX + 200.0f, startY + 285.0f, ColorUtils.rainbow().getRGB());
        if (pages > 1) {
            if (this.nowpage == 1) {
                RenderUtils.drawRect(startX + 210.0f, startY + 280.0f, startX + 230.0f, startY + 300.0f, ColorUtils.getColor(224, 224, 224));
            } else if (this.nowpage == pages) {
                RenderUtils.drawRect(startX + 170.0f, startY + 280.0f, startX + 190.0f, startY + 300.0f, ColorUtils.getColor(224, 224, 224));
            } else {
                RenderUtils.drawRect(startX + 175.0f, startY + 280.0f, startX + 195.0f, startY + 300.0f, ColorUtils.getColor(224, 224, 224));
                RenderUtils.drawRect(startX + 210.0f, startY + 280.0f, startX + 230.0f, startY + 300.0f, ColorUtils.getColor(224, 224, 224));
            }
        }
        RenderUtils.drawRect(startX + 10.0f, startY + 280.0f, startX + 100.0f, startY + 300.0f, CloudConfig.isHovered(startX + 10.0f, startY + 280.0f, startX + 100.0f, startY + 300.0f, mouseX, mouseY) ? new Color(80, 80, 80, 121).getRGB() : new Color(56, 56, 56, 121).getRGB());
        font.getFont("SFR 14").drawString(Mode, startX + 30.0f, startY + 285.0f, ColorUtils.rainbow().getRGB());
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }

    public void drawConfigList() {
    }

    public static void LoadConfig(String UUID) {
        String Config2 = Config.Send("121.62.61.198", 9990, verify + "[TARGET][LOAD][UUID][" + UUID + "]");
        if (Config2 != null) {
            CloudConfig.loadHacks(Config2);
        } else {
            ChatUtils.error("null config");
        }
    }

    public static void UpdateConfig(String UUID, String Name) {
        String Config2 = Config.Send("121.62.61.198", 9990, verify + "[TARGET][UPDATE][NAME][" + Name + "][UUID][" + UUID + "][IN][" + CloudConfig.GETIN() + "]");
        ChatUtils.message(Config2);
    }

    public static void UploadConfig(final String Name) {
        new Thread(){

            @Override
            public void run() {
                String s = verify + "[TARGET][UPLOAD][NAME][" + Name + "][IN][" + CloudConfig.GETIN() + "]";
                String Config2 = Config.Send("121.62.61.198", 9990, s);
                ChatUtils.message(Config2);
                super.run();
            }
        }.start();
    }

    public static void loadHacks(String IN) {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(IN));
        JsonObject jsonObject = (JsonObject)jsonParser.parse((Reader)bufferedReader);
        try {
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry entry : jsonObject.entrySet()) {
            Hack module = HackManager.getHack((String)entry.getKey());
            if (module == null) continue;
            JsonObject jsonObjectHack = (JsonObject)entry.getValue();
            module.setKey(jsonObjectHack.get("key").getAsInt());
            module.setToggled(jsonObjectHack.get("toggled").getAsBoolean());
            if (module.getValues().isEmpty()) continue;
            for (Value value : module.getValues()) {
                if (value == null) continue;
                if (jsonObjectHack.get(value.getName()) == null) {
                    if (value instanceof BooleanValue) {
                        jsonObjectHack.addProperty(value.getName(), (Boolean)value.getValue());
                    }
                    if (value instanceof NumberValue) {
                        jsonObjectHack.addProperty(value.getName(), (Number)value.getValue());
                    }
                    if (value instanceof ModeValue) {
                        jsonObjectHack.addProperty(value.getName(), (Boolean)value.getValue());
                    }
                }
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
        ChatUtils.message("Loaded!");
    }

    public static String GETIN() {
        try {
            JsonObject json = new JsonObject();
            for (Hack module : HackManager.getHacks()) {
                JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("toggled", Boolean.valueOf(module.isToggled()));
                jsonHack.addProperty("key", (Number)module.getKey());
                if (!module.getValues().isEmpty()) {
                    for (Value value : module.getValues()) {
                        if (value instanceof BooleanValue) {
                            jsonHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        if (value instanceof NumberValue) {
                            jsonHack.addProperty(value.getName(), (Number)value.getValue());
                        }
                        if (!(value instanceof ModeValue)) continue;
                        ModeValue modeValue = (ModeValue)value;
                        for (Mode mode2 : modeValue.getModes()) {
                            jsonHack.addProperty(mode2.getName(), Boolean.valueOf(mode2.isToggled()));
                        }
                    }
                }
                json.add(module.getName(), (JsonElement)jsonHack);
            }
            return json.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        OWN = new ConfigFrame[1024];
        Mode = "All";
        verify = "";
        jsonParser = new JsonParser();
    }
}

