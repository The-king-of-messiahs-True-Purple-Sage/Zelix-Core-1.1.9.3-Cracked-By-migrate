/*
 * Decompiled with CFR 0.152.
 */
package zelix.eventapi.events.callables;

import zelix.eventapi.events.Event;
import zelix.eventapi.events.Typed;

public abstract class EventTyped
implements Event,
Typed {
    private final byte type;

    protected EventTyped(byte eventType) {
        this.type = eventType;
    }

    @Override
    public byte getType() {
        return this.type;
    }
}

