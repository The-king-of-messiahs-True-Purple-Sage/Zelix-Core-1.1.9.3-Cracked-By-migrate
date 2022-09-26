/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.MobEffects
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package zelix.hack.hacks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import zelix.gui.clickguis.gishcode.ClickGuiScreen;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.Utils;
import zelix.utils.Wrapper;

public class AutoSprint
extends Hack {
    public AutoSprint() {
        super("AutoSprint", HackCategory.MOVEMENT);
    }

    @Override
    public String getDescription() {
        return "Sprints automatically when you should be walking.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.isMoveInGui() && this.canSprint(false)) {
            Wrapper.INSTANCE.player().setSprinting(true);
            return;
        }
        if (this.canSprint(true)) {
            Wrapper.INSTANCE.player().setSprinting(Utils.isMoving((Entity)Wrapper.INSTANCE.player()));
        }
        super.onClientTick(event);
    }

    boolean isMoveInGui() {
        return Keyboard.isKeyDown((int)Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode()) && (Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer || Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen) && HackManager.getHack("GuiWalk").isToggled();
    }

    boolean canSprint(boolean forward) {
        if (!Wrapper.INSTANCE.player().onGround) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isSprinting()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isOnLadder()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isInWater()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isInLava()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isCollidedHorizontally) {
            return false;
        }
        if (forward && Wrapper.INSTANCE.player().field_191988_bg < 0.1f) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isSneaking()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().getFoodStats().getFoodLevel() < 6) {
            return false;
        }
        if (Wrapper.INSTANCE.player().func_184218_aH()) {
            return false;
        }
        return !Wrapper.INSTANCE.player().isPotionActive(MobEffects.field_76440_q);
    }
}

