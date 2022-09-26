/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.fml.common.registry.ForgeRegistries
 */
package zelix.hack.hacks.xray.store;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import zelix.hack.hacks.xray.reference.block.BlockItem;
import zelix.hack.hacks.xray.xray.Controller;

public class GameBlockStore {
    private ArrayList<BlockItem> store = new ArrayList();

    public void populate() {
        if (this.store.size() != 0) {
            return;
        }
        for (Item item : ForgeRegistries.ITEMS) {
            if (!(item instanceof ItemBlock)) continue;
            Block block = Block.getBlockFromItem((Item)item);
            if (item == Items.field_190931_a || block == Blocks.air) continue;
            if (item.getHasSubtypes() && item.getCreativeTab() != null) {
                NonNullList subItems = NonNullList.func_191196_a();
                item.func_150895_a(item.getCreativeTab(), subItems);
                for (ItemStack subItem : subItems) {
                    if (subItem.equals(ItemStack.field_190927_a) || subItem.getItem() == Items.field_190931_a || Controller.blackList.contains(block)) continue;
                    this.store.add(new BlockItem(Block.getStateId((IBlockState)Block.getBlockFromItem((Item)subItem.getItem()).func_176194_O().func_177621_b()), subItem));
                }
                continue;
            }
            if (Controller.blackList.contains(block)) continue;
            this.store.add(new BlockItem(Block.getStateId((IBlockState)block.func_176194_O().func_177621_b()), new ItemStack(item)));
        }
    }

    public void repopulate() {
        this.store.clear();
        this.populate();
    }

    public ArrayList<BlockItem> getStore() {
        return this.store;
    }
}

