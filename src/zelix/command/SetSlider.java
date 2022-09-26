/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import zelix.command.Command;
import zelix.hack.Hack;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.NumberValue;
import zelix.value.Value;

public class SetSlider
extends Command {
    public SetSlider() {
        super("sets");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        Hack hack = HackManager.getHack(subcommands[0]);
        if (!hack.getValues().isEmpty()) {
            for (Value value : hack.getValues()) {
                if (!value.getName().equalsIgnoreCase(subcommands[1]) || !(value instanceof NumberValue)) continue;
                value.setValue(Double.parseDouble(subcommands[2]));
                ChatUtils.message("Number parameters set successfully!");
                FileManager.saveHacks();
                FileManager.saveClickGui();
            }
        }
    }

    @Override
    public String getDescription() {
        return "Set the Number parameter";
    }

    @Override
    public String getSyntax() {
        return "sets <hack> <setting> <value>";
    }
}

