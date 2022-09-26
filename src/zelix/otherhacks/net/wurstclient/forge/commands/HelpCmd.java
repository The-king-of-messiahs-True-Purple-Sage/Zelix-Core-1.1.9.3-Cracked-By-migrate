/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.utils.ChatUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.MathUtils;

public final class HelpCmd
extends Command {
    public HelpCmd() {
        super("help", "Shows help.", "Syntax: .help <command>", "List commands: .help [<page>]");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length > 1) {
            throw new Command.CmdSyntaxError();
        }
        String arg = args.length < 1 ? "1" : args[0];
        if (MathUtils.isInteger(arg)) {
            this.list(Integer.parseInt(arg));
        } else {
            this.help(arg);
        }
    }

    private void list(int page) throws Command.CmdException {
        List<Command> cmds = Arrays.asList(wurst.getCmds().getValues().toArray(new Command[0]));
        cmds.sort(Comparator.comparing(Command::getName));
        int size = cmds.size();
        int pages = Math.max((int)Math.ceil((double)size / 8.0), 1);
        if (page > pages || page < 1) {
            throw new Command.CmdSyntaxError("Invalid page: " + page);
        }
        ChatUtils.message("Total: " + size + (size == 1 ? " command" : " commands"));
        ChatUtils.message("Command list (page " + page + "/" + pages + ")");
        for (int i = (page - 1) * 8; i < Math.min(page * 8, size); ++i) {
            Command c = cmds.get(i);
            ChatUtils.message("." + c.getName() + " - " + c.getDescription());
        }
    }

    private void help(String cmdName) throws Command.CmdException {
        Command cmd;
        if (cmdName.startsWith(".")) {
            cmdName = cmdName.substring(1);
        }
        if ((cmd = wurst.getCmds().get(cmdName)) == null) {
            throw new Command.CmdSyntaxError("Unknown command: ." + cmdName);
        }
        ChatUtils.message("." + cmd.getName() + " - " + cmd.getDescription());
        for (String line : cmd.getSyntax()) {
            ChatUtils.message(line);
        }
    }
}

