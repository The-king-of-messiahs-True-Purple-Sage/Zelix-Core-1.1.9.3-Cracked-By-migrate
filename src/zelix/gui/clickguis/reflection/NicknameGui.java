/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  org.lwjgl.input.Keyboard
 */
package zelix.gui.clickguis.reflection;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import zelix.utils.Wrapper;

public class NicknameGui
extends GuiScreen {
    private final GuiScreen parentScreen = new GuiMultiplayer((GuiScreen)new GuiMainMenu());
    private GuiTextField nameField;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.func_73732_a(this.fontRendererObj, "Username Changer", this.width / 2, 17, 0xFFFFFF);
        this.func_73731_b(this.fontRendererObj, "Nickname:", this.width / 2 - 100, this.width / 2 - 180, 0xA0A0A0);
        this.nameField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updateScreen() {
        this.nameField.updateCursorCounter();
        super.updateScreen();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Done"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Cancel"));
        this.nameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 72, 200, 20);
        this.nameField.setFocused(true);
        this.nameField.setText(Wrapper.INSTANCE.mc().getSession().getUsername());
        super.initGui();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
        super.onGuiClosed();
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
        } else if (button.id == 0) {
            // empty if block
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.nameField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.actionPerformed((GuiButton)this.buttonList.get(1));
        }
        if (keyCode == 15) {
            this.nameField.setFocused(!this.nameField.isFocused());
        }
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }
}

