/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package zelix.eventapi.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventPlayerPost
extends Event {
    private float yaw;
    private float pitch;

    public EventPlayerPost(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}

