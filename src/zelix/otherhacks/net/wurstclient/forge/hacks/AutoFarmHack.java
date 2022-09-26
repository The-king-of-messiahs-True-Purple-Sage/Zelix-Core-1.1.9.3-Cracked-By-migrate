/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockCactus
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.block.BlockFarmland
 *  net.minecraft.block.BlockMelon
 *  net.minecraft.block.BlockNetherWart
 *  net.minecraft.block.BlockPumpkin
 *  net.minecraft.block.BlockReed
 *  net.minecraft.block.BlockSoulSand
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockMelon;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.PlayerControllerUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RenderUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

public final class AutoFarmHack
extends Hack {
    private final SliderSetting range = new SliderSetting("Range", 5.0, 1.0, 6.0, 0.05, SliderSetting.ValueDisplay.DECIMAL);
    private final HashMap<BlockPos, Item> plants = new HashMap();
    private final ArrayDeque<Set<BlockPos>> prevBlocks = new ArrayDeque();
    private BlockPos currentBlock;
    private float progress;
    private float prevProgress;
    private int displayList;
    private int box;
    private int node;

    public AutoFarmHack() {
        super("AutoFarm", "Harvests and re-plants crops automatically.\nWorks with wheat, carrots, potatoes, beetroots,\npumpkins, melons, cacti, sugar canes and\nnether warts.");
        this.setCategory(Category.BLOCKS);
        this.addSetting(this.range);
    }

    @Override
    protected void onEnable() {
        this.plants.clear();
        this.displayList = GL11.glGenLists((int)1);
        this.box = GL11.glGenLists((int)1);
        this.node = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.box, (int)4864);
        AxisAlignedBB box = new AxisAlignedBB(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(box);
        GL11.glEnd();
        GL11.glEndList();
        GL11.glNewList((int)this.node, (int)4864);
        AxisAlignedBB node = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);
        GL11.glBegin((int)1);
        RenderUtils.drawNode(node);
        GL11.glEnd();
        GL11.glEndList();
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (this.currentBlock != null) {
            try {
                PlayerControllerUtils.setIsHittingBlock(true);
                AutoFarmHack.mc.playerController.resetBlockRemoving();
                this.currentBlock = null;
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        this.prevBlocks.clear();
        GL11.glDeleteLists((int)this.displayList, (int)1);
        GL11.glDeleteLists((int)this.box, (int)1);
        GL11.glDeleteLists((int)this.node, (int)1);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        Item neededItem;
        BlockPos pos2;
        this.currentBlock = null;
        Vec3d eyesVec = RotationUtils.getEyesPos().func_178786_a(0.5, 0.5, 0.5);
        BlockPos eyesBlock = new BlockPos(RotationUtils.getEyesPos());
        double rangeSq = Math.pow(this.range.getValue(), 2.0);
        int blockRange = (int)Math.ceil(this.range.getValue());
        List<BlockPos> blocks = this.getBlockStream(eyesBlock, blockRange).filter(pos -> eyesVec.func_72436_e(new Vec3d((Vec3i)pos)) <= rangeSq).filter(pos -> BlockUtils.canBeClicked(pos)).collect(Collectors.toList());
        this.registerPlants(blocks);
        List<BlockPos> blocksToHarvest = blocks.parallelStream().filter(this::shouldBeHarvested).sorted(Comparator.comparingDouble(pos -> eyesVec.func_72436_e(new Vec3d((Vec3i)pos)))).collect(Collectors.toList());
        List<BlockPos> blocksToReplant = this.getBlockStream(eyesBlock, blockRange).filter(pos -> eyesVec.func_72436_e(new Vec3d((Vec3i)pos)) <= rangeSq).filter(pos -> BlockUtils.getMaterial(pos).isReplaceable()).filter(pos -> this.plants.containsKey(pos)).filter(this::canBeReplanted).sorted(Comparator.comparingDouble(pos -> eyesVec.func_72436_e(new Vec3d((Vec3i)pos)))).collect(Collectors.toList());
        while (!blocksToReplant.isEmpty() && !this.tryToReplant(pos2 = (BlockPos)blocksToReplant.get(0), neededItem = this.plants.get(pos2))) {
            blocksToReplant.removeIf(p -> this.plants.get(p) == neededItem);
        }
        if (blocksToReplant.isEmpty()) {
            this.harvest(blocksToHarvest);
        }
        this.updateDisplayList(blocksToHarvest, blocksToReplant);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        GL11.glCallList((int)this.displayList);
        if (this.currentBlock != null) {
            GL11.glPushMatrix();
            AxisAlignedBB box = new AxisAlignedBB(BlockPos.field_177992_a);
            float p = this.prevProgress + (this.progress - this.prevProgress) * event.getPartialTicks();
            float red = p * 2.0f;
            float green = 2.0f - red;
            GL11.glTranslated((double)this.currentBlock.func_177958_n(), (double)this.currentBlock.func_177956_o(), (double)this.currentBlock.func_177952_p());
            if (p < 1.0f) {
                GL11.glTranslated((double)0.5, (double)0.5, (double)0.5);
                GL11.glScaled((double)p, (double)p, (double)p);
                GL11.glTranslated((double)-0.5, (double)-0.5, (double)-0.5);
            }
            GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.25f);
            GL11.glBegin((int)7);
            RenderUtils.drawSolidBox(box);
            GL11.glEnd();
            GL11.glColor4f((float)red, (float)green, (float)0.0f, (float)0.5f);
            GL11.glBegin((int)1);
            RenderUtils.drawOutlinedBox(box);
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private Stream<BlockPos> getBlockStream(BlockPos center, int range) {
        return StreamSupport.stream(BlockPos.func_177980_a((BlockPos)center.func_177982_a(range, range, range), (BlockPos)center.func_177982_a(-range, -range, -range)).spliterator(), true);
    }

    private boolean shouldBeHarvested(BlockPos pos) {
        Block block = BlockUtils.getBlock(pos);
        IBlockState state = BlockUtils.getState(pos);
        if (block instanceof BlockCrops) {
            return ((BlockCrops)block).func_185525_y(state);
        }
        if (block instanceof BlockPumpkin || block instanceof BlockMelon) {
            return true;
        }
        if (block instanceof BlockReed) {
            return BlockUtils.getBlock(pos.func_177977_b()) instanceof BlockReed && !(BlockUtils.getBlock(pos.func_177979_c(2)) instanceof BlockReed);
        }
        if (block instanceof BlockCactus) {
            return BlockUtils.getBlock(pos.func_177977_b()) instanceof BlockCactus && !(BlockUtils.getBlock(pos.func_177979_c(2)) instanceof BlockCactus);
        }
        if (block instanceof BlockNetherWart) {
            return (Integer)state.getValue((IProperty)BlockNetherWart.AGE) >= 3;
        }
        return false;
    }

    private void registerPlants(List<BlockPos> blocks) {
        HashMap<Block, Item> seeds = new HashMap<Block, Item>();
        seeds.put(Blocks.wheat, Items.wheat_seeds);
        seeds.put(Blocks.carrots, Items.carrot);
        seeds.put(Blocks.potatoes, Items.potato);
        seeds.put(Blocks.field_185773_cZ, Items.field_185163_cU);
        seeds.put(Blocks.pumpkin_stem, Items.pumpkin_seeds);
        seeds.put(Blocks.melon_stem, Items.melon_seeds);
        seeds.put(Blocks.nether_wart, Items.nether_wart);
        this.plants.putAll(blocks.parallelStream().filter(pos -> seeds.containsKey(BlockUtils.getBlock(pos))).collect(Collectors.toMap(pos -> pos, pos -> (Item)seeds.get(BlockUtils.getBlock(pos)))));
    }

    private boolean canBeReplanted(BlockPos pos) {
        Item item = this.plants.get(pos);
        if (item == Items.wheat_seeds || item == Items.carrot || item == Items.potato || item == Items.field_185163_cU || item == Items.pumpkin_seeds || item == Items.melon_seeds) {
            return BlockUtils.getBlock(pos.func_177977_b()) instanceof BlockFarmland;
        }
        if (item == Items.nether_wart) {
            return BlockUtils.getBlock(pos.func_177977_b()) instanceof BlockSoulSand;
        }
        return false;
    }

    private boolean tryToReplant(BlockPos pos, Item neededItem) {
        EntityPlayerSP player = WMinecraft.getPlayer();
        ItemStack heldItem = player.func_184614_ca();
        if (heldItem != null && !heldItem.func_190926_b() && heldItem.getItem() == neededItem) {
            BlockUtils.placeBlockSimple(pos);
            return true;
        }
        for (int slot = 0; slot < 36; ++slot) {
            ItemStack stack;
            if (slot == player.inventory.currentItem || (stack = player.inventory.getStackInSlot(slot)) == null || stack.func_190926_b() || stack.getItem() != neededItem) continue;
            if (slot < 9) {
                player.inventory.currentItem = slot;
            } else if (player.inventory.getFirstEmptyStack() < 9) {
                PlayerControllerUtils.windowClick_QUICK_MOVE(slot);
            } else if (player.inventory.getFirstEmptyStack() != -1) {
                PlayerControllerUtils.windowClick_QUICK_MOVE(player.inventory.currentItem + 36);
                PlayerControllerUtils.windowClick_QUICK_MOVE(slot);
            } else {
                PlayerControllerUtils.windowClick_PICKUP(player.inventory.currentItem + 36);
                PlayerControllerUtils.windowClick_PICKUP(slot);
                PlayerControllerUtils.windowClick_PICKUP(player.inventory.currentItem + 36);
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void harvest(List<BlockPos> blocksToHarvest) {
        if (WMinecraft.getPlayer().capabilities.isCreativeMode) {
            void var2_3;
            Stream stream = blocksToHarvest.parallelStream();
            for (Set<BlockPos> set : this.prevBlocks) {
                Stream<BlockPos> stream2 = var2_3.filter(pos -> !set.contains(pos));
            }
            List<BlockPos> blocksToHarvest2 = var2_3.collect(Collectors.toList());
            this.prevBlocks.addLast(new HashSet(blocksToHarvest2));
            while (this.prevBlocks.size() > 5) {
                this.prevBlocks.removeFirst();
            }
            if (!blocksToHarvest2.isEmpty()) {
                this.currentBlock = (BlockPos)blocksToHarvest2.get(0);
            }
            AutoFarmHack.mc.playerController.resetBlockRemoving();
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
            BlockUtils.breakBlocksPacketSpam(blocksToHarvest2);
            return;
        }
        for (BlockPos pos2 : blocksToHarvest) {
            if (!BlockUtils.breakBlockSimple(pos2)) continue;
            this.currentBlock = pos2;
            break;
        }
        if (this.currentBlock == null) {
            AutoFarmHack.mc.playerController.resetBlockRemoving();
        }
        if (this.currentBlock != null && BlockUtils.getHardness(this.currentBlock) < 1.0f) {
            try {
                this.prevProgress = this.progress;
                this.progress = PlayerControllerUtils.getCurBlockDamageMP();
                if (!(this.progress < this.prevProgress)) return;
                this.prevProgress = this.progress;
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                this.setEnabled(false);
                throw new RuntimeException(reflectiveOperationException);
            }
        } else {
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
        }
    }

    private void updateDisplayList(List<BlockPos> blocksToHarvest, List<BlockPos> blocksToReplant) {
        GL11.glNewList((int)this.displayList, (int)4864);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        for (BlockPos pos : blocksToHarvest) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
            GL11.glCallList((int)this.box);
            GL11.glPopMatrix();
        }
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        for (BlockPos pos : this.plants.keySet()) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
            GL11.glCallList((int)this.node);
            GL11.glPopMatrix();
        }
        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)0.5f);
        for (BlockPos pos : blocksToReplant) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
            GL11.glCallList((int)this.box);
            GL11.glPopMatrix();
        }
        GL11.glEndList();
    }
}

