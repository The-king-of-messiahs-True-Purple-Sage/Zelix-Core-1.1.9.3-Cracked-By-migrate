/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ClickGui;

public final class ClickGuiScreen
extends GuiScreen {
    private final ClickGui gui;

    public ClickGuiScreen(ClickGui gui) {
        this.gui = gui;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.gui.handleMouseClick(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.gui.render(mouseX, mouseY, partialTicks);
    }
}

