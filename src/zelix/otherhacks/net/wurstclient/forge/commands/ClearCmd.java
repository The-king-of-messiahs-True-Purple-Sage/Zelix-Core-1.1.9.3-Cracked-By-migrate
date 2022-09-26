/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import net.minecraft.client.Minecraft;
import zelix.otherhacks.net.wurstclient.forge.Command;

public final class ClearCmd
extends Command {
    public ClearCmd() {
        super("clear", "Clears the chat completely.", "Syntax: .clear");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length > 0) {
            throw new Command.CmdSyntaxError();
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146231_a(true);
    }
}

