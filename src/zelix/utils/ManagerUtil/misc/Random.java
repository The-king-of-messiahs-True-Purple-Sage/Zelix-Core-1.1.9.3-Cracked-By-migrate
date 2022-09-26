/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.ManagerUtil.misc;

import java.security.SecureRandom;

public class Random {
    public static double dbRandom(double min, double max) {
        SecureRandom rng = new SecureRandom();
        return rng.nextDouble() * (max - min) + min;
    }

    public static float flRandom(float min, float max) {
        SecureRandom rng = new SecureRandom();
        return rng.nextFloat() * (max - min) + min;
    }
}

