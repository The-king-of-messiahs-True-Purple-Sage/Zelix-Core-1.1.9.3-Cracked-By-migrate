/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 */
package zelix.hack.hacks.xray.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.reference.block.SimpleBlockData;
import zelix.hack.hacks.xray.store.BlockStore;
import zelix.utils.hooks.visual.ChatUtils;

public class JsonStore {
    private static final String FILE = "block_store.json";
    private static final String CONFIG_DIR = XRay.mc.mcDataDir + "/config/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File jsonFile;

    public JsonStore() {
        File configDir = new File(CONFIG_DIR, "xray");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        this.jsonFile = new File(CONFIG_DIR + "xray", FILE);
        if (!this.jsonFile.exists()) {
            ArrayList<SimpleBlockData> simpleBlockData = new ArrayList<SimpleBlockData>(BlockStore.DEFAULT_BLOCKS);
            for (int i = 0; i < simpleBlockData.size(); ++i) {
                ((SimpleBlockData)simpleBlockData.get(i)).setOrder(i);
            }
            this.write(simpleBlockData);
        }
    }

    public void write(HashMap<String, BlockData> blockData) {
        ArrayList<SimpleBlockData> simpleBlockData = new ArrayList<SimpleBlockData>();
        blockData.forEach((k, v) -> simpleBlockData.add(new SimpleBlockData(v.getEntryName(), (String)k, v.getStateId(), v.getColor(), v.isDrawing(), v.getOrder())));
        this.write(simpleBlockData);
    }

    private void write(List<SimpleBlockData> simpleBlockData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.jsonFile));){
            gson.toJson(simpleBlockData, (Appendable)writer);
        }
        catch (IOException e) {
            ChatUtils.error("Failed to write json data to block_store.json");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<SimpleBlockData> read() {
        if (!this.jsonFile.exists()) {
            return new ArrayList<SimpleBlockData>();
        }
        try {
            Type type = new TypeToken<List<SimpleBlockData>>(){}.getType();
            try (BufferedReader reader = new BufferedReader(new FileReader(this.jsonFile));){
                List list = (List)gson.fromJson((Reader)reader, type);
                return list;
            }
        }
        catch (IOException e) {
            ChatUtils.error("Failed to read json data from block_store.json");
            return new ArrayList<SimpleBlockData>();
        }
    }
}

