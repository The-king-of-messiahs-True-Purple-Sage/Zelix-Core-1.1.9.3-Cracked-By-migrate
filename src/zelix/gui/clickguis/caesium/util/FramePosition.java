/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.util;

public class FramePosition {
    private int posX;
    private int posY;
    private boolean isExpanded;

    public FramePosition(int posX, int posY, boolean isExpanded) {
        this.posX = posX;
        this.posY = posY;
        this.isExpanded = isExpanded;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }
}

