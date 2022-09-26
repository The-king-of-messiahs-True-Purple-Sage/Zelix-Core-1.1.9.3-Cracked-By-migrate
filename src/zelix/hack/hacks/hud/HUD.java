/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.inventory.Slot
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 */
package zelix.hack.hacks.hud;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import zelix.Core;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.ClickGui;
import zelix.hack.hacks.xray.gui.GuiOverlay;
import zelix.managers.FontManager;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.Colors;
import zelix.utils.hooks.visual.GuiRenderUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.utils.hooks.visual.font.render.GlStateManager;
import zelix.utils.tenacityutils.render.ColorUtil;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class HUD
extends Hack {
    public static Minecraft mc = Wrapper.INSTANCE.mc();
    public BooleanValue effects;
    public BooleanValue SessionInfo;
    public BooleanValue Inentory;
    public BooleanValue notification;
    public BooleanValue MUSICPLAYER;
    public BooleanValue ListRainBow;
    public BooleanValue ListBackRainBow;
    public BooleanValue OraginRainBow;
    public BooleanValue ListBackVis;
    public BooleanValue ListBoxAlpha;
    public ModeValue HUDmode;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    public static FontManager font = new FontManager();
    public ColorUtils c = new ColorUtils();
    private ResourceLocation logo = new ResourceLocation("logo.png");
    int rainbowTick = 0;
    NumberValue invx;
    NumberValue ListBackTick;
    NumberValue ListOnUP;
    NumberValue ListAlpha;
    Boolean create = false;
    NumberValue invy;
    public BufferedImage trayIcon;
    public SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public HUD() {
        super("HUD", HackCategory.VISUAL);
        this.setToggled(true);
        this.setShow(false);
        this.effects = new BooleanValue("Effects", false);
        this.SessionInfo = new BooleanValue("SessionInfo", false);
        this.Inentory = new BooleanValue("Inentory", false);
        this.invx = new NumberValue("InvX", 10.0, 0.0, 100.0);
        this.invy = new NumberValue("InvY", 20.0, 0.0, 100.0);
        this.notification = new BooleanValue("Notification", false);
        this.HUDmode = new ModeValue("Mode", new Mode("Logo", false), new Mode("Normal", true), new Mode("OTC", false));
        this.ListRainBow = new BooleanValue("New ListRainbow", false);
        this.ListBackRainBow = new BooleanValue("New ListBackRainBow", false);
        this.ListBackTick = new NumberValue("ListBackTick", 80.0, -60.0, 500.0);
        this.ListOnUP = new NumberValue("ListOnUP", 1.5, 1.5, 10.0);
        this.OraginRainBow = new BooleanValue("OraginRainBow", false);
        this.ListBackVis = new BooleanValue("ListBackVis", false);
        this.ListBoxAlpha = new BooleanValue("isListBoxAlpha", true);
        this.ListAlpha = new NumberValue("ListAlpha", 0.35, 0.1, 1.0);
        this.addValue(this.effects, this.invx, this.invy, this.Inentory, this.notification, this.HUDmode, this.ListRainBow, this.ListBackRainBow, this.ListBackTick, this.ListOnUP, this.OraginRainBow, this.ListBackVis, this.ListBoxAlpha, this.ListAlpha);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public String getDescription() {
        return "Heads-Up Display.";
    }

    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        int heightFPS;
        int rainbowTickc = 0;
        if (((Boolean)this.notification.getValue()).booleanValue()) {
            // empty if block
        }
        if (((Boolean)this.Inentory.getValue()).booleanValue()) {
            this.drawInventory();
        }
        if (this.HUDmode.getMode("Logo").isToggled()) {
            RenderUtils.drawImage(this.logo, 2, 2, 100, 100);
        } else if (this.HUDmode.getMode("Normal").isToggled()) {
            HUD hUD = this;
            HUD hUD2 = this;
            HUD hUD3 = this;
            font.getFont("SFR 11").drawStringWithShadow("Z", 2.0f, 2.0f, new Color(hUD.c.rainbow().getRed(), hUD2.c.rainbow().getGreen(), hUD3.c.rainbow().getBlue()).getRGB());
            font.getFont("SFR 11").drawStringWithShadow("elix Cracked By The king of messiahs True Purple Sage Team - migrate Github: https://github.com/The-king-of-messiahs-True-Purple-Sage(" + this.sdf.format(new Date()) + ")", font.getFont("SFR 11").getStringWidth("Z") + 6, 2.0f, -1);
        } else if (this.HUDmode.getMode("OTC").isToggled()) {
            String test = "AutoWidthTest";
            String serverip = mc.isSingleplayer() ? "singleplayer" : (!HUD.mc.getCurrentServerData().serverIP.contains(":") ? HUD.mc.getCurrentServerData().serverIP + ":25565" : HUD.mc.getCurrentServerData().serverIP);
            String info = "Zelix | " + Core.UN + " | " + Minecraft.getDebugFPS() + " fps | " + serverip + " | " + this.formatter.format(new Date());
            GuiRenderUtils.drawRect(5.0f, 5.0f, font.getFont("SFR 5").getWidth(info) + 4.0f, 13.0f, new Color(40, 40, 40));
            GuiRenderUtils.drawRoundedRect(5.0f, 5.0f, font.getFont("SFR 5").getWidth(info) + 4.0f, 1.0f, 1.0f, new Color(255, 191, 0).getRGB(), 1.0f, new Color(255, 191, 0).getRGB());
            font.getFont("SFR 5").drawStringWithShadow(info, 7.0f, 7.0f, Colors.WHITE.c);
        } else if (this.HUDmode.getMode("GameSense").isToggled()) {
            // empty if block
        }
        ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
        int index = ((Double)this.invx.getValue()).intValue();
        int index_right = ((Double)this.invy.getValue()).intValue();
        int DrawColor = -1;
        ArrayList<Hack> hacks = new ArrayList<Hack>();
        hacks.addAll(HackManager.hacks);
        double x = Wrapper.INSTANCE.player().posX;
        double y = Wrapper.INSTANCE.player().posY;
        double z = Wrapper.INSTANCE.player().posZ;
        String coords = String.format("\u00a77X: \u00a7f%s \u00a77Y: \u00a7f%s \u00a77Z: \u00a7f%s", RenderUtils.DF((float)x, 1), RenderUtils.DF((float)y, 1), RenderUtils.DF((float)z, 1));
        boolean isChatOpen = Wrapper.INSTANCE.mc().currentScreen instanceof GuiChat;
        int heightCoords = isChatOpen ? sr.getScaledHeight() - 25 : sr.getScaledHeight() - 10;
        int colorRect = ColorUtils.color(0.0f, 0.0f, 0.0f, 0.0f);
        int colorRect2 = ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f);
        int n = heightFPS = isChatOpen ? sr.getScaledHeight() - 37 : sr.getScaledHeight() - 22;
        if (!ClickGui.language.getMode("Chinese").isToggled()) {
            for (Hack h : HackManager.getSortedHacks3()) {
                String modeName = "";
                if (!h.isToggled()) continue;
                if (++rainbowTickc > 100) {
                    rainbowTickc = 0;
                }
                if (h.getName().equals("Xray")) {
                    modeName = modeName + " \u00a77" + GuiOverlay.XrayStr;
                }
                for (Value value : h.getValues()) {
                    ModeValue modeValue;
                    if (!(value instanceof ModeValue) || !(modeValue = (ModeValue)value).getModeName().equals("Mode")) continue;
                    for (Mode mode2 : modeValue.getModes()) {
                        if (!mode2.isToggled()) continue;
                        modeName = modeName + " \u00a77" + mode2.getName();
                    }
                }
                int yPos = 18;
                int xPos = 4;
                xPos = 6;
                Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / 40.0 * 1.4)) % 1.0f, 1.0f, 1.0f));
                Color m_Backrainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / (Double)this.ListBackTick.getValue() * (Double)this.ListOnUP.getValue())) % 1.0f, 1.0f, 1.0f));
                if (((Boolean)this.ListRainBow.getValue()).booleanValue()) {
                    DrawColor = rainbow.getRGB();
                }
                if (((Boolean)this.ListBackVis.getValue()).booleanValue()) {
                    if (((Boolean)this.ListBoxAlpha.getValue()).booleanValue()) {
                        float alphaAnimation = 1.0f;
                        int SDrawColor = ColorUtil.applyOpacity(new Color(10, 10, 10), (float)((Double)this.ListAlpha.getValue() * (double)alphaAnimation)).getRGB();
                        RenderUtil.drawBorderedRect((float)sr.getScaledWidth() - font.getFont("SFB 6").getWidth(h.getName() + modeName) - (float)index_right - 4.0f, (double)index + 1.6, sr.getScaledWidth() - index_right, (double)index + 13.6, 0.0f, 0, SDrawColor);
                    } else if (((Boolean)this.ListBackRainBow.getValue()).booleanValue()) {
                        if (((Boolean)this.OraginRainBow.getValue()).booleanValue()) {
                            RenderUtil.drawBorderedRect((float)sr.getScaledWidth() - font.getFont("SFB 6").getWidth(h.getName() + modeName) - (float)index_right - 4.0f, index + 1, sr.getScaledWidth() - index_right, (double)index + 13.6, 1.0f, ColorUtils.rainbow((int)((Double)this.ListOnUP.getValue() * 10.0)), ColorUtils.rainbow((int)((Double)this.ListOnUP.getValue() * 10.0)));
                        } else {
                            RenderUtil.drawBorderedRect((float)sr.getScaledWidth() - font.getFont("SFB 6").getWidth(h.getName() + modeName) - (float)index_right - 4.0f, index + 1, sr.getScaledWidth() - index_right, (double)index + 13.6, 1.0f, m_Backrainbow.getRGB(), m_Backrainbow.getRGB());
                        }
                    } else {
                        RenderUtil.drawBorderedRect((float)sr.getScaledWidth() - font.getFont("SFB 6").getWidth(h.getName() + modeName) - (float)index_right - 4.0f, index + 1, sr.getScaledWidth() - index_right, (double)index + 13.6, 1.0f, -14869217, -14869217);
                    }
                }
                font.getFont("SFB 6").drawString(h.getName() + modeName, (float)sr.getScaledWidth() - font.getFont("SFB 6").getWidth(h.getName() + modeName) - (float)index_right - 2.0f, index + 2, DrawColor);
                index = (int)((float)index + (font.getFont("SFB 6").getHeight(h.getName()) + 3.0f));
            }
        } else {
            for (Hack h : HackManager.getSortedHacks()) {
                String modeName = "";
                if (!h.isToggled()) continue;
                if (++rainbowTickc > 100) {
                    rainbowTickc = 0;
                }
                if (h.getName().equals("Xray") && !GuiOverlay.XrayStr.equals("")) {
                    modeName = modeName + " \u00a77" + GuiOverlay.XrayStr;
                }
                for (Value value : h.getValues()) {
                    ModeValue modeValue;
                    if (!(value instanceof ModeValue) || !(modeValue = (ModeValue)value).getModeName().equals("Mode")) continue;
                    for (Mode mode3 : modeValue.getModes()) {
                        if (!mode3.isToggled()) continue;
                        modeName = modeName + " \u00a77" + mode3.getName();
                    }
                }
                int yPos = 18;
                int xPos = 4;
                xPos = 6;
                Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / 40.0 * 1.4)) % 1.0f, 1.0f, 1.0f));
                Color m_Backrainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0 - Math.sin((double)rainbowTickc / (Double)this.ListBackTick.getValue() * (Double)this.ListOnUP.getValue())) % 1.0f, 1.0f, 1.0f));
                if (((Boolean)this.ListRainBow.getValue()).booleanValue()) {
                    DrawColor = rainbow.getRGB();
                }
                if (((Boolean)this.ListBackVis.getValue()).booleanValue()) {
                    if (((Boolean)this.ListBoxAlpha.getValue()).booleanValue()) {
                        float alphaAnimation = 1.0f;
                        int SDrawColor = ColorUtil.applyOpacity(new Color(10, 10, 10), (float)((Double)this.ListAlpha.getValue() * (double)alphaAnimation)).getRGB();
                        RenderUtil.drawBorderedRect(sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(h.getRenderName() + modeName) - index_right - 4, (double)index + 1.6, sr.getScaledWidth() - index_right, (double)index + 13.6, 0.0f, 0, SDrawColor);
                    } else if (((Boolean)this.ListBackRainBow.getValue()).booleanValue()) {
                        if (((Boolean)this.OraginRainBow.getValue()).booleanValue()) {
                            RenderUtil.drawBorderedRect(sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(h.getRenderName() + modeName) - index_right - 4, index + 1, sr.getScaledWidth() - index_right, (double)index + 13.6, 1.0f, ColorUtils.rainbow((int)((Double)this.ListOnUP.getValue() * 10.0)), ColorUtils.rainbow((int)((Double)this.ListOnUP.getValue() * 10.0)));
                        } else {
                            RenderUtil.drawBorderedRect(sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(h.getRenderName() + modeName) - index_right - 4, index + 1, sr.getScaledWidth() - index_right, (double)index + 13.6, 1.0f, m_Backrainbow.getRGB(), m_Backrainbow.getRGB());
                        }
                    } else {
                        RenderUtil.drawBorderedRect(sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(h.getRenderName() + modeName) - index_right - 4, index + 1, sr.getScaledWidth() - index_right, (double)index + 13.6, 1.0f, -14869217, -14869217);
                    }
                }
                Wrapper.INSTANCE.fontRenderer().drawString(h.getRenderName() + modeName, sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(h.getRenderName() + modeName) - index_right - 2, index + 2, DrawColor);
                index += Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT + 3;
            }
        }
        super.onRenderGameOverlay(event);
    }

    public void drawInventory() {
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        int x = 5;
        int y = 22;
        Gui.drawRect((int)x, (int)y, (int)(x + 167), (int)(y + 73), (int)new Color(29, 29, 29, 255).getRGB());
        Gui.drawRect((int)(x + 1), (int)(y + 13), (int)(x + 166), (int)(y + 72), (int)new Color(40, 40, 40, 255).getRGB());
        FontLoaders.default16.drawString("Your Inventory", x + 3, y + 3, -1, true);
        boolean hasStacks = false;
        for (int i1 = 9; i1 < Wrapper.INSTANCE.player().inventoryContainer.inventorySlots.size() - 9; ++i1) {
            Slot slot = (Slot)Wrapper.INSTANCE.player().inventoryContainer.inventorySlots.get(i1);
            if (slot.getHasStack()) {
                hasStacks = true;
            }
            int i = slot.xDisplayPosition;
            int j = slot.yDisplayPosition;
            mc.func_175599_af().func_180450_b(slot.getStack(), x + i - 4, y + j - 68);
            mc.func_175599_af().func_180453_a(Wrapper.INSTANCE.fontRenderer(), slot.getStack(), x + i - 4, y + j - 68, null);
        }
        if (HUD.mc.currentScreen instanceof GuiInventory) {
            FontLoaders.default16.drawString("Already in inventory", x + 83 - FontLoaders.default16.getStringWidth("Already in inventory") / 2, y + 36, -1, true);
        } else if (!hasStacks) {
            FontLoaders.default16.drawString("Empty...", x + 83 - FontLoaders.default16.getStringWidth("Empty...") / 2, y + 36, -1, true);
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableDepth();
    }
}

