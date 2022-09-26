/*
 * Decompiled with CFR 0.152.
 */
package zelix.eventapi.events.callables;

import zelix.eventapi.events.Cancellable;
import zelix.eventapi.events.Event;

public abstract class EventCancellable
implements Event,
Cancellable {
    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

