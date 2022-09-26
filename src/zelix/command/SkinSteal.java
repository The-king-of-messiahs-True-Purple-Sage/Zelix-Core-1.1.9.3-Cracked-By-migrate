/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 */
package zelix.command;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import zelix.command.Command;
import zelix.managers.SkinChangerManager;
import zelix.utils.hooks.visual.ChatUtils;

public class SkinSteal
extends Command {
    public SkinSteal() {
        super("skinsteal");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            SkinChangerManager.addTexture(MinecraftProfileTexture.Type.SKIN, args[0]);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Steal skin for 'SkinChanger'.";
    }

    @Override
    public String getSyntax() {
        return "skinsteal <nickname/URL>";
    }
}

