/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.SkinChangerManager;
import zelix.utils.Wrapper;

public class SkinStealer
extends Hack {
    public EntityPlayer currentPlayer;

    public SkinStealer() {
        super("SkinStealer", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Left click on player - steal skin.";
    }

    @Override
    public void onDisable() {
        this.currentPlayer = null;
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Entity entity;
        RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
        if (object == null) {
            return;
        }
        if (object.field_72313_a == RayTraceResult.Type.ENTITY && (entity = object.field_72308_g) instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !Wrapper.INSTANCE.player().isDead && Wrapper.INSTANCE.player().canEntityBeSeen(entity)) {
            EntityPlayer player = (EntityPlayer)entity;
            if (Mouse.isButtonDown((int)0) && Wrapper.INSTANCE.mc().currentScreen == null && player != this.currentPlayer && player.getGameProfile() != null) {
                SkinChangerManager.addTexture(MinecraftProfileTexture.Type.SKIN, player.getGameProfile().getName());
                this.currentPlayer = player;
            }
        }
        super.onClientTick(event);
    }
}

