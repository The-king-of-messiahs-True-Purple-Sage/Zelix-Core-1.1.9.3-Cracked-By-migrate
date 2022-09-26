/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Mouse
 */
package zelix.hack.hacks;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.AimAssist;
import zelix.utils.MoveUtils;
import zelix.utils.TimerUtils;
import zelix.utils.Utils;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class NoSlow
extends Hack {
    TimerUtils timer = new TimerUtils();
    ModeValue mode;
    ModeValue action;
    BooleanValue sendpacket = new BooleanValue("SendPacket", true);

    public NoSlow() {
        super("NoSlow", HackCategory.MOVEMENT);
        this.mode = new ModeValue("Mode", new Mode("AAC1", false), new Mode("AAC2", true), new Mode("HytPit", false), new Mode("HYT 4V4", false));
        this.action = new ModeValue("ActionMode", new Mode("ABORT", false), new Mode("START", true), new Mode("STOP", false), new Mode("RELEASE", false));
        this.addValue(this.sendpacket, this.mode, this.action);
    }

    @Override
    public void onInputUpdate(InputUpdateEvent event) {
        Utils.nullCheck();
        if (Wrapper.INSTANCE.player().func_184587_cr() && !Wrapper.INSTANCE.player().func_184218_aH()) {
            event.getMovementInput().moveStrafe *= 5.0f;
            event.getMovementInput().field_192832_b *= 5.0f;
        }
        if (this.mode.getMode("HYT 4V4").isToggled() && Mouse.isButtonDown((int)1) && AimAssist.mc.thePlayer.func_184586_b(EnumHand.MAIN_HAND).getItem() instanceof ItemSword) {
            AimAssist.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(-1, -1, -1), Minecraft.getMinecraft().objectMouseOver.field_178784_b, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        }
    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!((Boolean)this.sendpacket.getValue()).booleanValue()) {
            return;
        }
        if (this.mode.getMode("AAC1").isToggled()) {
            if (Wrapper.INSTANCE.player().func_184587_cr() && !Wrapper.INSTANCE.player().func_184218_aH() && MoveUtils.isMoving()) {
                if (event.phase == TickEvent.Phase.START) {
                    if (Wrapper.INSTANCE.player().onGround || MoveUtils.isOnGround(0.5)) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    }
                } else if (event.phase == TickEvent.Phase.END && this.timer.delay(65.0f)) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(-1, -1, -1), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    this.timer.reset();
                }
            }
        } else if (this.mode.getMode("AAC2").isToggled()) {
            if (Wrapper.INSTANCE.player().func_184587_cr() && !Wrapper.INSTANCE.player().func_184218_aH() && MoveUtils.isMoving()) {
                if (event.phase == TickEvent.Phase.START) {
                    if (this.action.getMode("ABORT").isToggled()) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    } else if (this.action.getMode("START").isToggled()) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    } else if (this.action.getMode("STOP").isToggled()) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    } else if (this.action.getMode("RELEASE").isToggled()) {
                        Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                    }
                } else if (event.phase == TickEvent.Phase.END) {
                    Wrapper.INSTANCE.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(new BlockPos(-1, -1, -1), EnumFacing.DOWN, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
        } else if (this.mode.getMode("HytPit").isToggled()) {
            // empty if block
        }
        super.onPlayerTick(event);
    }
}

