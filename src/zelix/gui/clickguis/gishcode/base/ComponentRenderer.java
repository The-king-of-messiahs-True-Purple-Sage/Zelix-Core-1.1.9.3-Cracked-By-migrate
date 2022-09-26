/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.gishcode.base;

import java.awt.Color;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.utils.hooks.visual.GLUtils;

public abstract class ComponentRenderer {
    protected static final Color tooltipColor = new Color(0.0f, 0.5f, 1.0f, 0.75f);
    public Theme theme;
    private ComponentType type;

    public ComponentRenderer(ComponentType type, Theme theme) {
        this.type = type;
        this.theme = theme;
    }

    public abstract void drawComponent(Component var1, int var2, int var3);

    public abstract void doInteractions(Component var1, int var2, int var3);

    public void drawExpanded(int x, int y, int size, boolean expanded, int color) {
        GLUtils.glColor(color);
    }

    public void drawPin(int x, int y, int size, boolean expanded, int color) {
        GLUtils.glColor(color);
    }

    public void drawArrow(int x, int y, int size, boolean right, int color) {
        GLUtils.glColor(color);
    }

    public void drawArrow(int x, int y, int size, boolean right) {
        this.drawArrow(x, y, size, right, -1);
    }

    public void drawFilledRect(float x, float y, float x1, float y1) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glBegin((int)7);
        GL11.glVertex3f((float)x, (float)y1, (float)1.0f);
        GL11.glVertex3f((float)x1, (float)y1, (float)1.0f);
        GL11.glVertex3f((float)x1, (float)y, (float)1.0f);
        GL11.glVertex3f((float)x, (float)y, (float)1.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }

    public void drawRect(float x, float y, float x1, float y1, float thickness) {
        this.drawFilledRect(x + thickness, y, x1 - thickness, y + thickness);
        this.drawFilledRect(x, y, x + thickness, y1);
        this.drawFilledRect(x1 - thickness, y, x1, y1);
        this.drawFilledRect(x + thickness, y1 - thickness, x1 - thickness, y1);
    }
}

