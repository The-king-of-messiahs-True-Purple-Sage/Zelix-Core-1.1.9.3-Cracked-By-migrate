/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.gishcode.elements;

import java.awt.Dimension;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.gishcode.ClickGui;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.base.Container;
import zelix.utils.Wrapper;

public class Frame
extends Container {
    private boolean pinned;
    private boolean maximized;
    private boolean maximizible = true;
    private boolean visable = true;
    private boolean pinnable = true;
    private int ticksSinceScroll = 100;
    private int scrollAmmount = 0;

    public Frame(int xPos, int yPos, int width, int height, String title) {
        super(xPos, yPos, width, height, ComponentType.FRAME, null, title);
    }

    @Override
    public void renderChildren(int mouseX, int mouseY) {
        if (this.isMaximized()) {
            GL11.glEnable((int)3089);
            GL11.glScissor((int)(this.getX() * 2), (int)(Display.getHeight() - (this.getY() + this.getDimension().height) * 2), (int)(this.getDimension().width * 2), (int)((this.getDimension().height - this.getFrameBoxHeight()) * 2));
            for (Component component2 : this.getComponents()) {
                component2.render(mouseX, mouseY);
            }
            GL11.glDisable((int)3089);
        }
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (this.isMouseOverBar(x, y)) {
            ClickGui.getTheme().getRenderer().get((Object)this.getComponentType()).doInteractions(this, x, y);
        }
        if (x >= this.getX() && y >= this.getY() + this.getFrameBoxHeight() && (double)x <= (double)this.getX() + this.getDimension().getWidth() && (double)y <= (double)this.getY() + this.getDimension().getHeight()) {
            for (Component c : this.getComponents()) {
                if (!c.isMouseOver(x, y) || !this.maximized) continue;
                c.onMousePress(x, y, buttonID);
                ClickGui.getTheme().getRenderer().get((Object)this.getComponentType()).doInteractions(this, x, y);
            }
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int buttonID) {
        if (x >= this.getX() && y >= this.getY() + this.getFrameBoxHeight() && (double)x <= (double)this.getX() + this.getDimension().getWidth() && (double)y <= (double)this.getY() + this.getDimension().getHeight()) {
            for (Component c : this.getComponents()) {
                if (!c.isMouseOver(x, y) || !this.maximized) continue;
                c.onMouseRelease(x, y, buttonID);
            }
        }
    }

    @Override
    public void onMouseDrag(int x, int y) {
        if (this.isMouseOverBar(x, y)) {
            ClickGui.getTheme().getRenderer().get((Object)this.getComponentType()).doInteractions(this, x, y);
        }
        if (x >= this.getX() && y >= this.getY() + this.getFrameBoxHeight() && (double)x <= (double)this.getX() + this.getDimension().getWidth() && (double)y <= (double)this.getY() + this.getDimension().getHeight()) {
            for (Component c : this.getComponents()) {
                if (!c.isMouseOver(x, y) || !this.maximized) continue;
                c.onMouseDrag(x, y);
                ClickGui.getTheme().getRenderer().get((Object)this.getComponentType()).doInteractions(this, x, y);
            }
        }
    }

    @Override
    public void onKeyPressed(int key, char character) {
        for (Component c : this.getComponents()) {
            c.onKeyPressed(key, character);
        }
    }

    @Override
    public void onKeyReleased(int key, char character) {
        for (Component c : this.getComponents()) {
            c.onKeyReleased(key, character);
        }
    }

    public boolean isMouseOverBar(int x, int y) {
        return x >= this.getX() && y >= this.getY() && (double)x <= (double)this.getX() + this.getDimension().getWidth() && y <= this.getY() + this.getFrameBoxHeight();
    }

    public void scrollFrame(int ammount) {
        this.scrollAmmount += ammount;
        this.ticksSinceScroll = 0;
    }

    public void updateComponents() {
        ++this.ticksSinceScroll;
        if (this.scrollAmmount < this.getMaxScroll()) {
            this.scrollAmmount = this.getMaxScroll();
        }
        if (this.scrollAmmount > 0) {
            this.scrollAmmount = 0;
        }
        for (Component c : this.getComponents()) {
            c.onUpdate();
            if (c instanceof Container) {
                Container container = (Container)c;
                for (Component component1 : container.getComponents()) {
                    component1.onUpdate();
                }
            }
            int yCount = this.getY() + this.getFrameBoxHeight();
            for (Component component1 : this.getComponents()) {
                if (this.getComponents().indexOf(component1) >= this.getComponents().indexOf(c)) continue;
                yCount = (int)((double)yCount + (component1.getDimension().getHeight() + 2.0));
            }
            c.setyBase(yCount + 2);
            c.setyPos(c.getyBase() + this.scrollAmmount);
        }
        int height = Wrapper.INSTANCE.mc().displayHeight / 3 + this.getComponents().size();
        this.setDimension(new Dimension(this.getDimension().width, height));
    }

    public int getMaxScroll() {
        if (this.getComponents().size() == 0) {
            return 0;
        }
        Component last = this.getComponents().get(this.getComponents().size() - 1);
        int maxLast = (int)((double)last.getyBase() + last.getDimension().getHeight());
        return this.getMaxY() - maxLast;
    }

    public int getMaxY() {
        return (int)((double)this.getY() + this.getDimension().getHeight());
    }

    public int getFrameBoxHeight() {
        return ClickGui.getTheme().getFrameHeight();
    }

    public boolean isPinned() {
        return this.pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isMaximized() {
        return this.maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    public boolean isMaximizible() {
        return this.maximizible;
    }

    public void setMaximizible(boolean maximizible) {
        this.maximizible = maximizible;
    }

    public boolean isVisable() {
        return this.visable;
    }

    public void setVisable(boolean visable) {
        this.visable = visable;
    }

    public boolean isPinnable() {
        return this.pinnable;
    }

    public void setPinnable(boolean pinnable) {
        this.pinnable = pinnable;
    }

    public int getTicksSinceScroll() {
        return this.ticksSinceScroll;
    }

    public void setTicksSinceScroll(int ticksSinceScroll) {
        this.ticksSinceScroll = ticksSinceScroll;
    }

    public int getScrollAmmount() {
        return this.scrollAmmount;
    }

    public void setScrollAmmount(int scrollAmmount) {
        this.scrollAmmount = scrollAmmount;
    }
}

