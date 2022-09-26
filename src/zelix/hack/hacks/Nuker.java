/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.PlayerControllerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class Nuker
extends Hack {
    public ModeValue mode;
    public NumberValue distance;
    public BooleanValue packet;
    public BooleanValue nofalling;
    public final ArrayDeque<Set<BlockPos>> prevBlocks = new ArrayDeque();
    public BlockPos currentBlock;
    public float progress;
    public float prevProgress;
    public int id;

    public Nuker() {
        super("Nuker", HackCategory.PLAYER);
        this.mode = new ModeValue("Mode", new Mode("ID", true), new Mode("All", false));
        this.distance = new NumberValue("Distance", 6.0, 0.1, 6.0);
        this.packet = new BooleanValue("Creative", false);
        this.nofalling = new BooleanValue("NoFallingBlocks", true);
        this.addValue(this.mode, this.distance, this.packet, this.nofalling);
    }

    @Override
    public String getDescription() {
        return "Automatically breaks blocks around you.";
    }

    @Override
    public void onDisable() {
        if (this.currentBlock != null) {
            PlayerControllerUtils.setIsHittingBlock(true);
            Wrapper.INSTANCE.controller().resetBlockRemoving();
            this.currentBlock = null;
        }
        this.prevBlocks.clear();
        this.id = 0;
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        this.currentBlock = null;
        Vec3d eyesPos = Utils.getEyesPos().func_178786_a(0.5, 0.5, 0.5);
        BlockPos eyesBlock = new BlockPos(Utils.getEyesPos());
        double rangeSq = Math.pow((Double)this.distance.getValue(), 2.0);
        int blockRange = (int)Math.ceil((Double)this.distance.getValue());
        Stream<Object> stream = StreamSupport.stream(BlockPos.func_177980_a((BlockPos)eyesBlock.func_177982_a(blockRange, blockRange, blockRange), (BlockPos)eyesBlock.func_177982_a(-blockRange, -blockRange, -blockRange)).spliterator(), true);
        stream = stream.filter(pos -> eyesPos.func_72436_e(new Vec3d((Vec3i)pos)) <= rangeSq).filter(pos -> BlockUtils.canBeClicked(pos)).sorted(Comparator.comparingDouble(pos -> eyesPos.func_72436_e(new Vec3d((Vec3i)pos))));
        if (this.mode.getMode("ID").isToggled()) {
            stream = stream.filter(pos -> Block.getIdFromBlock((Block)BlockUtils.getBlock(pos)) == this.id);
        } else if (this.mode.getMode("All").isToggled()) {
            // empty if block
        }
        List blocks = stream.collect(Collectors.toList());
        if (Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Stream<Object> stream2 = blocks.parallelStream();
            for (Set<BlockPos> set : this.prevBlocks) {
                stream2 = stream2.filter(pos -> !set.contains(pos));
            }
            List<BlockPos> blocks2 = stream2.collect(Collectors.toList());
            this.prevBlocks.addLast(new HashSet(blocks2));
            while (this.prevBlocks.size() > 5) {
                this.prevBlocks.removeFirst();
            }
            if (!blocks2.isEmpty()) {
                this.currentBlock = (BlockPos)blocks2.get(0);
            }
            Wrapper.INSTANCE.controller().resetBlockRemoving();
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
            BlockUtils.breakBlocksPacketSpam(blocks2);
            return;
        }
        for (BlockPos pos2 : blocks) {
            if (!(BlockUtils.getBlock(pos2) instanceof Block) && ((Boolean)this.nofalling.getValue()).booleanValue()) continue;
            if (((Boolean)this.packet.getValue()).booleanValue()) {
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos2, EnumFacing.UP));
                continue;
            }
            if (!BlockUtils.breakBlockSimple(pos2)) continue;
            this.currentBlock = pos2;
            break;
        }
        if (this.currentBlock == null) {
            Wrapper.INSTANCE.controller().resetBlockRemoving();
        }
        if (this.currentBlock != null && BlockUtils.getHardness(this.currentBlock) < 1.0f) {
            this.prevProgress = this.progress;
        }
        this.progress = PlayerControllerUtils.getCurBlockDamageMP();
        if (this.progress < this.prevProgress) {
            this.prevProgress = this.progress;
        } else {
            this.progress = 1.0f;
            this.prevProgress = 1.0f;
        }
        super.onClientTick(event);
    }

    @Override
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (this.mode.getMode("ID").isToggled() && Wrapper.INSTANCE.world().isRemote) {
            IBlockState blockState = BlockUtils.getState(event.getPos());
            this.id = Block.getIdFromBlock((Block)blockState.getBlock());
        }
        super.onLeftClickBlock(event);
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.currentBlock == null) {
            return;
        }
        if (this.mode.getMode("All").isToggled()) {
            RenderUtils.drawBlockESP(this.currentBlock, 1.0f, 0.0f, 0.0f);
        } else if (this.mode.getMode("ID").isToggled()) {
            RenderUtils.drawBlockESP(this.currentBlock, 0.0f, 0.0f, 1.0f);
        }
        super.onRenderWorldLast(event);
    }
}

