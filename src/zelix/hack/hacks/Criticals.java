/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.world.World
 */
package zelix.hack.hacks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.TimerUtils;
import zelix.utils.Wrapper;
import zelix.utils.system.Connection;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;

public class Criticals
extends Hack {
    public ModeValue mode;
    NumberValue delayValue;
    NumberValue hurttime;
    TimerUtils timer = new TimerUtils();
    boolean cancelSomePackets;
    static double attacks = 0.0;
    Minecraft mc = Wrapper.INSTANCE.mc();
    static boolean antiDesync = false;

    public Criticals() {
        super("Criticals", HackCategory.COMBAT);
        this.mode = new ModeValue("Mode", new Mode("Hyt", true), new Mode("Hyt1", false), new Mode("Packet", false), new Mode("Jump", false), new Mode("PJump", false), new Mode("NoGround", false), new Mode("C08P", false), new Mode("FunkNight", false), new Mode("Vulcan", false), new Mode("HytPacket", false), new Mode("HytPit", false));
        this.delayValue = new NumberValue("Delay", 0.0, 0.0, 1000.0);
        this.hurttime = new NumberValue("HurtTime", 10.0, 0.0, 10.0);
        this.addValue(this.mode, this.delayValue, this.hurttime);
    }

    @Override
    public String getDescription() {
        return "Changes all your hits to critical hits.";
    }

    @Override
    public boolean onPacket(Object packet, Connection.Side side) {
        if (Wrapper.INSTANCE.player().onGround && side == Connection.Side.OUT) {
            if (packet instanceof CPacketUseEntity) {
                CPacketUseEntity attack = (CPacketUseEntity)packet;
                if (attack.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
                    if (this.mode.getMode("Packet").isToggled()) {
                        if (Wrapper.INSTANCE.player().isCollidedVertically && this.timer.isDelay(500L)) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.0627, Wrapper.INSTANCE.player().posZ, false));
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, false));
                            Entity entity = attack.func_149564_a((World)Wrapper.INSTANCE.world());
                            if (entity != null) {
                                Wrapper.INSTANCE.player().onCriticalHit(entity);
                            }
                            this.timer.setLastMS();
                            this.cancelSomePackets = true;
                        }
                    } else if (this.mode.getMode("Hyt1").isToggled()) {
                        Entity entity = attack.func_149564_a((World)Wrapper.INSTANCE.world());
                        if (entity != null && Wrapper.INSTANCE.player().onGround) {
                            Wrapper.INSTANCE.player().onCriticalHit(entity);
                        }
                    } else if (this.mode.getMode("Hyt").isToggled()) {
                        Entity entity = attack.func_149564_a((World)Wrapper.INSTANCE.world());
                        if (entity != null && Wrapper.INSTANCE.player().onGround) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.0031311231111, Wrapper.INSTANCE.player().posZ, false));
                            Wrapper.INSTANCE.player().onCriticalHit(entity);
                        }
                    } else if (this.mode.getMode("Jump").isToggled()) {
                        if (this.canJump()) {
                            Wrapper.INSTANCE.player().jump();
                        }
                    } else if (this.mode.getMode("PJump").isToggled()) {
                        if (this.canJump()) {
                            Wrapper.INSTANCE.player().jump();
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.0031311231111, Wrapper.INSTANCE.player().posZ, false));
                        }
                    } else if (this.mode.getMode("NoGround").isToggled()) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer(false));
                    } else if (this.mode.getMode("C08P").isToggled()) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.05250000001304, Wrapper.INSTANCE.player().posZ, true));
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.01400000001304, Wrapper.INSTANCE.player().posZ, false));
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.00150000001304, Wrapper.INSTANCE.player().posZ, false));
                    } else if (this.mode.getMode("FunkNight").isToggled()) {
                        if ((double)Wrapper.INSTANCE.player().hurtTime > (Double)this.hurttime.getValue() && this.timer.hasReached(((Double)this.delayValue.getValue()).floatValue())) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.051400040018, Wrapper.INSTANCE.player().posZ, true));
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.0015000018, Wrapper.INSTANCE.player().posZ, false));
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.01400000018, Wrapper.INSTANCE.player().posZ, false));
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.0015300600018, Wrapper.INSTANCE.player().posZ, false));
                            this.timer.reset();
                        }
                    } else if (this.mode.getMode("HytPacket").isToggled()) {
                        Entity entity = attack.func_149564_a((World)Wrapper.INSTANCE.world());
                        double x = this.mc.thePlayer.posX;
                        double y = this.mc.thePlayer.posY;
                        double z = this.mc.thePlayer.posZ;
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.11, z, true));
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.1100013579, z, false));
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y + 1.3579E-6, z, false));
                        this.mc.thePlayer.onCriticalHit(entity);
                    } else if (this.mode.getMode("HytPit").isToggled()) {
                        double x = this.mc.thePlayer.posX;
                        double y = this.mc.thePlayer.posY;
                        double z = this.mc.thePlayer.posZ;
                        if ((attacks += 1.0) > 0.0) {
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.2, z, false));
                            Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayer.Position(x, y + 0.1216, z, false));
                            attacks = 0.0;
                        } else {
                            antiDesync = false;
                        }
                    }
                }
            } else if (this.mode.getMode("Packet").isToggled() && packet instanceof CPacketPlayer && this.cancelSomePackets) {
                this.cancelSomePackets = false;
                return false;
            }
        }
        return true;
    }

    boolean canJump() {
        if (Wrapper.INSTANCE.player().isOnLadder()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isInWater()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isInLava()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().isSneaking()) {
            return false;
        }
        if (Wrapper.INSTANCE.player().func_184218_aH()) {
            return false;
        }
        return !Wrapper.INSTANCE.player().isPotionActive(MobEffects.field_76440_q);
    }
}

