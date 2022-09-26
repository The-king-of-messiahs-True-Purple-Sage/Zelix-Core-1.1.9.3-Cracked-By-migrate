/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.Core;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.CapeManager;
import zelix.utils.hooks.visual.ChatUtils;

public class TestHack
extends Hack {
    public TestHack() {
        super("RefreshCape", HackCategory.ANOTHER);
    }

    @Override
    public String getDescription() {
        return "Refresh Your CapeManager";
    }

    @Override
    public void onEnable() {
        Core.capeManager = new CapeManager();
        ChatUtils.message("Refreshed!");
        super.onEnable();
    }

    void processAttack_mzby() {
    }
}

