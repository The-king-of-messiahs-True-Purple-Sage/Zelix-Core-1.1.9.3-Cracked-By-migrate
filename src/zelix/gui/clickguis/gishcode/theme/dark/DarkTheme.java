/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.gui.clickguis.gishcode.theme.dark;

import net.minecraft.client.Minecraft;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.theme.Theme;
import zelix.gui.clickguis.gishcode.theme.dark.DarkButton;
import zelix.gui.clickguis.gishcode.theme.dark.DarkCheckButton;
import zelix.gui.clickguis.gishcode.theme.dark.DarkComboBox;
import zelix.gui.clickguis.gishcode.theme.dark.DarkDropDown;
import zelix.gui.clickguis.gishcode.theme.dark.DarkExpandingButton;
import zelix.gui.clickguis.gishcode.theme.dark.DarkFrame;
import zelix.gui.clickguis.gishcode.theme.dark.DarkKeybinds;
import zelix.gui.clickguis.gishcode.theme.dark.DarkSlider;
import zelix.gui.clickguis.gishcode.theme.dark.DarkText;

public class DarkTheme
extends Theme {
    public DarkTheme() {
        super("GishCodeDark");
        this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        this.addRenderer(ComponentType.FRAME, new DarkFrame(this));
        this.addRenderer(ComponentType.BUTTON, new DarkButton(this));
        this.addRenderer(ComponentType.SLIDER, new DarkSlider(this));
        this.addRenderer(ComponentType.CHECK_BUTTON, new DarkCheckButton(this));
        this.addRenderer(ComponentType.EXPANDING_BUTTON, new DarkExpandingButton(this));
        this.addRenderer(ComponentType.TEXT, new DarkText(this));
        this.addRenderer(ComponentType.KEYBIND, new DarkKeybinds(this));
        this.addRenderer(ComponentType.DROPDOWN, new DarkDropDown(this));
        this.addRenderer(ComponentType.COMBO_BOX, new DarkComboBox(this));
    }
}

