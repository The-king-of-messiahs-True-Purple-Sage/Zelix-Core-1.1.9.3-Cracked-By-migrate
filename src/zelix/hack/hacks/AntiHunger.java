/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.utils.system.Mapping;
import zelix.value.BooleanValue;

public class AntiHunger
extends Hack {
    public static BooleanValue sprint = new BooleanValue("Sprint", false);
    public static BooleanValue ground = new BooleanValue("Ground", false);

    public AntiHunger() {
        super("AntiHunger", HackCategory.PLAYER);
        this.addValue(sprint, ground);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        CPacketPlayer l_Packet;
        if ((Packet)packet instanceof CPacketPlayer && ((Boolean)ground.getValue()).booleanValue() && !Wrapper.INSTANCE.player().func_184613_cA()) {
            l_Packet = (CPacketPlayer)packet;
            if (Wrapper.INSTANCE.player().fallDistance > 0.0f || Wrapper.INSTANCE.controller().getIsHittingBlock()) {
                ReflectionHelper.setPrivateValue(CPacketPlayer.class, (Object)l_Packet, (Object)true, (String[])new String[]{Mapping.onGround});
            } else {
                ReflectionHelper.setPrivateValue(CPacketPlayer.class, (Object)l_Packet, (Object)false, (String[])new String[]{Mapping.onGround});
            }
        }
        return !((Packet)packet instanceof CPacketEntityAction) || (Boolean)sprint.getValue() == false || (l_Packet = (CPacketEntityAction)packet).func_180764_b() != CPacketEntityAction.Action.START_SPRINTING && l_Packet.func_180764_b() != CPacketEntityAction.Action.STOP_SPRINTING;
    }
}

