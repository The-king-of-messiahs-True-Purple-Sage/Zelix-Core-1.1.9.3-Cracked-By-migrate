/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.CommandList;
import zelix.otherhacks.net.wurstclient.forge.events.WChatOutputEvent;
import zelix.otherhacks.net.wurstclient.forge.utils.ChatUtils;

public final class CommandProcessor {
    private final CommandList cmds;

    public CommandProcessor(CommandList cmds) {
        this.cmds = cmds;
    }

    @SubscribeEvent
    public void onSentMessage(WChatOutputEvent event) {
        String message = event.getMessage().trim();
        if (!message.startsWith(".")) {
            return;
        }
        event.setCanceled(true);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(message);
        this.runCommand(message.substring(1));
    }

    public void runCommand(String input) {
        String[] parts = input.split(" ");
        Command cmd = this.cmds.get(parts[0]);
        if (cmd == null) {
            ChatUtils.error("Unknown command: ." + parts[0]);
            if (input.startsWith("/")) {
                ChatUtils.message("Use \".say " + input + "\" to send it as a chat command.");
            } else {
                ChatUtils.message("Type \".help\" for a list of commands or \".say ." + input + "\" to send it as a chat message.");
            }
            return;
        }
        try {
            cmd.call(Arrays.copyOfRange(parts, 1, parts.length));
        }
        catch (Command.CmdException e) {
            e.printToChat();
        }
    }
}

