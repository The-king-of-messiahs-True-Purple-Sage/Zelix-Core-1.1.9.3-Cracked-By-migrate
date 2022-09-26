/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package zelix.otherhacks.net.wurstclient.forge.settings;

import com.google.gson.JsonElement;
import java.util.Objects;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;

public abstract class Setting {
    private final String name;
    private final String description;

    public Setting(String name, String description) {
        this.name = Objects.requireNonNull(name);
        this.description = description;
    }

    public final String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract Component getComponent();

    public abstract void fromJson(JsonElement var1);

    public abstract JsonElement toJson();
}

