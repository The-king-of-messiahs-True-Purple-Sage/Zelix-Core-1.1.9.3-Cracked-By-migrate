/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks.xray.gui.manage;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.gui.GuiSelectionScreen;
import zelix.hack.hacks.xray.gui.manage.GuiAddBlock;
import zelix.hack.hacks.xray.gui.manage.GuiBlocksList;
import zelix.hack.hacks.xray.gui.utils.GuiBase;
import zelix.hack.hacks.xray.reference.block.BlockItem;

public class GuiBlockListScrollable
extends GuiBase {
    private RenderItem render;
    private GuiBlocksList blockList;
    private ArrayList<BlockItem> blocks = XRay.gameBlockStore.getStore();
    private GuiTextField search;
    private String lastSearched = "";
    private int selected = -1;
    private static final int BUTTON_CANCEL = 0;

    public GuiBlockListScrollable() {
        super(false);
    }

    boolean blockSelected(int index) {
        return index == this.selected;
    }

    void selectBlock(int index) {
        if (index == this.selected) {
            return;
        }
        this.selected = index;
        this.mc.thePlayer.closeScreen();
        this.mc.displayGuiScreen((GuiScreen)new GuiAddBlock(this.blocks.get(this.selected), null));
    }

    public void initGui() {
        this.render = this.itemRender;
        this.blockList = new GuiBlocksList(this, this.blocks);
        this.search = new GuiTextField(150, this.getFontRender(), this.width / 2 - 100, this.height / 2 + 85, 140, 18);
        this.search.setFocused(true);
        this.search.setCanLoseFocus(true);
        this.buttonList.add(new GuiButton(0, this.width / 2 + 43, this.height / 2 + 84, 60, 20, I18n.format((String)"xray.single.cancel", (Object[])new Object[0])));
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                this.mc.thePlayer.closeScreen();
                this.mc.displayGuiScreen((GuiScreen)new GuiSelectionScreen());
                break;
            }
        }
    }

    @Override
    protected void keyTyped(char charTyped, int hex) throws IOException {
        super.keyTyped(charTyped, hex);
        this.search.textboxKeyTyped(charTyped, hex);
    }

    public void updateScreen() {
        this.search.updateCursorCounter();
        if (!this.search.getText().equals(this.lastSearched)) {
            this.reloadBlocks();
        }
    }

    private void reloadBlocks() {
        this.blocks = new ArrayList();
        ArrayList<BlockItem> tmpBlocks = new ArrayList<BlockItem>();
        for (BlockItem block : XRay.gameBlockStore.getStore()) {
            if (!block.getItemStack().getDisplayName().toLowerCase().contains(this.search.getText().toLowerCase())) continue;
            tmpBlocks.add(block);
        }
        this.blocks = tmpBlocks;
        this.blockList.updateBlockList(this.blocks);
        this.lastSearched = this.search.getText();
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.search.drawTextBox();
        this.blockList.drawScreen(x, y, f);
    }

    @Override
    public void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        this.search.func_146192_a(x, y, button);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        this.blockList.handleMouseInput(mouseX, mouseY);
    }

    Minecraft getMinecraftInstance() {
        return this.mc;
    }

    RenderItem getRender() {
        return this.render;
    }
}

