/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 */
package zelix.utils.hooks;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AbstractClientPlayerHook
extends AbstractClientPlayer {
    public AbstractClientPlayerHook(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    public ResourceLocation getLocationCape() {
        return null;
    }
}

