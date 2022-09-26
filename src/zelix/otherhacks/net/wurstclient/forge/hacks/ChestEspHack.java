/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityMinecartChest
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.ArrayList;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WVec3d;
import zelix.otherhacks.net.wurstclient.forge.events.WUpdateEvent;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.BlockUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RenderUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

public final class ChestEspHack
extends Hack {
    private final EnumSetting<Style> style = new EnumSetting("Style", (Enum[])Style.values(), (Enum)Style.BOXES);
    private final ArrayList<AxisAlignedBB> basicChests = new ArrayList();
    private final ArrayList<AxisAlignedBB> trappedChests = new ArrayList();
    private final ArrayList<AxisAlignedBB> enderChests = new ArrayList();
    private final ArrayList<Entity> minecarts = new ArrayList();
    private int greenBox;
    private int orangeBox;
    private int cyanBox;
    private int normalChests;

    public ChestEspHack() {
        super("ChestESP", "Highlights nearby chests.\n\u00a7agreen\u00a7r - normal chests\n\u00a76orange\u00a7r - trapped chests\n\u00a7bcyan\u00a7r - ender chests");
        this.setCategory(Category.RENDER);
        this.addSetting(this.style);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
        AxisAlignedBB bb = new AxisAlignedBB(BlockPos.field_177992_a);
        this.greenBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.greenBox, (int)4864);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.25f);
        GL11.glBegin((int)7);
        RenderUtils.drawSolidBox(bb);
        GL11.glEnd();
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(bb);
        GL11.glEnd();
        GL11.glEndList();
        this.orangeBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.orangeBox, (int)4864);
        GL11.glColor4f((float)1.0f, (float)0.5f, (float)0.0f, (float)0.25f);
        GL11.glBegin((int)7);
        RenderUtils.drawSolidBox(bb);
        GL11.glEnd();
        GL11.glColor4f((float)1.0f, (float)0.5f, (float)0.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(bb);
        GL11.glEnd();
        GL11.glEndList();
        this.cyanBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.cyanBox, (int)4864);
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.25f);
        GL11.glBegin((int)7);
        RenderUtils.drawSolidBox(bb);
        GL11.glEnd();
        GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(bb);
        GL11.glEnd();
        GL11.glEndList();
        this.normalChests = GL11.glGenLists((int)1);
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        GL11.glDeleteLists((int)this.greenBox, (int)1);
        this.greenBox = 0;
        GL11.glDeleteLists((int)this.orangeBox, (int)1);
        this.orangeBox = 0;
        GL11.glDeleteLists((int)this.cyanBox, (int)1);
        this.cyanBox = 0;
        GL11.glDeleteLists((int)this.normalChests, (int)1);
        this.normalChests = 0;
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        World world = event.getPlayer().worldObj;
        this.basicChests.clear();
        this.trappedChests.clear();
        this.enderChests.clear();
        for (TileEntity tileEntity : world.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest) {
                AxisAlignedBB bb2;
                BlockPos pos2;
                TileEntityChest chest = (TileEntityChest)tileEntity;
                if (chest.adjacentChestXPos != null || chest.adjacentChestZPos != null) continue;
                BlockPos pos = chest.func_174877_v();
                AxisAlignedBB bb = BlockUtils.getBoundingBox(pos);
                if (chest.adjacentChestXNeg != null) {
                    pos2 = chest.adjacentChestXNeg.func_174877_v();
                    bb2 = BlockUtils.getBoundingBox(pos2);
                    bb = bb.func_111270_a(bb2);
                } else if (chest.adjacentChestZNeg != null) {
                    pos2 = chest.adjacentChestZNeg.func_174877_v();
                    bb2 = BlockUtils.getBoundingBox(pos2);
                    bb = bb.func_111270_a(bb2);
                }
                switch (chest.func_145980_j()) {
                    case BASIC: {
                        this.basicChests.add(bb);
                        break;
                    }
                    case TRAP: {
                        this.trappedChests.add(bb);
                    }
                }
                continue;
            }
            if (!(tileEntity instanceof TileEntityEnderChest)) continue;
            BlockPos pos = ((TileEntityEnderChest)tileEntity).func_174877_v();
            AxisAlignedBB bb = BlockUtils.getBoundingBox(pos);
            this.enderChests.add(bb);
        }
        GL11.glNewList((int)this.normalChests, (int)4864);
        this.renderBoxes(this.basicChests, this.greenBox);
        this.renderBoxes(this.trappedChests, this.orangeBox);
        this.renderBoxes(this.enderChests, this.cyanBox);
        GL11.glEndList();
        this.minecarts.clear();
        for (Entity entity : world.loadedEntityList) {
            if (!(entity instanceof EntityMinecartChest)) continue;
            this.minecarts.add(entity);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        double partialTicks = event.getPartialTicks();
        ArrayList<AxisAlignedBB> minecartBoxes = new ArrayList<AxisAlignedBB>(this.minecarts.size());
        this.minecarts.forEach(e -> {
            double offsetX = -(e.posX - e.lastTickPosX) + (e.posX - e.lastTickPosX) * partialTicks;
            double offsetY = -(e.posY - e.lastTickPosY) + (e.posY - e.lastTickPosY) * partialTicks;
            double offsetZ = -(e.posZ - e.lastTickPosZ) + (e.posZ - e.lastTickPosZ) * partialTicks;
            minecartBoxes.add(e.func_184177_bl().func_72317_d(offsetX, offsetY, offsetZ));
        });
        if (this.style.getSelected().boxes) {
            GL11.glCallList((int)this.normalChests);
            this.renderBoxes(minecartBoxes, this.greenBox);
        }
        if (this.style.getSelected().lines) {
            Vec3d start = RotationUtils.getClientLookVec().func_72441_c(0.0, (double)WMinecraft.getPlayer().getEyeHeight(), 0.0).func_72441_c(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
            GL11.glBegin((int)1);
            GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)0.5f);
            this.renderLines(start, this.basicChests);
            this.renderLines(start, minecartBoxes);
            GL11.glColor4f((float)1.0f, (float)0.5f, (float)0.0f, (float)0.5f);
            this.renderLines(start, this.trappedChests);
            GL11.glColor4f((float)0.0f, (float)1.0f, (float)1.0f, (float)0.5f);
            this.renderLines(start, this.enderChests);
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private void renderBoxes(ArrayList<AxisAlignedBB> boxes, int displayList) {
        for (AxisAlignedBB bb : boxes) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)bb.field_72340_a, (double)bb.field_72338_b, (double)bb.field_72339_c);
            GL11.glScaled((double)(bb.field_72336_d - bb.field_72340_a), (double)(bb.field_72337_e - bb.field_72338_b), (double)(bb.field_72334_f - bb.field_72339_c));
            GL11.glCallList((int)displayList);
            GL11.glPopMatrix();
        }
    }

    private void renderLines(Vec3d start, ArrayList<AxisAlignedBB> boxes) {
        for (AxisAlignedBB bb : boxes) {
            Vec3d end = bb.func_189972_c();
            GL11.glVertex3d((double)WVec3d.getX(start), (double)WVec3d.getY(start), (double)WVec3d.getZ(start));
            GL11.glVertex3d((double)WVec3d.getX(end), (double)WVec3d.getY(end), (double)WVec3d.getZ(end));
        }
    }

    private static enum Style {
        BOXES("Boxes only", true, false),
        LINES("Lines only", false, true),
        LINES_AND_BOXES("Lines and boxes", true, true);

        private final String name;
        private final boolean boxes;
        private final boolean lines;

        private Style(String name, boolean boxes, boolean lines) {
            this.name = name;
            this.boxes = boxes;
            this.lines = lines;
        }

        public String toString() {
            return this.name;
        }
    }
}

