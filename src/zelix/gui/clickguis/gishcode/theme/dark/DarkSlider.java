/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.Slider;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.hack.hacks.ClickGui;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class DarkSlider
extends ComponentRenderer {
    public DarkSlider(Theme theme) {
        super(ComponentType.SLIDER, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        Slider slider = (Slider)component2;
        int width = (int)(slider.getDimension().getWidth() * slider.getPercent());
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 255) : ColorUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        int strColor = ClickGui.isLight ? ColorUtils.color(0.3f, 0.3f, 0.3f, 1.0f) : ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f);
        this.theme.fontRenderer.drawString(slider.getText(), slider.getX() + 4, slider.getY() + 2, strColor);
        this.theme.fontRenderer.drawString(slider.getValue() + "", slider.getX() + slider.getDimension().width - this.theme.fontRenderer.getStringWidth(slider.getValue() + "") - 2, slider.getY() + 2, mainColorInv);
        RenderUtils.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + width + 3, slider.getY() + slider.getDimension().height / 2 + 6, mainColorInv);
        RenderUtils.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + width, slider.getY() + slider.getDimension().height / 2 + 6, ClickGui.getColor());
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

