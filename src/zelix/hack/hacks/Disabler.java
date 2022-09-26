/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketPlayer
 */
package zelix.hack.hacks;

import net.minecraft.network.play.client.CPacketPlayer;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.system.Connection;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Disabler
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Hyt", true));

    public Disabler() {
        super("Disabler", HackCategory.COMBAT);
        this.addValue(this.mode);
    }

    @Override
    public String getDescription() {
        return "Hyt Disabler.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        return !(packet instanceof CPacketPlayer);
    }
}

