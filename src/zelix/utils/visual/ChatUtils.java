/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package zelix.utils.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import zelix.utils.system.EnumChatFormatting;

public class ChatUtils {
    public static void component(ITextComponent component2) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new TextComponentString((Object)((Object)EnumChatFormatting.RED) + "[" + (Object)((Object)EnumChatFormatting.GOLD) + "Zelix" + (Object)((Object)EnumChatFormatting.RED) + "]" + (Object)((Object)EnumChatFormatting.WHITE) + " ").func_150257_a(component2));
    }

    public static void componentIRC(ITextComponent component2) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new TextComponentString("").func_150257_a(component2));
    }

    public static void messageIRC(String message) {
        ChatUtils.componentIRC((ITextComponent)new TextComponentString(message));
    }

    public static void IRC(String message) {
        ChatUtils.messageIRC((Object)((Object)EnumChatFormatting.RED) + "[" + (Object)((Object)EnumChatFormatting.GOLD) + "IRC" + (Object)((Object)EnumChatFormatting.RED) + "]" + (Object)((Object)EnumChatFormatting.WHITE) + " " + message);
    }

    public static void message(String message) {
        ChatUtils.component((ITextComponent)new TextComponentString(message));
    }

    public static void warning(String message) {
        ChatUtils.message((Object)((Object)EnumChatFormatting.RED) + "[" + (Object)((Object)EnumChatFormatting.GOLD) + (Object)((Object)EnumChatFormatting.BOLD) + "WARNING" + (Object)((Object)EnumChatFormatting.RED) + "]" + (Object)((Object)EnumChatFormatting.WHITE) + " " + message);
    }

    public static void error(String message) {
        ChatUtils.message((Object)((Object)EnumChatFormatting.RED) + "[" + (Object)((Object)EnumChatFormatting.DARK_RED) + "" + (Object)((Object)EnumChatFormatting.BOLD) + "ERROR" + (Object)((Object)EnumChatFormatting.RED) + "]" + (Object)((Object)EnumChatFormatting.WHITE) + " " + message);
    }

    public static void success(String message) {
        ChatUtils.message((Object)((Object)EnumChatFormatting.GREEN) + "[" + (Object)((Object)EnumChatFormatting.DARK_GREEN) + (Object)((Object)EnumChatFormatting.BOLD) + "SUCCESS" + (Object)((Object)EnumChatFormatting.GREEN) + "]" + (Object)((Object)EnumChatFormatting.WHITE) + " " + message);
    }

    public static void failure(String message) {
        ChatUtils.message("" + (Object)((Object)EnumChatFormatting.RED) + "[" + (Object)((Object)EnumChatFormatting.DARK_RED) + "" + (Object)((Object)EnumChatFormatting.BOLD) + "FAILURE" + (Object)((Object)EnumChatFormatting.RED) + "]" + (Object)((Object)EnumChatFormatting.WHITE) + " " + message);
    }
}

