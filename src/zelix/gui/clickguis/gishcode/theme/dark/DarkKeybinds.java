/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import org.lwjgl.input.Keyboard;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentRenderer;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.elements.KeybindMods;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.hack.hacks.ClickGui;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;

public class DarkKeybinds
extends ComponentRenderer {
    public DarkKeybinds(Theme theme) {
        super(ComponentType.KEYBIND, theme);
    }

    @Override
    public void drawComponent(Component component2, int mouseX, int mouseY) {
        KeybindMods keybind = (KeybindMods)component2;
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 55) : ColorUtils.color(0, 0, 0, 55);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        this.theme.fontRenderer.drawString("Key", keybind.getX() + 4, keybind.getY() + 2, mainColorInv);
        int nameWidth = this.theme.fontRenderer.getStringWidth("Key") + 7;
        RenderUtils.drawRect(keybind.getX() + nameWidth, keybind.getY(), keybind.getX() + keybind.getDimension().width, keybind.getY() + 12, mainColor);
        if (keybind.getMod().getKey() == -1) {
            this.theme.fontRenderer.drawString(keybind.isEditing() ? "|" : "NONE", keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - this.theme.fontRenderer.getStringWidth("NONE") / 2, keybind.getY() + 2, keybind.isEditing() ? mainColorInv : ColorUtils.color(0.4f, 0.4f, 0.4f, 1.0f));
        } else {
            this.theme.fontRenderer.drawString(keybind.isEditing() ? "|" : Keyboard.getKeyName((int)keybind.getMod().getKey()), keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - this.theme.fontRenderer.getStringWidth(Keyboard.getKeyName((int)keybind.getMod().getKey())) / 2, keybind.getY() + 2, keybind.isEditing() ? mainColorInv : mainColorInv);
        }
    }

    @Override
    public void doInteractions(Component component2, int mouseX, int mouseY) {
    }
}

