/*
 * Decompiled with CFR 0.152.
 */
package zelix.value;

import zelix.hack.hacks.ClickGui;

public class Value<T> {
    public T value;
    private String name;
    public static String chinese;
    private T defaultValue;

    public Value(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getName() {
        return this.name;
    }

    public String getRenderName() {
        if (chinese != null && ClickGui.language.getMode("Chinese").isToggled()) {
            return chinese;
        }
        return this.name;
    }

    public void setCNName(String cnName) {
        chinese = cnName;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

