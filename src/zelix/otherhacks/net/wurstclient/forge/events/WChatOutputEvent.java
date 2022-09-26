/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  net.minecraftforge.fml.common.eventhandler.Cancelable
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package zelix.otherhacks.net.wurstclient.forge.events;

import com.google.common.base.Strings;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public final class WChatOutputEvent
extends Event {
    private String message;
    private final String originalMessage;

    public WChatOutputEvent(String message) {
        this.setMessage(message);
        this.originalMessage = Strings.nullToEmpty((String)message);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = Strings.nullToEmpty((String)message);
    }

    public String getOriginalMessage() {
        return this.originalMessage;
    }
}

