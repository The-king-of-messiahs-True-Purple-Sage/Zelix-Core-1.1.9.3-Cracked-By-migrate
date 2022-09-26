/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;

public final class SetEnumCmd
extends Command {
    public SetEnumCmd() {
        super("setenum", "Modifies an enum setting.", "Syntax: .setenum <hack> <enum> <value>");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length != 3) {
            throw new Command.CmdSyntaxError();
        }
        Hack hack = wurst.getHax().get(args[0]);
        if (hack == null) {
            throw new Command.CmdError("Hack \"" + args[0] + "\" could not be found.");
        }
        Setting setting = hack.getSettings().get(args[1].toLowerCase().replace("_", " "));
        if (setting == null) {
            throw new Command.CmdError("Setting \"" + args[0] + " " + args[1] + "\" could not be found.");
        }
        if (!(setting instanceof EnumSetting)) {
            throw new Command.CmdError(hack.getName() + " " + setting.getName() + " is not an enum.");
        }
        EnumSetting e = (EnumSetting)setting;
        e.setSelected(args[2].replace("_", " "));
    }
}

