/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.resources.DefaultPlayerSkin
 *  net.minecraft.util.ResourceLocation
 *  org.apache.commons.io.IOUtils
 */
package zelix.managers;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import zelix.hack.Hack;
import zelix.hack.hacks.skinchanger.CacheRetriever;
import zelix.hack.hacks.skinchanger.TextureReflection;
import zelix.hack.hacks.skinchanger.resources.BetterJsonObject;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;

public class SkinChangerManager {
    private static final HashMap<String, String> responses = new HashMap();
    private static final LinkedHashMap<String, String[]> idCaches = new LinkedHashMap();
    private static final LinkedHashMap<String, Boolean> slimSkins = new LinkedHashMap();
    private static final LinkedHashMap<String, BetterJsonObject> idEncryptedTextures = new LinkedHashMap();
    private static final LinkedHashMap<String, ResourceLocation> skins = new LinkedHashMap();
    public static Map<MinecraftProfileTexture.Type, ResourceLocation> playerTextures = Maps.newEnumMap(MinecraftProfileTexture.Type.class);
    private static HashMap<AbstractClientPlayer, NetworkPlayerInfo> cachedPlayerInfo = new HashMap();
    private static CacheRetriever cacheRetriever = new CacheRetriever();
    private static final String[] TRUSTED_DOMAINS = new String[]{".minecraft.net", ".mojang.com"};

    public static void addTexture(MinecraftProfileTexture.Type type, String content) {
        boolean loaded = SkinChangerManager.loadTexture(type, content);
        if (loaded) {
            ChatUtils.message(String.format("\u00a77TYPE: \u00a73%s \u00a77CONTENT: \u00a73%s \u00a77- ADDED.", type.toString(), content));
            Hack hack = HackManager.getHack("SkinChanger");
            if (hack.isToggled()) {
                hack.onEnable();
            }
        } else {
            ChatUtils.error("SkinChanger: Failed load texture!");
        }
    }

    private static boolean loadTexture(MinecraftProfileTexture.Type type, String content) {
        String name = type.toString().substring(0, 1);
        for (int i = 0; i < 6; ++i) {
            name = name + Utils.random(0, 9);
        }
        ResourceLocation resource = null;
        if (content.startsWith("http://") || content.startsWith("https://")) {
            resource = cacheRetriever.loadIntoGame(name, content, type);
        } else if (!content.contains("/") && !content.contains("\\")) {
            String playerId = SkinChangerManager.getIdFromUsername(content);
            String userName = SkinChangerManager.getRealNameFromName(content);
            if (userName == null) {
                userName = content;
            }
            String finalUserName = userName;
            ResourceLocation resourceLocation = SkinChangerManager.getSkinFromId(playerId);
            boolean hasSlimSkin = SkinChangerManager.hasSlimSkin(finalUserName);
            resource = resourceLocation;
        }
        if (resource == null) {
            return false;
        }
        playerTextures.put(type, resource);
        return true;
    }

    public static void removeTexture(MinecraftProfileTexture.Type type) {
        playerTextures.remove(type);
    }

    public static void clear() {
        playerTextures.clear();
    }

    public static boolean setTexture(MinecraftProfileTexture.Type type, AbstractClientPlayer player, ResourceLocation location) {
        NetworkPlayerInfo playerInfo = SkinChangerManager.getPlayerInfo(player);
        return TextureReflection.setTextureForPlayer(type, playerInfo, location);
    }

    private static NetworkPlayerInfo getPlayerInfo(AbstractClientPlayer player) {
        if (cachedPlayerInfo.containsKey(player)) {
            return cachedPlayerInfo.get(player);
        }
        NetworkPlayerInfo playerInfo = TextureReflection.getNetworkPlayerInfo(player);
        if (playerInfo != null) {
            cachedPlayerInfo.put(player, playerInfo);
        }
        return playerInfo;
    }

    public static String getRealNameFromName(String nameIn) {
        if (nameIn == null) {
            return null;
        }
        if (idCaches.containsKey(nameIn)) {
            return idCaches.get(nameIn)[1];
        }
        SkinChangerManager.getIdFromUsername(nameIn);
        if (idCaches.get(nameIn) == null) {
            return null;
        }
        return idCaches.get(nameIn)[1];
    }

    public static ResourceLocation getSkinFromId(String id) {
        try {
            return SkinChangerManager.getSkinFromIdUnsafe(id);
        }
        catch (IllegalArgumentException ex) {
            if (ex.getMessage() != null) {
                System.err.println(ex.getMessage());
            }
            return DefaultPlayerSkin.getDefaultSkinLegacy();
        }
    }

    private static ResourceLocation getSkinFromIdUnsafe(String id) {
        if (id != null && !id.isEmpty()) {
            JsonObject realTextures;
            if (skins.containsKey(id)) {
                ResourceLocation loc = skins.get(id);
                if (Minecraft.getMinecraft().getTextureManager().getTexture(loc) != null) {
                    return loc;
                }
                skins.remove(id);
            }
            if (!(realTextures = SkinChangerManager.getEncryptedTexturesUnsafe(id).getData()).has("SKIN")) {
                skins.put(id, DefaultPlayerSkin.getDefaultSkinLegacy());
                return DefaultPlayerSkin.getDefaultSkinLegacy();
            }
            JsonObject skinData = realTextures.get("SKIN").getAsJsonObject();
            if (!skinData.has("url")) {
                skins.put(id, DefaultPlayerSkin.getDefaultSkinLegacy());
                return DefaultPlayerSkin.getDefaultSkinLegacy();
            }
            String url = skinData.get("url").getAsString();
            if (!SkinChangerManager.isTrustedDomain(url)) {
                throw new IllegalArgumentException("Invalid payload, the domain issued was not trusted.");
            }
            ResourceLocation playerSkin = cacheRetriever.loadIntoGame(id, url, MinecraftProfileTexture.Type.SKIN);
            skins.put(id, playerSkin);
            return playerSkin;
        }
        return DefaultPlayerSkin.getDefaultSkinLegacy();
    }

