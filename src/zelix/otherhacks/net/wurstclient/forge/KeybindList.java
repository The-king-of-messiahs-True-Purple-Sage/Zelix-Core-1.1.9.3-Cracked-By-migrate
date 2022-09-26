/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.lwjgl.input.Keyboard
 */
package zelix.otherhacks.net.wurstclient.forge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.lwjgl.input.Keyboard;
import zelix.otherhacks.net.wurstclient.forge.utils.JsonUtils;

public final class KeybindList {
    private final Path path;
    private final ArrayList<Keybind> keybinds = new ArrayList();

    public KeybindList(Path file) {
        this.path = file;
    }

    public void init() {
        JsonObject json;
        try {
            BufferedReader reader = Files.newBufferedReader(this.path);
            Iterator iterator = null;
            try {
                json = JsonUtils.jsonParser.parse((Reader)reader).getAsJsonObject();
            }
            catch (Throwable throwable) {
                iterator = throwable;
                throw throwable;
            }
            finally {
                if (reader != null) {
                    if (iterator != null) {
                        try {
                            reader.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)((Object)iterator)).addSuppressed(throwable);
                        }
                    } else {
                        reader.close();
                    }
                }
            }
        }
        catch (NoSuchFileException e) {
            this.loadDefaults();
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.loadDefaults();
            return;
        }
        this.keybinds.clear();
        TreeMap<String, String> keybinds2 = new TreeMap<String, String>();
        for (Map.Entry entry : json.entrySet()) {
            String key = ((String)entry.getKey()).toUpperCase();
            if (Keyboard.getKeyIndex((String)key) == 0 || !((JsonElement)entry.getValue()).isJsonPrimitive() || !((JsonElement)entry.getValue()).getAsJsonPrimitive().isString()) continue;
            String commands = ((JsonElement)entry.getValue()).getAsString();
            keybinds2.put(key, commands);
        }
        for (Map.Entry entry : keybinds2.entrySet()) {
            this.keybinds.add(new Keybind((String)entry.getKey(), (String)entry.getValue()));
        }
        this.save();
    }

    public void loadDefaults() {
        this.keybinds.clear();
        this.keybinds.add(new Keybind("B", "fastplace;fastbreak"));
        this.keybinds.add(new Keybind("C", "fullbright"));
        this.keybinds.add(new Keybind("G", "flight"));
        this.keybinds.add(new Keybind("LCONTROL", "clickgui"));
        this.keybinds.add(new Keybind("N", "nuker"));
        this.keybinds.add(new Keybind("R", "killaura"));
        this.keybinds.add(new Keybind("RSHIFT", "clickgui"));
        this.save();
    }

    private void save() {
        JsonObject json = new JsonObject();
        for (Keybind keybind : this.keybinds) {
            json.addProperty(keybind.getKey(), keybind.getCommands());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(this.path, new OpenOption[0]);){
            JsonUtils.prettyGson.toJson((JsonElement)json, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return this.keybinds.size();
    }

    public Keybind get(int index) {
        return this.keybinds.get(index);
    }

    public String getCommands(String key) {
        for (Keybind keybind : this.keybinds) {
            if (!key.equals(keybind.getKey())) continue;
            return keybind.getCommands();
        }
        return null;
    }

    public void add(String key, String commands) {
        this.keybinds.removeIf(keybind -> key.equals(keybind.getKey()));
        this.keybinds.add(new Keybind(key, commands));
        this.keybinds.sort(Comparator.comparing(Keybind::getKey));
        this.save();
    }

    public void remove(String key) {
        this.keybinds.removeIf(keybind -> key.equals(keybind.getKey()));
        this.save();
    }

    public void removeAll() {
        this.keybinds.clear();
        this.save();
    }

    public static class Keybind {
        private final String key;
        private final String commands;

        public Keybind(String key, String commands) {
            this.key = key;
            this.commands = commands;
        }

        public String getKey() {
            return this.key;
        }

        public String getCommands() {
            return this.commands;
        }
    }
}

