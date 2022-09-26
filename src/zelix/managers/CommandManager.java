/*
 * Decompiled with CFR 0.152.
 */
package zelix.managers;

import java.util.ArrayList;
import zelix.command.Cape;
import zelix.command.Command;
import zelix.command.Config;
import zelix.command.DumpClasses;
import zelix.command.DumpPlayers;
import zelix.command.Effect;
import zelix.command.Enemy;
import zelix.command.Esu;
import zelix.command.Friend;
import zelix.command.Hacks;
import zelix.command.Help;
import zelix.command.Key;
import zelix.command.LoadHack;
import zelix.command.Login;
import zelix.command.OpenDir;
import zelix.command.PFilter;
import zelix.command.Say;
import zelix.command.SetCheckbox;
import zelix.command.SetMode;
import zelix.command.SetName;
import zelix.command.SetSlider;
import zelix.command.SkinSteal;
import zelix.command.TP;
import zelix.command.Toggle;
import zelix.command.VClip;
import zelix.command.irc.IRC;
import zelix.utils.hooks.visual.ChatUtils;

public class CommandManager {
    public static ArrayList<Command> commands = new ArrayList();
    private static volatile CommandManager instance;
    public static char cmdPrefix;

    public CommandManager() {
        this.addCommands();
    }

    public void addCommands() {
        commands.add(new Help());
        commands.add(new Hacks());
        commands.add(new Key());
        commands.add(new VClip());
        commands.add(new Login());
        commands.add(new Say());
        commands.add(new Effect());
        commands.add(new DumpPlayers());
        commands.add(new DumpClasses());
        commands.add(new SkinSteal());
        commands.add(new Friend());
        commands.add(new Enemy());
        commands.add(new Toggle());
        commands.add(new PFilter());
        commands.add(new OpenDir());
        commands.add(new SetName());
        commands.add(new Esu());
        commands.add(new SetCheckbox());
        commands.add(new SetSlider());
        commands.add(new SetMode());
        commands.add(new IRC());
        commands.add(new Config());
        commands.add(new TP());
        commands.add(new LoadHack());
        commands.add(new Cape());
    }

    public void runCommands(String s) {
        String readString = s.trim().substring(Character.toString(cmdPrefix).length()).trim();
        boolean commandResolved = false;
        boolean hasArgs = readString.trim().contains(" ");
        String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
        String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[]{};
        for (Command command : commands) {
            if (!command.getCommand().trim().equalsIgnoreCase(commandName.trim())) continue;
            command.runCommand(readString, args);
            commandResolved = true;
            break;
        }
        if (!commandResolved) {
            ChatUtils.error("Cannot resolve internal command: \u00a7c" + commandName);
        }
    }

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    static {
        cmdPrefix = (char)46;
    }
}

