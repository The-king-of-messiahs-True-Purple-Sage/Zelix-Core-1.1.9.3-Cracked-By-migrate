/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 */
package zelix.hack.hacks.xray.gui.manage;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.gui.GuiSelectionScreen;
import zelix.hack.hacks.xray.gui.manage.GuiAddBlock;
import zelix.hack.hacks.xray.gui.utils.GuiBase;
import zelix.hack.hacks.xray.gui.utils.GuiSlider;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.utils.OutlineColor;
import zelix.hack.hacks.xray.xray.Controller;

public class GuiEdit
extends GuiBase {
    private GuiTextField oreName;
    private GuiSlider redSlider;
    private GuiSlider greenSlider;
    private GuiSlider blueSlider;
    private BlockData block;
    private String storeKey;
    private static final int BUTTON_DELETE = 100;
    private static final int BUTTON_SAVE = 98;
    private static final int BUTTON_CANCEL = 99;

    public GuiEdit(String storeKey, BlockData block) {
        super(true);
        this.setSideTitle(I18n.format((String)"xray.single.tools", (Object[])new Object[0]));
        this.storeKey = storeKey;
        this.block = block;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(100, this.width / 2 + 78, this.height / 2 - 60, 120, 20, I18n.format((String)"xray.single.delete", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(99, this.width / 2 + 78, this.height / 2 + 58, 120, 20, I18n.format((String)"xray.single.cancel", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(98, this.width / 2 - 138, this.height / 2 + 83, 202, 20, I18n.format((String)"xray.single.save", (Object[])new Object[0])));
        this.redSlider = new GuiSlider(3, this.width / 2 - 138, this.height / 2 + 7, I18n.format((String)"xray.color.red", (Object[])new Object[0]), 0.0f, 255.0f);
        this.buttonList.add(this.redSlider);
        this.greenSlider = new GuiSlider(2, this.width / 2 - 138, this.height / 2 + 30, I18n.format((String)"xray.color.green", (Object[])new Object[0]), 0.0f, 255.0f);
        this.buttonList.add(this.greenSlider);
        this.blueSlider = new GuiSlider(1, this.width / 2 - 138, this.height / 2 + 53, I18n.format((String)"xray.color.blue", (Object[])new Object[0]), 0.0f, 255.0f);
        this.buttonList.add(this.blueSlider);
        this.redSlider.sliderValue = (float)this.block.getColor().getRed() / 255.0f;
        this.greenSlider.sliderValue = (float)this.block.getColor().getGreen() / 255.0f;
        this.blueSlider.sliderValue = (float)this.block.getColor().getBlue() / 255.0f;
        this.oreName = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 138, this.height / 2 - 63, 202, 20);
        this.oreName.setText(this.block.getEntryName());
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 98: {
                BlockData block = new BlockData(this.storeKey, this.oreName.getText(), this.block.getStateId(), new OutlineColor((int)(this.redSlider.sliderValue * 255.0f), (int)(this.greenSlider.sliderValue * 255.0f), (int)(this.blueSlider.sliderValue * 255.0f)), this.block.getItemStack(), this.block.isDrawing(), this.block.getOrder());
                Controller.getBlockStore().getStore().remove(this.storeKey);
                Controller.getBlockStore().getStore().put(this.storeKey, block);
                XRay.blockStore.write(Controller.getBlockStore().getStore());
                this.mc.thePlayer.closeScreen();
                this.mc.displayGuiScreen((GuiScreen)new GuiSelectionScreen());
                break;
            }
            case 100: {
                Controller.getBlockStore().getStore().remove(this.storeKey);
                XRay.blockStore.write(Controller.getBlockStore().getStore());
                this.mc.thePlayer.closeScreen();
                this.mc.displayGuiScreen((GuiScreen)new GuiSelectionScreen());
                break;
            }
            case 99: {
                this.mc.thePlayer.closeScreen();
                this.mc.displayGuiScreen((GuiScreen)new GuiSelectionScreen());
                break;
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        if (this.oreName.isFocused()) {
            this.oreName.textboxKeyTyped(par1, par2);
        }
    }

    public void updateScreen() {
        this.oreName.updateCursorCounter();
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.getFontRender().drawStringWithShadow(this.block.getItemStack().getDisplayName(), (float)(this.width / 2 - 138), (float)(this.height / 2 - 90), 0xFFFFFF);
        this.oreName.drawTextBox();
        GuiAddBlock.renderPreview(this.width / 2 - 138, this.height / 2 - 40, this.redSlider.sliderValue, this.greenSlider.sliderValue, this.blueSlider.sliderValue);
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRender.func_180450_b(this.block.getItemStack(), this.width / 2 + 50, this.height / 2 - 105);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public void mouseClicked(int x, int y, int mouse) throws IOException {
        super.mouseClicked(x, y, mouse);
        this.oreName.func_146192_a(x, y, mouse);
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String title() {
        return I18n.format((String)"xray.title.edit", (Object[])new Object[0]);
    }
}

