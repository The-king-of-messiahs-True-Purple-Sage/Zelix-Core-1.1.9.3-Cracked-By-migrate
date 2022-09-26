/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import java.awt.Color;
import java.awt.Dimension;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.Frame;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.hack.hacks.ClickGui;
import zelix.utils.MathUtils;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class DarkFrame
extends ComponentRenderer {
    public DarkFrame(Theme theme) {
        super(ComponentType.FRAME, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        Frame frame = (Frame)component2;
        Dimension dimension = frame.getDimension();
        if (frame.isMaximized()) {
            this.isMaximized(frame, dimension, mouseX, mouseY);
        }
        RenderUtils.drawRect(frame.getX(), frame.getY(), frame.getX() + dimension.width, frame.getY() + 15, ClickGui.getColor());
        if (frame.isMaximizible()) {
            this.isMaximizible(frame, dimension, mouseX, mouseY);
        }
        this.theme.fontRenderer.drawStringWithShadow(frame.getText(), (float)(frame.getX() + 4), (float)(MathUtils.getMiddle(frame.getY(), frame.getY() + 10) - this.theme.fontRenderer.FONT_HEIGHT / 10 - 1), ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    private void isPinnable(Frame frame, Dimension dimension, int mouseX, int mouseY) {
    }

    private void isMaximizible(Frame frame, Dimension dimension, int mouseX, int mouseY) {
        Color color = mouseX >= frame.getX() + dimension.width - 19 && mouseY >= frame.getY() && mouseY <= frame.getY() + 19 && mouseX <= frame.getX() + dimension.width ? new Color(255, 255, 255, 255) : new Color(155, 155, 155, 255);
        this.theme.fontRenderer.drawStringWithShadow(frame.isMaximized() ? "-" : "+", (float)(frame.getX() + dimension.width - 12), (float)(frame.getY() + 3), color.getRGB());
    }

    private void isMaximized(Frame frame, Dimension dimension, int mouseX, int mouseY) {
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 255) : ColorUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        for (Component component2 : frame.getComponents()) {
            component2.setxPos(frame.getX());
        }
        float height = 5.0f;
        float maxHeight = 0.0f;
        height = dimension.height - 40;
        for (Component component3 : frame.getComponents()) {
            maxHeight += (float)component3.getDimension().height;
        }
        float barHeight = height * (height / maxHeight);
        double y = (frame.getDimension().getHeight() - 16.0 - (double)barHeight) * ((double)frame.getScrollAmmount() / (double)frame.getMaxScroll());
        y += (double)(frame.getY() + 16);
        frame.renderChildren(mouseX, mouseY);
        if (!(barHeight >= height)) {
            RenderUtils.drawRect((int)((double)frame.getX() + dimension.getWidth() - 1.0), (int)y, (int)((double)frame.getX() + frame.getDimension().getWidth()), (int)(y + (double)barHeight), ClickGui.getColor());
        }
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
        Frame frame = (Frame)component2;
        Dimension area = frame.getDimension();
        if (mouseX >= frame.getX() + area.width - 16 && frame.isMaximizible() && mouseY >= frame.getY() && mouseY <= frame.getY() + 16 && mouseX <= frame.getX() + area.width) {
            frame.setMaximized(!frame.isMaximized());
        }
        if (mouseX >= frame.getX() + area.width - 38 && mouseY >= frame.getY() && mouseY <= frame.getY() + 16 && mouseX <= frame.getX() + area.width - 16) {
            frame.setPinned(!frame.isPinned());
        }
    }
}

