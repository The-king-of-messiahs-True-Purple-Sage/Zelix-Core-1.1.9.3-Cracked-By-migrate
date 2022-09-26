/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  org.lwjgl.input.Keyboard
 */
package zelix.otherhacks.net.wurstclient.forge;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import zelix.otherhacks.net.wurstclient.forge.CommandProcessor;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.HackList;
import zelix.otherhacks.net.wurstclient.forge.KeybindList;

public final class KeybindProcessor {
    private final HackList hax;
    private final KeybindList keybinds;
    private final CommandProcessor cmdProcessor;

    public KeybindProcessor(HackList hax, KeybindList keybinds, CommandProcessor cmdProcessor) {
        this.hax = hax;
        this.keybinds = keybinds;
        this.cmdProcessor = cmdProcessor;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        int keyCode = Keyboard.getEventKey();
        if (keyCode == 0 || !Keyboard.getEventKeyState()) {
            return;
        }
        String commands = this.keybinds.getCommands(Keyboard.getKeyName((int)keyCode));
        if (commands == null) {
            return;
        }
        commands = commands.replace(";", "\u00a7").replace("\u00a7\u00a7", ";");
        for (String command : commands.split("\u00a7")) {
            if ((command = command.trim()).startsWith(".")) {
                this.cmdProcessor.runCommand(command.substring(1));
                continue;
            }
            if (command.contains(" ")) {
                this.cmdProcessor.runCommand(command);
                continue;
            }
            Hack hack = this.hax.get(command);
            if (hack != null) {
                hack.setEnabled(!hack.isEnabled());
                continue;
            }
            this.cmdProcessor.runCommand(command);
        }
    }
}

