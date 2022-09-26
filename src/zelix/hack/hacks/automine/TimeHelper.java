/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.automine;

public class TimeHelper {
    public long lastMs = 0L;

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

    public boolean hasReach(double milliseconds) {
        if ((double)(System.currentTimeMillis() - this.lastMs) >= milliseconds) {
            this.reset();
            return true;
        }
        return false;
    }
}

