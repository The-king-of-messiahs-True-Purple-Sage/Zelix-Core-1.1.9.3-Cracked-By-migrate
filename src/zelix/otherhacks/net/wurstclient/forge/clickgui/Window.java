/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import java.util.ArrayList;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;

public final class Window {
    private String title;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean valid;
    private final ArrayList<Component> children = new ArrayList();
    private boolean dragging;
    private int dragOffsetX;
    private int dragOffsetY;
    private boolean minimized;
    private boolean minimizable = true;
    private boolean pinned;
    private boolean pinnable = true;
    private boolean closable;
    private boolean closing;
    private boolean invisible;
    private int innerHeight;
    private int maxHeight;
    private int scrollOffset;
    private boolean scrollingEnabled;
    private boolean draggingScrollbar;
    private int scrollbarDragOffsetY;

    public Window(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        if (this.width != width) {
            this.invalidate();
        }
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        if (this.height != height) {
            this.invalidate();
        }
        this.height = height;
    }

    public void pack() {
        int maxChildWidth = 0;
        for (Component c : this.children) {
            if (c.getWidth() <= maxChildWidth) continue;
            maxChildWidth = c.getDefaultWidth();
        }
        maxChildWidth += 4;
        int titleBarWidth = WMinecraft.getFontRenderer().getStringWidth(this.title) + 4;
        if (this.minimizable) {
            titleBarWidth += 11;
        }
        if (this.pinnable) {
            titleBarWidth += 11;
        }
        if (this.closable) {
            titleBarWidth += 11;
        }
        int childrenHeight = 13;
        for (Component c : this.children) {
            childrenHeight += c.getHeight() + 2;
        }
        if ((childrenHeight += 2) > this.maxHeight + 13 && this.maxHeight > 0) {
            this.setWidth(Math.max(maxChildWidth + 3, titleBarWidth));
            this.setHeight(this.maxHeight + 13);
        } else {
            this.setWidth(Math.max(maxChildWidth, titleBarWidth));
            this.setHeight(childrenHeight);
        }
        this.validate();
    }

    public void validate() {
        if (this.valid) {
            return;
        }
        int offsetY = 2;
        int cWidth = this.width - 4;
        for (Component c : this.children) {
            c.setX(2);
            c.setY(offsetY);
            c.setWidth(cWidth);
            offsetY += c.getHeight() + 2;
        }
        this.innerHeight = offsetY;
        if (this.maxHeight == 0) {
            this.setHeight(this.innerHeight + 13);
        } else if (this.height > this.maxHeight + 13) {
            this.setHeight(this.maxHeight + 13);
        } else if (this.height < this.maxHeight + 13) {
            this.setHeight(Math.min(this.maxHeight + 13, this.innerHeight + 13));
        }
        boolean bl = this.scrollingEnabled = this.innerHeight > this.height - 13;
        if (this.scrollingEnabled) {
            cWidth -= 3;
        }
        this.scrollOffset = Math.min(this.scrollOffset, 0);
        this.scrollOffset = Math.max(this.scrollOffset, -this.innerHeight + this.height - 13);
        for (Component c : this.children) {
            c.setWidth(cWidth);
        }
        this.valid = true;
    }

    public void invalidate() {
        this.valid = false;
    }

    public int countChildren() {
        return this.children.size();
    }

    public Component getChild(int index) {
        return this.children.get(index);
    }

    public void add(Component component2) {
        this.children.add(component2);
        component2.setParent(this);
        this.invalidate();
    }

    public void remove(int index) {
        this.children.get(index).setParent(null);
        this.children.remove(index);
        this.invalidate();
    }

    public void remove(Component component2) {
        this.children.remove(component2);
        component2.setParent(null);
        this.invalidate();
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void startDragging(int mouseX, int mouseY) {
        this.dragging = true;
        this.dragOffsetX = this.x - mouseX;
        this.dragOffsetY = this.y - mouseY;
    }

    public void dragTo(int mouseX, int mouseY) {
        this.x = mouseX + this.dragOffsetX;
        this.y = mouseY + this.dragOffsetY;
    }

    public void stopDragging() {
        this.dragging = false;
        this.dragOffsetX = 0;
        this.dragOffsetY = 0;
    }

    public boolean isMinimized() {
        return this.minimized;
    }

    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }

    public boolean isMinimizable() {
        return this.minimizable;
    }

    public void setMinimizable(boolean minimizable) {
        this.minimizable = minimizable;
    }

    public boolean isPinned() {
        return this.pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isPinnable() {
        return this.pinnable;
    }

    public void setPinnable(boolean pinnable) {
        this.pinnable = pinnable;
    }

    public boolean isClosable() {
        return this.closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }

    public boolean isClosing() {
        return this.closing;
    }

    public void close() {
        this.closing = true;
    }

    public boolean isInvisible() {
        return this.invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public int getInnerHeight() {
        return this.innerHeight;
    }

    public void setMaxHeight(int maxHeight) {
        if (this.maxHeight != maxHeight) {
            this.invalidate();
        }
        this.maxHeight = maxHeight;
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }

    public boolean isScrollingEnabled() {
        return this.scrollingEnabled;
    }

    public boolean isDraggingScrollbar() {
        return this.draggingScrollbar;
    }

    public void startDraggingScrollbar(int mouseY) {
        this.draggingScrollbar = true;
        double outerHeight = this.height - 13;
        double scrollbarY = outerHeight * ((double)(-this.scrollOffset) / (double)this.innerHeight) + 1.0;
        this.scrollbarDragOffsetY = (int)(scrollbarY - (double)mouseY);
    }

    public void dragScrollbarTo(int mouseY) {
        int scrollbarY = mouseY + this.scrollbarDragOffsetY;
        double outerHeight = this.height - 13;
        this.scrollOffset = (int)((double)(scrollbarY - 1) / outerHeight * (double)this.innerHeight * -1.0);
        this.scrollOffset = Math.min(this.scrollOffset, 0);
        this.scrollOffset = Math.max(this.scrollOffset, -this.innerHeight + this.height - 13);
    }

    public void stopDraggingScrollbar() {
        this.draggingScrollbar = false;
        this.scrollbarDragOffsetY = 0;
    }
}

