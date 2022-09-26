/*
 * Decompiled with CFR 0.152.
 */
package zelix.hack.hacks.xray;

import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.hack.hacks.xray.XRay;

public class AntiAntiXrayMod
extends Hack {
    public static boolean isFirst = true;
    XRay XRAY;

    public AntiAntiXrayMod() {
        super("Xray", HackCategory.VISUAL);
        this.setToggled(false);
    }

    @Override
    public void onEnable() {
        this.XRAY = new XRay();
        this.XRAY.onEnable();
        if (isFirst) {
            isFirst = false;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.XRAY != null) {
            this.XRAY.onDisable();
        }
    }
}

