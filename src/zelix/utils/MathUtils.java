/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package zelix.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.util.math.MathHelper;

public class MathUtils {
    public static int getMiddle(int i, int j) {
        return (i + j) / 2;
    }

    public static double getMiddleDouble(int i, int j) {
        return ((double)i + (double)j) / 2.0;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static float getAngleDifference(float direction, float rotationYaw) {
        float phi = Math.abs(rotationYaw - direction) % 360.0f;
        float distance = phi > 180.0f ? 360.0f - phi : phi;
        return distance;
    }

    public static int clamp(int num, int min, int max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static float clamp(float num, float min, float max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp(double num, double min, double max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static int floor(float value) {
        return MathHelper.func_76141_d((float)value);
    }

    public static int floor(double value) {
        return MathHelper.func_76128_c((double)value);
    }

    public static int ceil(float value) {
        return MathHelper.func_76123_f((float)value);
    }

    public static int ceil(double value) {
        return MathHelper.func_76143_f((double)value);
    }

    public static float sin(float value) {
        return MathHelper.func_76126_a((float)value);
    }

    public static float cos(float value) {
        return MathHelper.func_76134_b((float)value);
    }

    public static float wrapDegrees(float value) {
        return MathHelper.func_76142_g((float)value);
    }

    public static double wrapDegrees(double value) {
        return MathHelper.func_76138_g((double)value);
    }
}

