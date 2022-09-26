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
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Checkbox;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;

public final class CheckboxSetting
extends Setting {
    private boolean checked;
    private final boolean checkedByDefault;

    public CheckboxSetting(String name, String description, boolean checked) {
        super(name, description);
        this.checked = checked;
        this.checkedByDefault = checked;
    }

    public CheckboxSetting(String name, boolean checked) {
        this(name, null, checked);
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isCheckedByDefault() {
        return this.checkedByDefault;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        ForgeWurst.getForgeWurst().getHax().saveSettings();
    }

    @Override
    public Component getComponent() {
        return new Checkbox(this);
    }

    @Override
    public void fromJson(JsonElement json) {
        if (!json.isJsonPrimitive()) {
            return;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if (!primitive.isBoolean()) {
            return;
        }
        this.setChecked(primitive.getAsBoolean());
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(Boolean.valueOf(this.checked));
    }
}

