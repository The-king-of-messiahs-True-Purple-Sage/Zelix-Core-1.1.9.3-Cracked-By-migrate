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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.reflection.clickgui.component.Component;
import zelix.gui.clickguis.reflection.clickgui.component.components.Button;
import zelix.value.BooleanValue;

public class Checkbox
extends Component {
    private boolean hovered;
    private BooleanValue op;
    private Button parent;
    private int offset;
    private int x;
    private int y;

    public Checkbox(BooleanValue option, Button button, int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + this.parent.parent.getWidth() * 1), (int)(this.parent.parent.getY() + this.offset + 12), (int)(this.hovered ? -14540254 : -15658735));
        GL11.glPushMatrix();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.op.getRenderName(), (float)((this.parent.parent.getX() + 10 + 4) * 2 + 5), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 4), -1);
        GL11.glPopMatrix();
        if (((Boolean)this.op.getValue()).booleanValue()) {
            Gui.drawRect((int)(this.parent.parent.getX() + 4 + 4), (int)(this.parent.parent.getY() + this.offset + 4), (int)(this.parent.parent.getX() + 8 + 4), (int)(this.parent.parent.getY() + this.offset + 8), (int)Color.MAGENTA.getRGB());
        } else {
            Gui.drawRect((int)(this.parent.parent.getX() + 5 + 4), (int)(this.parent.parent.getY() + this.offset + 5), (int)(this.parent.parent.getX() + 7 + 4), (int)(this.parent.parent.getY() + this.offset + 7), (int)Color.MAGENTA.getRGB());
        }
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.op.setValue((Boolean)this.op.getValue() == false);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12;
    }
}

