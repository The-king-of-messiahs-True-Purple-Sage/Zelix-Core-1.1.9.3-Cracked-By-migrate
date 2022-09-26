/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.MathUtils;

public final class EditSliderScreen
extends GuiScreen {
    private final GuiScreen prevScreen;
    private final SliderSetting slider;
    private GuiTextField valueField;
    private GuiButton doneButton;

    public EditSliderScreen(GuiScreen prevScreen, SliderSetting slider) {
        this.prevScreen = prevScreen;
        this.slider = slider;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        this.valueField = new GuiTextField(1, WMinecraft.getFontRenderer(), this.width / 2 - 100, 60, 200, 20);
        this.valueField.setText(SliderSetting.ValueDisplay.DECIMAL.getValueString(this.slider.getValue()));
        this.valueField.setSelectionPos(0);
        this.valueField.setFocused(true);
        this.doneButton = new GuiButton(0, this.width / 2 - 100, this.height / 3 * 2, "Done");
        this.buttonList.add(this.doneButton);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        String value = this.valueField.getText();
        if (MathUtils.isDouble(value)) {
            this.slider.setValue(Double.parseDouble(value));
        }
        this.mc.displayGuiScreen(this.prevScreen);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.valueField.func_146192_a(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.valueField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 28) {
            this.actionPerformed(this.doneButton);
        } else if (keyCode == 1) {
            this.mc.displayGuiScreen(this.prevScreen);
        }
    }

    public void updateScreen() {
        this.valueField.updateCursorCounter();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.func_73732_a(WMinecraft.getFontRenderer(), this.slider.getName(), this.width / 2, 20, 0xFFFFFF);
        this.valueField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

