/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.utils.frame.FCommand;

public class CommandFrame
extends Hack {
    public CommandFrame() {
        super("CommandFrame", HackCategory.ANOTHER);
        this.setShow(false);
    }

    @Override
    public void onEnable() {
        FCommand.main(null);
        this.setToggled(false);
    }
}

