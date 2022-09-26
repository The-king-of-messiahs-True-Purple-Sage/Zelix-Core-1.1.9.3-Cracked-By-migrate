/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.utils.system;

import net.minecraft.client.Minecraft;

public class Mapping {
    public static String onGround = Mapping.isNotObfuscated() ? "onGround" : "field_149474_g";
    public static String tickLength = Mapping.isNotObfuscated() ? "tickLength" : "field_194149_e";
    public static String timer = Mapping.isNotObfuscated() ? "timer" : "field_71428_T";
    public static String session = Mapping.isNotObfuscated() ? "session" : "field_71449_j";
    public static String yaw = Mapping.isNotObfuscated() ? "yaw" : "field_149476_e";
    public static String pitch = Mapping.isNotObfuscated() ? "pitch" : "field_149473_f";
    public static String rightClickDelayTimer = Mapping.isNotObfuscated() ? "rightClickDelayTimer" : "field_71467_ac";
    public static String getPlayerInfo = Mapping.isNotObfuscated() ? "getPlayerInfo" : "func_175155_b";
    public static String playerTextures = Mapping.isNotObfuscated() ? "playerTextures" : "field_187107_a";
    public static String currentGameType = Mapping.isNotObfuscated() ? "currentGameType" : "field_78779_k";
    public static String connection = Mapping.isNotObfuscated() ? "connection" : "field_78774_b";
    public static String blockHitDelay = Mapping.isNotObfuscated() ? "blockHitDelay" : "field_78781_i";
    public static String isInWeb = Mapping.isNotObfuscated() ? "isInWeb" : "field_70134_J";
    public static String curBlockDamageMP = Mapping.isNotObfuscated() ? "curBlockDamageMP" : "field_78770_f";
    public static String isHittingBlock = Mapping.isNotObfuscated() ? "isHittingBlock" : "field_78778_j";
    public static String onUpdateWalkingPlayer = Mapping.isNotObfuscated() ? "onUpdateWalkingPlayer" : "func_175161_p";

    public static boolean isNotObfuscated() {
        try {
            return Minecraft.class.getDeclaredField("instance") != null;
        }
        catch (Exception ex) {
            return false;
        }
    }
}

