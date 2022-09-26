/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.lwjgl.opengl.Display
 */
package zelix.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import org.lwjgl.opengl.Display;
import zelix.Core;
import zelix.command.Command;
import zelix.gui.cloudconfig.CloudConfig;
import zelix.hack.Hack;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class Config
extends Command {
    public static File configs;
    public static URL configurl;
    private static JsonParser jsonParser;
    private static Gson gsonPretty;

    public Config() {
        super("config");
    }

    @Override
    public void runCommand(String s, String[] args) {
        if (args[0].equals("reload")) {
            Core.fileManager = new FileManager();
        } else if (args[0].equals("save") && args[1] != null) {
            if (!FileManager.CONFIG.exists()) {
                FileManager.CONFIG.mkdir();
            }
            configs = new File(FileManager.CONFIG, args[1] + ".json");
            Config.saveHacks();
            ChatUtils.message("Successfully Save Config");
        } else if (args[0].equals("load") && args[1] != null) {
            configs = new File(FileManager.CONFIG, args[1] + ".json");
            if (configs.exists()) {
                Config.loadHacks();
                ChatUtils.message("Successfully Load Config");
            } else {
                ChatUtils.message("NoFound Config");
            }
        } else if (args[0].equals("upload") && args[1] != null) {
            CloudConfig.UploadConfig(args[1]);
        }
    }

    @Override
    public String getDescription() {
        return "load cloud/local config";
    }

    @Override
    public String getSyntax() {
        return "config";
    }

    public static void saveHacks() {
        try {
            JsonObject json = new JsonObject();
            for (Hack module : HackManager.getHacks()) {
                JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("toggled", Boolean.valueOf(module.isToggled()));
                jsonHack.addProperty("key", (Number)module.getKey());
                if (!module.getValues().isEmpty()) {
                    for (Value value : module.getValues()) {
                        if (value instanceof BooleanValue) {
                            jsonHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        if (value instanceof NumberValue) {
                            jsonHack.addProperty(value.getName(), (Number)value.getValue());
                        }
                        if (!(value instanceof ModeValue)) continue;
                        ModeValue modeValue = (ModeValue)value;
                        for (Mode mode2 : modeValue.getModes()) {
                            jsonHack.addProperty(mode2.getName(), Boolean.valueOf(mode2.isToggled()));
                        }
                    }
                }
                json.add(module.getName(), (JsonElement)jsonHack);
            }
            PrintWriter saveJson = new PrintWriter(new FileWriter(configs));
            saveJson.println(gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadHacks() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configs));
            JsonObject jsonObject = (JsonObject)jsonParser.parse((Reader)bufferedReader);
            bufferedReader.close();
            for (Map.Entry entry : jsonObject.entrySet()) {
                Hack module = HackManager.getHack((String)entry.getKey());
                if (module == null) continue;
                JsonObject jsonObjectHack = (JsonObject)entry.getValue();
                module.setKey(jsonObjectHack.get("key").getAsInt());
                module.setToggled(jsonObjectHack.get("toggled").getAsBoolean());
                if (module.getValues().isEmpty()) continue;
                for (Value value : module.getValues()) {
                    if (jsonObjectHack.get(value.getName()) == null) {
                        if (value instanceof BooleanValue) {
                            jsonObjectHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        if (value instanceof NumberValue) {
                            jsonObjectHack.addProperty(value.getName(), (Number)value.getValue());
                        }
                        if (value instanceof ModeValue) {
                            jsonObjectHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        Config.saveHacks();
                    }
                    if (value instanceof BooleanValue) {
                        value.setValue(jsonObjectHack.get(value.getName()).getAsBoolean());
                    }
                    if (value instanceof NumberValue) {
                        value.setValue(jsonObjectHack.get(value.getName()).getAsDouble());
                    }
                    if (!(value instanceof ModeValue)) continue;
                    ModeValue modeValue = (ModeValue)value;
                    for (Mode mode2 : modeValue.getModes()) {
                        mode2.setToggled(jsonObjectHack.get(mode2.getName()).getAsBoolean());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCloudHacks() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(configurl.openStream()));
            JsonObject jsonObject = (JsonObject)jsonParser.parse((Reader)bufferedReader);
            bufferedReader.close();
            for (Map.Entry entry : jsonObject.entrySet()) {
                Hack module = HackManager.getHack((String)entry.getKey());
                if (module == null) continue;
                JsonObject jsonObjectHack = (JsonObject)entry.getValue();
                module.setKey(jsonObjectHack.get("key").getAsInt());
                module.setToggled(jsonObjectHack.get("toggled").getAsBoolean());
                if (module.getValues().isEmpty()) continue;
                for (Value value : module.getValues()) {
                    if (jsonObjectHack.get(value.getName()) == null) {
                        if (value instanceof BooleanValue) {
                            jsonObjectHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        if (value instanceof NumberValue) {
                            jsonObjectHack.addProperty(value.getName(), (Number)value.getValue());
                        }
                        if (value instanceof ModeValue) {
                            jsonObjectHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        Config.saveHacks();
                    }
                    if (value instanceof BooleanValue) {
                        value.setValue(jsonObjectHack.get(value.getName()).getAsBoolean());
                    }
                    if (value instanceof NumberValue) {
                        value.setValue(jsonObjectHack.get(value.getName()).getAsDouble());
                    }
                    if (!(value instanceof ModeValue)) continue;
                    ModeValue modeValue = (ModeValue)value;
                    for (Mode mode2 : modeValue.getModes()) {
                        mode2.setToggled(jsonObjectHack.get(mode2.getName()).getAsBoolean());
                    }
                }
            }
            ChatUtils.message("Successfully Load Cloud Config");
        }
        catch (Exception e) {
            ChatUtils.error("Failed To Load Cloud Config");
            e.printStackTrace();
        }
    }

    public static void saveCloudHacks() {
        try {
            JsonObject json = new JsonObject();
            for (Hack module : HackManager.getHacks()) {
                JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("toggled", Boolean.valueOf(module.isToggled()));
                jsonHack.addProperty("key", (Number)module.getKey());
                if (!module.getValues().isEmpty()) {
                    for (Value value : module.getValues()) {
                        if (value instanceof BooleanValue) {
                            jsonHack.addProperty(value.getName(), (Boolean)value.getValue());
                        }
                        if (value instanceof NumberValue) {
                            jsonHack.addProperty(value.getName(), (Number)value.getValue());
                        }
                        if (!(value instanceof ModeValue)) continue;
                        ModeValue modeValue = (ModeValue)value;
                        for (Mode mode2 : modeValue.getModes()) {
                            jsonHack.addProperty(mode2.getName(), Boolean.valueOf(mode2.isToggled()));
                        }
                    }
                }
                json.add(module.getName(), (JsonElement)jsonHack);
            }
            String ConfigMain = json.toString();
            Config.Send("localhost", 19731, "[Target][SaveConfig][String][" + ConfigMain + "]");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String Send(String IP, int Port, String Message) {
        try {
            Socket socket = new Socket(IP, Port);
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops);
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(Message);
            bw.flush();
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String s = null;
            s = br.readLine();
            if (s != null) {
                return s;
            }
            socket.close();
        }
        catch (Exception e) {
            Display.setTitle((String)"Failed Connect to The Server(0x66FF)");
            e.printStackTrace();
        }
        return null;
    }

    static {
        jsonParser = new JsonParser();
        gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    }
}

