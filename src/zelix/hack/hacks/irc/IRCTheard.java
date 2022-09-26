/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.irc;

import zelix.hack.hacks.irc.IRCChat;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.ChatUtils;

public class IRCTheard
extends Thread {
    @Override
    public void run() {
        IRCChat.connect();
        do {
            IRCChat.handleInput();
        } while (HackManager.getHack("IRCChat").isToggled());
        ChatUtils.error("Disconnect from IRC :-(");
    }
}

