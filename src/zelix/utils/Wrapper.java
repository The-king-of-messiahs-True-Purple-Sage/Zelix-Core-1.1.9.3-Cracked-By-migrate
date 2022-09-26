/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.network.Packet
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.utils;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.utils.system.Mapping;

public class Wrapper {
    public static boolean SelfFontRender = true;
    public static volatile Wrapper INSTANCE = new Wrapper();

    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public EntityPlayerSP player() {
        return Wrapper.INSTANCE.mc().thePlayer;
    }

    public WorldClient world() {
        return Wrapper.INSTANCE.mc().theWorld;
    }

    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().gameSettings;
    }

    public FontRenderer fontRenderer() {
        return Wrapper.INSTANCE.mc().fontRendererObj;
    }

    public void sendPacket(Packet packet) {
        this.player().sendQueue.addToSendQueue(packet);
    }

    public InventoryPlayer inventory() {
        return this.player().inventory;
    }

    public PlayerControllerMP controller() {
        return Wrapper.INSTANCE.mc().playerController;
    }

    public static double getRenderPosX() {
        return (Double)Wrapper.getField("renderPosX", "field_78725_b", Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() {
        return (Double)Wrapper.getField("renderPosY", "field_78726_c", Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() {
        return (Double)Wrapper.getField("renderPosZ", "field_78723_d", Minecraft.getMinecraft().getRenderManager());
    }

    public static Object getField(String field, String obfName, Object instance) {
        String[] stringArray;
        Class<?> clazz = instance.getClass();
        if (Mapping.isNotObfuscated()) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = field;
        } else {
            String[] stringArray3 = new String[1];
            stringArray = stringArray3;
            stringArray3[0] = obfName;
        }
        Field fField = ReflectionHelper.findField(clazz, (String[])stringArray);
        fField.setAccessible(true);
        try {
            return fField.get(instance);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

