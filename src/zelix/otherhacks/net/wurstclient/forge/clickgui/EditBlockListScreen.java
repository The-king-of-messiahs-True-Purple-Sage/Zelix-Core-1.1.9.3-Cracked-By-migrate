/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.GuiYesNo
 *  net.minecraft.client.gui.GuiYesNoCallback
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.client.GuiScrollingList
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.IOException;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.GuiScrollingList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.BlockListSetting;

public final class EditBlockListScreen
extends GuiScreen {
    private final GuiScreen prevScreen;
    private final BlockListSetting blockList;
    private ListGui listGui;
    private GuiTextField blockNameField;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiButton doneButton;
    private Block blockToAdd;

    public EditBlockListScreen(GuiScreen prevScreen, BlockListSetting slider) {
        this.prevScreen = prevScreen;
        this.blockList = slider;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void initGui() {
        this.listGui = new ListGui(this.mc, this, this.blockList.getBlockNames());
        this.blockNameField = new GuiTextField(1, WMinecraft.getFontRenderer(), 64, this.height - 55, 150, 18);
        this.addButton = new GuiButton(0, 214, this.height - 56, 30, 20, "Add");
        this.buttonList.add(this.addButton);
        this.removeButton = new GuiButton(1, this.width - 150, this.height - 56, 100, 20, "Remove Selected");
        this.buttonList.add(this.removeButton);
        this.buttonList.add(new GuiButton(2, this.width - 108, 8, 100, 20, "Reset to Defaults"));
        this.doneButton = new GuiButton(3, this.width / 2 - 100, this.height - 28, "Done");
        this.buttonList.add(this.doneButton);
    }

    protected void actionPerformed(GuiButton button) {
        if (!button.enabled) {
            return;
        }
        switch (button.id) {
            case 0: {
                this.blockList.add(this.blockToAdd);
                this.blockNameField.setText("");
                break;
            }
            case 1: {
                this.blockList.remove(this.listGui.selected);
                break;
            }
            case 2: {
                this.mc.displayGuiScreen((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, "Reset to Defaults", "Are you sure?", 0));
                break;
            }
            case 3: {
                this.mc.renderGlobal.loadRenderers();
                this.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }

    public void confirmClicked(boolean result, int id) {
        if (id == 0 && result) {
            this.blockList.resetToDefaults();
        }
        super.confirmClicked(result, id);
        this.mc.displayGuiScreen((GuiScreen)this);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        this.listGui.handleMouseInput(mouseX, mouseY);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.blockNameField.func_146192_a(mouseX, mouseY, mouseButton);
        if (mouseX < 50 || mouseX > this.width - 50 || mouseY < 32 || mouseY > this.height - 64) {
            this.listGui.selected = -1;
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.blockNameField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 28) {
            this.actionPerformed(this.addButton);
        } else if (keyCode == 211) {
            this.actionPerformed(this.removeButton);
        } else if (keyCode == 1) {
            this.actionPerformed(this.doneButton);
        }
    }

    public void updateScreen() {
        this.blockNameField.updateCursorCounter();
        this.blockToAdd = Block.getBlockFromName((String)this.blockNameField.getText());
        this.addButton.enabled = this.blockToAdd != null;
        this.removeButton.enabled = this.listGui.selected >= 0 && this.listGui.selected < this.listGui.list.size();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.func_73732_a(WMinecraft.getFontRenderer(), this.blockList.getName() + " (" + this.listGui.getSize() + ")", this.width / 2, 12, 0xFFFFFF);
        this.listGui.drawScreen(mouseX, mouseY, partialTicks);
        this.blockNameField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.blockNameField.getText().isEmpty() && !this.blockNameField.isFocused()) {
            this.func_73731_b(WMinecraft.getFontRenderer(), "block name or ID", 68, this.height - 50, 0x808080);
        }
        EditBlockListScreen.func_73734_a((int)48, (int)(this.height - 56), (int)64, (int)(this.height - 36), (int)-6250336);
        EditBlockListScreen.func_73734_a((int)49, (int)(this.height - 55), (int)64, (int)(this.height - 37), (int)-16777216);
        EditBlockListScreen.func_73734_a((int)214, (int)(this.height - 56), (int)244, (int)(this.height - 55), (int)-6250336);
        EditBlockListScreen.func_73734_a((int)214, (int)(this.height - 37), (int)244, (int)(this.height - 36), (int)-6250336);
        EditBlockListScreen.func_73734_a((int)244, (int)(this.height - 56), (int)246, (int)(this.height - 36), (int)-6250336);
        EditBlockListScreen.func_73734_a((int)214, (int)(this.height - 55), (int)243, (int)(this.height - 52), (int)-16777216);
        EditBlockListScreen.func_73734_a((int)214, (int)(this.height - 40), (int)243, (int)(this.height - 37), (int)-16777216);
        EditBlockListScreen.func_73734_a((int)215, (int)(this.height - 55), (int)216, (int)(this.height - 37), (int)-16777216);
        EditBlockListScreen.func_73734_a((int)242, (int)(this.height - 55), (int)245, (int)(this.height - 37), (int)-16777216);
        this.listGui.renderIconAndGetName(new ItemStack(this.blockToAdd), this.height - 52);
    }

    private static class ListGui
    extends GuiScrollingList {
        private final Minecraft mc;
        private final List<String> list;
        private int selected = -1;

        public ListGui(Minecraft mc, EditBlockListScreen screen, List<String> list) {
            super(mc, screen.width - 100, screen.height, 32, screen.height - 64, 50, 16, screen.width, screen.height);
            this.mc = mc;
            this.list = list;
        }

        protected int getSize() {
            return this.list.size();
        }

        protected void elementClicked(int index, boolean doubleClick) {
            if (index >= 0 && index < this.list.size()) {
                this.selected = index;
            }
        }

        protected boolean isSelected(int index) {
            return index == this.selected;
        }

        protected void drawBackground() {
            Gui.drawRect((int)50, (int)this.top, (int)66, (int)this.bottom, (int)-1);
        }

        protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
            String name = this.list.get(slotIdx);
            ItemStack stack = new ItemStack(Block.getBlockFromName((String)name));
            FontRenderer fr = WMinecraft.getFontRenderer();
            String displayName = this.renderIconAndGetName(stack, slotTop);
            fr.drawString(displayName + " (" + name + ")", 68, slotTop + 2, 0xF0F0F0);
        }

        private String renderIconAndGetName(ItemStack stack, int y) {
            if (stack == null || stack.func_190926_b()) {
                GL11.glPushMatrix();
                GL11.glTranslated((double)52.0, (double)y, (double)0.0);
                GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
                RenderHelper.enableGUIStandardItemLighting();
                this.mc.func_175599_af().func_180450_b(new ItemStack((Block)Blocks.grass), 0, 0);
                RenderHelper.disableStandardItemLighting();
                GL11.glPopMatrix();
                GL11.glDisable((int)2929);
                FontRenderer fr = WMinecraft.getFontRenderer();
                fr.drawString("?", 55.0f, (float)(y + 2), 0xF0F0F0, true);
                GL11.glEnable((int)2929);
                return ChatFormatting.ITALIC + "unknown block" + ChatFormatting.RESET;
            }
            GL11.glPushMatrix();
            GL11.glTranslated((double)52.0, (double)y, (double)0.0);
            GL11.glScaled((double)0.75, (double)0.75, (double)0.75);
            RenderHelper.enableGUIStandardItemLighting();
            this.mc.func_175599_af().func_180450_b(stack, 0, 0);
            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
            return stack.getDisplayName();
        }
    }
}

