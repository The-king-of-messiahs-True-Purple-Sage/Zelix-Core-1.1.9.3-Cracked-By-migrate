/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import zelix.gui.clickguis.caesium.components.GuiButton;
import zelix.hack.Hack;
import zelix.managers.HackManager;

public class ClickListener
implements ActionListener {
    private GuiButton button;

    public ClickListener(GuiButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Hack m = HackManager.getHack(this.button.getText());
        m.toggle();
    }
}

