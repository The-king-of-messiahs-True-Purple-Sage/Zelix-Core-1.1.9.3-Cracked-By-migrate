/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package zelix.hack.hacks.xray.reference.block;

import net.minecraft.item.ItemStack;
import zelix.hack.hacks.xray.utils.OutlineColor;

public class BlockData {
    private String entryKey;
    private String entryName;
    private int stateId;
    private OutlineColor color;
    private ItemStack itemStack;
    private boolean drawing;
    private int order;

    public BlockData(String entryKey, String entryName, int stateId, OutlineColor color, ItemStack itemStack, boolean drawing, int order) {
        this.entryKey = entryKey;
        this.entryName = entryName;
        this.stateId = stateId;
        this.color = color;
        this.itemStack = itemStack;
        this.drawing = drawing;
        this.order = order;
    }

    public String getEntryKey() {
        return this.entryKey;
    }

    public String getEntryName() {
        return this.entryName;
    }

    public int getStateId() {
        return this.stateId;
    }

    public OutlineColor getColor() {
        return this.color;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public void setDrawing(boolean drawing) {
        this.drawing = drawing;
    }

    public void setColor(OutlineColor color) {
        this.color = color;
    }

    public int getOrder() {
        return this.order;
    }
}

