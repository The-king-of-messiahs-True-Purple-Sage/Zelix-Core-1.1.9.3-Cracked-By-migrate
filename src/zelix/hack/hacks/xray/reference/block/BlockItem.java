/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package zelix.hack.hacks.xray.reference.block;

import net.minecraft.item.ItemStack;

public class BlockItem {
    private int stateId;
    private ItemStack item;

    public BlockItem(int stateId, ItemStack item) {
        this.stateId = stateId;
        this.item = item;
    }

    public int getStateId() {
        return this.stateId;
    }

    public ItemStack getItemStack() {
        return this.item;
    }
}

