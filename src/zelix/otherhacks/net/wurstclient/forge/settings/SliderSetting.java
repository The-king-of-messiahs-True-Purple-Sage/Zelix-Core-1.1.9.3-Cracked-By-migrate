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
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Slider;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;
import zelix.otherhacks.net.wurstclient.forge.utils.MathUtils;

public final class SliderSetting
extends Setting {
    private double value;
    private final double defaultValue;
    private final double min;
    private final double max;
    private final double increment;
    private final ValueDisplay display;

    public SliderSetting(String name, String description, double value, double min, double max, double increment, ValueDisplay display) {
        super(name, description);
        this.value = value;
        this.defaultValue = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.display = display;
    }

    public SliderSetting(String name, double value, double min, double max, double increment, ValueDisplay display) {
        this(name, null, value, min, max, increment, display);
    }

    public double getValue() {
        return this.value;
    }

    public float getValueF() {
        return (float)this.value;
    }

    public int getValueI() {
        return (int)this.value;
    }

    public String getValueString() {
        return this.display.getValueString(this.value);
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public void setValue(double value) {
        value = (double)Math.round(value / this.increment) * this.increment;
        this.value = value = MathUtils.clamp(value, this.min, this.max);
        ForgeWurst.getForgeWurst().getHax().saveSettings();
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    @Override
    public Component getComponent() {
        return new Slider(this);
    }

    @Override
    public void fromJson(JsonElement json) {
        if (!json.isJsonPrimitive()) {
            return;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if (!primitive.isNumber()) {
            return;
        }
        this.setValue(primitive.getAsDouble());
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((Number)((double)Math.round(this.value * 1000000.0) / 1000000.0));
    }

    public static interface ValueDisplay {
        public static final ValueDisplay DECIMAL = v -> (double)Math.round(v * 1000000.0) / 1000000.0 + "";
        public static final ValueDisplay INTEGER = v -> (int)v + "";
        public static final ValueDisplay PERCENTAGE = v -> (int)((double)Math.round(v * 1.0E8) / 1000000.0) + "%";
        public static final ValueDisplay DEGREES = v -> (int)v + "\u00b0";
        public static final ValueDisplay NONE = v -> "";

        public String getValueString(double var1);
    }
}

