/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.hack.Hack;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.Value;

public class SetMode
extends Command {
    public SetMode() {
        super("setm");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        Hack hack = HackManager.getHack(subcommands[0]);
        if (!hack.getValues().isEmpty()) {
            for (Value value : hack.getValues()) {
                if (!(value instanceof ModeValue) || !((ModeValue)value).getModeName().equalsIgnoreCase(subcommands[1])) continue;
                ModeValue modeValue = (ModeValue)value;
                for (Mode mode2 : modeValue.getModes()) {
                    if (mode2.getName().equalsIgnoreCase(subcommands[2])) {
                        mode2.setToggled(true);
                        ChatUtils.message("Mode parameters set successfully!");
                        FileManager.saveHacks();
                        continue;
                    }
                    mode2.setToggled(false);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Set the Mode parameter";
    }

    @Override
    public String getSyntax() {
        return "setm <hack> <mode> <modevalue> <true/false>";
    }
}

