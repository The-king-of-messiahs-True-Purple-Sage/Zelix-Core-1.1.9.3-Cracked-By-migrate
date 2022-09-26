/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraft.network.play.client.CPacketKeepAlive
 */
package zelix.hack.hacks;

import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketKeepAlive;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.system.Connection;

public class PortalGodMode
extends Hack {
    public PortalGodMode() {
        super("PortalGodMode", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Portal God Mode, cancels the CPacketConfirmTeleport packet.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (packet instanceof CPacketConfirmTeleport) {
            return false;
        }
        return !(packet instanceof CPacketKeepAlive);
    }
}

