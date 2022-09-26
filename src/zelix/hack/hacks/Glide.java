/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Glide
extends Hack {
    public BooleanValue damage = new BooleanValue("SelfDamage", false);
    public ModeValue mode = new ModeValue("Mode", new Mode("Falling", true), new Mode("Flat", false));
    static int tick;
    static boolean fall;
    static int times;
    TimerUtils timer;

    public Glide() {
        super("Glide", HackCategory.MOVEMENT);
        this.addValue(this.mode, this.damage);
        this.timer = new TimerUtils();
    }

    @Override
    public String getDescription() {
        return "Makes you glide down slowly when falling.";
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.damage.getValue()).booleanValue()) {
            HackManager.getHack("SelfDamage").toggle();
        }
        super.onEnable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Wrapper.INSTANCE.player();
        if (this.mode.getMode("Flat").isToggled()) {
            if (!player.capabilities.isFlying && player.fallDistance > 0.0f && !player.isSneaking()) {
                player.motionY = 0.0;
            }
            if (Wrapper.INSTANCE.mcSettings().keyBindSneak.isKeyDown()) {
                player.motionY = -0.11;
            }
            if (Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
                player.motionY = 0.11;
            }
            if (this.timer.delay(50.0f)) {
                player.onGround = false;
                this.timer.setLastMS();
            }
        } else if (this.mode.getMode("Falling").isToggled()) {
            if (player.onGround) {
                times = 0;
            }
            if (player.fallDistance > 0.0f && times <= 1) {
                if (tick > 0 && fall) {
                    player.motionY = 0.0;
                    tick = 0;
                } else {
                    ++tick;
                }
                if ((double)player.fallDistance >= 0.1) {
                    fall = false;
                }
                if ((double)player.fallDistance >= 0.4) {
                    fall = true;
                    player.fallDistance = 0.0f;
                }
            }
        }
        super.onClientTick(event);
    }
}