    private static boolean isTrustedDomain(String url) {
        URI uri;
        if (url == null) {
            return false;
        }
        try {
            uri = new URI(url);
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL '" + url + "'");
        }
        String host = uri.getHost();
        for (String domain : TRUSTED_DOMAINS) {
            if (!host.endsWith(domain)) continue;
            return true;
        }
        return false;
    }

    public static boolean hasSlimSkin(String id) {
        try {
            return SkinChangerManager.hasSlimSkinUnsafe(id);
        }
        catch (JsonParseException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean hasSlimSkinUnsafe(String id) throws NullPointerException, IllegalStateException, JsonParseException {
        if (id == null) {
            return false;
        }
        if (slimSkins.containsKey(id)) {
            return slimSkins.get(id);
        }
        JsonObject realTextures = SkinChangerManager.getEncryptedTexturesUnsafe(id).getData();
        if (!realTextures.has("SKIN")) {
            slimSkins.put(id, false);
            return false;
        }
        JsonObject skinData = realTextures.get("SKIN").getAsJsonObject();
        if (skinData.has("metadata")) {
            JsonObject metaData = skinData.get("metadata").getAsJsonObject();
            slimSkins.put(id, metaData.has("model"));
            return metaData.has("model") && metaData.get("model").getAsString().equals("slim");
        }
        slimSkins.put(id, false);
        return false;
    }

    private static BetterJsonObject getEncryptedTexturesUnsafe(String id) throws IllegalStateException, JsonParseException {
        if (id == null) {
            return new BetterJsonObject();
        }
        if (idEncryptedTextures.containsKey(id)) {
            return idEncryptedTextures.get(id);
        }
        BetterJsonObject texturesIn = SkinChangerManager.getTexturesFromId(id);
        if (!texturesIn.has("properties") || !texturesIn.get("properties").isJsonArray()) {
            return new BetterJsonObject();
        }
        JsonArray propertyArray = texturesIn.get("properties").getAsJsonArray();
        for (JsonElement propertyElement : propertyArray) {
            byte[] decoded;
            JsonObject decodedObj;
            JsonObject property;
            if (!propertyElement.isJsonObject() || !(property = propertyElement.getAsJsonObject()).has("name") || !property.get("name").getAsString().equals("textures") || !property.has("value") || !(decodedObj = new JsonParser().parse(new String(decoded = Base64.getDecoder().decode(property.get("value").getAsString()), StandardCharsets.UTF_8)).getAsJsonObject()).has("textures") || !decodedObj.has("profileId") || !decodedObj.get("profileId").getAsString().equals(texturesIn.get("id").getAsString())) continue;
            idEncryptedTextures.put(id, new BetterJsonObject(decodedObj.get("textures").getAsJsonObject()));
            return idEncryptedTextures.get(id);
        }
        idEncryptedTextures.put(id, new BetterJsonObject());
        return idEncryptedTextures.get(id);
    }

    public static BetterJsonObject getTexturesFromId(String id) {
        if (id == null || id.isEmpty()) {
            return new BetterJsonObject();
        }
        id = id.replace("-", "");
        return new BetterJsonObject(SkinChangerManager.getUrl("https://sessionserver.mojang.com/session/minecraft/profile/" + id));
    }

    public static String getIdFromUsername(String nameIn) {
        if (nameIn == null) {
            return null;
        }
        if (idCaches.containsKey(nameIn)) {
            return idCaches.get(nameIn)[0];
        }
        if (nameIn.isEmpty()) {
            idCaches.put("", new String[]{"", ""});
            return "";
        }
        BetterJsonObject profile = SkinChangerManager.getProfileFromUsername(nameIn);
        if (profile.has("success") && !profile.get("success").getAsBoolean()) {
            idCaches.put(nameIn, new String[]{"", ""});
            return "";
        }
        if (profile.has("id")) {
            idCaches.put(nameIn, new String[]{profile.get("id").getAsString(), profile.get("name").getAsString()});
            return profile.get("id").getAsString();
        }
        idCaches.put(nameIn, new String[]{"", ""});
        return "";
    }

    public static BetterJsonObject getProfileFromUsername(String name) {
        if (name == null || name.isEmpty()) {
            return new BetterJsonObject();
        }
        return new BetterJsonObject(SkinChangerManager.getUrl("https://api.mojang.com/users/profiles/minecraft/" + name));
    }

    public static final String getUrl(String url) {
        String response;
        if (responses.containsKey(url)) {
            return responses.get(url);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url.replace(" ", "%20")).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; SkinChanger; 3.0.1) Chrome/83.0.4103.116");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            response = IOUtils.toString((InputStream)connection.getInputStream(), (Charset)StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            JsonObject object = new JsonObject();
            object.addProperty("success", Boolean.valueOf(false));
            object.addProperty("cause", "Exception");
            object.addProperty("message", e.getMessage() != null ? "" : e.getMessage());
            response = object.toString();
        }
        responses.put(url, response);
        return response;
    }
}

