/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 */
package zelix.gui.clickguis.tenacity;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import zelix.gui.clickguis.tenacity.Frame.tenacity;

public class TenacityScreen
extends GuiScreen {
    public tenacity frame = new tenacity();

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.frame.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.frame.mouseReleased(mouseX, mouseY, state);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.frame.keyTyped(typedChar, keyCode);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sR = new ScaledResolution(this.mc);
        this.frame.render(mouseX, mouseY);
    }

    public void initGui() {
        this.frame.initialize();
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.mc.entityRenderer.stopUseShader();
    }
}

