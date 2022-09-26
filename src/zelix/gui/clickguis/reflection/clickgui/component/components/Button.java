/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 */
package zelix.gui.clickguis.reflection.clickgui.component.components;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import zelix.gui.clickguis.reflection.clickgui.component.Component;
import zelix.gui.clickguis.reflection.clickgui.component.Frame;
import zelix.gui.clickguis.reflection.clickgui.component.components.sub.Checkbox;
import zelix.gui.clickguis.reflection.clickgui.component.components.sub.InfoButton;
import zelix.gui.clickguis.reflection.clickgui.component.components.sub.Keybind;
import zelix.gui.clickguis.reflection.clickgui.component.components.sub.ModeButton;
import zelix.gui.clickguis.reflection.clickgui.component.components.sub.Slider;
import zelix.gui.clickguis.reflection.clickgui.component.components.sub.VisibleButton;
import zelix.hack.Hack;
import zelix.value.BooleanValue;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class Button
extends Component {
    public Hack mod;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    public int height;
    public FontRenderer fr;

    public Button(Hack mod, Frame parent, int offset) {
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.height = 12;
        this.subcomponents = new ArrayList();
        this.open = false;
        int opY = offset + 12;
        if (mod.getValues() != null) {
            for (Value s : mod.getValues()) {
                if (s instanceof ModeValue) {
                    this.subcomponents.add(new ModeButton((ModeValue)s, this, mod, opY));
                    opY += 12;
                }
                if (s instanceof NumberValue) {
                    this.subcomponents.add(new Slider((NumberValue)s, this, opY));
                    opY += 12;
                }
                if (!(s instanceof BooleanValue)) continue;
                this.subcomponents.add(new Checkbox((BooleanValue)s, this, opY));
                opY += 12;
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
        this.subcomponents.add(new VisibleButton(this, mod, opY));
        this.subcomponents.add(new InfoButton(this, mod, opY));
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
        int opY = this.offset + 12;
        for (Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect((int)this.parent.getX(), (int)(this.parent.getY() + this.offset), (int)(this.parent.getX() + this.parent.getWidth()), (int)(this.parent.getY() + 12 + this.offset), (int)(this.isHovered ? -14540254 : -15658735));
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getRenderName(), (float)(this.parent.getX() + 5), (float)(this.parent.getY() + this.offset + 2), this.mod.isToggled() ? 16712414 : 0xFFFFFF);
        if (this.subcomponents.size() > 3) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "\u00a77\u25bc" : "\u00a77\u25b6", (float)(this.parent.getX() + this.parent.getWidth() - 10), (float)(this.parent.getY() + this.offset + 2), -1);
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 12 * (this.subcomponents.size() + 1);
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}

