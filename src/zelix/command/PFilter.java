/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.managers.PickupFilterManager;
import zelix.utils.hooks.visual.ChatUtils;

public class PFilter
extends Command {
    public PFilter() {
        super("pfilter");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                PickupFilterManager.addItem(Integer.parseInt(args[1]));
            } else if (args[0].equalsIgnoreCase("remove")) {
                PickupFilterManager.removeItem(Integer.parseInt(args[1]));
            } else if (args[0].equalsIgnoreCase("clear")) {
                PickupFilterManager.clear();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "PickupFilter manager.";
    }

    @Override
    public String getSyntax() {
        return "pfilter add <id> | remove <id> | clear";
    }
}

