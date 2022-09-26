/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.potion.Potion
 */
package zelix.command;

import net.minecraft.potion.Potion;
import zelix.command.Command;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;

public class Effect
extends Command {
    public Effect() {
        super("effect");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                int id = Integer.parseInt(args[1]);
                int duration = Integer.parseInt(args[2]);
                int amplifier = Integer.parseInt(args[3]);
                if (Potion.func_188412_a((int)id) == null) {
                    ChatUtils.error("Potion is null");
                    return;
                }
                Utils.addEffect(id, duration, amplifier);
            } else if (args[0].equalsIgnoreCase("remove")) {
                int id = Integer.parseInt(args[1]);
                if (Potion.func_188412_a((int)id) == null) {
                    ChatUtils.error("Potion is null");
                    return;
                }
                Utils.removeEffect(id);
            } else if (args[0].equalsIgnoreCase("clear")) {
                Utils.clearEffects();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Effect manager.";
    }

    @Override
    public String getSyntax() {
        return "effect <add/remove/clear> <id> <duration> <amplifier>";
    }
}

