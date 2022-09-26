/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonParser
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public final class JsonUtils {
    public static final Gson gson = new Gson();
    public static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
    public static final JsonParser jsonParser = new JsonParser();
}

