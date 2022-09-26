/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.hacks;

import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
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
import zelix.otherhacks.net.wurstclient.forge.settings.CheckboxSetting;
import zelix.otherhacks.net.wurstclient.forge.settings.EnumSetting;
import zelix.otherhacks.net.wurstclient.forge.utils.RenderUtils;
import zelix.otherhacks.net.wurstclient.forge.utils.RotationUtils;

public final class ItemEspHack
extends Hack {
    private final CheckboxSetting names = new CheckboxSetting("Show item names", true);
    private final EnumSetting<Style> style = new EnumSetting("Style", (Enum[])Style.values(), (Enum)Style.BOXES);
    private int itemBox;
    private final ArrayList<EntityItem> items = new ArrayList();

    public ItemEspHack() {
        super("ItemESP", "Highlights nearby items.");
        this.setCategory(Category.RENDER);
        this.addSetting(this.names);
        this.addSetting(this.style);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
        this.itemBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.itemBox, (int)4864);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        GL11.glBegin((int)1);
        RenderUtils.drawOutlinedBox(new AxisAlignedBB(-0.175, 0.0, -0.175, 0.175, 0.35, 0.175));
        GL11.glEnd();
        GL11.glEndList();
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        GL11.glDeleteLists((int)this.itemBox, (int)1);
        this.itemBox = 0;
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        World world = event.getPlayer().worldObj;
        this.items.clear();
        for (Entity entity : world.loadedEntityList) {
            if (!(entity instanceof EntityItem)) continue;
            this.items.add((EntityItem)entity);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        double partialTicks = event.getPartialTicks();
        this.renderBoxes(partialTicks);
        if (this.style.getSelected().lines) {
            this.renderTracers(partialTicks);
        }
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    private void renderBoxes(double partialTicks) {
        for (EntityItem e : this.items) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)(e.prevPosX + (e.posX - e.prevPosX) * partialTicks), (double)(e.prevPosY + (e.posY - e.prevPosY) * partialTicks), (double)(e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks));
            if (this.style.getSelected().boxes) {
                GL11.glCallList((int)this.itemBox);
            }
            if (this.names.isChecked()) {
                ItemStack stack = e.getEntityItem();
                EntityRenderer.func_189692_a((FontRenderer)WMinecraft.getFontRenderer(), (String)(stack.func_190916_E() + "x " + stack.getDisplayName()), (float)0.0f, (float)1.0f, (float)0.0f, (int)0, (float)ItemEspHack.mc.getRenderManager().playerViewY, (float)ItemEspHack.mc.getRenderManager().playerViewX, (ItemEspHack.mc.getRenderManager().options.thirdPersonView == 2 ? 1 : 0) != 0, (boolean)false);
                GL11.glDisable((int)2896);
            }
            GL11.glPopMatrix();
        }
    }

    private void renderTracers(double partialTicks) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)0.5f);
        Vec3d start = RotationUtils.getClientLookVec().func_72441_c(0.0, (double)WMinecraft.getPlayer().getEyeHeight(), 0.0).func_72441_c(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
        GL11.glBegin((int)1);
        for (EntityItem e : this.items) {
            Vec3d end = e.func_174813_aQ().func_189972_c().func_178788_d(new Vec3d(e.posX, e.posY, e.posZ).func_178786_a(e.prevPosX, e.prevPosY, e.prevPosZ).func_186678_a(1.0 - partialTicks));
            GL11.glVertex3d((double)WVec3d.getX(start), (double)WVec3d.getY(start), (double)WVec3d.getZ(start));
            GL11.glVertex3d((double)WVec3d.getX(end), (double)WVec3d.getY(end), (double)WVec3d.getZ(end));
        }
        GL11.glEnd();
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

