/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 */
package zelix.otherhacks.net.wurstclient.forge.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Objects;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.ComboBox;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;

public final class EnumSetting<T extends Enum>
extends Setting {
    private final T[] values;
    private T selected;
    private final T defaultSelected;

    public EnumSetting(String name, String description, T[] values, T selected) {
        super(name, description);
        this.values = (Enum[])Objects.requireNonNull(values);
        this.selected = (Enum)Objects.requireNonNull(selected);
        this.defaultSelected = selected;
    }

    public EnumSetting(String name, T[] values, T selected) {
        this(name, null, (Enum[])values, (Enum)selected);
    }

    public T[] getValues() {
        return this.values;
    }

    public T getSelected() {
        return this.selected;
    }

    public T getDefaultSelected() {
        return this.defaultSelected;
    }

    public void setSelected(T selected) {
        this.selected = (Enum)Objects.requireNonNull(selected);
        ForgeWurst.getForgeWurst().getHax().saveSettings();
    }

    public void setSelected(String selected) {
        for (T value : this.values) {
            if (!((Enum)value).toString().equalsIgnoreCase(selected)) continue;
            this.setSelected(value);
            break;
        }
    }

    @Override
    public Component getComponent() {
        return new ComboBox(this);
    }

    @Override
    public void fromJson(JsonElement json) {
        if (!json.isJsonPrimitive()) {
            return;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if (!primitive.isString()) {
            return;
        }
        this.setSelected(primitive.getAsString());
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(((Enum)this.selected).toString());
    }
}

