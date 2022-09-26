/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketAnimation
 */
package zelix.hack.hacks;

import net.minecraft.network.play.client.CPacketAnimation;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.system.Connection;

public class NoSwing
extends Hack {
    public NoSwing() {
        super("NoSwing", HackCategory.PLAYER);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        return side != Connection.Side.OUT || !(packet instanceof CPacketAnimation);
    }
}

