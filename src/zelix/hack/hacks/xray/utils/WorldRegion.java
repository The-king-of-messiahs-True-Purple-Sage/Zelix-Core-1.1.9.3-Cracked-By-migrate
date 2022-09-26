/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec3i
 */
package zelix.hack.hacks.xray.utils;

import net.minecraft.util.math.Vec3i;

public class WorldRegion {
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;
    public int minChunkX;
    public int minChunkY;
    public int minChunkZ;
    public int maxChunkX;
    public int maxChunkY;
    public int maxChunkZ;

    public WorldRegion(Vec3i pos, int radius) {
        this.minX = pos.func_177958_n() - radius;
        this.maxX = pos.func_177958_n() + radius;
        this.minY = Math.max(0, pos.func_177956_o() - 92);
        this.maxY = Math.min(255, pos.func_177956_o() + 32);
        this.minZ = pos.func_177952_p() - radius;
        this.maxZ = pos.func_177952_p() + radius;
        this.minChunkX = this.minX >> 4;
        this.maxChunkX = this.maxX >> 4;
        this.minChunkY = this.minY >> 4;
        this.maxChunkY = this.maxY >> 4;
        this.minChunkZ = this.minZ >> 4;
        this.maxChunkZ = this.maxZ >> 4;
    }
}

