/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.system;

import zelix.utils.frame.MSGBOX;
import zelix.utils.system.Check;

public class NMSL {
    public static void RINIMACAONIMA() {
        if (!Check.checkuser()) {
            MSGBOX.main(null);
            return;
        }
    }
}

