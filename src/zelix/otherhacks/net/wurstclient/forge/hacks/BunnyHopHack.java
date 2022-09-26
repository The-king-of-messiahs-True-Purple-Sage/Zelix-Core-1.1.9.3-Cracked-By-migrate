/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.function.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;

public final class BunnyHopHack
extends Hack {
    private final EnumSetting<JumpIf> jumpIf = new EnumSetting("Jump if", (Enum[])JumpIf.values(), (Enum)JumpIf.SPRINTING);

    public BunnyHopHack() {
        super("BunnyHop", "Makes you jump automatically.");
        this.setCategory(Category.MOVEMENT);
        this.addSetting(this.jumpIf);
    }

    @Override
    public String getRenderName() {
        return this.getName() + " [" + this.jumpIf.getSelected().name + "]";
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
        if (!player.onGround || player.isSneaking() || player.isInsideOfMaterial(Material.water)) {
            return;
        }
        if (this.jumpIf.getSelected().condition.test(player)) {
            player.jump();
        }
    }

    private static enum JumpIf {
        SPRINTING("Sprinting", p -> p.isSprinting() && (p.field_191988_bg != 0.0f || p.moveStrafing != 0.0f)),
        WALKING("Walking", p -> p.field_191988_bg != 0.0f || p.moveStrafing != 0.0f),
        ALWAYS("Always", p -> true);

        private final String name;
        private final Predicate<EntityPlayerSP> condition;

        private JumpIf(String name, Predicate<EntityPlayerSP> condition) {
            this.name = name;
            this.condition = condition;
        }

        public String toString() {
            return this.name;
        }
    }
}

