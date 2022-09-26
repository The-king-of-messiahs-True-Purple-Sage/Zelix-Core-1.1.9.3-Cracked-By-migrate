/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package zelix.hack.hacks;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.NumberValue;

public class Cilp
extends Hack {
    TimerUtils timer = new TimerUtils();
    NumberValue horizontalValue = new NumberValue("Horizontal", 0.0, 0.0, 10.0);
    NumberValue verticalValue = new NumberValue("Vertical", 5.0, 0.0, 10.0);

    public Cilp() {
        super("Cilp", HackCategory.MOVEMENT);
        this.addValue(this.horizontalValue, this.verticalValue);
    }

    @Override
    public void onEnable() {
        Utils.nullCheck();
        this.Jump();
        this.toggle();
        super.onEnable();
    }

    public void Jump() {
        double yaw = Math.toRadians(Wrapper.INSTANCE.player().rotationYaw);
        double x = -Math.sin(yaw) * (Double)this.horizontalValue.getValue();
        double z = Math.cos(yaw) * (Double)this.horizontalValue.getValue();
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, true));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(0.5, 0.0, 0.5, true));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, true));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX + x, Wrapper.INSTANCE.player().posY + (Double)this.verticalValue.getValue(), Wrapper.INSTANCE.player().posZ + z, true));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(0.5, 0.0, 0.5, true));
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX + 0.5, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ + 0.5, true));
        Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX + -Math.sin(yaw) * 0.04, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ + Math.cos(yaw) * 0.04);
    }
}

