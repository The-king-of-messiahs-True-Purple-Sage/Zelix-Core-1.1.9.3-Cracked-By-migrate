/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraftforge.fml.client.GuiScrollingList
 */
package zelix.hack.hacks.xray.gui.manage;

import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;
import zelix.hack.hacks.xray.gui.manage.GuiBlockListScrollable;
import zelix.hack.hacks.xray.reference.block.BlockItem;

public class GuiBlocksList
extends GuiScrollingList {
    private static final int HEIGHT = 35;
    private final GuiBlockListScrollable parent;
    private List<BlockItem> blockList;

    GuiBlocksList(GuiBlockListScrollable parent, List<BlockItem> blockList) {
        super(parent.getMinecraftInstance(), 202, 210, parent.height / 2 - 105, parent.height / 2 + 80, parent.width / 2 - 100, 35, parent.width, parent.height);
        this.parent = parent;
        this.blockList = blockList;
    }

    protected int getSize() {
        return this.blockList.size();
    }

    protected void elementClicked(int index, boolean doubleClick) {
        this.parent.selectBlock(index);
    }

    protected boolean isSelected(int index) {
        return this.parent.blockSelected(index);
    }

    protected void drawBackground() {
    }

    protected int getContentHeight() {
        return this.getSize() * 35;
    }

    protected void drawSlot(int idx, int right, int top, int height, Tessellator tess) {
        BlockItem block = this.blockList.get(idx);
        FontRenderer font = this.parent.getFontRender();
        font.drawString(block.getItemStack().getDisplayName(), this.left + 30, top + 7, 0xFFFFFF);
        font.drawString(Objects.requireNonNull(block.getItemStack().getItem().getRegistryName()).getResourceDomain(), this.left + 30, top + 17, 13750223);
        RenderHelper.enableGUIStandardItemLighting();
        this.parent.getRender().func_180450_b(block.getItemStack(), this.left + 5, top + 7);
        RenderHelper.disableStandardItemLighting();
    }

    void updateBlockList(List<BlockItem> blocks) {
        this.blockList = blocks;
    }
}

