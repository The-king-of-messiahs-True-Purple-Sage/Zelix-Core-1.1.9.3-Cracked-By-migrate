/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.Core;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.external.EXTTheard;
import zelix.managers.CommandManager;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Connection;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class External
extends Hack {
    public static boolean isdev = false;
    public static OutputStream out;
    public BufferedReader reader;
    public static Socket socket;
    public static PrintWriter pw;
    static InputStream in;
    private static boolean connect;
    private static String prefix;
    public static List<String> banned;
    public static String FGF;
    private static int sec;

    public External() {
        super("External", HackCategory.ANOTHER);
    }

    @Override
    public void onEnable() {
        if (Utils.nullCheck()) {
            return;
        }
        new EXTTheard().start();
        super.onEnable();
    }

    public static void send(String TEXT) {
        try {
            out.write(TEXT.getBytes("UTF-8"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (++sec > 10000) {
            this.onDisable();
            this.onEnable();
            sec = 0;
        }
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
                        out.write(("[TARGET][MESSAGE][MSG][" + p.func_149439_c().substring(1, p.func_149439_c().length()) + "]").getBytes(StandardCharsets.UTF_8));
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

    @Override
    public void onDisable() {
        try {
            connect = false;
            if (in == null || out == null || socket == null) {
                return;
            }
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        super.onDisable();
    }

    public static void connect() {
        try {
            socket = new Socket("127.0.0.1", 38912);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            pw = new PrintWriter(socket.getOutputStream(), true);
            out.write(External.getHackList().getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            ChatUtils.error("Failed to Connect!");
            HackManager.getHack("External").setToggled(false);
            HackManager.getHack("External").onDisable();
            e.printStackTrace();
        }
    }

    public static String getHackList() {
        String Info = "";
        for (Hack m : Core.hackManager.getHacks()) {
            Info = Info + "=" + m.getName() + "(" + m.getCategory().name() + ")[";
            for (Value v : m.getValues()) {
                if (v instanceof NumberValue) {
                    Info = Info + "{NumberValue:" + v.getName() + "|" + ((NumberValue)v).getMax().toString() + "|" + ((NumberValue)v).getMin().toString() + "|" + ((Double)((NumberValue)v).getDefaultValue()).toString() + "}";
                }
                if (v instanceof ModeValue) {
                    Info = Info + "{ModeValue:" + v.getName();
                    for (Mode z : ((ModeValue)v).getModes()) {
                        Info = Info + "|" + z.getName();
                    }
                    Info = Info + "}";
                }
                if (!(v instanceof BooleanValue)) continue;
                Info = Info + "{BooleanValue:" + v.getName() + "}";
            }
            Info = Info + "]";
        }
        return Info.substring(1);
    }

    public static String getNowSetting() {
        String Info = "";
        for (Hack m : Core.hackManager.getHacks()) {
            Info = Info + "=" + m.getName() + "(" + (m.isToggled() ? "true" : "false") + ")[";
            for (Value v : m.getValues()) {
                if (v instanceof NumberValue) {
                    Info = Info + "{NumberValue:" + v.getName() + "|" + v.getValue().toString() + "}";
                }
                if (v instanceof ModeValue && ((ModeValue)v).getSelectMode() != null) {
                    Info = Info + "{ModeValue:" + v.getName() + "|" + ((ModeValue)v).getSelectMode().getName() + "}";
                }
                if (!(v instanceof BooleanValue)) continue;
                if (((Boolean)((BooleanValue)v).getValue()).booleanValue()) {
                    Info = Info + "{BooleanValue:" + v.getName() + "|true}";
                    continue;
                }
                Info = Info + "{BooleanValue:" + v.getName() + "|false}";
            }
            Info = Info + "]";
        }
        return Info.substring(1);
    }

    public static void handleInput() {
        byte[] data = new byte[1024];
        try {
            int len = in.read(data);
            String ircmessage = new String(data, 0, len);
            ircmessage = ircmessage.replaceAll("\n", "");
            ircmessage = ircmessage.replaceAll("\r", "");
            ircmessage = ircmessage.replaceAll("\t", "");
            if (!connect) {
                if (ircmessage.equals("OK CONNECTION")) {
                    connect = true;
                    out.write(External.getNowSetting().getBytes(StandardCharsets.UTF_8));
                }
            } else {
                CommandManager.getInstance().runCommands("." + ircmessage);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static void write(String str) {
        try {
            out.write(str.getBytes("UTF-8"));
        }
        catch (IOException e) {
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

