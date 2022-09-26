/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.HackManager;
import zelix.utils.MoveUtils;
import zelix.utils.PlayerControllerUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class LongJump
extends Hack {
    Hack h = HackManager.getHack("Blink");
    TimerUtils timer = new TimerUtils();
    ModeValue mode = new ModeValue("Mode", new Mode("Simple", false), new Mode("Blink", true), new Mode("HYT", false));
    NumberValue distance;
    NumberValue blinktime = new NumberValue("BlinkTime", 500.0, 0.0, 2000.0);
    NumberValue y;

    public LongJump() {
        super("LongJump", HackCategory.MOVEMENT);
        this.distance = new NumberValue("Distance", 4.0, 2.0, 8.0);
        this.y = new NumberValue("MotionY", 0.4, 0.0, 2.0);
        this.addValue(this.mode, this.blinktime, this.distance, this.y);
    }

    @Override
    public String getDescription() {
        return "Jump further";
    }

    @Override
    public void onEnable() {
        Utils.nullCheck();
        if (this.mode.getMode("Simple").isToggled()) {
            this.Jump();
        } else if (this.mode.getMode("Blink").isToggled()) {
            this.h.onEnable();
            this.h.setToggled(true);
            this.Jump();
        } else if (this.mode.getMode("HYT").isToggled()) {
            this.h.onEnable();
            this.h.setToggled(true);
            this.JumpHYT();
            if (this.timer.hasReached(((Double)this.blinktime.getValue()).floatValue())) {
                ChatUtils.message("OK");
                this.onDisable();
                this.timer.reset();
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.h.onDisable();
        this.h.setToggled(false);
        super.onDisable();
    }

    public void Jump() {
        if (PlayerControllerUtils.isMoving()) {
            MoveUtils.strafeHYT(((Double)this.distance.getValue()).floatValue());
            Wrapper.INSTANCE.player().motionY = ((Double)this.y.getValue()).floatValue();
            MoveUtils.strafeHYT(((Double)this.distance.getValue()).floatValue());
        } else {
            Wrapper.INSTANCE.player().motionZ = 0.0;
            Wrapper.INSTANCE.player().motionX = 0.0;
        }
    }

    public void JumpHYT() {
        if (PlayerControllerUtils.isMoving()) {
            MoveUtils.strafeHYT(((Double)this.distance.getValue()).floatValue());
            Wrapper.INSTANCE.player().motionY = ((Double)this.y.getValue()).floatValue();
            MoveUtils.strafeHYT(((Double)this.distance.getValue()).floatValue());
        } else {
            Wrapper.INSTANCE.player().motionZ = 0.0;
            Wrapper.INSTANCE.player().motionX = 0.0;
        }
    }
}

