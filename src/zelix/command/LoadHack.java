/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.utils.hooks.visual.ChatUtils;

public class LoadHack
extends Command {
    public LoadHack() {
        super("loadWURST");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            new ForgeWurst();
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "loadhack other hacks";
    }

    @Override
    public String getSyntax() {
        return "loadhack WURST";
    }
}

