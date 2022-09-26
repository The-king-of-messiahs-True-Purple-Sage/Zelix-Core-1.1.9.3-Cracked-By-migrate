/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
    public static float round(float toRound, int scale) {
        return new BigDecimal(toRound).setScale(scale, RoundingMode.HALF_EVEN).floatValue();
    }

    public static double round(double toRound, int scale) {
        return new BigDecimal(toRound).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }
}

