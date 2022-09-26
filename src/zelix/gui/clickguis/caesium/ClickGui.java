/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 */
package zelix.gui.clickguis.caesium;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.Frame;
import zelix.utils.Wrapper;

public class ClickGui
extends GuiScreen {
    public static int compID = 0;
    private ArrayList<Frame> frames = new ArrayList();
    private final FontRenderer fr = Wrapper.INSTANCE.fontRenderer();

    public ClickGui() {
        compID = 0;
    }

    protected void addFrame(Frame frame) {
        if (!this.frames.contains(frame)) {
            this.frames.add(frame);
        }
    }

    protected ArrayList<Frame> getFrames() {
        return this.frames;
    }

    public void initGui() {
        for (Frame frame : this.frames) {
            frame.initialize();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : this.frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Frame frame : this.frames) {
            frame.keyTyped(keyCode, typedChar);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sR = new ScaledResolution(this.mc);
        this.fr.drawString("Thanks For Using!", 2, sR.getScaledHeight() - this.fr.FONT_HEIGHT, Panel.fontColor);
        for (Frame frame : this.frames) {
            frame.render(mouseX, mouseY);
        }
    }
}

