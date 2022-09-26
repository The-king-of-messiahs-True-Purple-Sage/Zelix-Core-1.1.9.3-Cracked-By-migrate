/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.Button;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.GLUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class DarkButton
extends ComponentRenderer {
    public DarkButton(Theme theme) {
        super(ComponentType.BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        Button button = (Button)component2;
        String text = button.getText();
        int color = ColorUtils.color(50, 50, 50, 100);
        int enable = ColorUtils.color(255, 255, 255, 255);
        if (GLUtils.isHovered(button.getX(), button.getY(), button.getDimension().width, button.getDimension().height, mouseX, mouseY)) {
            color = ColorUtils.color(70, 70, 70, 255);
        }
        if (button.isEnabled()) {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height, enable);
        } else {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height, color);
        }
        this.theme.fontRenderer.drawString(text, button.getX() + 5, button.getY() + (button.getDimension().height / 2 - this.theme.fontRenderer.FONT_HEIGHT / 4), ColorUtils.color(255, 255, 255, 255));
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

