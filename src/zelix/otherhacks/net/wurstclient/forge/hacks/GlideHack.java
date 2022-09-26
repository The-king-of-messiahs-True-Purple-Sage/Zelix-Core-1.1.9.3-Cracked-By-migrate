/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;

public final class GlideHack
extends Hack {
    private final SliderSetting fallSpeed = new SliderSetting("Fall speed", 0.125, 0.005, 0.25, 0.005, SliderSetting.ValueDisplay.DECIMAL);
    private final SliderSetting moveSpeed = new SliderSetting("Move speed", "Horizontal movement factor.", 1.2, 1.0, 5.0, 0.05, SliderSetting.ValueDisplay.PERCENTAGE);
    private final SliderSetting minHeight = new SliderSetting("Min height", "Won't glide when you are\ntoo close to the ground.", 0.0, 0.0, 2.0, 0.01, v -> v == 0.0 ? "disabled" : SliderSetting.ValueDisplay.DECIMAL.getValueString(v));

    public GlideHack() {
        super("Glide", "Makes you glide down slowly when falling.");
        this.setCategory(Category.MOVEMENT);
        this.addSetting(this.fallSpeed);
        this.addSetting(this.moveSpeed);
        this.addSetting(this.minHeight);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        EntityPlayerSP player = event.getPlayer();
        World world = player.worldObj;
        if (!player.isAirBorne || player.isInWater() || player.isInLava() || player.isOnLadder() || player.motionY >= 0.0) {
            return;
        }
        if (this.minHeight.getValue() > 0.0) {
            AxisAlignedBB box = player.func_174813_aQ();
            if (world.func_184143_b(box = box.func_111270_a(box.func_72317_d(0.0, -this.minHeight.getValue(), 0.0)))) {
                return;
            }
            BlockPos min = new BlockPos(new Vec3d(box.field_72340_a, box.field_72338_b, box.field_72339_c));
            BlockPos max = new BlockPos(new Vec3d(box.field_72336_d, box.field_72337_e, box.field_72334_f));
            Stream<BlockPos> stream = StreamSupport.stream(BlockPos.func_177980_a((BlockPos)min, (BlockPos)max).spliterator(), true);
            if (stream.map(BlockUtils::getBlock).anyMatch(b -> b instanceof BlockLiquid)) {
                return;
            }
        }
        player.motionY = Math.max(player.motionY, -this.fallSpeed.getValue());
        player.jumpMovementFactor *= this.moveSpeed.getValueF();
    }
}

