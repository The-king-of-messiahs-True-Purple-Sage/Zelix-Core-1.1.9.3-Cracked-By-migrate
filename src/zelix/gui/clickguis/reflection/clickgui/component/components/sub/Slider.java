/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.reflection.clickgui.component.components.sub;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.reflection.clickgui.component.Component;
import zelix.gui.clickguis.reflection.clickgui.component.components.Button;
import zelix.value.NumberValue;

public class Slider
extends Component {
    private boolean hovered;
    private NumberValue set;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging = false;
    private double renderWidth;

    public Slider(NumberValue value, Button button, int offset) {
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + this.parent.parent.getWidth()), (int)(this.parent.parent.getY() + this.offset + 12), (int)-15658735);
        int drag = (int)((Double)this.set.getValue() / this.set.getMax() * (double)this.parent.parent.getWidth());
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset), (int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset + 12), (int)-15658735);
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset + 8), (int)(this.parent.parent.getX() + 88), (int)(this.parent.parent.getY() + this.offset + 12), (int)Color.GRAY.darker().darker().darker().getRGB());
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset + 8), (int)(this.parent.parent.getX() + (int)this.renderWidth), (int)(this.parent.parent.getY() + this.offset + 12), (int)(this.hovered ? -11184811 : -12303292));
        GL11.glPushMatrix();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.set.getRenderName() + ": " + this.set.getValue(), (float)(this.parent.parent.getX() * 2 + 15), (float)((this.parent.parent.getY() + this.offset - 1) * 2 + 5), -1);
        GL11.glPopMatrix();
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        double diff = Math.min(88, Math.max(0, mouseX - this.x));
        double min = this.set.getMin();
        double max = this.set.getMax();
        this.renderWidth = 88.0 * ((Double)this.set.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.set.setValue(this.set.getMin());
            } else {
                double newValue = Slider.roundToPlace(diff / 88.0 * (max - min) + min, 2);
                this.set.setValue(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public boolean isMouseOnButtonD(int x, int y) {
        return x > this.x && x < this.x + (this.parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }

    public boolean isMouseOnButtonI(int x, int y) {
        return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}

