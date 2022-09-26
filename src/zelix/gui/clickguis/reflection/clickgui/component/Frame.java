/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 */
package zelix.gui.clickguis.reflection.clickgui.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import zelix.gui.clickguis.reflection.clickgui.component.Component;
import zelix.gui.clickguis.reflection.clickgui.component.components.Button;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;

public class Frame {
    public ArrayList<Component> components = new ArrayList();
    public HackCategory category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    public static int color;

    public Frame(HackCategory cat) {
        this.category = cat;
        this.width = 88;
        this.x = 0;
        this.y = 60;
        this.dragX = 0;
        this.barHeight = 12;
        this.open = true;
        this.isDragging = false;
        int tY = this.barHeight;
        for (Hack mod : HackManager.getModulesInType(this.category)) {
            Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
        }
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public void setDrag(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void renderFrame(FontRenderer fontRenderer) {
        if (Calendar.getInstance().get(5) == 31 && Calendar.getInstance().get(2) == 11) {
            Gui.drawRect((int)this.x, (int)(this.y - 1), (int)(this.x + this.width), (int)this.y, (int)-1);
        } else {
            Gui.drawRect((int)this.x, (int)(this.y - 1), (int)(this.x + this.width), (int)this.y, (int)Color.MAGENTA.getRGB());
        }
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + 12), (int)-15658735);
        if (Calendar.getInstance().get(5) == 31 && Calendar.getInstance().get(2) == 11) {
            if (this.category == HackCategory.COMBAT) {
                fontRenderer.drawStringWithShadow("\u00a7b\u2694 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.PLAYER) {
                fontRenderer.drawStringWithShadow("\u00a7b\u2620 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.MOVEMENT) {
                fontRenderer.drawStringWithShadow("\u00a7b\u2607 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.ANOTHER) {
                fontRenderer.drawStringWithShadow("\u00a7b\u2600 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.VISUAL) {
                fontRenderer.drawStringWithShadow("\u00a7b\u2761 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
        } else {
            if (this.category == HackCategory.COMBAT) {
                fontRenderer.drawStringWithShadow("\u00a7d\u2694 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.PLAYER) {
                fontRenderer.drawStringWithShadow("\u00a7d\u2620 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.MOVEMENT) {
                fontRenderer.drawStringWithShadow("\u00a7d\u2607 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.ANOTHER) {
                fontRenderer.drawStringWithShadow("\u00a7d\u2600 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
            if (this.category == HackCategory.VISUAL) {
                fontRenderer.drawStringWithShadow("\u00a7d\u2761 \u00a7r" + this.category.name(), (float)(this.x + 5), (float)(this.y + 2), -1);
            }
        }
        if (this.open && !this.components.isEmpty()) {
            for (Component component2 : this.components) {
                component2.renderComponent();
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }

    public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}

