/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.IImageBuffer
 *  net.minecraft.client.renderer.ThreadDownloadImageData
 *  net.minecraft.client.renderer.texture.ITextureObject
 *  net.minecraft.client.resources.DefaultPlayerSkin
 *  net.minecraft.util.ResourceLocation
 */
package zelix.hack.hacks.skinchanger;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import zelix.hack.hacks.skinchanger.resources.CapeBuffer;
import zelix.hack.hacks.skinchanger.resources.LocalFileData;
import zelix.hack.hacks.skinchanger.resources.SkinBuffer;
import zelix.managers.FileManager;
import zelix.utils.hooks.visual.ChatUtils;

public class CacheRetriever {
    private static final ResourceLocation undefinedTexture = new ResourceLocation("skinchanger", "light.png");
    private static final boolean FORCE_HTTPS = true;
    private final HashMap<String, String> cachedValues = new HashMap();
    private final File cacheDirectory = FileManager.SKINCHANGER;

    public CacheRetriever() {
        this.genCacheDirectory();
    }

    public ResourceLocation loadIntoGame(String name, String url) {
        return this.loadIntoGame(name, url, null);
    }

    public ResourceLocation loadIntoGame(String name, String url, MinecraftProfileTexture.Type type) {
        CapeBuffer buffer;
        CacheType cacheType = null;
        cacheType = type == null ? CacheType.OTHER : CacheType.valueOf(type.toString());
        if (cacheType == null) {
            return null;
        }
        File cacheDirectory = this.getCacheDirForName(name);
        File cachedFile = cacheType != CacheType.OTHER ? this.getCacheFileIfIExists(name, ".png") : null;
        ResourceLocation location = new ResourceLocation("skins/" + this.getCacheName(name));
        Object object = cacheType == CacheType.CAPE ? new CapeBuffer() : (buffer = cacheType == CacheType.SKIN ? new SkinBuffer() : null);
        if (cachedFile != null) {
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().renderEngine.loadTexture(location, (ITextureObject)new LocalFileData(DefaultPlayerSkin.getDefaultSkinLegacy(), cachedFile, buffer)));
            return location;
        }
        File dataFile = new File(cacheDirectory, cacheDirectory.getName() + ".png");
        if (url.startsWith("http://") && !url.contains("optifine")) {
            url = "https://" + url.substring("http://".length());
        }
        ThreadDownloadImageData imageData = new ThreadDownloadImageData(dataFile, url, cacheType == CacheType.CAPE ? new ResourceLocation("skinchanger", "light.png") : DefaultPlayerSkin.getDefaultSkinLegacy(), (IImageBuffer)buffer);
        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().renderEngine.loadTexture(location, (ITextureObject)imageData));
        if (this.generateCacheFiles(name)) {
            return location;
        }
        return null;
    }

    public boolean generateCacheFiles(String name) {
        File cacheDirectory = this.getCacheDirForName(name);
        if (this.isCacheExpired(name)) {
            cacheDirectory.delete();
        }
        if (!cacheDirectory.exists() && !cacheDirectory.mkdir()) {
            ChatUtils.error("Failed to create a cache directory.");
            return false;
        }
        File dataFile = new File(cacheDirectory, cacheDirectory.getName() + ".png");
        File cacheFile = new File(cacheDirectory, cacheDirectory.getName() + ".lock");
        try {
            if (!dataFile.exists() && !dataFile.createNewFile()) {
                ChatUtils.error("SkinChanger: Failed to create a data file.");
                return false;
            }
            if (!cacheFile.exists()) {
                if (!cacheFile.createNewFile()) {
                    ChatUtils.error("Failed to create a cache file.");
                    return false;
                }
                FileWriter writer = new FileWriter(cacheFile);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                long expirationTime = System.currentTimeMillis();
                bufferedWriter.write((expirationTime += 86400000L) + System.lineSeparator());
                bufferedWriter.close();
            }
        }
        catch (Exception ex) {
            ChatUtils.error(ex.getMessage());
            return false;
        }
        return true;
    }

    public File getCacheFileIfIExists(String name, String extension) {
        if (!extension.startsWith(".")) {
            return null;
        }
        if (!this.doesCacheExist(name) || this.isCacheExpired(name)) {
            return null;
        }
        String cached_name = this.getCacheName(name);
        File dir = new File(this.cacheDirectory, cached_name);
        File data_file = new File(dir, cached_name + extension);
        if (!data_file.exists()) {
            return null;
        }
        if (data_file.isDirectory()) {
            if (data_file.delete()) {
                return null;
            }
            return null;
        }
        return data_file;
    }

    public boolean isCacheExpired(String name) {
        if (name == null) {
            return true;
        }
        File fileCache = this.getCacheDirForName(name);
        if (!fileCache.exists()) {
            return true;
        }
        File cacheLock = new File(fileCache, fileCache.getName() + ".lock");
        if (!cacheLock.exists()) {
            return true;
        }
        try {
            FileReader fileReader = new FileReader(cacheLock);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            bufferedReader.close();
            long time = Long.parseLong(line);
            return System.currentTimeMillis() > time;
        }
        catch (IOException ex) {
            ChatUtils.error("SkinChanger: Unable to read cache file for " + name);
            return true;
        }
        catch (NumberFormatException ex) {
            ChatUtils.error("SkinChanger: Cache file had an invalid number.");
            return true;
        }
    }

    public boolean doesCacheExist(String name) {
        if (!this.genCacheDirectory()) {
            return false;
        }
        File cacheDirectory = this.getCacheDirForName(name);
        if (!cacheDirectory.exists()) {
            return false;
        }
        File dataFile = new File(cacheDirectory, cacheDirectory.getName() + ".png");
        File cacheFile = new File(cacheDirectory, cacheDirectory.getName() + ".lock");
        return dataFile.exists() && dataFile.length() > 2L && cacheFile.exists();
    }

    private String getCacheName(String name) {
        if (this.cachedValues.containsKey(name = name.toLowerCase())) {
            return this.cachedValues.get(name);
        }
        UUID id = UUID.nameUUIDFromBytes(name.getBytes());
        String subStrName = name.substring(0, Math.min(7, name.length()));
        String[] uuidSplit = id.toString().split("-");
        String idFirstComponent = uuidSplit[0] + uuidSplit[1];
        String finalCacheName = subStrName + "_" + idFirstComponent;
        this.cachedValues.put(name, finalCacheName);
        return finalCacheName;
    }

    private File getCacheDirForName(String nameOfFile) {
        String cacheName = this.getCacheName(nameOfFile);
        return new File(this.cacheDirectory, cacheName);
    }

    private boolean genCacheDirectory() {
        boolean existed = true;
        if (!this.cacheDirectory.getParentFile().exists()) {
            existed = false;
            if (!this.cacheDirectory.getParentFile().mkdirs()) {
                ChatUtils.error("SkinChanger: Unable to create the mod directory.");
                return false;
            }
        }
        if (!this.cacheDirectory.exists()) {
            existed = false;
            if (!this.cacheDirectory.mkdir()) {
                ChatUtils.error("SkinChanger: Unable to create cache directory.");
                return false;
            }
        }
        return existed;
    }

    public File getCacheDirectory() {
        return this.cacheDirectory;
    }

    public static enum CacheType {
        SKIN,
        CAPE,
        OTHER;

    }
}

