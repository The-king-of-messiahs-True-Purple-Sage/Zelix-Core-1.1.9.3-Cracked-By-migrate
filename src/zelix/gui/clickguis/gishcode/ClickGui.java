/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector2f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 */
package zelix.gui.clickguis.gishcode;

import java.util.ArrayList;
import javax.vecmath.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.Container;
import zelix.gui.clickguis.gishcode.elements.Frame;
import zelix.gui.clickguis.gishcode.elements.Slider;
import zelix.gui.clickguis.gishcode.theme.Theme;

public class ClickGui
extends ClickGuiScreen {
    private static Theme theme;
    private static ArrayList<Frame> frames;
    private Frame currentFrame;
    private boolean dragging = false;
    private Vector2f draggingOffset;

    public static void renderPinned() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        float scale = (float)scaledResolution.getScaleFactor() / (float)Math.pow(scaledResolution.getScaleFactor(), 2.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)0.0f, (float)0.0f, (float)1000.0f);
        GlStateManager.scale((float)(scale * 2.0f), (float)(scale * 2.0f), (float)(scale * 2.0f));
        for (Frame frame : frames) {
            if (!frame.isPinned()) continue;
            frame.render(mouse[0], mouse[1]);
        }
        GlStateManager.popMatrix();
    }

    public static Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        ClickGui.theme = theme;
    }

    public void render() {
        for (Frame frame : frames) {
            frame.render(mouse[0], mouse[1]);
        }
    }

    public void onMouseUpdate(int x, int y) {
        for (Frame frame : frames) {
            for (Component component2 : frame.getComponents()) {
                if (component2.isMouseOver(x, y)) {
                    component2.onMouseDrag(x, y);
                    continue;
                }
                if (!(component2 instanceof Slider)) continue;
                Slider s = (Slider)component2;
                s.dragging = false;
            }
        }
        if (this.dragging && this.currentFrame != null) {
            int yOffset = (int)((float)y - this.draggingOffset.getY() - (float)this.currentFrame.getY());
            this.currentFrame.setxPos((int)((float)x - this.draggingOffset.getX()));
            this.currentFrame.setyPos((int)((float)y - this.draggingOffset.getY()));
            for (Component component3 : this.currentFrame.getComponents()) {
                component3.setyBase(component3.getyBase() + yOffset);
                if (!(component3 instanceof Container)) continue;
                Container container = (Container)component3;
                int height = 0;
                for (Component component1 : container.getComponents()) {
                    component1.setxPos(component3.getX());
                    component1.setyPos(component3.getY());
                    height += component1.getDimension().height;
                }
            }
        }
    }

    public boolean onMouseScroll(int ammount) {
        boolean overFrame = false;
        for (Frame frame : frames) {
            if (frame.isMouseOver(mouse[0], mouse[1])) {
                frame.scrollFrame(ammount * 4);
                overFrame = true;
            }
            frame.onMouseScroll(ammount * 4);
        }
        return overFrame;
    }

    public void onMouseRelease(int x, int y) {
        for (Frame frame : frames) {
            if (!frame.isMouseOver(x, y)) continue;
            this.currentFrame = frame;
            if (frame.isMouseOverBar(x, y)) {
                this.dragging = false;
            }
            frame.onMouseRelease(x, y, 0);
        }
    }

    public void onMouseClick(int x, int y, int buttonID) {
        for (Frame frame : frames) {
            if (!frame.isMouseOver(x, y)) continue;
            this.currentFrame = frame;
            if (frame.isMouseOverBar(x, y)) {
                this.dragging = true;
                this.draggingOffset = new Vector2f((float)(x - frame.getX()), (float)(y - frame.getY()));
            }
            frame.onMousePress(x, y, buttonID);
        }
    }

    public void onUpdate() {
        for (Frame frame : frames) {
            frame.updateComponents();
        }
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public void onKeyRelease(int eventKey, char eventCharacter) {
        for (Frame frame : frames) {
            frame.onKeyReleased(eventKey, eventCharacter);
        }
    }

    public void onkeyPressed(int eventKey, char eventCharacter) {
        for (Frame frame : frames) {
            frame.onKeyPressed(eventKey, eventCharacter);
        }
    }

    static {
        frames = new ArrayList();
    }
}

