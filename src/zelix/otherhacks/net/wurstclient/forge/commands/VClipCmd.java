/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package zelix.otherhacks.net.wurstclient.forge.commands;

import net.minecraft.client.entity.EntityPlayerSP;
import zelix.otherhacks.net.wurstclient.forge.Command;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.utils.MathUtils;

public final class VClipCmd
extends Command {
    public VClipCmd() {
        super("vclip", "Lets you clip through blocks vertically.", "Syntax: .vclip <height>");
    }

    @Override
    public void call(String[] args) throws Command.CmdException {
        if (args.length != 1) {
            throw new Command.CmdSyntaxError();
        }
        if (!MathUtils.isInteger(args[0])) {
            throw new Command.CmdSyntaxError();
        }
        EntityPlayerSP player = WMinecraft.getPlayer();
        player.setPosition(player.posX, player.posY + (double)Integer.parseInt(args[0]), player.posZ);
    }
}

