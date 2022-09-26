/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import java.math.BigInteger;
import zelix.command.Command;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.ChatUtils;

public class VClip
extends Command {
    public VClip() {
        super("vclip");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + (double)new BigInteger(args[0]).longValue(), Wrapper.INSTANCE.player().posZ);
            ChatUtils.message("Height teleported to " + new BigInteger(args[0]).longValue());
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Teleports you up/down.";
    }

    @Override
    public String getSyntax() {
        return "vclip <height>";
    }
}

