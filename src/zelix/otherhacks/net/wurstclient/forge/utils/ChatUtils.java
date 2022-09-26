/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public final class ChatUtils {
    private static boolean enabled = true;

    public static void setEnabled(boolean enabled) {
        ChatUtils.enabled = enabled;
    }

    public static void component(ITextComponent component2) {
        if (enabled) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new TextComponentString("\u00a7c[\u00a76Zelix\u00a7c]\u00a7r ").func_150257_a(component2));
        }
    }

    public static void message(String message) {
        ChatUtils.component((ITextComponent)new TextComponentString(message));
    }

    public static void warning(String message) {
        ChatUtils.message("\u00a7c[\u00a76\u00a7lWARNING\u00a7c]\u00a7r " + message);
    }

    public static void error(String message) {
        ChatUtils.message("\u00a7c[\u00a74\u00a7lERROR\u00a7c]\u00a7r " + message);
    }
}

