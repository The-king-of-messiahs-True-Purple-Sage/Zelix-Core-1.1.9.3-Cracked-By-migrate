/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.storage.ExtendedBlockStorage
 */
package zelix.hack.hacks.xray.xray;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import zelix.hack.hacks.xray.Configuration;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.reference.block.BlockData;
import zelix.hack.hacks.xray.reference.block.BlockInfo;
import zelix.hack.hacks.xray.utils.WorldRegion;
import zelix.hack.hacks.xray.xray.Controller;
import zelix.hack.hacks.xray.xray.Render;

public class RenderEnqueue
implements Runnable {
    private final WorldRegion box;

    public RenderEnqueue(WorldRegion region) {
        this.box = region;
    }

    @Override
    public void run() {
        if (!Configuration.freeze) {
            this.blockFinder2();
        }
    }

    private void blockFinder2() {
        HashMap<String, BlockData> blocks = Controller.getBlockStore().getStore();
        if (blocks.isEmpty()) {
            if (!Render.ores.isEmpty()) {
                Render.ores.clear();
            }
            return;
        }
        WorldClient world = XRay.mc.theWorld;
        ArrayList<BlockInfo> renderQueue = new ArrayList<BlockInfo>();
        BlockPos pos = XRay.mc.thePlayer.func_180425_c();
        int radX = Configuration.radius_x;
        int radY = Configuration.radius_y;
        int radZ = Configuration.radius_z;
        for (int cx = -radX; cx <= radX; ++cx) {
            for (int cy = -radY; cy <= radY; ++cy) {
                for (int cz = -radZ; cz <= radZ; ++cz) {
                    BlockData blockData;
                    BlockPos currblock = new BlockPos(pos.func_177958_n() + cx, pos.func_177956_o() + cy, pos.func_177952_p() + cz);
                    IBlockState currentState = Minecraft.getMinecraft().thePlayer.worldObj.func_180495_p(currblock);
                    if (Controller.blackList.contains(currentState.getBlock())) continue;
                    IBlockState defaultState = currentState.getBlock().getDefaultState();
                    boolean defaultExists = blocks.containsKey(defaultState.toString());
                    boolean currentExists = blocks.containsKey(currentState.toString());
                    if (!defaultExists && !currentExists || (blockData = blocks.get(currentExists ? currentState.toString() : defaultState.toString())) == null || !blockData.isDrawing()) continue;
                    double alpha = !Configuration.shouldFade ? 255.0 : Math.max(0.0, ((double)Controller.getRadius() - XRay.mc.thePlayer.getDistance((double)currblock.func_177958_n(), (double)currblock.func_177956_o(), (double)currblock.func_177952_p())) / (double)Controller.getRadius() * 255.0);
                    renderQueue.add(new BlockInfo(currblock.func_177958_n(), currblock.func_177956_o(), currblock.func_177952_p(), blockData.getColor().getColor(), alpha));
                }
            }
        }
        renderQueue.sort((t, t1) -> Double.compare(t1.func_177951_i((Vec3i)pos), t.func_177951_i((Vec3i)pos)));
        Render.ores.clear();
        Render.ores.addAll(renderQueue);
    }

    private void blockFinder() {
        HashMap<String, BlockData> blocks = Controller.getBlockStore().getStore();
        if (blocks.isEmpty()) {
            if (!Render.ores.isEmpty()) {
                Render.ores.clear();
            }
            return;
        }
        WorldClient world = XRay.mc.theWorld;
        ArrayList<BlockInfo> renderQueue = new ArrayList<BlockInfo>();
        for (int chunkX = this.box.minChunkX; chunkX <= this.box.maxChunkX; ++chunkX) {
            int x = chunkX << 4;
            int lowBoundX = x < this.box.minX ? this.box.minX - x : 0;
            int highBoundX = x + 15 > this.box.maxX ? this.box.maxX - x : 15;
            for (int chunkZ = this.box.minChunkZ; chunkZ <= this.box.maxChunkZ; ++chunkZ) {
                Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
                if (!chunk.isLoaded()) continue;
                ExtendedBlockStorage[] extendsList = chunk.getBlockStorageArray();
                int z = chunkZ << 4;
                int lowBoundZ = z < this.box.minZ ? this.box.minZ - z : 0;
                int highBoundZ = z + 15 > this.box.maxZ ? this.box.maxZ - z : 15;
                for (int curExtend = this.box.minChunkY; curExtend <= this.box.maxChunkY; ++curExtend) {
                    ExtendedBlockStorage ebs = extendsList[curExtend];
                    if (ebs == null) continue;
                    int y = curExtend << 4;
                    int lowBoundY = y < this.box.minY ? this.box.minY - y : 0;
                    int highBoundY = y + 15 > this.box.maxY ? this.box.maxY - y : 15;
                    for (int i = lowBoundX; i <= highBoundX; ++i) {
                        for (int j = lowBoundY; j <= highBoundY; ++j) {
                            for (int k = lowBoundZ; k <= highBoundZ; ++k) {
                                BlockData blockData;
                                IBlockState currentState = ebs.get(i, j, k);
                                if (Controller.blackList.contains(currentState.getBlock())) continue;
                                IBlockState defaultState = currentState.getBlock().getDefaultState();
                                boolean defaultExists = blocks.containsKey(defaultState.toString());
                                boolean currentExists = blocks.containsKey(currentState.toString());
                                if (!defaultExists && !currentExists || (blockData = blocks.get(currentExists ? currentState.toString() : defaultState.toString())) == null || !blockData.isDrawing()) continue;
                                double alpha = !Configuration.shouldFade ? 255.0 : Math.max(0.0, ((double)Controller.getRadius() - XRay.mc.thePlayer.getDistance((double)(x + i), (double)(y + j), (double)(z + k))) / (double)Controller.getRadius() * 255.0);
                                renderQueue.add(new BlockInfo(x + i, y + j, z + k, blockData.getColor().getColor(), alpha));
                            }
                        }
                    }
                }
            }
        }
        BlockPos playerPos = XRay.mc.thePlayer.func_180425_c();
        renderQueue.sort((t, t1) -> Double.compare(t1.func_177951_i((Vec3i)playerPos), t.func_177951_i((Vec3i)playerPos)));
        Render.ores.clear();
        Render.ores.addAll(renderQueue);
    }

    public static void checkBlock(BlockPos pos, IBlockState state, boolean add) {
        if (!Controller.drawOres() || Controller.getBlockStore().getStore().isEmpty()) {
            return;
        }
        String defaultState = state.getBlock().getDefaultState().toString();
        if (Controller.getBlockStore().getStore().containsKey(defaultState)) {
            if (!add) {
                Render.ores.remove((Object)new BlockInfo((Vec3i)pos, null, 0.0));
                return;
            }
            BlockData data = null;
            if (Controller.getBlockStore().getStore().containsKey(defaultState)) {
                data = Controller.getBlockStore().getStore().get(defaultState);
            }
            if (data == null) {
                return;
            }
            double alpha = !Configuration.shouldFade ? 255.0 : Math.max(0.0, ((double)Controller.getRadius() - XRay.mc.thePlayer.getDistance((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p())) / (double)Controller.getRadius() * 255.0);
            Render.ores.add(new BlockInfo((Vec3i)pos, data.getColor().getColor(), alpha));
        }
    }
}

