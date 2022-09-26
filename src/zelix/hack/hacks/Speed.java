/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockIce
 *  net.minecraft.block.BlockPackedIce
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.Timer
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.MoveUtils;
import zelix.utils.PlayerControllerUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.utils.system.Mapping;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class Speed
extends Hack {
    public ModeValue mode;
    public boolean shouldslow = false;
    double count = 0.0;
    int jumps;
    private float air;
    private float ground;
    private float aacSlow;
    public static TimerUtils timer = new TimerUtils();
    boolean collided = false;
    boolean lessSlow;
    int spoofSlot = 0;
    double less;
    double stair;
    Number ticks = Float.valueOf(1.05f);
    private int offGroundTicks;
    private double speed;
    private double speedvalue;
    private double lastDist;
    public static int stage;
    public static int aacCount;
    TimerUtils aac = new TimerUtils();
    TimerUtils lastFall = new TimerUtils();
    TimerUtils lastCheck = new TimerUtils();
    public static Minecraft mc;

    public Speed() {
        super("Speed", HackCategory.MOVEMENT);
        this.mode = new ModeValue("Mode", new Mode("Basic", false), new Mode("OldHypixel", false), new Mode("HYT", false), new Mode("AAC4", true), new Mode("AAC", false), new Mode("VulcanLowHop", false));
        this.addValue(this.mode);
    }

    @Override
    public void onDisable() {
        this.setTickLength(50.0f);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.offGroundTicks = 0;
    }

    @Override
    public String getDescription() {
        return "You move faster.";
    }

    @Override
    public void onInputUpdate(InputUpdateEvent event) {
        Utils.nullCheck();
        if (!this.mode.getMode("HYT").isToggled()) {
            return;
        }
        if (MoveUtils.isMoving() && Speed.mc.thePlayer.onGround) {
            this.setTickLength(50.0f / this.ticks.floatValue());
            Speed.mc.thePlayer.motionY = 0.2;
            Speed.mc.thePlayer.jump();
            MoveUtils.strafe(0.3);
        } else {
            MoveUtils.strafe();
        }
        super.onInputUpdate(event);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("Basic").isToggled()) {
            boolean boost;
            boolean bl = boost = Math.abs(Wrapper.INSTANCE.player().rotationYawHead - Wrapper.INSTANCE.player().rotationYaw) < 90.0f;
            if (Wrapper.INSTANCE.player().field_191988_bg > 0.0f && Wrapper.INSTANCE.player().hurtTime < 5) {
                if (Wrapper.INSTANCE.player().onGround) {
                    Wrapper.INSTANCE.player().motionY = 0.405;
                    float f = Utils.getDirection();
                    Wrapper.INSTANCE.player().motionX -= (double)(MathHelper.func_76126_a((float)f) * 0.2f);
                    Wrapper.INSTANCE.player().motionZ += (double)(MathHelper.func_76134_b((float)f) * 0.2f);
                } else {
                    double currentSpeed = Math.sqrt(Wrapper.INSTANCE.player().motionX * Wrapper.INSTANCE.player().motionX + Wrapper.INSTANCE.player().motionZ * Wrapper.INSTANCE.player().motionZ);
                    double speed = boost ? 1.0064 : 1.001;
                    double direction = Utils.getDirection();
                    Wrapper.INSTANCE.player().motionX = -Math.sin(direction) * speed * currentSpeed;
                    Wrapper.INSTANCE.player().motionZ = Math.cos(direction) * speed * currentSpeed;
                }
            }
        } else if (this.mode.getMode("OldHypixel").isToggled()) {
            if (Wrapper.INSTANCE.player().isCollidedHorizontally) {
                this.collided = true;
            }
            if (this.collided) {
                stage = -1;
            }
            if (this.stair > 0.0) {
                this.stair -= 0.25;
            }
            this.less -= this.less > 1.0 ? 0.12 : 0.11;
            if (this.less < 0.0) {
                this.less = 0.0;
            }
            if (!BlockUtils.isInLiquid() && MoveUtils.isOnGround(0.01) && PlayerControllerUtils.isMoving2()) {
                this.collided = Wrapper.INSTANCE.player().isCollidedHorizontally;
                if (stage >= 0 || this.collided) {
                    stage = 0;
                    double motY = 0.407 + (double)MoveUtils.getJumpEffect() * 0.1;
                    if (this.stair == 0.0) {
                        Wrapper.INSTANCE.player().jump();
                        Wrapper.INSTANCE.player().motionY = motY;
                    }
                    this.less += 1.0;
                    this.lessSlow = this.less > 1.0 && !this.lessSlow;
                    if (this.less > 1.12) {
                        this.less = 1.12;
                    }
                }
            }
            this.speed = this.getHypixelSpeed(stage) + 0.0331;
            this.speed *= 0.91;
            if (this.stair > 0.0) {
                this.speed *= 0.7 - (double)MoveUtils.getSpeedEffect() * 0.1;
            }
            if (stage < 0) {
                this.speed = MoveUtils.defaultSpeed();
            }
            if (this.lessSlow) {
                this.speed *= 0.95;
            }
            if (BlockUtils.isInLiquid()) {
                this.speed = 0.55;
            }
            if (Wrapper.INSTANCE.player().field_191988_bg != 0.0f || Wrapper.INSTANCE.player().moveStrafing != 0.0f) {
                this.setMotion(this.speed);
                ++stage;
            }
        } else if (this.mode.getMode("AAC4").isToggled()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (Wrapper.INSTANCE.mc().thePlayer.field_191988_bg > 0.0f) {
                if (Wrapper.INSTANCE.mc().thePlayer.onGround) {
                    Wrapper.INSTANCE.mc().thePlayer.jump();
                    this.setMotion(1.61);
                    Wrapper.INSTANCE.mc().thePlayer.motionX *= 1.0708;
                    Wrapper.INSTANCE.mc().thePlayer.motionZ *= 1.0708;
                } else if (Wrapper.INSTANCE.mc().thePlayer.fallDistance > 0.0f && Wrapper.INSTANCE.mc().thePlayer.fallDistance < 1.0f) {
                    this.setMotion(0.6);
                }
            }
        } else if (this.mode.getMode("AAC").isToggled()) {
            if ((double)Wrapper.INSTANCE.player().fallDistance > 1.2) {
                this.lastFall.reset();
            }
            if (!BlockUtils.isInLiquid() && Wrapper.INSTANCE.player().isCollidedVertically && MoveUtils.isOnGround(0.01) && (Wrapper.INSTANCE.player().field_191988_bg != 0.0f || Wrapper.INSTANCE.player().moveStrafing != 0.0f)) {
                stage = 0;
                Wrapper.INSTANCE.player().jump();
                Wrapper.INSTANCE.player().motionY = 0.4199 + (double)MoveUtils.getJumpEffect();
                if (aacCount < 4) {
                    ++aacCount;
                }
            }
            this.speed = this.getAACSpeed(stage, aacCount);
            if (Wrapper.INSTANCE.player().field_191988_bg != 0.0f || Wrapper.INSTANCE.player().moveStrafing != 0.0f) {
                if (BlockUtils.isInLiquid()) {
                    this.speed = 0.075;
                }
                this.setMotion(this.speed);
            }
            if (Wrapper.INSTANCE.player().field_191988_bg != 0.0f || Wrapper.INSTANCE.player().moveStrafing != 0.0f) {
                ++stage;
            }
        }
        super.onClientTick(event);
    }

    private double getHypixelSpeed(int stage) {
        double value = MoveUtils.defaultSpeed() + 0.028 * (double)MoveUtils.getSpeedEffect() + (double)MoveUtils.getSpeedEffect() / 15.0;
        double firstvalue = 0.4145 + (double)MoveUtils.getSpeedEffect() / 12.5;
        double decr = (double)stage / 500.0 * 2.0;
        if (stage == 0) {
            if (timer.delay(300.0f)) {
                timer.reset();
            }
            if (!this.lastCheck.delay(500.0f)) {
                if (!this.shouldslow) {
                    this.shouldslow = true;
                }
            } else if (this.shouldslow) {
                this.shouldslow = false;
            }
            value = 0.64 + ((double)MoveUtils.getSpeedEffect() + 0.028 * (double)MoveUtils.getSpeedEffect()) * 0.134;
        } else if (stage == 1) {
            value = firstvalue;
        } else if (stage >= 2) {
            value = firstvalue - decr;
        }
        if (this.shouldslow || !this.lastCheck.delay(500.0f) || this.collided) {
            value = 0.2;
            if (stage == 0) {
                value = 0.0;
            }
        }
        return Math.max(value, this.shouldslow ? value : MoveUtils.defaultSpeed() + 0.028 * (double)MoveUtils.getSpeedEffect());
    }

    private void setMotion(double speed) {
        double forward = Wrapper.INSTANCE.player().movementInput.field_192832_b;
        double strafe = Wrapper.INSTANCE.player().movementInput.moveStrafe;
        float yaw = Wrapper.INSTANCE.player().rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Wrapper.INSTANCE.player().motionX = 0.0;
            Wrapper.INSTANCE.player().motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            Wrapper.INSTANCE.player().motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            Wrapper.INSTANCE.player().motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    private double getAACSpeed(int stage, int jumps) {
        double value = 0.29;
        double firstvalue = 0.3019;
        double thirdvalue = 0.0286 - (double)stage / 1000.0;
        if (stage == 0) {
            Block block;
            value = 0.497;
            if (jumps >= 2) {
                value += 0.1069;
            }
            if (jumps >= 3) {
                value += 0.046;
            }
            if ((block = MoveUtils.getBlockUnderPlayer((EntityPlayer)Wrapper.INSTANCE.player(), 0.01)) instanceof BlockIce || block instanceof BlockPackedIce) {
                value = 0.59;
            }
        } else if (stage == 1) {
            value = 0.3031;
            if (jumps >= 2) {
                value += 0.0642;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 2) {
            value = 0.302;
            if (jumps >= 2) {
                value += 0.0629;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 3) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0607;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 4) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.04584;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 5) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.04561;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 6) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0539;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 7) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0517;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 8) {
            value = firstvalue;
            if (MoveUtils.isOnGround(0.05)) {
                value -= 0.002;
            }
            if (jumps >= 2) {
                value += 0.0496;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 9) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0475;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 10) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0455;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 11) {
            value = 0.3;
            if (jumps >= 2) {
                value += 0.045;
            }
            if (jumps >= 3) {
                value += 0.018;
            }
        } else if (stage == 12) {
            value = 0.301;
            if (jumps <= 2) {
                aacCount = 0;
            }
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        } else if (stage == 13) {
            value = 0.298;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        } else if (stage == 14) {
            value = 0.297;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        }
        if (Wrapper.INSTANCE.player().field_191988_bg <= 0.0f) {
            value -= 0.06;
        }
        if (Wrapper.INSTANCE.player().isCollidedHorizontally) {
            value -= 0.1;
            aacCount = 0;
        }
        return value;
    }

    private void setTickLength(float tickLength) {
        try {
            Field fTimer = Minecraft.getMinecraft().getClass().getDeclaredField(Mapping.timer);
            fTimer.setAccessible(true);
            Field fTickLength = Timer.class.getDeclaredField(Mapping.tickLength);
            fTickLength.setAccessible(true);
            fTickLength.setFloat(fTimer.get(Minecraft.getMinecraft()), tickLength);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        mc = Wrapper.INSTANCE.mc();
    }
}

