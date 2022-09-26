/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package zelix.utils;

import net.minecraft.entity.Entity;

public class EntityUtils {
    public static float getDistance(Entity e1, Entity e2) {
        return e1.getDistanceToEntity(e2);
    }

    public static double getDistanceSq(Entity e1, Entity e2) {
        return e1.getDistanceSqToEntity(e2);
    }
}

