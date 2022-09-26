/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Keyboard
 */
package zelix.gui.clickguis.reflection;

import java.awt.Color;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ColorUtils;

public class ReflectionGui
extends GuiScreen {
    private final GuiScreen parentScreen = new GuiMainMenu();
    protected boolean hovered;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.func_73733_a(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), Color.BLACK.getRGB(), ColorUtils.rainbow(300));
        ReflectionGui.func_73734_a((int)0, (int)0, (int)sr.getScaledWidth(), (int)sr.getScaledHeight(), (int)-1627389952);
        ReflectionGui.func_73734_a((int)0, (int)0, (int)sr.getScaledWidth(), (int)sr.getScaledHeight(), (int)0x7F000000);
        this.func_73732_a(this.fontRendererObj, "Reflection Client", this.width / 2, 17, ColorUtils.rainbow(300));
        this.func_73732_a(this.fontRendererObj, "Version: 1.1.6", this.width / 2, 30, 0xA0A0A0);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        super.initGui();
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiMainMenu());
        } else if (button.id == 0) {
            // empty if block
        }
    }
}

