/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetworkPlayerInfo
 */
package zelix.command;

import java.util.ArrayList;
import net.minecraft.client.network.NetworkPlayerInfo;
import zelix.command.Command;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;

public class DumpPlayers
extends Command {
    public DumpPlayers() {
        super("dumpplayers");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            ArrayList<String> list = new ArrayList<String>();
            if (args[0].equalsIgnoreCase("all")) {
                for (NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getNetHandler().getPlayerInfoMap()) {
                    list.add("\n" + npi.getGameProfile().getName());
                }
            } else if (args[0].equalsIgnoreCase("creatives")) {
                for (NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getNetHandler().getPlayerInfoMap()) {
                    if (!npi.func_178848_b().func_77145_d()) continue;
                    list.add("\n" + npi.getGameProfile().getName());
                }
            }
            if (list.isEmpty()) {
                ChatUtils.error("List is empty.");
            } else {
                Utils.copy(list.toString());
                ChatUtils.message("List copied to clipboard.");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Get list of players.";
    }

    @Override
    public String getSyntax() {
        return "dumpplayers <all/creatives>";
    }
}

