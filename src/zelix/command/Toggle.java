/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;

public class Toggle
extends Command {
    public Toggle() {
        super("t");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            HackManager.getHack(args[0]).toggle();
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Toggling selected hack.";
    }

    @Override
    public String getSyntax() {
        return "t <hackname>";
    }
}

