/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.ReflectionHelper
 */
package zelix.hack.hacks;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class Velocity
extends Hack {
    public ModeValue mode;
    private boolean ishurt;
    private TimerUtils velocitytimer = new TimerUtils();
    private float velocityTick = 0.0f;
    public static Boolean velocityInput = false;
    Minecraft mc = Wrapper.INSTANCE.mc();
    NumberValue velocityTickValue;

    public Velocity() {
        super("Velocity", HackCategory.COMBAT);
        this.mode = new ModeValue("Mode", new Mode("AAC", false), new Mode("Simple", true), new Mode("AAC4", false), new Mode("Tick", false));
        this.velocityTickValue = new NumberValue("Tick", 10.0, 0.0, 10.0);
        this.addValue(this.mode, this.velocityTickValue);
    }

    @Override
    public String getDescription() {
        return "Prevents you from getting pushed by players, mobs and flowing water.";
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("AAC").isToggled()) {
            EntityPlayerSP player = Wrapper.INSTANCE.player();
            if (player.hurtTime > 0 && player.hurtTime <= 6) {
                player.motionX *= 0.5;
                player.motionZ *= 0.5;
                player.motionY *= 0.96;
            }
            if (player.hurtTime > 0 && player.hurtTime < 6) {
                player.motionX = 0.0;
                player.motionZ = 0.0;
            }
        } else if (this.mode.getMode("AAC4").isToggled()) {
            if (Wrapper.INSTANCE.player().isInWater() || Wrapper.INSTANCE.player().isInLava()) {
                return;
            }
            if (!Wrapper.INSTANCE.player().onGround) {
                if (this.ishurt) {
                    Field field = ReflectionHelper.findField(EntityPlayer.class, (String[])new String[]{"speedInAir", "field_71102_ce"});
                    try {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.setFloat(Wrapper.INSTANCE.player(), 0.02f);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    Wrapper.INSTANCE.player().motionX *= 0.6;
                    Wrapper.INSTANCE.player().motionZ *= 0.6;
                }
            } else if (this.velocitytimer.hasReached(80.0f)) {
                this.ishurt = false;
                Field field = ReflectionHelper.findField(EntityPlayer.class, (String[])new String[]{"speedInAir", "field_71102_ce"});
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.setFloat(Wrapper.INSTANCE.player(), 0.02f);
                }
                catch (Exception exception) {}
            }
        } else if (this.mode.getMode("Tick").isToggled()) {
            if (this.velocityTick > ((Double)this.velocityTickValue.getValue()).floatValue()) {
                if (this.mc.thePlayer.motionY > 0.0) {
                    this.mc.thePlayer.motionY = 0.0;
                }
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.jumpMovementFactor = -1.0E-5f;
                velocityInput = false;
            }
            if (this.mc.thePlayer.onGround && this.velocityTick > 1.0f) {
                velocityInput = false;
            }
        }
        super.onClientTick(event);
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        SPacketEntityVelocity p;
        if (packet instanceof SPacketEntityVelocity && this.mode.getMode("Simple").isToggled() && Wrapper.INSTANCE.player().hurtTime >= 0) {
            SPacketEntityVelocity p2 = (SPacketEntityVelocity)packet;
            if (p2.func_149412_c() == Wrapper.INSTANCE.player().getEntityId()) {
                return false;
            }
        } else if (packet instanceof SPacketEntityVelocity && this.mode.getMode("AAC4").isToggled() && Wrapper.INSTANCE.player().hurtTime >= 0 && (p = (SPacketEntityVelocity)packet).func_149412_c() == Wrapper.INSTANCE.player().getEntityId()) {
            this.ishurt = true;
            this.velocitytimer.reset();
        }
        return true;
    }
}

