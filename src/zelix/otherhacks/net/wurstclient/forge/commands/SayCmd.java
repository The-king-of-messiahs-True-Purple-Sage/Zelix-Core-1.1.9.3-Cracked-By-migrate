/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import zelix.otherhacks.net.wurstclient.forge.Command;

public final class SayCmd
extends Command {
    public SayCmd() {
        super("say", "Sends the given chat message.", "Syntax: .say <message>");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length < 1) {
            throw new Command.CmdSyntaxError();
        }
        String message = String.join((CharSequence)" ", args);
        CPacketChatMessage packet = new CPacketChatMessage(message);
        mc.getNetHandler().addToSendQueue((Packet)packet);
    }
}

