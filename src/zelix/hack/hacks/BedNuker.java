/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBed
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;

public class BedNuker
extends Hack {
    public static BlockPos blockBreaking;
    TimerUtils timer = new TimerUtils();
    List<BlockPos> beds = new ArrayList<BlockPos>();
    public BooleanValue instant = new BooleanValue("Instant", false);

    public BedNuker() {
        super("BedNuker", HackCategory.PLAYER);
        this.addValue(this.instant);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        int reach;
        if (Wrapper.INSTANCE.world() == null || Wrapper.INSTANCE.player() == null) {
            return;
        }
        for (int y = reach = 6; y >= -reach; --y) {
            for (int x = -reach; x <= reach; ++x) {
                for (int z = -reach; z <= reach; ++z) {
                    if (Wrapper.INSTANCE.player().isSneaking()) {
                        return;
                    }
                    BlockPos pos = new BlockPos(Wrapper.INSTANCE.player().posX + (double)x, Wrapper.INSTANCE.player().posY + (double)y, Wrapper.INSTANCE.player().posZ + (double)z);
                    if (!this.blockChecks(Wrapper.INSTANCE.world().func_180495_p(pos).getBlock()) || !(Wrapper.INSTANCE.player().getDistance(Wrapper.INSTANCE.player().posX + (double)x, Wrapper.INSTANCE.player().posY + (double)y, Wrapper.INSTANCE.player().posZ + (double)z) < (double)Wrapper.INSTANCE.controller().getBlockReachDistance() - 0.2) || this.beds.contains(pos)) continue;
                    this.beds.add(pos);
                }
            }
        }
        BlockPos closest = null;
        if (!this.beds.isEmpty()) {
            for (int i = 0; i < this.beds.size(); ++i) {
                BlockPos bed = this.beds.get(i);
                if (Wrapper.INSTANCE.player().getDistance((double)bed.func_177958_n(), (double)bed.func_177956_o(), (double)bed.func_177952_p()) > (double)Wrapper.INSTANCE.controller().getBlockReachDistance() - 0.2 || Wrapper.INSTANCE.world().func_180495_p(bed).getBlock() != Blocks.bed) {
                    this.beds.remove(i);
                }
                if (closest != null && (!(Wrapper.INSTANCE.player().getDistance((double)bed.func_177958_n(), (double)bed.func_177956_o(), (double)bed.func_177952_p()) < Wrapper.INSTANCE.player().getDistance((double)closest.func_177958_n(), (double)closest.func_177956_o(), (double)closest.func_177952_p())) || Wrapper.INSTANCE.player().ticksExisted % 50 != 0)) continue;
                closest = bed;
            }
        }
        if (closest != null) {
            float[] rot = this.getRotations(closest, this.getClosestEnum(closest));
            blockBreaking = closest;
            return;
        }
        blockBreaking = null;
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (blockBreaking != null) {
            if (((Boolean)this.instant.getValue()).booleanValue()) {
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockBreaking, EnumFacing.DOWN));
                Wrapper.INSTANCE.player().func_184609_a(EnumHand.MAIN_HAND);
                Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockBreaking, EnumFacing.DOWN));
            } else {
                Field field = ReflectionHelper.findField(PlayerControllerMP.class, (String[])new String[]{"curBlockDamageMP", "field_78770_f"});
                Field blockdelay = ReflectionHelper.findField(PlayerControllerMP.class, (String[])new String[]{"blockHitDelay", "field_78781_i"});
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (!blockdelay.isAccessible()) {
                        blockdelay.setAccessible(true);
                    }
                    if (field.getFloat(Wrapper.INSTANCE.mc().playerController) > 1.0f) {
                        blockdelay.setInt(Wrapper.INSTANCE.mc().playerController, 1);
                    }
                    Wrapper.INSTANCE.player().func_184609_a(EnumHand.MAIN_HAND);
                    EnumFacing direction = this.getClosestEnum(blockBreaking);
                    if (direction != null) {
                        Wrapper.INSTANCE.controller().func_180512_c(blockBreaking, direction);
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        super.onPlayerTick(event);
    }

    @Override
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (blockBreaking != null) {
            GlStateManager.pushMatrix();
            GlStateManager.disableDepth();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        super.onRenderWorldLast(event);
    }

    private boolean blockChecks(Block block) {
        return block == Blocks.bed;
    }

    public float[] getRotations(BlockPos block, EnumFacing face) {
        double x = (double)block.func_177958_n() + 0.5 - Wrapper.INSTANCE.player().posX;
        double z = (double)block.func_177952_p() + 0.5 - Wrapper.INSTANCE.player().posZ;
        double d1 = Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight() - ((double)block.func_177956_o() + 0.5);
        double d3 = MathHelper.func_76133_a((double)(x * x + z * z));
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    private EnumFacing getClosestEnum(BlockPos pos) {
        EnumFacing closestEnum = EnumFacing.UP;
        float rotations = MathHelper.func_76142_g((float)this.getRotations(pos, EnumFacing.UP)[0]);
        if (rotations >= 45.0f && rotations <= 135.0f) {
            closestEnum = EnumFacing.EAST;
        } else if (rotations >= 135.0f && rotations <= 180.0f || rotations <= -135.0f && rotations >= -180.0f) {
            closestEnum = EnumFacing.SOUTH;
        } else if (rotations <= -45.0f && rotations >= -135.0f) {
            closestEnum = EnumFacing.WEST;
        } else if (rotations >= -45.0f && rotations <= 0.0f || rotations <= 45.0f && rotations >= 0.0f) {
            closestEnum = EnumFacing.NORTH;
        }
        if (MathHelper.func_76142_g((float)this.getRotations(pos, EnumFacing.UP)[1]) > 75.0f || MathHelper.func_76142_g((float)this.getRotations(pos, EnumFacing.UP)[1]) < -75.0f) {
            closestEnum = EnumFacing.UP;
        }
        return closestEnum;
    }

    private EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        if (!Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 1, 0)).getBlock().func_149686_d(null) && !(Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.UP;
        } else if (!Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, -1, 0)).getBlock().func_149686_d(null) && !(Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, -1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.DOWN;
        } else if (!Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(1, 0, 0)).getBlock().func_149686_d(null) && !(Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.EAST;
        } else if (!Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(-1, 0, 0)).getBlock().func_149686_d(null) && !(Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(-1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.WEST;
        } else if (!Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 0, 1)).getBlock().func_149686_d(null) && !(Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 0, 1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.SOUTH;
        } else if (!Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 0, 1)).getBlock().func_149686_d(null) && !(Wrapper.INSTANCE.world().func_180495_p(pos.func_177982_a(0, 0, -1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.NORTH;
        }
        RayTraceResult rayResult = Wrapper.INSTANCE.world().func_72933_a(new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ), new Vec3d((double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o(), (double)pos.func_177952_p() + 0.5));
        if (rayResult != null && rayResult.func_178782_a() == pos) {
            return rayResult.field_178784_b;
        }
        return direction;
    }
}

