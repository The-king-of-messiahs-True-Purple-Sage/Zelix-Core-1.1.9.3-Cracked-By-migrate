/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.external;

import zelix.hack.hacks.external.External;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;

public class EXTTheard
extends Thread {
    @Override
    public void run() {
        External.connect();
        do {
            External.handleInput();
        } while (HackManager.getHack("External").isToggled());
        ChatUtils.error("Disconnect from IRC :-(");
    }
}

