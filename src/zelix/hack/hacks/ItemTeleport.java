/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.util.vector.Vector3f
 */
package zelix.hack.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.BlockUtils;
import zelix.utils.PlayerUtil;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class ItemTeleport
extends Hack {
    public Minecraft mc = Minecraft.getMinecraft();
    private final ModeValue mode;
    private final BooleanValue resetAfterTp = new BooleanValue("ResetAfterTP", true);
    private final ModeValue button;
    private int delay;
    private BlockPos endPos;
    private RayTraceResult objectPosition;

    public ItemTeleport() {
        super("ItemTeleport", HackCategory.PLAYER);
        this.mode = new ModeValue("Mode", new Mode("New", true), new Mode("Old", false));
        this.button = new ModeValue("Button", new Mode("Left", false), new Mode("Right", false), new Mode("Middle", true));
        this.addValue(this.mode, this.button, this.resetAfterTp);
    }

    @Override
    public void onDisable() {
        this.delay = 0;
        this.endPos = null;
        super.onDisable();
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP thePlayer;
        if (Mouse.isButtonDown((int)Arrays.asList(this.button.getModes()).indexOf(this.button.getSelectMode())) && this.delay <= 0) {
            this.objectPosition = this.mc.objectMouseOver;
            this.endPos = this.objectPosition.func_178782_a();
            IBlockState state = BlockUtils.getState(this.endPos);
            if (state.getBlock().func_149688_o(state) == Material.air) {
                this.endPos = null;
                return;
            }
            ChatUtils.message("\u00a77[\u00a78\u00a7lItemTeleport\u00a77] \u00a73Position was set to \u00a78" + this.endPos.func_177958_n() + "\u00a73, \u00a78" + this.endPos.func_177956_o() + "\u00a73, \u00a78" + this.endPos.func_177952_p());
            this.delay = 6;
        }
        if (this.delay > 0) {
            --this.delay;
        }
        if ((thePlayer = this.mc.thePlayer) == null) {
            return;
        }
        if (this.endPos != null && thePlayer.isSneaking()) {
            if (!thePlayer.onGround) {
                double endX = (double)this.endPos.func_177958_n() + 0.5;
                double endY = (double)this.endPos.func_177956_o() + 1.0;
                double endZ = (double)this.endPos.func_177952_p() + 0.5;
                switch (this.mode.getSelectMode().getName().toLowerCase()) {
                    case "old": {
                        for (Vector3f vector3f : this.vanillaTeleportPositions(endX, endY, endZ, 4.0)) {
                            this.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Position((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), false));
                        }
                        break;
                    }
                    case "new": {
                        for (Vector3f vector3f : this.vanillaTeleportPositions(endX, endY, endZ, 5.0)) {
                            this.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Position(thePlayer.posX, thePlayer.posY, thePlayer.posZ, true));
                            this.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Position((double)vector3f.x, (double)vector3f.y, (double)vector3f.z, true));
                            this.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Position(thePlayer.posX, thePlayer.posY, thePlayer.posZ, true));
                            this.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Position(thePlayer.posX, thePlayer.posY + 4.0, thePlayer.posZ, true));
                            this.mc.getNetHandler().addToSendQueue((Packet)new CPacketPlayer.Position((double)vector3f.x, (double)vector3f.y, (double)vector3f.z, true));
                            PlayerUtil.forward(0.04);
                        }
                        break;
                    }
                }
                if (((Boolean)this.resetAfterTp.getValue()).booleanValue()) {
                    this.endPos = null;
                }
                ChatUtils.message("\u00a77[\u00a78\u00a7lItemTeleport\u00a77] \u00a73Tried to collect items");
            } else {
                thePlayer.jump();
            }
        }
    }

    private List<Vector3f> vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
        double d;
        ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
        double posX = tpX - this.mc.thePlayer.posX;
        double posZ = tpZ - this.mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI - 90.0);
        double tmpY = this.mc.thePlayer.posY;
        double steps = 1.0;
        for (d = speed; d < this.getDistance(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            steps += 1.0;
        }
        for (d = speed; d < this.getDistance(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
            double tmpX = this.mc.thePlayer.posX - Math.sin(Math.toRadians(yaw)) * d;
            double tmpZ = this.mc.thePlayer.posZ + Math.cos(Math.toRadians(yaw)) * d;
            positions.add(new Vector3f((float)tmpX, (float)(tmpY -= (this.mc.thePlayer.posY - tpY) / steps), (float)tmpZ));
        }
        positions.add(new Vector3f((float)tpX, (float)tpY, (float)tpZ));
        return positions;
    }

    private double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }
}

