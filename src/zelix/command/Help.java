/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.managers.CommandManager;
import zelix.utils.hooks.visual.ChatUtils;

public class Help
extends Command {
    public Help() {
        super("help");
    }

    @Override
    public void runCommand(String s, String[] args) {
        for (Command cmd : CommandManager.commands) {
            if (cmd == this) continue;
            ChatUtils.message(cmd.getSyntax().replace("<", "<\u00a79").replace(">", "\u00a77>") + " - " + cmd.getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "Lists all commands.";
    }

    @Override
    public String getSyntax() {
        return "help";
    }
}

