/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.Text;
import zelix.gui.clickguis.gishcode.theme.Theme;

public class DarkText
extends ComponentRenderer {
    public DarkText(Theme theme) {
        super(ComponentType.TEXT, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        Text text = (Text)component2;
        String[] message = text.getMessage();
        int y = text.getY();
        for (String s : message) {
            this.theme.fontRenderer.drawString(s, text.getX() - 4, y - 4, -1);
            y += 10;
        }
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

