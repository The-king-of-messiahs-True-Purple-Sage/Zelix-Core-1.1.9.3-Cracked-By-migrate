/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;

public final class AutoSwimHack
extends Hack {
    private final EnumSetting<Mode> mode = new EnumSetting("Mode", (Enum[])Mode.values(), (Enum)Mode.DOLPHIN);

    public AutoSwimHack() {
        super("AutoSwim", "Makes you swim automatically.");
        this.setCategory(Category.MOVEMENT);
        this.addSetting(this.mode);
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
        if (player.isInWater() && !player.isSneaking() && !GameSettings.isKeyDown((KeyBinding)AutoSwimHack.mc.gameSettings.keyBindJump)) {
            player.motionY += this.mode.getSelected().upwardsMotion;
        }
    }

    private static enum Mode {
        DOLPHIN("Dolphin", 0.04),
        FISH("Fish", 0.02);

        private final String name;
        private final double upwardsMotion;

        private Mode(String name, double upwardsMotion) {
            this.name = name;
            this.upwardsMotion = upwardsMotion;
        }

        public String toString() {
            return this.name;
        }
    }
}

