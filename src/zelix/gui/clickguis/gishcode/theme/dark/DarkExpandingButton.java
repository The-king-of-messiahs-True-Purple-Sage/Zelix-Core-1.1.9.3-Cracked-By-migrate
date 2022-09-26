/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import java.awt.Color;
import zelix.gui.clickguis.Tooltip;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.ExpandingButton;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.hack.hacks.ClickGui;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class DarkExpandingButton
extends ComponentRenderer {
    public DarkExpandingButton(Theme theme) {
        super(ComponentType.EXPANDING_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        String description;
        int mainColorInv;
        ExpandingButton button = (ExpandingButton)component2;
        String text = button.getText();
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 155) : ColorUtils.color(0, 0, 0, 155);
        int n = mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        if (button.isMaximized()) {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height - 1, mainColor);
        }
        if (button.isEnabled()) {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14, mainColor);
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 95, button.getY() + 14, ClickGui.getColor());
            this.theme.fontRenderer.drawString(text, button.getX() + button.getDimension().width / 2 - this.theme.fontRenderer.getStringWidth(text) / 2, button.getY() + (button.getButtonHeight() / 2 - this.theme.fontRenderer.FONT_HEIGHT / 4) - 1, ClickGui.getColor());
        } else {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14, mainColor);
            this.theme.fontRenderer.drawString(text, button.getX() + button.getDimension().width / 2 - this.theme.fontRenderer.getStringWidth(text) / 2, button.getY() + (button.getButtonHeight() / 2 - this.theme.fontRenderer.FONT_HEIGHT / 4) - 1, mainColorInv);
        }
        if (button.isMaximized()) {
            RenderUtils.drawRect(button.getX(), button.getY() + button.getButtonHeight() - 1, button.getX() + button.getDimension().width, button.getY() + button.getButtonHeight(), ClickGui.getColor());
            RenderUtils.drawRect(button.getX(), button.getY() + button.getDimension().height - 1, button.getX() + button.getDimension().width, button.getY() + button.getDimension().height, ClickGui.getColor());
        }
        if (!button.isMaximized()) {
            this.drawExpanded(button.getX() + button.getDimension().width - 15, button.getY() + 3, 13, false, new Color(255, 255, 255, 255).hashCode());
        } else {
            this.drawExpanded(button.getX() + button.getDimension().width - 15, button.getY() + 3, 13, true, new Color(255, 255, 255, 255).hashCode());
        }
        if (button.isMaximized()) {
            button.renderChildren(mouseX, mouseY);
        }
        if ((description = button.hack.getDescription()) != null && button.isMouseOver(mouseX, mouseY) && !button.isMaximized() && HackManager.getHack("ClickGui").isToggledValue("Tooltip")) {
            ClickGuiScreen.tooltip = new Tooltip(description, mouseX, mouseY, this.theme.fontRenderer);
        }
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

