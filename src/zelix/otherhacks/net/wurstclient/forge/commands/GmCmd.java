/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;

public final class GmCmd
extends Command {
    public GmCmd() {
        super("gm", "Shortcut for /gamemode.", "Syntax: .gm <gamemode>");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length < 1) {
            throw new Command.CmdSyntaxError();
        }
        String message = "/gamemode " + String.join((CharSequence)" ", args);
        WMinecraft.getPlayer().sendChatMessage(message);
    }
}

