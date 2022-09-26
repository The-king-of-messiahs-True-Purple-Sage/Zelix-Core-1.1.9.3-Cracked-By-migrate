/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.hack.Hack;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.BooleanValue;
import zelix.value.Value;

public class SetCheckbox
extends Command {
    public SetCheckbox() {
        super("setc");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        Hack hack = HackManager.getHack(subcommands[0]);
        if (!hack.getValues().isEmpty()) {
            for (Value value : hack.getValues()) {
                if (!value.getName().equalsIgnoreCase(subcommands[1]) || !(value instanceof BooleanValue)) continue;
                if (subcommands[2].equalsIgnoreCase("true") || subcommands[2].equalsIgnoreCase("false")) {
                    value.setValue(Boolean.parseBoolean(subcommands[2]));
                    ChatUtils.message("Boolean parameters set successfully!");
                    FileManager.saveHacks();
                    FileManager.saveClickGui();
                    continue;
                }
                ChatUtils.error("Please use true or false for the third parameter");
                ChatUtils.message(subcommands[2]);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Set the Boolean parameter";
    }

    @Override
    public String getSyntax() {
        return "setc <hack> <setting> <value>";
    }
}

