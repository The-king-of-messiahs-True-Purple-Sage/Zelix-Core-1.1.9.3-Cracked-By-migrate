/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.CommandManager;
import zelix.utils.system.Connection;

public class CommandGetter
extends Hack {
    public CommandGetter() {
        super("CommandGetter", HackCategory.ANOTHER);
        this.setToggled(true);
        this.setShow(false);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        boolean send = true;
        if (side == Connection.Side.OUT && packet instanceof CPacketChatMessage) {
            Field field = ReflectionHelper.findField(CPacketChatMessage.class, (String[])new String[]{"message", "field_149440_a"});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (packet instanceof CPacketChatMessage) {
                    CPacketChatMessage p = (CPacketChatMessage)packet;
                    if (p.func_149439_c().subSequence(0, 1).equals(".")) {
                        send = false;
                        CommandManager.getInstance().runCommands(p.func_149439_c());
                        return send;
                    }
                    send = true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return send;
    }
}

