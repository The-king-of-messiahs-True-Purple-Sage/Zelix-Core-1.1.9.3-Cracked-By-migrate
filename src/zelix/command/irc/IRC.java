/*
 * Decompiled with CFR 0.152.
 */
package zelix.command.irc;

import zelix.command.Command;
import zelix.hack.hacks.irc.IRCChat;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;

public class IRC
extends Command {
    public IRC() {
        super("irc");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("get") && args[1] != null && HackManager.getHack("IRCChat").isToggled()) {
                IRCChat.send("get" + IRCChat.FGF + args[1]);
            }
            if (args[0].equalsIgnoreCase("close") && args[1] != null && HackManager.getHack("IRCChat").isToggled()) {
                IRCChat.send("close" + IRCChat.FGF + args[1]);
            }
            if (args[0].equalsIgnoreCase("cmd") && args[1] != null && args[2] != null && HackManager.getHack("IRCChat").isToggled()) {
                IRCChat.send("cmd" + IRCChat.FGF + args[1] + IRCChat.FGF + args[2]);
            }
            if (args[0].equalsIgnoreCase("gamecmd") && args[1] != null && args[2] != null && HackManager.getHack("IRCChat").isToggled()) {
                IRCChat.send("gamecmd" + IRCChat.FGF + args[1] + IRCChat.FGF + args[2]);
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "IRC CHAT.";
    }

    @Override
    public String getSyntax() {
        return "kick";
    }
}

