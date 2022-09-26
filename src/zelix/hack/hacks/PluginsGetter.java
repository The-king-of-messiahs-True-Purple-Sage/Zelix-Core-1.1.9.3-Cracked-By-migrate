/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  joptsimple.internal.Strings
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketTabComplete
 *  net.minecraft.network.play.server.SPacketTabComplete
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.Collections;
import joptsimple.internal.Strings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.server.SPacketTabComplete;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Connection;

public class PluginsGetter
extends Hack {
    public PluginsGetter() {
        super("PluginsGetter", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Show all plugins on current server.";
    }

    @Override
    public void onEnable() {
        if (Wrapper.INSTANCE.player() == null) {
            return;
        }
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketTabComplete("/", null, false));
        super.onEnable();
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (packet instanceof SPacketTabComplete) {
            SPacketTabComplete s3APacketTabComplete = (SPacketTabComplete)packet;
            ArrayList<String> plugins = new ArrayList<String>();
            String[] commands = s3APacketTabComplete.func_149630_c();
            for (int i = 0; i < commands.length; ++i) {
                String pluginName;
                String[] command = commands[i].split(":");
                if (command.length <= 1 || plugins.contains(pluginName = command[0].replace("/", ""))) continue;
                plugins.add(pluginName);
            }
            Collections.sort(plugins);
            if (!plugins.isEmpty()) {
                ChatUtils.message("Plugins \u00a77(\u00a78" + plugins.size() + "\u00a77): \u00a79" + Strings.join((String[])plugins.toArray(new String[0]), (String)"\u00a77, \u00a79"));
            } else {
                ChatUtils.error("No plugins found.");
            }
            this.setToggled(false);
        }
        return true;
    }
}

