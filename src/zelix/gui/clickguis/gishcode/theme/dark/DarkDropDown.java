/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.Dropdown;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.hack.hacks.ClickGui;

public class DarkDropDown
extends ComponentRenderer {
    public DarkDropDown(Theme theme) {
        super(ComponentType.DROPDOWN, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        Dropdown dropdown = (Dropdown)component2;
        String text = dropdown.getText();
        this.theme.fontRenderer.drawString(text, dropdown.getX() + 5, dropdown.getY() + (dropdown.getDropdownHeight() / 2 - this.theme.fontRenderer.FONT_HEIGHT / 4), ClickGui.getColor());
        if (dropdown.isMaximized()) {
            dropdown.renderChildren(mouseX, mouseY);
        }
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

