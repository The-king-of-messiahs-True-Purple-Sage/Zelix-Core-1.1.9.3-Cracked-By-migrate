/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;

public final class SetCheckboxCmd
extends Command {
    public SetCheckboxCmd() {
        super("setcheckbox", "Modifies a checkbox setting.", "Syntax: .setcheckbox <hack> <checkbox> <value>");
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
        if (!(setting instanceof CheckboxSetting)) {
            throw new Command.CmdError(hack.getName() + " " + setting.getName() + " is not a checkbox.");
        }
        CheckboxSetting e = (CheckboxSetting)setting;
        if (!args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false")) {
            throw new Command.CmdSyntaxError("Not a boolean: " + args[2]);
        }
        e.setChecked(Boolean.parseBoolean(args[2]));
    }
}

