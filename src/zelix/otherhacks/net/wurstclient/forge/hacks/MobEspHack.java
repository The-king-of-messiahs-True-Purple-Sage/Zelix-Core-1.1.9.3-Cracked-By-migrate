/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

public final class MobEspHack
extends Hack {
    private final EnumSetting<Style> style = new EnumSetting("Style", (Enum[])Style.values(), (Enum)Style.BOXES);
    private final CheckboxSetting filterInvisible = new CheckboxSetting("Filter invisible", "Won't show invisible mobs.", false);
    private int mobBox;
    private final ArrayList<EntityLiving> mobs = new ArrayList();

    public MobEspHack() {
        super("MobESP", "Highlights nearby mobs.");
        this.setCategory(Category.RENDER);
        this.addSetting(this.style);
        this.addSetting(this.filterInvisible);
    }

    @Override
    protected void onEnable() {
        wurst.register((Object)this);
        this.mobBox = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.mobBox, (int)4864);
        GL11.glBegin((int)1);
        AxisAlignedBB bb = new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 1.0, 0.5);
        RenderUtils.drawOutlinedBox(bb);
        GL11.glEnd();
        GL11.glEndList();
    }

    @Override
    protected void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        GL11.glDeleteLists((int)this.mobBox, (int)1);
        this.mobBox = 0;
    }

    @SubscribeEvent
    public void onUpdate(WUpdateEvent event) {
        World world = event.getPlayer().worldObj;
        this.mobs.clear();
        Stream<EntityLiving> stream = world.loadedEntityList.parallelStream().filter(e -> e instanceof EntityLiving).map(e -> (EntityLiving)e).filter(e -> !e.isDead && e.func_110143_aJ() > 0.0f);
        if (this.filterInvisible.isChecked()) {
            stream = stream.filter(e -> !e.isInvisible());
        }
        this.mobs.addAll(stream.collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-TileEntityRendererDispatcher.staticPlayerX), (double)(-TileEntityRendererDispatcher.staticPlayerY), (double)(-TileEntityRendererDispatcher.staticPlayerZ));
        double partialTicks = event.getPartialTicks();
        if (this.style.getSelected().boxes) {
            this.renderBoxes(partialTicks);
        }
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
        for (EntityLiving e : this.mobs) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)(e.prevPosX + (e.posX - e.prevPosX) * partialTicks), (double)(e.prevPosY + (e.posY - e.prevPosY) * partialTicks), (double)(e.prevPosZ + (e.posZ - e.prevPosZ) * partialTicks));
            GL11.glScaled((double)((double)e.width + 0.1), (double)((double)e.height + 0.1), (double)((double)e.width + 0.1));
            float f = MobEspHack.mc.thePlayer.getDistanceToEntity((Entity)e) / 20.0f;
            GL11.glColor4f((float)(2.0f - f), (float)f, (float)0.0f, (float)0.5f);
            GL11.glCallList((int)this.mobBox);
            GL11.glPopMatrix();
        }
    }

    private void renderTracers(double partialTicks) {
        Vec3d start = RotationUtils.getClientLookVec().func_72441_c(0.0, (double)WMinecraft.getPlayer().getEyeHeight(), 0.0).func_72441_c(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
        GL11.glBegin((int)1);
        for (EntityLiving e : this.mobs) {
            Vec3d end = e.func_174813_aQ().func_189972_c().func_178788_d(new Vec3d(e.posX, e.posY, e.posZ).func_178786_a(e.prevPosX, e.prevPosY, e.prevPosZ).func_186678_a(1.0 - partialTicks));
            float f = MobEspHack.mc.thePlayer.getDistanceToEntity((Entity)e) / 20.0f;
            GL11.glColor4f((float)(2.0f - f), (float)f, (float)0.0f, (float)0.5f);
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

