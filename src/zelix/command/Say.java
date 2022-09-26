/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 */
package zelix.command;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import zelix.command.Command;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;

public class Say
extends Command {
    public Say() {
        super("say");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            String content = "";
            for (int i = 0; i < args.length; ++i) {
                content = content + " " + args[i];
            }
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketChatMessage(content));
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Send message to chat.";
    }

    @Override
    public String getSyntax() {
        return "say <message>";
    }
}

