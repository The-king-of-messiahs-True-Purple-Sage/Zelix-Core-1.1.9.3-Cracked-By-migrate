/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.Core;
import zelix.command.Command;
import zelix.utils.hooks.visual.ChatUtils;

public class Cape
extends Command {
    public Cape() {
        super("Cape");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equals("reload")) {
                Core.capeManager.reload();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Opening directory of config.";
    }

    @Override
    public String getSyntax() {
        return "opendir";
    }
}

