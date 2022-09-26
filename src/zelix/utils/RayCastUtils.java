/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.EntitySelectors
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 */
package zelix.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import zelix.utils.Wrapper;

public class RayCastUtils {
    public static Entity rayCast(double range, float yaw, float pitch) {
        double d0;
        double d1 = d0 = range;
        Vec3d vec3 = Wrapper.INSTANCE.player().func_174824_e(1.0f);
        boolean flag = false;
        boolean flag1 = true;
        if (d0 > 3.0) {
            flag = true;
        }
        Vec3d vec31 = RayCastUtils.getVectorForRotation(pitch, yaw);
        Vec3d vec32 = vec3.func_72441_c(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0);
        Entity pointedEntity = null;
        Vec3d vec33 = null;
        float f = 1.0f;
        List list = Wrapper.INSTANCE.world().func_175674_a(Wrapper.INSTANCE.mc().getRenderViewEntity(), Wrapper.INSTANCE.mc().getRenderViewEntity().func_174813_aQ().func_72317_d(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0).func_72321_a((double)f, (double)f, (double)f), Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d2 = d1;
        for (int i = 0; i < list.size(); ++i) {
            double d3;
            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72321_a((double)f1, (double)f1, (double)f1);
            RayTraceResult movingobjectposition = axisalignedbb.func_72327_a(vec3, vec32);
            if (axisalignedbb.func_72318_a(vec3)) {
                if (!(d2 >= 0.0)) continue;
                pointedEntity = entity1;
                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.field_72307_f;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = vec3.func_72438_d(movingobjectposition.field_72307_f)) < d2) && d2 != 0.0) continue;
            boolean flag2 = false;
            if (entity1 == Wrapper.INSTANCE.mc().getRenderViewEntity().func_184187_bx() && !flag2) {
                if (d2 != 0.0) continue;
                pointedEntity = entity1;
                vec33 = movingobjectposition.field_72307_f;
                continue;
            }
            pointedEntity = entity1;
            vec33 = movingobjectposition.field_72307_f;
            d2 = d3;
        }
        return pointedEntity;
    }

    public static Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.func_76134_b((float)(-yaw * ((float)Math.PI / 180) - (float)Math.PI));
        float f1 = MathHelper.func_76126_a((float)(-yaw * ((float)Math.PI / 180) - (float)Math.PI));
        float f2 = -MathHelper.func_76134_b((float)(-pitch * ((float)Math.PI / 180)));
        float f3 = MathHelper.func_76126_a((float)(-pitch * ((float)Math.PI / 180)));
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
}

