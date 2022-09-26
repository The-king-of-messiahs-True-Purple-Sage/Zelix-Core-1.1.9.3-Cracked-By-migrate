/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.multiplayer.WorldClient
 */
package zelix.otherhacks.net.wurstclient.forge.compatibility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;

public final class WMinecraft {
    public static final String VERSION = "1.12.2";
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static EntityPlayerSP getPlayer() {
        return WMinecraft.mc.thePlayer;
    }

    public static WorldClient getWorld() {
        return WMinecraft.mc.theWorld;
    }

    public static FontRenderer getFontRenderer() {
        return WMinecraft.mc.fontRendererObj;
    }
}

