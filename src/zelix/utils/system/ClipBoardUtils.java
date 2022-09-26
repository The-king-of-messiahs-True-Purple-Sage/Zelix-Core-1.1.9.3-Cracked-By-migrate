/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.system;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipBoardUtils {
    public static void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }
}

