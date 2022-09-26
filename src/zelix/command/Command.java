/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

public abstract class Command {
    private String command;

    public Command(String command) {
        this.command = command;
    }

    public abstract void runCommand(String var1, String[] var2);

    public abstract String getDescription();

    public abstract String getSyntax();

    public String getCommand() {
        return this.command;
    }
}

