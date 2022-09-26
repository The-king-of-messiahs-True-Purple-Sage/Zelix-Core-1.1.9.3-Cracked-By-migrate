/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.Hack;

public final class TCmd
extends Command {
    public TCmd() {
        super("t", "Toggles a hack.", "Syntax: .t <hack> [on|off]");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length < 1 || args.length > 2) {
            throw new Command.CmdSyntaxError();
        }
        Hack hack = wurst.getHax().get(args[0]);
        if (hack == null) {
            throw new Command.CmdError("Unknown hack: " + args[0]);
        }
        if (args.length == 1) {
            hack.setEnabled(!hack.isEnabled());
        } else {
            switch (args[1].toLowerCase()) {
                case "on": {
                    hack.setEnabled(true);
                    break;
                }
                case "off": {
                    hack.setEnabled(false);
                    break;
                }
                default: {
                    throw new Command.CmdSyntaxError();
                }
            }
        }
    }
}

