/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockCactus
 *  net.minecraft.block.BlockChest
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockGlass
 *  net.minecraft.block.BlockPane
 *  net.minecraft.block.BlockPistonBase
 *  net.minecraft.block.BlockPistonExtension
 *  net.minecraft.block.BlockPistonMoving
 *  net.minecraft.block.BlockSkull
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.BlockStainedGlass
 *  net.minecraft.block.BlockStairs
 *  net.minecraft.block.BlockTrapDoor
 *  net.minecraft.block.BlockWall
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package zelix.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AStarCustomPathFinder {
    private Vec3d startVec3;
    private Vec3d endVec3;
    private ArrayList<Vec3d> path = new ArrayList();
    private ArrayList<Hub> hubs = new ArrayList();
    private ArrayList<Hub> hubsToWork = new ArrayList();
    private double minDistanceSquared = 9.0;
    private boolean nearest = true;
    private static Vec3d[] flatCardinalDirections = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0)};

    public AStarCustomPathFinder(Vec3d startVec3, Vec3d endVec3) {
        this.startVec3 = startVec3.func_72441_c(0.0, 0.0, 0.0);
        this.endVec3 = endVec3.func_72441_c(0.0, 0.0, 0.0);
    }

    public ArrayList<Vec3d> getPath() {
        return this.path;
    }

    public void compute() {
        this.compute(1000, 4);
    }

    public void compute(int loops, int depth) {
        this.path.clear();
        this.hubsToWork.clear();
        ArrayList<Vec3d> initPath = new ArrayList<Vec3d>();
        initPath.add(this.startVec3);
        this.hubsToWork.add(new Hub(this.startVec3, null, initPath, this.startVec3.func_72436_e(this.endVec3), 0.0, 0.0));
        block0: for (int i = 0; i < loops; ++i) {
            Collections.sort(this.hubsToWork, new CompareHub());
            int j = 0;
            if (this.hubsToWork.size() == 0) break;
            for (Hub hub : new ArrayList<Hub>(this.hubsToWork)) {
                Vec3d loc2;
                if (++j > depth) continue block0;
                this.hubsToWork.remove(hub);
                this.hubs.add(hub);
                for (Vec3d direction : flatCardinalDirections) {
                    Vec3d loc = hub.getLoc().func_178787_e(direction);
                    if (AStarCustomPathFinder.checkPositionValidity(loc, false) && this.addHub(hub, loc, 0.0)) break block0;
                }
                Vec3d loc1 = hub.getLoc().func_72441_c(0.0, 1.0, 0.0);
                if ((!AStarCustomPathFinder.checkPositionValidity(loc1, false) || !this.addHub(hub, loc1, 0.0)) && (!AStarCustomPathFinder.checkPositionValidity(loc2 = hub.getLoc().func_72441_c(0.0, -1.0, 0.0), false) || !this.addHub(hub, loc2, 0.0))) continue;
                break block0;
            }
        }
        if (this.nearest) {
            Collections.sort(this.hubs, new CompareHub());
            this.path = this.hubs.get(0).getPath();
        }
    }

    public static boolean checkPositionValidity(Vec3d loc, boolean checkGround) {
        return AStarCustomPathFinder.checkPositionValidity((int)loc.field_72450_a, (int)loc.field_72448_b, (int)loc.field_72449_c, checkGround);
    }

    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        return !AStarCustomPathFinder.isBlockSolid(block1) && !AStarCustomPathFinder.isBlockSolid(block2) && (AStarCustomPathFinder.isBlockSolid(block3) || !checkGround) && AStarCustomPathFinder.isSafeToWalkOn(block3);
    }

    private static boolean isBlockSolid(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.func_180495_p(block).func_185915_l() || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockSlab || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockStairs || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockCactus || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockChest || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockEnderChest || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockSkull || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockPane || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockFence || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockWall || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockGlass || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockPistonBase || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockPistonExtension || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockPistonMoving || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockStainedGlass || Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockTrapDoor;
    }

    private static boolean isSafeToWalkOn(BlockPos block) {
        return !(Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockFence) && !(Minecraft.getMinecraft().theWorld.func_180495_p(block) instanceof BlockWall);
    }

    public Hub isHubExisting(Vec3d loc) {
        for (Hub hub : this.hubs) {
            if (hub.getLoc().field_72450_a != loc.field_72450_a || hub.getLoc().field_72448_b != loc.field_72448_b || hub.getLoc().field_72449_c != loc.field_72449_c) continue;
            return hub;
        }
        for (Hub hub : this.hubsToWork) {
            if (hub.getLoc().field_72450_a != loc.field_72450_a || hub.getLoc().field_72448_b != loc.field_72448_b || hub.getLoc().field_72449_c != loc.field_72449_c) continue;
            return hub;
        }
        return null;
    }

    public boolean addHub(Hub parent, Vec3d loc, double cost) {
        Hub existingHub = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if (loc.field_72450_a == this.endVec3.field_72450_a && loc.field_72448_b == this.endVec3.field_72448_b && loc.field_72449_c == this.endVec3.field_72449_c || this.minDistanceSquared != 0.0 && loc.func_72436_e(this.endVec3) <= this.minDistanceSquared) {
                this.path.clear();
                this.path = parent.getPath();
                this.path.add(loc);
                return true;
            }
            ArrayList<Vec3d> path = new ArrayList<Vec3d>(parent.getPath());
            path.add(loc);
            this.hubsToWork.add(new Hub(loc, parent, path, loc.func_72436_e(this.endVec3), cost, totalCost));
        } else if (existingHub.getCost() > cost) {
            ArrayList<Vec3d> path = new ArrayList<Vec3d>(parent.getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path);
            existingHub.setSquareDistanceToFromTarget(loc.func_72436_e(this.endVec3));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
        }
        return false;
    }

    public class CompareHub
    implements Comparator<Hub> {
        @Override
        public int compare(Hub o1, Hub o2) {
            return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
        }
    }

    private class Hub {
        private Vec3d loc = null;
        private Hub parent = null;
        private ArrayList<Vec3d> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;

        public Hub(Vec3d loc, Hub parent, ArrayList<Vec3d> path, double squareDistanceToFromTarget, double cost, double totalCost) {
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        public Vec3d getLoc() {
            return this.loc;
        }

        public Hub getParent() {
            return this.parent;
        }

        public ArrayList<Vec3d> getPath() {
            return this.path;
        }

        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }

        public double getCost() {
            return this.cost;
        }

        public void setLoc(Vec3d loc) {
            this.loc = loc;
        }

        public void setParent(Hub parent) {
            this.parent = parent;
        }

        public void setPath(ArrayList<Vec3d> path) {
            this.path = path;
        }

        public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getTotalCost() {
            return this.totalCost;
        }

        public void setTotalCost(double totalCost) {
            this.totalCost = totalCost;
        }
    }
}

