/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockAir
 *  net.minecraft.block.BlockCactus
 *  net.minecraft.block.BlockChest
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockFence
 *  net.minecraft.block.BlockGlass
 *  net.minecraft.block.BlockGravel
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockPane
 *  net.minecraft.block.BlockPistonBase
 *  net.minecraft.block.BlockPistonExtension
 *  net.minecraft.block.BlockPistonMoving
 *  net.minecraft.block.BlockSand
 *  net.minecraft.block.BlockSkull
 *  net.minecraft.block.BlockSlab
 *  net.minecraft.block.BlockStainedGlass
 *  net.minecraft.block.BlockStairs
 *  net.minecraft.block.BlockTrapDoor
 *  net.minecraft.block.BlockWall
 *  net.minecraft.util.math.BlockPos
 */
package zelix.hack.hacks.automine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.util.math.BlockPos;
import zelix.hack.hacks.automine.AutoMine;
import zelix.hack.hacks.automine.BlockUtils;
import zelix.hack.hacks.automine.Vec3;

public class AutoMinePathFinder {
    private static Vec3 startVec3;
    private static Vec3 endVec3;
    private ArrayList<Vec3> path = new ArrayList();
    private ArrayList<Hub> hubs = new ArrayList();
    private ArrayList<Hub> hubsToWork = new ArrayList();
    private double minDistanceSquared = 1.0E-4;
    private boolean nearest = true;
    private static boolean doIt;
    private static Vec3[] flatCardinalDirections;

    public AutoMinePathFinder(Vec3 startVec3, Vec3 endVec3) {
        AutoMinePathFinder.startVec3 = startVec3.addVector(0.0, 0.0, 0.0).floor();
        AutoMinePathFinder.endVec3 = endVec3.addVector(0.0, 0.0, 0.0).floor();
    }

    public ArrayList<Vec3> getPath() {
        return this.path;
    }

    public void compute() {
        this.compute(1000, 13);
    }

    public void compute(int loops, int depth) {
        this.path.clear();
        this.hubsToWork.clear();
        ArrayList<Vec3> initPath = new ArrayList<Vec3>();
        initPath.add(startVec3);
        this.hubsToWork.add(new Hub(startVec3, null, initPath, startVec3.squareDistanceTo(endVec3), 0.0, 0.0));
        block0: for (int i = 0; i < loops; ++i) {
            Collections.sort(this.hubsToWork, new CompareHub());
            int j = 0;
            if (this.hubsToWork.size() == 0) break;
            for (Hub hub : new ArrayList<Hub>(this.hubsToWork)) {
                Vec3 loc2;
                if (++j > depth) continue block0;
                this.hubsToWork.remove(hub);
                this.hubs.add(hub);
                for (Vec3 direction : flatCardinalDirections) {
                    Vec3 loc = hub.getLoc().add(direction).floor();
                    if (AutoMinePathFinder.checkPositionValidity(loc, true) && this.addHub(hub, loc, 0.0)) break block0;
                }
                Vec3 loc1 = hub.getLoc().addVector(0.0, 1.0, 0.0).floor();
                if ((!AutoMinePathFinder.checkPositionValidity(loc1, true) || !this.addHub(hub, loc1, 0.0)) && (!AutoMinePathFinder.checkPositionValidity(loc2 = hub.getLoc().addVector(0.0, -1.0, 0.0).floor(), true) || !this.addHub(hub, loc2, 0.0))) continue;
                break block0;
            }
        }
        if (this.nearest) {
            Collections.sort(this.hubs, new CompareHub());
            this.path = this.hubs.get(0).getPath();
        }
    }

