/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package zelix.hack.hacks.xray.xray;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.store.BlockStore;
import zelix.hack.hacks.xray.utils.WorldRegion;
import zelix.hack.hacks.xray.xray.Render;
import zelix.hack.hacks.xray.xray.RenderEnqueue;

public class Controller {
    private static final int[] distanceList = new int[]{8, 16, 32, 48, 64, 80, 128, 256};
    public static ArrayList blackList = new ArrayList<Block>(){
        {
            this.add(Blocks.air);
            this.add(Blocks.bedrock);
            this.add(Blocks.stone);
            this.add(Blocks.grass);
            this.add(Blocks.dirt);
        }
    };
    private static Vec3i lastPlayerPos = null;
    private static BlockStore blockStore = new BlockStore();
    private static Future task;
    private static ExecutorService executor;
    private static boolean drawOres;

    public static BlockStore getBlockStore() {
        return blockStore;
    }

    public static boolean drawOres() {
        return drawOres && XRay.mc.theWorld != null && XRay.mc.thePlayer != null;
    }

    public static void toggleDrawOres() {
        if (!drawOres) {
            Render.ores.clear();
            executor = Executors.newSingleThreadExecutor();
            drawOres = true;
            Controller.requestBlockFinder(true);
            if (!Configuration.showOverlay) {
                XRay.mc.thePlayer.func_145747_a((ITextComponent)new TextComponentString(I18n.format((String)"xray.toggle.activated", (Object[])new Object[0])));
            }
        } else {
            if (!Configuration.showOverlay) {
                XRay.mc.thePlayer.func_145747_a((ITextComponent)new TextComponentString(I18n.format((String)"xray.toggle.deactivated", (Object[])new Object[0])));
            }
            Controller.shutdownExecutor();
        }
    }

    public static int getRadius() {
        return distanceList[Configuration.radius];
    }

    public static void incrementCurrentDist() {
        Configuration.radius = Configuration.radius < distanceList.length - 1 ? ++Configuration.radius : 0;
    }

    public static void decrementCurrentDist() {
        Configuration.radius = Configuration.radius > 0 ? --Configuration.radius : distanceList.length - 1;
    }

    private static boolean playerHasMoved() {
        return lastPlayerPos == null || lastPlayerPos.func_177958_n() != XRay.mc.thePlayer.func_180425_c().func_177958_n() || lastPlayerPos.func_177952_p() != XRay.mc.thePlayer.func_180425_c().func_177952_p();
    }

    private static void updatePlayerPosition() {
        lastPlayerPos = XRay.mc.thePlayer.func_180425_c();
    }

    public static synchronized void requestBlockFinder(boolean force) {
        if (Controller.drawOres() && (task == null || task.isDone()) && (force || Controller.playerHasMoved())) {
            Controller.updatePlayerPosition();
            WorldRegion region = new WorldRegion(lastPlayerPos, Controller.getRadius());
            task = executor.submit(new RenderEnqueue(region));
        }
    }

    public static void shutdownExecutor() {
        drawOres = false;
        try {
            executor.shutdownNow();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    static {
        drawOres = false;
    }
}

