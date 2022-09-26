/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package zelix.command;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import zelix.command.Command;
import zelix.utils.Wrapper;

public class TP
extends Command {
    public TP() {
        super("tp");
    }

    @Override
    public void runCommand(String s, String[] args) {
        if (args[0] != null && args[1] != null && args[2] != null) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            Minecraft.getMinecraft().thePlayer.setPosition((double)x, (double)y, (double)z);
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position((double)x, (double)y, (double)z, Wrapper.INSTANCE.player().onGround));
        }
    }

    @Override
    public String getDescription() {
        return "TP Anywhere";
    }

    @Override
    public String getSyntax() {
        return "tp <X> <Y> <Z>tp <PlayerID>";
    }
}

