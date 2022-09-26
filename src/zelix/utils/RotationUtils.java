/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils;

public class RotationUtils {
    public static Rotation serverRotation = new Rotation(0.0f, 0.0f);

    public static class Rotation {
        public float yaw;
        public float pitch;

        public Rotation(float yaw, float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public float getYaw() {
            return this.yaw;
        }

        public float getPitch() {
            return this.pitch;
        }
    }
}

