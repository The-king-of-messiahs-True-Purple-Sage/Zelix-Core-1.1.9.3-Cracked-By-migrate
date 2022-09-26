/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.CheckButton;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.hack.hacks.ClickGui;
import zelix.utils.MathUtils;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.value.Mode;

public class DarkCheckButton
extends ComponentRenderer {
    public DarkCheckButton(Theme theme) {
        super(ComponentType.CHECK_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        int strColor;
        CheckButton button = (CheckButton)component2;
        String text = button.getText();
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 255) : ColorUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        int n = strColor = ClickGui.isLight ? ColorUtils.color(0.3f, 0.3f, 0.3f, 1.0f) : ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f);
        if (button.getModeValue() == null) {
            this.theme.fontRenderer.drawString("> " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - this.theme.fontRenderer.FONT_HEIGHT / 3 - 1, button.isEnabled() ? mainColorInv : strColor);
            return;
        }
        for (Mode mode2 : button.getModeValue().getModes()) {
            if (!mode2.getName().equals(text)) continue;
            this.theme.fontRenderer.drawString("- " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - this.theme.fontRenderer.FONT_HEIGHT / 3 - 1, mode2.isToggled() ? mainColorInv : strColor);
        }
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

