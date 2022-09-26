/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.caesium.components;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import zelix.gui.clickguis.caesium.Panel;
import zelix.gui.clickguis.caesium.components.GuiComponent;
import zelix.gui.clickguis.caesium.components.listeners.ValueListener;
import zelix.gui.clickguis.caesium.util.MathUtil;
import zelix.gui.clickguis.caesium.util.RenderUtil;
import zelix.value.NumberValue;

public class GuiSlider
implements GuiComponent {
    private static int dragId = -1;
    private int round;
    private int id;
    private int width;
    private int posX;
    private int posY;
    private double min;
    private double max;
    private double current;
    private double c;
    private boolean wasSliding;
    private NumberValue text;
    private ArrayList<ValueListener> valueListeners = new ArrayList();

    public GuiSlider(NumberValue text, double min, double max, double current, int round) {
        this.text = text;
        this.min = min;
        this.max = max;
        this.current = current;
        this.c = current / max;
        this.round = round;
        this.id = ++Panel.compID;
    }

    public void addValueListener(ValueListener vallistener) {
        this.valueListeners.add(vallistener);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        boolean hovered = RenderUtil.isHovered(posX, posY, width, this.getHeight(), mouseX, mouseY);
        if (Mouse.isButtonDown((int)0) && (dragId == this.id || dragId == -1) && hovered) {
            if (mouseX < posX + 2) {
                this.current = this.min;
            } else if (mouseX > posX + width) {
                this.current = this.max;
            } else {
                double diff = this.max - this.min;
                double val = this.min + MathHelper.func_151237_a((double)((double)(mouseX - posX + 3) / (double)width), (double)0.0, (double)1.0) * diff;
                this.current = this.round == 0 ? (double)((int)val) : (double)((float)val);
            }
            dragId = this.id;
            for (ValueListener listener : this.valueListeners) {
                listener.valueUpdated(this.current);
            }
            this.wasSliding = true;
        } else if (!Mouse.isButtonDown((int)0) && this.wasSliding) {
            for (ValueListener listener : this.valueListeners) {
                listener.valueChanged(this.current);
            }
            dragId = -1;
            this.wasSliding = false;
        }
        double percent = (this.current - this.min) / (this.max - this.min);
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium(percent);
                break;
            }
        }
    }

    private void renderCaesium(double percent) {
        String value = this.round == 0 ? "" + Math.round(this.current) : "" + MathUtil.round(this.current, this.round);
        Color color = new Color(Panel.color);
        Panel.fR.drawStringWithShadow(this.text.getRenderName() + ":", (float)(this.posX + 3), (float)(this.posY + 3), Panel.fontColor);
        Panel.fR.drawStringWithShadow(value, (float)(this.posX + this.width - Panel.fR.getStringWidth(value) - 3), (float)(this.posY + 3), Panel.fontColor);
        RenderUtil.drawRect(this.posX, this.posY + Panel.fR.FONT_HEIGHT + 3, this.posX + this.width - 2, this.posY + Panel.fR.FONT_HEIGHT + 5, Color.black.getRGB());
        RenderUtil.drawHorizontalGradient(this.posX, this.posY + Panel.fR.FONT_HEIGHT + 3, (float)(percent * (double)this.width - 2.0), 2.0f, color.darker().getRGB(), color.brighter().getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return Panel.fR.getStringWidth(this.text.getRenderName() + (this.round == 0 ? (double)Math.round(this.current) : MathUtil.round(this.current, this.round))) + 68;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 6;
    }
}

