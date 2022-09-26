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
import zelix.managers.EnemyManager;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;

public class Enemy
extends Command {
    public Enemy() {
        super("enemy");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                if (args[1].equalsIgnoreCase("all")) {
                    for (Entity object : Utils.getEntityList()) {
                        EntityPlayer player;
                        if (!(object instanceof EntityPlayer) || (player = (EntityPlayer)object).isInvisible()) continue;
                        EnemyManager.addEnemy(Utils.getPlayerName(player));
                    }
                } else {
                    EnemyManager.addEnemy(args[1]);
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                EnemyManager.removeEnemy(args[1]);
            } else if (args[0].equalsIgnoreCase("clear")) {
                EnemyManager.clear();
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Enemy manager.";
    }

    @Override
    public String getSyntax() {
        return "enemy <add/remove/clear> <nick>";
    }
}

