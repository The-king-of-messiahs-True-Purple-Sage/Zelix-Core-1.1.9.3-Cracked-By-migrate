/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 */
package zelix.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import zelix.command.Command;
import zelix.managers.FriendManager;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;

public class Friend
extends Command {
    public Friend() {
        super("friend");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                if (args[1].equalsIgnoreCase("all")) {
                    for (Entity object : Utils.getEntityList()) {
                        EntityPlayer player;
                        if (!(object instanceof EntityPlayer) || (player = (EntityPlayer)object).isInvisible()) continue;
                        FriendManager.addFriend(Utils.getPlayerName(player));
                    }
                } else {
                    FriendManager.addFriend(args[1]);
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                FriendManager.removeFriend(args[1]);
            } else if (args[0].equalsIgnoreCase("clear")) {
                FriendManager.clear();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Friend manager.";
    }

    @Override
    public String getSyntax() {
        return "friend <add/remove/clear> <nick>";
    }
}

