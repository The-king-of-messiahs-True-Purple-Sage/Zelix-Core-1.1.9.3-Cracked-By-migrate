/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package zelix.otherhacks.net.wurstclient.forge;

import net.minecraft.client.Minecraft;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WForgeRegistryEntry;
import zelix.otherhacks.net.wurstclient.forge.utils.ChatUtils;

public abstract class Command
extends WForgeRegistryEntry<Command> {
    protected static final ForgeWurst wurst = ForgeWurst.getForgeWurst();
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private final String description;
    private final String[] syntax;

    public Command(String name, String description, String ... syntax) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    public abstract void call(String[] var1) throws CmdException;

    public final String getName() {
        return this.name;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String[] getSyntax() {
        return this.syntax;
    }

    public final class CmdSyntaxError
    extends CmdException {
        public CmdSyntaxError() {
        }

        public CmdSyntaxError(String message) {
            super(message);
        }

        @Override
        public void printToChat() {
            if (this.getMessage() != null) {
                ChatUtils.message("\u00a74Syntax error:\u00a7r " + this.getMessage());
            }
            for (String line : Command.this.syntax) {
                ChatUtils.message(line);
            }
        }
    }

    public final class CmdError
    extends CmdException {
        public CmdError(String message) {
            super(message);
        }

        @Override
        public void printToChat() {
            ChatUtils.error(this.getMessage());
        }
    }

    public abstract class CmdException
    extends Exception {
        public CmdException() {
        }

        public CmdException(String message) {
            super(message);
        }

        public abstract void printToChat();
    }
}

