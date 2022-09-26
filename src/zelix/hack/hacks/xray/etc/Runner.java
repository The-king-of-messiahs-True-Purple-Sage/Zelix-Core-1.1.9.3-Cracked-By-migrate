/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package zelix.hack.hacks.xray.etc;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.xray.Controller;

public class Runner
implements Runnable {
    boolean isRunning = true;
    public boolean done = false;
    long delay;
    int current;
    int max;
    int radX;
    int radY;
    int radZ;

    public Runner(int radX, int radY, int radZ, long delay) {
        this.max = (radX + radX + 1) * (radY + radY + 1) * (radZ + radZ + 1);
        this.radX = radX;
        this.radY = radY;
        this.radZ = radZ;
        this.delay = delay;
    }

    public double getProcess() {
        return (double)this.current / (double)this.max * 100.0;
    }

    public String getProcessText() {
        return new DecimalFormat("0.00").format(this.getProcess());
    }

    @Override
    public void run() {
        NetHandlerPlayClient conn = Minecraft.getMinecraft().getNetHandler();
        if (conn == null) {
            return;
        }
        assert (Minecraft.getMinecraft().thePlayer != null);
        BlockPos pos = Minecraft.getMinecraft().thePlayer.func_180425_c();
        for (int cx = -this.radX; cx <= this.radX; ++cx) {
            for (int cy = -this.radY; cy <= this.radY; ++cy) {
                for (int cz = -this.radZ; cz <= this.radZ && this.isRunning; ++cz) {
                    ++this.current;
                    BlockPos currblock = new BlockPos(pos.func_177958_n() + cx, pos.func_177956_o() + cy, pos.func_177952_p() + cz);
                    CPacketPlayerDigging packet = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, currblock, EnumFacing.UP);
                    conn.addToSendQueue((Packet)packet);
                    packet = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, currblock, EnumFacing.UP);
                    conn.addToSendQueue((Packet)packet);
                    try {
                        Thread.sleep(this.delay);
                        continue;
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
        }
        Minecraft.getMinecraft().thePlayer.func_146105_b((ITextComponent)new TextComponentString("\u00a76[ \u00a7a\uff01 \u00a76] \u00a7fRefresh done."), true);
        Configuration.freeze = false;
        Controller.requestBlockFinder(true);
        this.done = true;
    }
}

