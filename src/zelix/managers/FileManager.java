/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.item.Item
 */
package zelix.managers;

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
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import zelix.gui.clickguis.kendall.frame.KendallFrame;
import zelix.hack.Hack;
import zelix.hack.hacks.ClickGui;
import zelix.managers.EnemyManager;
import zelix.managers.FriendManager;
import zelix.managers.HackManager;
import zelix.managers.PickupFilterManager;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class FileManager {
    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    private static JsonParser jsonParser = new JsonParser();
    public static File GISHCODE_DIR = null;
    public static File CONFIG = null;
    private static File HACKS = null;
    private static File XRAYDATA = null;
    private static File PICKUPFILTER = null;
    private static File FRIENDS = null;
    private static File ENEMYS = null;
    public static File CLICKGUI = null;
    public static File SKINCHANGER = null;

    public FileManager() {
        GISHCODE_DIR = FileManager.getDirectory();
        if (GISHCODE_DIR == null) {
            return;
        }
        HACKS = new File(GISHCODE_DIR, "hacks.json");
        XRAYDATA = new File(GISHCODE_DIR, "xraydata.json");
        CONFIG = new File(GISHCODE_DIR, "configs");
        PICKUPFILTER = new File(GISHCODE_DIR, "pickupfilter.json");
        SKINCHANGER = new File(GISHCODE_DIR, "cachedtextures");
        CLICKGUI = new File(GISHCODE_DIR, "clickgui.json");
        FRIENDS = new File(GISHCODE_DIR, "friends.json");
        ENEMYS = new File(GISHCODE_DIR, "enemys.json");
        if (!GISHCODE_DIR.exists()) {
            GISHCODE_DIR.mkdir();
        }
        if (!HACKS.exists()) {
            FileManager.saveHacks();
        } else {
            FileManager.loadHacks();
        }
        if (!PICKUPFILTER.exists()) {
            FileManager.savePickupFilter();
        } else {
            FileManager.loadPickupFilter();
        }
        if (!FRIENDS.exists()) {
            FileManager.saveFriends();
        } else {
            FileManager.loadFriends();
        }
        if (!ENEMYS.exists()) {
            FileManager.saveEnemys();
        } else {
            FileManager.loadEnemys();
        }
        if (!SKINCHANGER.exists()) {
            SKINCHANGER.mkdir();
        }
    }

    public static File getDirectory() {
        String var = System.getenv("GISHCODE_DIR");
        File dir = var == null || var == "" ? Wrapper.INSTANCE.mc().mcDataDir : new File(var);
        return new File(String.format("%s%s%s-%s%s", dir, File.separator, "Zelix", "1.12.2", File.separator));
    }

    public static void loadHacks() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HACKS));
            JsonObject jsonObject = (JsonObject)jsonParser.parse((Reader)bufferedReader);
            bufferedReader.close();
            for (Map.Entry entry : jsonObject.entrySet()) {
                Hack hack = HackManager.getHack((String)entry.getKey());
                if (hack == null) continue;
                JsonObject jsonObjectHack = (JsonObject)entry.getValue();
                hack.setKey(jsonObjectHack.get("key").getAsInt());
                hack.setToggled(jsonObjectHack.get("toggled").getAsBoolean());
                if (hack.getValues().isEmpty()) continue;
                for (Value value : hack.getValues()) {
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
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void loadClickGui_Kendall() {
        try {
            BufferedReader loadJson = new BufferedReader(new FileReader(CLICKGUI));
            JsonObject json = (JsonObject)jsonParser.parse((Reader)loadJson);
            loadJson.close();
            for (Map.Entry entry : json.entrySet()) {
                JsonObject jsonData = (JsonObject)entry.getValue();
                String text = (String)entry.getKey();
                int posX = jsonData.get("posX").getAsInt();
                int posY = jsonData.get("posY").getAsInt();
                for (KendallFrame frame : ClickGui.KendallMyGod.frames) {
                    if (!frame.category.name().equals(text)) continue;
                    frame.x = posX;
                    frame.y = posY;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveClickGui_Kendall() {
        try {
            JsonObject json = new JsonObject();
            for (KendallFrame frame : ClickGui.KendallMyGod.frames) {
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("posX", (Number)Float.valueOf(frame.x));
                jsonData.addProperty("posY", (Number)Float.valueOf(frame.y));
                json.add(frame.category.name(), (JsonElement)jsonData);
            }
            PrintWriter saveJson = new PrintWriter(new FileWriter(CLICKGUI));
            saveJson.println(gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFriends() {
        List<String> friends = FileManager.read(FRIENDS);
        for (String name : friends) {
            FriendManager.addFriend(name);
        }
    }

    public static void loadEnemys() {
        List<String> enemys = FileManager.read(ENEMYS);
        for (String name : enemys) {
            EnemyManager.addEnemy(name);
        }
    }

    public static void loadPickupFilter() {
        try {
            BufferedReader loadJson = new BufferedReader(new FileReader(PICKUPFILTER));
            JsonObject json = (JsonObject)jsonParser.parse((Reader)loadJson);
            loadJson.close();
            for (Map.Entry entry : json.entrySet()) {
                JsonObject jsonData = (JsonObject)entry.getValue();
                int id = Integer.parseInt((String)entry.getKey());
                PickupFilterManager.addItem(id);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void savePickupFilter() {
        try {
            JsonObject json = new JsonObject();
            Iterator iterator = PickupFilterManager.items.iterator();
            while (iterator.hasNext()) {
                int id = (Integer)iterator.next();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("name", Item.getItemById((int)id).getUnlocalizedName());
                json.add("" + id, (JsonElement)jsonData);
            }
            PrintWriter saveJson = new PrintWriter(new FileWriter(PICKUPFILTER));
            saveJson.println(gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadClickGui() {
    }

    public static void saveClickGui() {
    }

    public static void saveFriends() {
        FileManager.write(FRIENDS, FriendManager.friendsList, true, true);
    }

    public static void saveEnemys() {
        FileManager.write(ENEMYS, EnemyManager.enemysList, true, true);
    }

    public static void saveHacks() {
        try {
            JsonObject json = new JsonObject();
            for (Hack hack : HackManager.getHacks()) {
                JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("toggled", Boolean.valueOf(hack.isToggled()));
                jsonHack.addProperty("key", (Number)hack.getKey());
                if (!hack.getValues().isEmpty()) {
                    for (Value value : hack.getValues()) {
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
                json.add(hack.getName(), (JsonElement)jsonHack);
            }
            PrintWriter saveJson = new PrintWriter(new FileWriter(HACKS));
            saveJson.println(gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(File outputFile, List<String> writeContent, boolean newline, boolean overrideContent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
            for (String outputLine : writeContent) {
                writer.write(outputLine);
                writer.flush();
                if (!newline) continue;
                writer.newLine();
            }
        }
        catch (Exception ex) {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static List<String> read(File inputFile) {
        ArrayList<String> readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            String line;
            reader = new BufferedReader(new FileReader(inputFile));
            while ((line = reader.readLine()) != null) {
                readContent.add(line);
            }
        }
        catch (Exception ex) {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return readContent;
    }
}

