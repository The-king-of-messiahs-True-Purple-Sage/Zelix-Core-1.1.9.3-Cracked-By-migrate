/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.Astolfo.ClickGui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import zelix.gui.clickguis.Astolfo.ClickGui.Window;
import zelix.hack.HackCategory;

public class ClickUi
extends GuiScreen {
    public static ArrayList<Window> windows = Lists.newArrayList();
    public double opacity = 0.0;
    public int scrollVelocity;
    public static boolean binding = false;
    private float animpos = 75.0f;

    public ClickUi() {
        if (windows.isEmpty()) {
            int x = 5;
            for (HackCategory c : HackCategory.values()) {
                windows.add(new Window(c, x, 5));
                x += 105;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.opacity = this.opacity + 10.0 < 200.0 ? (this.opacity = this.opacity + 10.0) : 200.0;
        GlStateManager.pushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        float scale = (float)scaledRes.getScaleFactor() / (float)Math.pow(scaledRes.getScaleFactor(), 2.0);
        windows.forEach(w -> w.render(mouseX, mouseY));
        GlStateManager.popMatrix();
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            this.scrollVelocity = wheel < 0 ? -120 : (wheel > 0 ? 120 : 0);
        }
        windows.forEach(w -> w.mouseScroll(mouseX, mouseY, this.scrollVelocity));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        windows.forEach(w -> w.click(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 && !binding) {
            this.mc.displayGuiScreen(null);
            return;
        }
        windows.forEach(w -> w.key(typedChar, keyCode));
    }

    public void initGui() {
        super.initGui();
    }

    public void onGuiClosed() {
        this.mc.entityRenderer.stopUseShader();
    }

    public synchronized void sendToFront(Window window) {
        int panelIndex = 0;
        for (int i = 0; i < windows.size(); ++i) {
            if (windows.get(i) != window) continue;
            panelIndex = i;
            break;
        }
        Window t = windows.get(windows.size() - 1);
        windows.set(windows.size() - 1, windows.get(panelIndex));
        windows.set(panelIndex, t);
    }
}

