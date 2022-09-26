/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.tenacityutils.client;

public enum ReleaseType {
    PUBLIC("Public"),
    BETA("Beta"),
    DEV("Developer");

    private final String name;

    private ReleaseType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

