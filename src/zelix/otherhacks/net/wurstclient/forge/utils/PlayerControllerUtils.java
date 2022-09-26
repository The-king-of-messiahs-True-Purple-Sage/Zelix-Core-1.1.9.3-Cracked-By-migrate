/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 */
package zelix.otherhacks.net.wurstclient.forge.utils;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;

public final class PlayerControllerUtils {
    private static final ForgeWurst wurst = ForgeWurst.getForgeWurst();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static ItemStack windowClick_PICKUP(int slot) {
        return PlayerControllerUtils.mc.playerController.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WMinecraft.getPlayer());
    }

    public static ItemStack windowClick_QUICK_MOVE(int slot) {
        return PlayerControllerUtils.mc.playerController.func_187098_a(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WMinecraft.getPlayer());
    }

    public static ItemStack windowClick_THROW(int slot) {
        return PlayerControllerUtils.mc.playerController.func_187098_a(0, slot, 1, ClickType.THROW, (EntityPlayer)WMinecraft.getPlayer());
    }

    public static float getCurBlockDamageMP() throws ReflectiveOperationException {
        Field field = PlayerControllerMP.class.getDeclaredField(wurst.isObfuscated() ? "field_78770_f" : "curBlockDamageMP");
        field.setAccessible(true);
        return field.getFloat(PlayerControllerUtils.mc.playerController);
    }

    public static void setBlockHitDelay(int blockHitDelay) throws ReflectiveOperationException {
        Field field = PlayerControllerMP.class.getDeclaredField(wurst.isObfuscated() ? "field_78781_i" : "blockHitDelay");
        field.setAccessible(true);
        field.setInt(PlayerControllerUtils.mc.playerController, blockHitDelay);
    }

    public static void setIsHittingBlock(boolean isHittingBlock) throws ReflectiveOperationException {
        Field field = PlayerControllerMP.class.getDeclaredField(wurst.isObfuscated() ? "field_78778_j" : "isHittingBlock");
        field.setAccessible(true);
        field.setBoolean(PlayerControllerUtils.mc.playerController, isHittingBlock);
    }
}

