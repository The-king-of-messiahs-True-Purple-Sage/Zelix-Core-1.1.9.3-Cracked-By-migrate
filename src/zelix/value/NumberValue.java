/*
 * Decompiled with CFR 0.152.
 */
package zelix.value;

import zelix.value.Value;

public class NumberValue
extends Value<Double> {
    protected Double min;
    protected Double max;

    public NumberValue(String name, Double defaultValue, Double min, Double max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    public Double getMin() {
        return this.min;
    }

    public Double getMax() {
        return this.max;
    }
}

