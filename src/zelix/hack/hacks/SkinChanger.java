/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.util.ResourceLocation
 */
package zelix.hack.hacks;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.SkinChangerManager;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;

public class SkinChanger
extends Hack {
    public ResourceLocation defaultSkin;

    public SkinChanger() {
        super("SkinChanger", HackCategory.VISUAL);
    }

    @Override
    public String getDescription() {
        return "Changing your skin/cape. (BETA)";
    }

    @Override
    public void onEnable() {
        ResourceLocation location;
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        if (this.defaultSkin == null) {
            this.defaultSkin = player.getLocationSkin();
        }
        if ((location = SkinChangerManager.playerTextures.get(MinecraftProfileTexture.Type.SKIN)) != null && !SkinChangerManager.setTexture(MinecraftProfileTexture.Type.SKIN, (AbstractClientPlayer)player, location)) {
            this.failed();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.defaultSkin != null && !SkinChangerManager.setTexture(MinecraftProfileTexture.Type.SKIN, (AbstractClientPlayer)Wrapper.INSTANCE.player(), this.defaultSkin)) {
            this.failed();
        }
        this.defaultSkin = null;
        super.onDisable();
    }

    void failed() {
        ChatUtils.error("SkinChanger: Set texture failed!");
    }
}

