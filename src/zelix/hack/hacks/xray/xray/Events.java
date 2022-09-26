/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.world.BlockEvent$BreakEvent
 *  net.minecraftforge.event.world.BlockEvent$PlaceEvent
 *  net.minecraftforge.event.world.ChunkEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$Phase
 */
package zelix.hack.hacks.xray.xray;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.etc.RefreshingJob;
import zelix.hack.hacks.xray.xray.AntiAntiXray;
import zelix.hack.hacks.xray.xray.Controller;
import zelix.hack.hacks.xray.xray.Render;
import zelix.hack.hacks.xray.xray.RenderEnqueue;
import zelix.utils.hooks.visual.ChatUtils;

public class Events {
    public BlockPos old;
    public int movedblocks;

    @SubscribeEvent
    public void pickupItem(BlockEvent.BreakEvent event) {
        RenderEnqueue.checkBlock(event.getPos(), event.getState(), false);
    }

    @SubscribeEvent
    public void placeItem(BlockEvent.PlaceEvent event) {
        RenderEnqueue.checkBlock(event.getPos(), event.getState(), true);
    }

    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load event) {
        Controller.requestBlockFinder(true);
    }

    @SubscribeEvent
    public void tickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Controller.requestBlockFinder(false);
        }
        ArrayList<RefreshingJob> nl = new ArrayList<RefreshingJob>();
        AntiAntiXray.jobs.forEach(refreshingJob -> {
            if (!refreshingJob.refresher.done) {
                nl.add((RefreshingJob)refreshingJob);
            }
        });
        AntiAntiXray.jobs = nl;
        if (Configuration.auto) {
            try {
                assert (Minecraft.getMinecraft().thePlayer != null);
                BlockPos pos = Minecraft.getMinecraft().thePlayer.func_180425_c();
                if (pos != this.old) {
                    ++this.movedblocks;
                    if (this.movedblocks > Configuration.movethreshhold && AntiAntiXray.jobs.size() == 0) {
                        AntiAntiXray.revealNewBlocks(Configuration.radius_x, Configuration.radius_y, Configuration.radius_z, Configuration.delay);
                        ChatUtils.message("Scanning new pos: " + pos);
                        this.movedblocks = 0;
                    }
                }
                this.old = pos;
            }
            catch (NullPointerException e) {
                ChatUtils.error("Null Error");
            }
        }
    }

    @SubscribeEvent
    public void onWorldRenderLast(RenderWorldLastEvent event) {
        if (Controller.drawOres()) {
            float f = event.getPartialTicks();
            Render.drawOres((float)XRay.mc.thePlayer.prevPosX + ((float)XRay.mc.thePlayer.posX - (float)XRay.mc.thePlayer.prevPosX) * f, (float)XRay.mc.thePlayer.prevPosY + ((float)XRay.mc.thePlayer.posY - (float)XRay.mc.thePlayer.prevPosY) * f, (float)XRay.mc.thePlayer.prevPosZ + ((float)XRay.mc.thePlayer.posZ - (float)XRay.mc.thePlayer.prevPosZ) * f);
        }
    }
}

