/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.hack.Hack;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;

public class Hacks
extends Command {
    public Hacks() {
        super("hacks");
    }

    @Override
    public void runCommand(String s, String[] args) {
        for (Hack hack : HackManager.getHacks()) {
            ChatUtils.message(String.format("%s \u00a79| \u00a7f%s \u00a79| \u00a7f%s \u00a79| \u00a7f%s", new Object[]{hack.getName(), hack.getCategory(), hack.getKey(), hack.isToggled()}));
        }
        ChatUtils.message("Loaded " + HackManager.getHacks().size() + " Hacks.");
    }

    @Override
    public String getDescription() {
        return "Lists all hacks.";
    }

    @Override
    public String getSyntax() {
        return "hacks";
    }
}

