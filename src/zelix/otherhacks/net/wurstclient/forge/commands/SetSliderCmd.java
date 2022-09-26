/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.MathUtils;

public final class SetSliderCmd
extends Command {
    public SetSliderCmd() {
        super("setslider", "Modifies a slider setting.", "Syntax: .setslider <hack> <slider> <value>");
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
        if (!(setting instanceof SliderSetting)) {
            throw new Command.CmdError(hack.getName() + " " + setting.getName() + " is not a slider.");
        }
        SliderSetting slider = (SliderSetting)setting;
        if (MathUtils.isDouble(args[2])) {
            slider.setValue(Double.parseDouble(args[2]));
        } else if (args[2].startsWith("~") && MathUtils.isDouble(args[2].substring(1))) {
            slider.setValue(slider.getValue() + Double.parseDouble(args[2].substring(1)));
        } else {
            throw new Command.CmdSyntaxError("Not a number: " + args[2]);
        }
    }
}

