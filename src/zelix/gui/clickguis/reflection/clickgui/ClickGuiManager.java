/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 */
package zelix.gui.clickguis.reflection.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import zelix.gui.clickguis.reflection.clickgui.Effect;
import zelix.gui.clickguis.reflection.clickgui.component.Component;
import zelix.gui.clickguis.reflection.clickgui.component.Frame;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;

public class ClickGuiManager
extends GuiScreen {
    public static ArrayList<Frame> frames;
    public static int color;
    private ArrayList<Effect> effectList = new ArrayList();

    public ClickGuiManager() {
        frames = new ArrayList();
        int frameX = 2;
        for (HackCategory category : HackCategory.values()) {
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth();
        }
    }

    public void initGui() {
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        Wrapper.INSTANCE.mc().fontRendererObj.drawStringWithShadow("Press \"R\" to restore the position of frames", (float)((sr.getScaledWidth() - Wrapper.INSTANCE.mc().fontRendererObj.getStringWidth("Press \"R\" to restore the position of frames")) * 2 + Wrapper.INSTANCE.mc().fontRendererObj.getStringWidth("Press \"R\" to restore the position of frames") - 5), (float)((sr.getScaledHeight() - Wrapper.INSTANCE.mc().fontRendererObj.FONT_HEIGHT - 1) * 2), -1);
        for (Frame frame : frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            for (Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component2 : frame.getComponents()) {
                component2.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        for (Frame frame : frames) {
            if (!frame.isOpen() || keyCode == 1 || frame.getComponents().isEmpty()) continue;
            for (Component component2 : frame.getComponents()) {
                component2.keyTyped(typedChar, keyCode);
            }
        }
        if (keyCode == 19) {
            frames = new ArrayList();
            int frameX = 2;
            for (HackCategory category : HackCategory.values()) {
                Frame frame = new Frame(category);
                ScaledResolution sr = new ScaledResolution(this.mc);
                if (sr.getScaledWidth() < frame.getWidth() * 7) continue;
                frame.setX(frameX);
                frames.add(frame);
                frameX += frame.getWidth();
            }
        }
        if (keyCode == 1) {
            Wrapper.INSTANCE.mc().displayGuiScreen(null);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) {
            frame.setDrag(false);
        }
        for (Frame frame : frames) {
            if (!frame.isOpen() || frame.getComponents().isEmpty()) continue;
            for (Component component2 : frame.getComponents()) {
                component2.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return true;
    }
}

