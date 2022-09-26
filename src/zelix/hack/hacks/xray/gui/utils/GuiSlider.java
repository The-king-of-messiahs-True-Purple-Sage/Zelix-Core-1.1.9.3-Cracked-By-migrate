/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  org.lwjgl.opengl.GL11
 */
package zelix.hack.hacks.xray.gui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiSlider
extends GuiButton {
    public float sliderValue;
    private float sliderMaxValue;
    private boolean dragging = false;
    private String label;

    public GuiSlider(int id, int x, int y, String label, float startingValue, float maxValue) {
        super(id, x, y, 202, 20, label);
        this.label = label;
        this.sliderValue = startingValue;
        this.sliderMaxValue = maxValue;
    }

    public int getHoverState(boolean par1) {
        return 0;
    }

    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        if (this.dragging) {
            this.updateValue(par2);
        }
        this.displayString = this.label + ": " + (int)(this.sliderValue * this.sliderMaxValue);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.func_73729_b(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
        this.func_73729_b(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
    }

    private void updateValue(int value) {
        this.sliderValue = (float)(value - (this.xPosition + 4)) / (float)(this.width - 8);
        if (this.sliderValue < 0.0f) {
            this.sliderValue = 0.0f;
        }
        if (this.sliderValue > 1.0f) {
            this.sliderValue = 1.0f;
        }
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.updateValue(par2);
            this.dragging = true;
            return true;
        }
        return false;
    }

    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }
}

