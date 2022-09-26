/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class AntiFall
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("AAC", true), new Mode("Simple", false));

    public AntiFall() {
        super("NoFall", HackCategory.PLAYER);
        this.addValue(this.mode);
    }

    @Override
    public String getDescription() {
        return "Gives you zero damage on fall.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("Simple").isToggled() && Wrapper.INSTANCE.player().fallDistance > 2.0f) {
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(true));
        }
        super.onClientTick(event);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
            CPacketPlayer p = (CPacketPlayer)packet;
            if (this.mode.getMode("AAC").isToggled()) {
                Field field = ReflectionHelper.findField(CPacketPlayer.class, (String[])new String[]{"onGround", "field_149474_g"});
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.setBoolean(p, true);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return true;
    }
}

