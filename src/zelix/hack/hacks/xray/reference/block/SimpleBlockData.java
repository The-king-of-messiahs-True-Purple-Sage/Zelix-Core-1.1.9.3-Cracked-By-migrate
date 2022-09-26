/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.oredict.OreDictionary
 */
package zelix.hack.hacks.xray.reference.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import zelix.hack.hacks.xray.utils.OutlineColor;

public class SimpleBlockData {
    private String name;
    private String stateString;
    private int stateId;
    private int order;
    private OutlineColor color;
    private boolean drawing;

    public SimpleBlockData(String name, String stateString, int stateId, OutlineColor color, boolean drawing, int order) {
        this.name = name;
        this.stateString = stateString;
        this.stateId = stateId;
        this.color = color;
        this.drawing = drawing;
        this.order = order;
    }

    public static SimpleBlockData firstOreInDictionary(String name, String entryName, int[] color, boolean draw) {
        NonNullList ores = OreDictionary.getOres((String)name);
        if (ores.isEmpty() || ((ItemStack)ores.get(0)).func_190926_b()) {
            return null;
        }
        ItemStack stack = (ItemStack)ores.get(0);
        return new SimpleBlockData(entryName, Block.getBlockFromItem((Item)stack.getItem()).getDefaultState().toString(), Block.getStateId((IBlockState)Block.getBlockFromItem((Item)stack.getItem()).getDefaultState()), new OutlineColor(color[0], color[1], color[2]), draw, 0);
    }

    public String getName() {
        return this.name;
    }

    public String getStateString() {
        return this.stateString;
    }

    public OutlineColor getColor() {
        return this.color;
    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public int getStateId() {
        return this.stateId;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

