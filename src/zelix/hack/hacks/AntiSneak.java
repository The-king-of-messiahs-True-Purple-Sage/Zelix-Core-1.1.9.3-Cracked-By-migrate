/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;

public class AntiSneak
extends Hack {
    public BooleanValue fullSprint = new BooleanValue("FullSprint", true);

    public AntiSneak() {
        super("AntiSneak", HackCategory.MOVEMENT);
        this.addValue(this.fullSprint);
    }

    @Override
    public String getDescription() {
        return "Does not change walking speed when sneak.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        CPacketEntityAction p;
        return side != Connection.Side.OUT || !(packet instanceof CPacketEntityAction) || (p = (CPacketEntityAction)packet).func_180764_b() != CPacketEntityAction.Action.START_SNEAKING;
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        GameSettings settings = Wrapper.INSTANCE.mcSettings();
        if (player.onGround && settings.keyBindSneak.isKeyDown()) {
            if (!((Boolean)this.fullSprint.getValue()).booleanValue() && settings.keyBindForward.isKeyDown()) {
                player.setSprinting(Utils.isMoving((Entity)player));
            } else if (((Boolean)this.fullSprint.getValue()).booleanValue()) {
                player.setSprinting(Utils.isMoving((Entity)player));
            }
            if (settings.keyBindRight.isKeyDown() || settings.keyBindLeft.isKeyDown() || settings.keyBindBack.isKeyDown()) {
                if (settings.keyBindBack.isKeyDown()) {
                    player.motionX *= 1.268;
                    player.motionZ *= 1.268;
                } else {
                    player.motionX *= 1.252;
                    player.motionZ *= 1.252;
                }
            } else {
                player.motionX *= 1.2848;
                player.motionZ *= 1.2848;
            }
        }
        super.onClientTick(event);
    }
}

