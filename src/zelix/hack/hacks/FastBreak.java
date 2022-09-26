/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.PlayerControllerUtils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.utils.system.Mapping;
import zelix.value.BooleanValue;

public class FastBreak
extends Hack {
    public BooleanValue Reflection = new BooleanValue("Reflection", false);

    public FastBreak() {
        super("FastBreak", HackCategory.PLAYER);
        this.addValue(this.Reflection);
    }

    @Override
    public String getDescription() {
        return "Allows you to break blocks faster.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        PlayerControllerUtils.setBlockHitDelay(0);
        super.onClientTick(event);
    }

    @Override
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        float progress = PlayerControllerUtils.getCurBlockDamageMP() + BlockUtils.getHardness(event.getPos());
        if (progress >= 1.0f) {
            return;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), Wrapper.INSTANCE.mc().objectMouseOver.field_178784_b));
        super.onLeftClickBlock(event);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (((Boolean)this.Reflection.getValue()).booleanValue() && !Wrapper.INSTANCE.controller().isInCreativeMode()) {
            Field field = ReflectionHelper.findField(PlayerControllerMP.class, (String[])new String[]{Mapping.curBlockDamageMP});
            Field blockdelay = ReflectionHelper.findField(PlayerControllerMP.class, (String[])new String[]{Mapping.blockHitDelay});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (!blockdelay.isAccessible()) {
                    blockdelay.setAccessible(true);
                }
                blockdelay.setInt(Wrapper.INSTANCE.controller(), 0);
                if (field.getFloat(Wrapper.INSTANCE.controller()) >= 0.7f) {
                    field.setFloat(Wrapper.INSTANCE.controller(), 1.0f);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

