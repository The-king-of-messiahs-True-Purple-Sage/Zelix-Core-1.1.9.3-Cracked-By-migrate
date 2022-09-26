/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 */
package zelix.hack.hacks;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;

public class SelfKick
extends Hack {
    public SelfKick() {
        super("SelfKick", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Kick you from Server.";
    }

    @Override
    public void onEnable() {
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Rotation(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
        super.onEnable();
    }
}

