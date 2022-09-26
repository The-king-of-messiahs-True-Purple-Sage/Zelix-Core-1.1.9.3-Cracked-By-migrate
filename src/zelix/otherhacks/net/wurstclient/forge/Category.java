/*
 * Decompiled with CFR 0.152.
 */
package zelix.otherhacks.net.wurstclient.forge;

public enum Category {
    BLOCKS("Blocks"),
    MOVEMENT("Movement"),
    COMBAT("Combat"),
    RENDER("Render"),
    CHAT("Chat"),
    FUN("Fun"),
    OTHER("Other");

    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

