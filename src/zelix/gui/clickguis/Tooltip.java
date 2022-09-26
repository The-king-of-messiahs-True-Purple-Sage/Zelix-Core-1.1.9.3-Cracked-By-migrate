/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 */
package zelix.gui.clickguis;

import net.minecraft.client.gui.FontRenderer;
import zelix.hack.hacks.ClickGui;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class Tooltip {
    private FontRenderer fontRenderer;
    private String text;
    private int x;
    private int y;

    public Tooltip(String text, int x, int y, FontRenderer fontRenderer) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontRenderer = fontRenderer;
    }

    public String getText() {
        return this.text;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void render() {
        int textColor = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(200, 200, 200, 255);
        int rectColor = ClickGui.isLight ? ColorUtils.color(155, 155, 155, 155) : ColorUtils.color(100, 100, 100, 100);
        int aboveMouse = 8;
        int width = this.fontRenderer.getStringWidth(this.text);
        RenderUtils.drawStringWithRect(this.getText(), this.getX() + 2, this.getY() - aboveMouse + 2, textColor, rectColor, rectColor);
    }
}

