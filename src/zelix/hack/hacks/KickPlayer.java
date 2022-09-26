/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.login.client.CPacketLoginStart
 */
package zelix.hack.hacks;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.login.client.CPacketLoginStart;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;

public class KickPlayer
extends Hack {
    public static String kickname;

    public KickPlayer() {
        super("KickPlayer", HackCategory.ANOTHER);
    }

    @Override
    public void onEnable() {
        if (kickname == null) {
            return;
        }
        Wrapper.INSTANCE.player();
        Wrapper.INSTANCE.sendPacket((Packet)new CPacketLoginStart(new GameProfile(EntityPlayerSP.getUUID((GameProfile)Wrapper.INSTANCE.player().getGameProfile()), kickname)));
        super.onEnable();
    }
}

