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
import zelix.utils.Wrapper;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class AutoStep
extends Hack {
    public ModeValue mode = new ModeValue("Mode", new Mode("Simple", true), new Mode("AAC", false));
    public NumberValue height = new NumberValue("Height", 0.5, 0.0, 10.0);
    public float tempHeight;
    public int ticks = 0;

    public AutoStep() {
        super("AutoStep", HackCategory.PLAYER);
        this.addValue(this.mode, this.height);
    }

    @Override
    public String getDescription() {
        return "Allows you to walk on value blocks height.";
    }

    @Override
    public void onEnable() {
        this.ticks = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Wrapper.INSTANCE.player().stepHeight = 0.5f;
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("AAC").isToggled()) {
            EntityPlayerSP player = Wrapper.INSTANCE.player();
            if (player.isCollidedHorizontally) {
                switch (this.ticks) {
                    case 0: {
                        if (!player.onGround) break;
                        player.jump();
                        break;
                    }
                    case 7: {
                        player.motionY = 0.0;
                        break;
                    }
                    case 8: {
                        if (player.onGround) break;
                        player.setPosition(player.posX, player.posY + 1.0, player.posZ);
                    }
                }
                ++this.ticks;
            } else {
                this.ticks = 0;
            }
        } else if (this.mode.getMode("Simple").isToggled()) {
            Wrapper.INSTANCE.player().stepHeight = ((Double)this.height.getValue()).floatValue();
        }
        super.onClientTick(event);
    }
}

