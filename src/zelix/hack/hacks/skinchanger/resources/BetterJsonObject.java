/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonIOException
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSyntaxException
 */
package zelix.hack.hacks.skinchanger.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BetterJsonObject {
    private static final Gson prettyPrinter = new GsonBuilder().setPrettyPrinting().create();
    private JsonObject data;

    public BetterJsonObject() {
        this.data = new JsonObject();
    }

    public BetterJsonObject(String jsonIn) {
        if (jsonIn == null || jsonIn.isEmpty()) {
            this.data = new JsonObject();
            return;
        }
        try {
            this.data = new JsonParser().parse(jsonIn).getAsJsonObject();
        }
        catch (JsonIOException | JsonSyntaxException ex) {
            ex.printStackTrace();
        }
    }

    public BetterJsonObject(JsonObject objectIn) {
        this.data = objectIn != null ? objectIn : new JsonObject();
    }

    public String optString(String key) {
        return this.optString(key, "");
    }

    public String optString(String key, String value) {
        if (key == null || key.isEmpty() || !this.has(key)) {
            return value;
        }
        JsonPrimitive primitive = this.asPrimitive(this.get(key));
        if (primitive != null && primitive.isString()) {
            return primitive.getAsString();
        }
        return value;
    }

    public int optInt(String key) {
        return this.optInt(key, 0);
    }

    public int optInt(String key, int value) {
        if (key == null || key.isEmpty() || !this.has(key)) {
            return value;
        }
        JsonPrimitive primitive = this.asPrimitive(this.get(key));
        try {
            if (primitive != null && primitive.isNumber()) {
                return primitive.getAsInt();
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return value;
    }

    public double optDouble(String key) {
        return this.optDouble(key, 0.0);
    }

    public double optDouble(String key, double value) {
        if (key == null || key.isEmpty() || !this.has(key)) {
            return value;
        }
        JsonPrimitive primitive = this.asPrimitive(this.get(key));
        try {
            if (primitive != null && primitive.isNumber()) {
                return primitive.getAsDouble();
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return value;
    }

    public boolean optBoolean(String key) {
        return this.optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean value) {
        if (key == null || key.isEmpty() || !this.has(key)) {
            return value;
        }
        JsonPrimitive primitive = this.asPrimitive(this.get(key));
        if (primitive != null && primitive.isBoolean()) {
            return primitive.getAsBoolean();
        }
        return value;
    }

    public boolean has(String key) {
        return this.data.has(key);
    }

    public JsonElement get(String key) {
        return this.data.get(key);
    }

    public JsonObject getData() {
        return this.data;
    }

    public BetterJsonObject addProperty(String key, String value) {
        if (key != null) {
            this.data.addProperty(key, value);
        }
        return this;
    }

    public BetterJsonObject addProperty(String key, Number value) {
        if (key != null) {
            this.data.addProperty(key, value);
        }
        return this;
    }

    public BetterJsonObject addProperty(String key, Boolean value) {
        if (key != null) {
            this.data.addProperty(key, value);
        }
        return this;
    }

    public BetterJsonObject add(String key, BetterJsonObject object) {
        if (key != null) {
            this.data.add(key, (JsonElement)object.getData());
        }
        return this;
    }

    public void writeToFile(File file) {
        if (file == null || file.exists() && file.isDirectory()) {
            return;
        }
        try {
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(this.toPrettyString());
            bufferedWriter.close();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonPrimitive asPrimitive(JsonElement element) {
        return element instanceof JsonPrimitive ? (JsonPrimitive)element : null;
    }

    public String toString() {
        return this.data.toString();
    }

    public String toPrettyString() {
        return prettyPrinter.toJson((JsonElement)this.data);
    }

    public Gson getGsonData() {
        return prettyPrinter;
    }
}

