/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 */
package zelix.hack.hacks;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class SuperKick
extends Hack {
    NumberValue hurtTimeValue = new NumberValue("HurtTime", 10.0, 0.0, 10.0);

    public SuperKick() {
        super("SuperKick", HackCategory.COMBAT);
        this.addValue(this.hurtTimeValue);
    }

    @Override
    public void onAttackEntity(AttackEntityEvent event) {
        if (event.getTarget().hurtResistantTime > ((Double)this.hurtTimeValue.getValue()).intValue() && Wrapper.INSTANCE.player().isSprinting()) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.STOP_SPRINTING));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.START_SPRINTING));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.STOP_SPRINTING));
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.INSTANCE.player(), CPacketEntityAction.Action.START_SPRINTING));
            Wrapper.INSTANCE.player().setSprinting(true);
        }
        super.onAttackEntity(event);
    }
}

