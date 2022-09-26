/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.command;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import zelix.command.Command;
import zelix.utils.hooks.visual.ChatUtils;

public class SetName
extends Command {
    public SetName() {
        super("setname");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            String message = "";
            for (int i = 0; i < args.length; ++i) {
                String str;
                message = str = args[i];
            }
            Class<?> var51 = Minecraft.getMinecraft().getSession().getClass();
            Field f = var51.getDeclaredFields()[0];
            f.setAccessible(true);
            f.set(Minecraft.getMinecraft().getSession(), message);
            ChatUtils.message("Successfully set the name");
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Reset your name";
    }

    @Override
    public String getSyntax() {
        return "setname <name>";
    }
}

