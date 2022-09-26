/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.Item
 */
package zelix.hack.hacks.xray.gui.manage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.gui.GuiSelectionScreen;
import zelix.hack.hacks.xray.gui.utils.GuiBase;
import zelix.hack.hacks.xray.gui.utils.GuiSlider;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.reference.block.BlockItem;
import zelix.hack.hacks.xray.utils.OutlineColor;
import zelix.hack.hacks.xray.xray.Controller;

public class GuiAddBlock
extends GuiBase {
    private static final int BUTTON_ADD = 98;
    private static final int BUTTON_CANCEL = 99;
    private GuiTextField oreName;
    private GuiButton addBtn;
    private GuiSlider redSlider;
    private GuiSlider greenSlider;
    private GuiSlider blueSlider;
    private BlockItem selectBlock;
    private boolean oreNameCleared = false;
    private IBlockState state;

    public GuiAddBlock(BlockItem selectedBlock, @Nullable IBlockState state) {
        super(false);
        this.selectBlock = selectedBlock;
        this.state = state;
    }

    public void initGui() {
        this.addBtn = new GuiButton(98, this.width / 2 - 100, this.height / 2 + 85, 128, 20, I18n.format((String)"xray.single.add", (Object[])new Object[0]));
        this.buttonList.add(this.addBtn);
        this.buttonList.add(new GuiButton(99, this.width / 2 + 30, this.height / 2 + 85, 72, 20, I18n.format((String)"xray.single.cancel", (Object[])new Object[0])));
        this.redSlider = new GuiSlider(3, this.width / 2 - 100, this.height / 2 + 7, I18n.format((String)"xray.color.red", (Object[])new Object[0]), 0.0f, 255.0f);
        this.buttonList.add(this.redSlider);
        this.greenSlider = new GuiSlider(2, this.width / 2 - 100, this.height / 2 + 30, I18n.format((String)"xray.color.green", (Object[])new Object[0]), 0.0f, 255.0f);
        this.buttonList.add(this.greenSlider);
        this.blueSlider = new GuiSlider(1, this.width / 2 - 100, this.height / 2 + 53, I18n.format((String)"xray.color.blue", (Object[])new Object[0]), 0.0f, 255.0f);
        this.buttonList.add(this.blueSlider);
        this.redSlider.sliderValue = 0.0f;
        this.greenSlider.sliderValue = 0.654f;
        this.blueSlider.sliderValue = 1.0f;
        this.oreName = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 70, 202, 20);
        this.oreName.setText(this.selectBlock.getItemStack().getDisplayName());
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 98: {
                this.mc.thePlayer.closeScreen();
                if (this.state == null) {
                    this.state = Block.getBlockFromItem((Item)this.selectBlock.getItemStack().getItem()).getDefaultState();
                }
                Controller.getBlockStore().put(this.state.toString(), new BlockData(this.state.toString(), this.oreName.getText(), Block.getStateId((IBlockState)this.state), new OutlineColor((int)(this.redSlider.sliderValue * 255.0f), (int)(this.greenSlider.sliderValue * 255.0f), (int)(this.blueSlider.sliderValue * 255.0f)), this.selectBlock.getItemStack(), true, Controller.getBlockStore().getStore().size() + 1));
                XRay.blockStore.write(Controller.getBlockStore().getStore());
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
        } else if (par2 == 15) {
            if (!this.oreNameCleared) {
                this.oreName.setText("");
            }
            this.oreName.setFocused(true);
        }
    }

    public void updateScreen() {
        this.oreName.updateCursorCounter();
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.getFontRender().drawStringWithShadow(this.selectBlock.getItemStack().getDisplayName(), (float)this.width / 2.0f - 100.0f, (float)this.height / 2.0f - 90.0f, 0xFFFFFF);
        this.oreName.drawTextBox();
        GuiAddBlock.renderPreview(this.width / 2 - 100, this.height / 2 - 40, this.redSlider.sliderValue, this.greenSlider.sliderValue, this.blueSlider.sliderValue);
        if (this.state == null && this.addBtn.isMouseOver()) {
            this.drawHoveringText(Arrays.asList(I18n.format((String)"xray.message.state_warning", (Object[])new Object[0]).split("\n")), this.addBtn.xPosition - 30, this.addBtn.yPosition - 45);
        }
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRender.func_180450_b(this.selectBlock.getItemStack(), this.width / 2 + 85, this.height / 2 - 105);
        RenderHelper.disableStandardItemLighting();
    }

    static void renderPreview(int x, int y, float r, float g, float b) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder tessellate = tessellator.func_178180_c();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)r, (float)g, (float)b, (float)1.0f);
        tessellate.func_181668_a(7, DefaultVertexFormats.POSITION);
        tessellate.func_181662_b((double)x, (double)y, 0.0).func_181675_d();
        tessellate.func_181662_b((double)x, (double)(y + 45), 0.0).func_181675_d();
        tessellate.func_181662_b((double)(x + 202), (double)(y + 45), 0.0).func_181675_d();
        tessellate.func_181662_b((double)(x + 202), (double)y, 0.0).func_181675_d();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    @Override
    public void mouseClicked(int x, int y, int mouse) throws IOException {
        super.mouseClicked(x, y, mouse);
        this.oreName.func_146192_a(x, y, mouse);
        if (this.oreName.isFocused() && !this.oreNameCleared) {
            this.oreName.setText("");
            this.oreNameCleared = true;
        }
        if (!this.oreName.isFocused() && this.oreNameCleared && Objects.equals(this.oreName.getText(), "")) {
            this.oreNameCleared = false;
            this.oreName.setText(I18n.format((String)"xray.input.gui", (Object[])new Object[0]));
        }
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public String title() {
        return I18n.format((String)"xray.title.config", (Object[])new Object[0]);
    }
}

