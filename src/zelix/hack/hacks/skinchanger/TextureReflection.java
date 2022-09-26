/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.util.ResourceLocation
 */
package zelix.hack.hacks.skinchanger;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.Mapping;

public class TextureReflection {
    public static boolean setTextureForPlayer(MinecraftProfileTexture.Type type, NetworkPlayerInfo playerInfo, ResourceLocation location) {
        if (playerInfo == null) {
            ChatUtils.error("getPlayerInfo is null!");
            return false;
        }
        try {
            Field playerTextures = NetworkPlayerInfo.class.getDeclaredField(Mapping.playerTextures);
            boolean accessible = playerTextures.isAccessible();
            playerTextures.setAccessible(true);
            ConcurrentHashMap<MinecraftProfileTexture.Type, ResourceLocation> concHashMap = new ConcurrentHashMap<MinecraftProfileTexture.Type, ResourceLocation>();
            concHashMap.put(type, location);
            playerTextures.set(playerInfo, concHashMap);
            playerTextures.setAccessible(accessible);
            return true;
        }
        catch (Exception ex) {
            ChatUtils.error(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public static NetworkPlayerInfo getNetworkPlayerInfo(AbstractClientPlayer player) {
        try {
            Method method = AbstractClientPlayer.class.getDeclaredMethod(Mapping.getPlayerInfo, new Class[0]);
            method.setAccessible(true);
            NetworkPlayerInfo playerInfo = (NetworkPlayerInfo)method.invoke((Object)player, new Object[0]);
            return playerInfo;
        }
        catch (Exception ex) {
            ChatUtils.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}

