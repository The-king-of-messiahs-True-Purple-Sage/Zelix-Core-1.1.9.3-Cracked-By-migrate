/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.item.ItemStack
 */
package zelix.hack.hacks.xray.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.reference.block.SimpleBlockData;

public class BlockStore {
    static final List<SimpleBlockData> DEFAULT_BLOCKS = new ArrayList<SimpleBlockData>(){
        {
            this.add(SimpleBlockData.firstOreInDictionary("oreIron", "Iron Ore", new int[]{170, 117, 37}, false));
            this.add(SimpleBlockData.firstOreInDictionary("oreCoal", "Coal Ore", new int[]{0, 0, 0}, false));
            this.add(SimpleBlockData.firstOreInDictionary("oreGold", "Gold Ore", new int[]{255, 255, 0}, false));
            this.add(SimpleBlockData.firstOreInDictionary("oreRedstone", "Redstone Ore", new int[]{255, 0, 0}, false));
            this.add(SimpleBlockData.firstOreInDictionary("oreDiamond", "Diamond Ore", new int[]{136, 136, 255}, true));
            this.add(SimpleBlockData.firstOreInDictionary("oreEmerald", "Emerald Ore", new int[]{0, 136, 10}, true));
            this.add(SimpleBlockData.firstOreInDictionary("oreQuartz", "Nether Quart", new int[]{30, 74, 0}, false));
            this.add(SimpleBlockData.firstOreInDictionary("oreLapis", "Lapis", new int[]{0, 0, 255}, false));
        }
    };
    private HashMap<String, BlockData> store = new HashMap();

    public void put(String key, BlockData data) {
        if (this.store.containsKey(key)) {
            return;
        }
        this.store.put(key, data);
    }

    public HashMap<String, BlockData> getStore() {
        return this.store;
    }

    public void setStore(HashMap<String, BlockData> store) {
        this.store = store;
    }

    public void toggleDrawing(String key) {
        BlockData data;
        if (!this.store.containsKey(key)) {
            return;
        }
        data.setDrawing(!(data = this.store.get(key)).isDrawing());
    }

    public static HashMap<String, BlockData> getFromSimpleBlockList(List<SimpleBlockData> simpleList) {
        HashMap<String, BlockData> blockData = new HashMap<String, BlockData>();
        for (SimpleBlockData e : simpleList) {
            IBlockState state = Block.getStateById((int)e.getStateId());
            blockData.put(e.getStateString(), new BlockData(e.getStateString(), e.getName(), e.getStateId(), e.getColor(), new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)), e.isDrawing(), e.getOrder()));
        }
        return blockData;
    }
}

