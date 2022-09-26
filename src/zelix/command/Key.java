/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package zelix.command;

import org.lwjgl.input.Keyboard;
import zelix.command.Command;
import zelix.hack.Hack;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;

public class Key
extends Command {
    public Key() {
        super("key");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            for (Hack hack : HackManager.getHacks()) {
                if (!hack.getName().equalsIgnoreCase(args[1])) continue;
                hack.setKey(Keyboard.getKeyIndex((String)args[0].toUpperCase()));
                ChatUtils.message(hack.getName() + " key changed to \u00a79" + Keyboard.getKeyName((int)hack.getKey()));
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Change key for hack.";
    }

    @Override
    public String getSyntax() {
        return "key <key> <hack>";
    }
}

