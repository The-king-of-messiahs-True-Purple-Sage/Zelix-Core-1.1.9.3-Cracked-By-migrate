/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import java.util.Arrays;
import org.lwjgl.input.Keyboard;
import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.KeybindList;
import zelix.otherhacks.net.wurstclient.forge.utils.ChatUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.MathUtils;

public final class BindsCmd
extends Command {
    public BindsCmd() {
        super("binds", "Manages keybinds.", "Syntax: .binds add <key> <hacks>", ".binds add <key> <commands>", ".binds remove <key>", ".binds list [<page>]", ".binds remove-all", ".binds reset", "Multiple hacks/commands must be separated by ';'.");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length < 1) {
            throw new Command.CmdSyntaxError();
        }
        switch (args[0].toLowerCase()) {
            case "add": {
                this.add(args);
                break;
            }
            case "remove": {
                this.remove(args);
                break;
            }
            case "list": {
                this.list(args);
                break;
            }
            case "remove-all": {
                wurst.getKeybinds().removeAll();
                ChatUtils.message("All keybinds removed.");
                break;
            }
            case "reset": {
                wurst.getKeybinds().loadDefaults();
                ChatUtils.message("All keybinds reset to defaults.");
                break;
            }
            default: {
                throw new Command.CmdSyntaxError();
            }
        }
    }

    private void add(String[] args) throws Command.CmdException {
        if (args.length < 3) {
            throw new Command.CmdSyntaxError();
        }
        String key = args[1].toUpperCase();
        if (Keyboard.getKeyIndex((String)key) == 0) {
            throw new Command.CmdSyntaxError("Unknown key: " + key);
        }
        String commands = String.join((CharSequence)" ", Arrays.copyOfRange(args, 2, args.length));
        wurst.getKeybinds().add(key, commands);
        ChatUtils.message("Keybind set: " + key + " -> " + commands);
    }

    private void remove(String[] args) throws Command.CmdException {
        if (args.length != 2) {
            throw new Command.CmdSyntaxError();
        }
        String key = args[1].toUpperCase();
        if (Keyboard.getKeyIndex((String)key) == 0) {
            throw new Command.CmdSyntaxError("Unknown key: " + key);
        }
        String oldCommands = wurst.getKeybinds().getCommands(key);
        if (oldCommands == null) {
            throw new Command.CmdError("Nothing to remove.");
        }
        wurst.getKeybinds().remove(key);
        ChatUtils.message("Keybind removed: " + key + " -> " + oldCommands);
    }

    private void list(String[] args) throws Command.CmdException {
        int page;
        if (args.length > 2) {
            throw new Command.CmdSyntaxError();
        }
        if (args.length < 2) {
            page = 1;
        } else if (MathUtils.isInteger(args[1])) {
            page = Integer.parseInt(args[1]);
        } else {
            throw new Command.CmdSyntaxError("Not a number: " + args[1]);
        }
        int keybinds = wurst.getKeybinds().size();
        int pages = Math.max((int)Math.ceil((double)keybinds / 8.0), 1);
        if (page > pages || page < 1) {
            throw new Command.CmdSyntaxError("Invalid page: " + page);
        }
        ChatUtils.message("Total: " + keybinds + (keybinds == 1 ? " keybind" : " keybinds"));
        ChatUtils.message("Keybind list (page " + page + "/" + pages + ")");
        for (int i = (page - 1) * 8; i < Math.min(page * 8, keybinds); ++i) {
            KeybindList.Keybind k = wurst.getKeybinds().get(i);
            ChatUtils.message(k.getKey() + " -> " + k.getCommands());
        }
    }
}

