/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 */
package zelix.gui.clickguis.caesium;

import java.awt.Color;
import java.util.HashMap;
import net.minecraft.client.gui.FontRenderer;
import zelix.Core;
import zelix.gui.clickguis.caesium.ClickGui;
import zelix.gui.clickguis.caesium.components.Frame;
import zelix.gui.clickguis.caesium.components.GuiButton;
import zelix.gui.clickguis.caesium.components.GuiFrame;
import zelix.gui.clickguis.caesium.listeners.ClickListener;
import zelix.gui.clickguis.caesium.listeners.ComponentsListener;
import zelix.gui.clickguis.caesium.util.FramePosition;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.Wrapper;
import zelix.utils.resourceloader.Strings;

public class Panel
extends ClickGui {
    public static HashMap<String, FramePosition> framePositions = new HashMap();
    public static FontRenderer fR = Wrapper.INSTANCE.fontRenderer();
    public static String theme;
    public static int FRAME_WIDTH;
    public static int color;
    public static int fontColor;
    public static int grey40_240;
    public static int black195;
    public static int black100;

    public Panel(String theme, int fontSize) {
        Panel.theme = theme;
    }

    @Override
    public void initGui() {
        int x = 25;
        int n = 0;
        for (HackCategory cat : HackCategory.values()) {
            GuiFrame frame;
            if (framePositions.containsKey(cat.name())) {
                FramePosition curPos = framePositions.get(cat.name());
                frame = new GuiFrame(cat.name(), curPos.getPosX(), curPos.getPosY(), curPos.isExpanded(), Strings.Chinese_HackCategory.split("=")[n]);
            } else {
                frame = new GuiFrame(cat.name(), x, 50, true, Strings.Chinese_HackCategory.split("=")[n]);
            }
            for (Hack m : Core.hackManager.getHacks()) {
                if (cat != m.getCategory()) continue;
                GuiButton button = new GuiButton(m);
                button.addClickListener(new ClickListener(button));
                button.addExtendListener(new ComponentsListener(button));
                frame.addButton(button);
            }
            this.addFrame(frame);
            x += 140;
            ++n;
        }
        super.initGui();
    }

    public void onGuiClosed() {
        if (!this.getFrames().isEmpty()) {
            for (Frame frame : this.getFrames()) {
                GuiFrame guiFrame = (GuiFrame)frame;
                framePositions.put(guiFrame.getTitle(), new FramePosition(guiFrame.getPosX(), guiFrame.getPosY(), guiFrame.isExpaned()));
            }
        }
        HackManager.getHack("ClickGui").onDisable();
    }

    static {
        FRAME_WIDTH = 100;
        color = new Color(193, 105, 170, 220).getRGB();
        fontColor = Color.white.getRGB();
        grey40_240 = new Color(40, 40, 40, 140).getRGB();
        black195 = new Color(0, 0, 0, 195).getRGB();
        black100 = new Color(0, 0, 0, 100).getRGB();
    }
}

