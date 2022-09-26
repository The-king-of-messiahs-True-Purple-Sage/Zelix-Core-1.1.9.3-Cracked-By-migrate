/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.SliderSetting;

public final class FlightHack
extends Hack {
    private final SliderSetting speed = new SliderSetting("Speed", 1.0, 0.05, 5.0, 0.05, SliderSetting.ValueDisplay.DECIMAL);

    public FlightHack() {
        super("Flight", "Allows you to fly.\n\n\u00a7c\u00a7lWARNING:\u00a7r You will take fall damage\nif you don't use NoFall.");
        this.setCategory(Category.MOVEMENT);
        this.addSetting(this.speed);
    }

    @Override
    public String getRenderName() {
        return this.getName() + " [" + this.speed.getValueString() + "]";
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        EntityPlayerSP player = event.getPlayer();
        player.capabilities.isFlying = false;
        player.motionX = 0.0;
        player.motionY = 0.0;
        player.motionZ = 0.0;
        player.jumpMovementFactor = this.speed.getValueF();
        if (FlightHack.mc.gameSettings.keyBindJump.isKeyDown()) {
            player.motionY += this.speed.getValue();
        }
        if (FlightHack.mc.gameSettings.keyBindSneak.isKeyDown()) {
            player.motionY -= this.speed.getValue();
        }
    }
}

