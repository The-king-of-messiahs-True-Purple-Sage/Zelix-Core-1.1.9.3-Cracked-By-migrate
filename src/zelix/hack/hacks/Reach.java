/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.MouseEvent
 */
package zelix.hack.hacks;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.Wrapper;
import zelix.value.BooleanValue;
import zelix.value.NumberValue;

public class Reach
extends Hack {
    static BooleanValue throughBlocks;
    static NumberValue reachMinVal;
    static NumberValue reachMaxVal;
    static BooleanValue bypassMAC;
    public Random r;

    public Reach() {
        super("Reach", HackCategory.COMBAT);
        reachMinVal = new NumberValue("ReachMin", 3.0, 3.0, 6.0);
        reachMaxVal = new NumberValue("ReachMax", 3.0, 3.0, 6.0);
        throughBlocks = new BooleanValue("ThroughBlocks", false);
        this.r = new Random();
        this.addValue(throughBlocks, reachMinVal, reachMaxVal, bypassMAC);
    }

    @Override
    public void onMouse(MouseEvent event) {
        if (!((Boolean)throughBlocks.getValue()).booleanValue() && Wrapper.INSTANCE.mc().objectMouseOver != null && Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a != null && Wrapper.INSTANCE.mc().objectMouseOver.field_72313_a == RayTraceResult.Type.BLOCK) {
            return;
        }
        double range = (Double)reachMinVal.getValue() + this.r.nextDouble() * ((Double)reachMaxVal.getValue() - (Double)reachMinVal.getValue());
        Object[] mouseOver = Reach.getMouseOver(range, 0.0, 0.0f);
        if (mouseOver == null) {
            return;
        }
        Vec3d lookVec = Wrapper.INSTANCE.player().func_70040_Z();
        Wrapper.INSTANCE.mc().objectMouseOver = new RayTraceResult((Entity)mouseOver[0], (Vec3d)mouseOver[1]);
        Wrapper.INSTANCE.mc().pointedEntity = (Entity)mouseOver[0];
        super.onMouse(event);
    }

    public static Object[] getMouseOver(double Range, double bbExpand, float f) {
        Entity renderViewEntity = Wrapper.INSTANCE.mc().getRenderViewEntity();
        Entity entity = null;
        if (renderViewEntity == null || Wrapper.INSTANCE.world() == null) {
            return null;
        }
        Wrapper.INSTANCE.mc().mcProfiler.startSection("pick");
        Vec3d positionEyes = renderViewEntity.func_174824_e(0.0f);
        Vec3d renderViewEntityLook = renderViewEntity.func_70676_i(0.0f);
        Vec3d vector = positionEyes.func_72441_c(renderViewEntityLook.field_72450_a * Range, renderViewEntityLook.field_72448_b * Range, renderViewEntityLook.field_72449_c * Range);
        Vec3d ve = null;
        List entitiesWithinAABB = Wrapper.INSTANCE.world().func_72839_b(renderViewEntity, renderViewEntity.func_174813_aQ().func_72321_a(renderViewEntityLook.field_72450_a * Range, renderViewEntityLook.field_72448_b * Range, renderViewEntityLook.field_72449_c * Range).func_72321_a(1.0, 1.0, 1.0));
        double range = Range;
        for (int i = 0; i < entitiesWithinAABB.size(); ++i) {
            double v;
            Entity e = (Entity)entitiesWithinAABB.get(i);
            if (!e.canBeCollidedWith()) continue;
            float size = e.getCollisionBorderSize();
            AxisAlignedBB bb = e.func_174813_aQ().func_72321_a((double)size, (double)size, (double)size);
            bb = bb.func_72321_a(bbExpand, bbExpand, bbExpand);
            RayTraceResult objectPosition = bb.func_72327_a(positionEyes, vector);
            if (bb.func_72318_a(positionEyes)) {
                if (!(0.0 < range) && range != 0.0) continue;
                entity = e;
                ve = objectPosition == null ? positionEyes : objectPosition.field_72307_f;
                range = 0.0;
                continue;
            }
            if (objectPosition == null || !((v = positionEyes.func_72438_d(objectPosition.field_72307_f)) < range) && range != 0.0) continue;
            boolean b = false;
            if (e == renderViewEntity.func_184187_bx()) {
                if (range != 0.0) continue;
                entity = e;
                ve = objectPosition.field_72307_f;
                continue;
            }
            entity = e;
            ve = objectPosition.field_72307_f;
            range = v;
        }
        if (range < Range && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Wrapper.INSTANCE.mc().mcProfiler.endSection();
        if (entity == null || ve == null) {
            return null;
        }
        return new Object[]{entity, ve};
    }
}