    public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
        return AutoMinePathFinder.checkPositionValidity((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), checkGround);
    }

    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        for (int x1 = x - 1; x1 <= x + 1; ++x1) {
            for (int y1 = y; y1 <= y + 2; ++y1) {
                for (int z1 = z - 1; z1 <= z + 1; ++z1) {
                    if (!(BlockUtils.getBlock(new BlockPos(x1, y1, z1)) instanceof BlockLiquid)) continue;
                    return false;
                }
            }
        }
        Block blockUp = BlockUtils.getBlock(new BlockPos(x, y + 2, z));
        if (blockUp instanceof BlockGravel || blockUp instanceof BlockSand) {
            return false;
        }
        if (AutoMine.tryTwo) {
            return !(!(BlockUtils.getBlock(block1) instanceof BlockAir) && !AutoMinePathFinder.isBlockSolid(block1) || BlockUtils.getBlock(block1) == Block.getBlockById((int)7) || !(BlockUtils.getBlock(block2) instanceof BlockAir) && !AutoMinePathFinder.isBlockSolid(block2) || BlockUtils.getBlock(block2) == Block.getBlockById((int)7) || !AutoMinePathFinder.isBlockSolid(block3) && checkGround || !AutoMinePathFinder.isSafeToThrough(block3) || !AutoMinePathFinder.isSafeToThrough(block2) || !AutoMinePathFinder.isSafeToThrough(block1));
        }
        return !AutoMinePathFinder.isBlockSolid(block1) && BlockUtils.getBlock(block1) != Block.getBlockById((int)7) && !AutoMinePathFinder.isBlockSolid(block2) && BlockUtils.getBlock(block2) != Block.getBlockById((int)7) && (AutoMinePathFinder.isBlockSolid(block3) || !checkGround) && AutoMinePathFinder.isSafeToThrough(block3) && AutoMinePathFinder.isSafeToThrough(block2) && AutoMinePathFinder.isSafeToThrough(block1);
    }

    public static boolean isBlockSolid(BlockPos block) {
        return BlockUtils.getBlock(block).func_149637_q(zelix.utils.BlockUtils.getState(block)) || BlockUtils.getBlock(block) instanceof BlockSlab || BlockUtils.getBlock(block) instanceof BlockStairs || BlockUtils.getBlock(block) instanceof BlockCactus || BlockUtils.getBlock(block) instanceof BlockChest || BlockUtils.getBlock(block) instanceof BlockEnderChest || BlockUtils.getBlock(block) instanceof BlockSkull || BlockUtils.getBlock(block) instanceof BlockPane || BlockUtils.getBlock(block) instanceof BlockFence || BlockUtils.getBlock(block) instanceof BlockWall || BlockUtils.getBlock(block) instanceof BlockGlass || BlockUtils.getBlock(block) instanceof BlockPistonBase || BlockUtils.getBlock(block) instanceof BlockPistonExtension || BlockUtils.getBlock(block) instanceof BlockPistonMoving || BlockUtils.getBlock(block) instanceof BlockStainedGlass || BlockUtils.getBlock(block) instanceof BlockTrapDoor;
    }

    public static boolean isSafeToThrough(BlockPos block) {
        return !(BlockUtils.getBlock(block) instanceof BlockFence) && !(BlockUtils.getBlock(block) instanceof BlockWall) && !(BlockUtils.getBlock(block) instanceof BlockLiquid);
    }

    public static boolean isSafeToWalkOn(BlockPos block) {
        return !(BlockUtils.getBlock(block) instanceof BlockLiquid) && !(BlockUtils.getBlock(block) instanceof BlockAir);
    }

    public Hub isHubExisting(Vec3 loc) {
        for (Hub hub : this.hubs) {
            if (hub.getLoc().getX() != loc.getX() || hub.getLoc().getY() != loc.getY() || hub.getLoc().getZ() != loc.getZ()) continue;
            return hub;
        }
        for (Hub hub : this.hubsToWork) {
            if (hub.getLoc().getX() != loc.getX() || hub.getLoc().getY() != loc.getY() || hub.getLoc().getZ() != loc.getZ()) continue;
            return hub;
        }
        return null;
    }

    public boolean addHub(Hub parent, Vec3 loc, double cost) {
        Hub existingHub = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if (loc.getX() == endVec3.getX() && loc.getY() == endVec3.getY() && loc.getZ() == endVec3.getZ()) {
                this.path.clear();
                this.path = parent.getPath();
                this.path.add(loc);
                return true;
            }
            ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            this.hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(endVec3), cost, totalCost));
        } else if (existingHub.getCost() > cost) {
            ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            existingHub.setLoc(loc);
            existingHub.setParent(parent);
            existingHub.setPath(path);
            existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(endVec3));
            existingHub.setCost(cost);
            existingHub.setTotalCost(totalCost);
        }
        return false;
    }

    static {
        doIt = false;
        flatCardinalDirections = new Vec3[]{new Vec3(1.0, 0.0, 0.0), new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 0.0, -1.0)};
    }

    public class CompareHub
    implements Comparator<Hub> {
        @Override
        public int compare(Hub o1, Hub o2) {
            return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
        }
    }

    private class Hub {
        private Vec3 loc = null;
        private Hub parent = null;
        private ArrayList<Vec3> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;

        public Hub(Vec3 loc, Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
            this.loc = loc;
            this.parent = parent;
            this.path = path;
            this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            this.cost = cost;
            this.totalCost = totalCost;
        }

        public Vec3 getLoc() {
            return this.loc;
        }

        public Hub getParent() {
            return this.parent;
        }

        public ArrayList<Vec3> getPath() {
            return this.path;
        }

        public double getSquareDistanceToFromTarget() {
            return this.squareDistanceToFromTarget;
        }

        public double getCost() {
            return this.cost;
        }

        public void setLoc(Vec3 loc) {
            this.loc = loc;
        }

        public void setParent(Hub parent) {
            this.parent = parent;
        }

        public void setPath(ArrayList<Vec3> path) {
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

