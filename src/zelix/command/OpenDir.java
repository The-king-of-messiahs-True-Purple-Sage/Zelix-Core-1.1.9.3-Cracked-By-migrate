/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import java.awt.Desktop;
import zelix.command.Command;
import zelix.managers.FileManager;
import zelix.utils.hooks.visual.ChatUtils;

public class OpenDir
extends Command {
    public OpenDir() {
        super("opendir");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            Desktop.getDesktop().open(FileManager.GISHCODE_DIR);
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Opening directory of config.";
    }

    @Override
    public String getSyntax() {
        return "opendir";
    }
}

