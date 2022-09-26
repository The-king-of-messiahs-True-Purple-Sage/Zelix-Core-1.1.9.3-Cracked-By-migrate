/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import zelix.gui.clickguis.caesium.components.GuiButton;
import zelix.gui.clickguis.caesium.components.GuiComboBox;
import zelix.gui.clickguis.caesium.components.GuiGetKey;
import zelix.gui.clickguis.caesium.components.GuiLabel;
import zelix.gui.clickguis.caesium.components.GuiSlider;
import zelix.gui.clickguis.caesium.components.GuiToggleButton;
import zelix.gui.clickguis.caesium.components.listeners.ComboListener;
import zelix.gui.clickguis.caesium.components.listeners.ComponentListener;
import zelix.gui.clickguis.caesium.components.listeners.KeyListener;
import zelix.gui.clickguis.caesium.components.listeners.ValueListener;
import zelix.hack.Hack;
import zelix.managers.HackManager;
import zelix.value.BooleanValue;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class ComponentsListener
extends ComponentListener {
    private GuiButton button;

    public ComponentsListener(GuiButton button) {
        this.button = button;
    }

    @Override
    public void addComponents() {
        this.add(new GuiLabel("Settings >"));
        final Hack m = HackManager.getHack(this.button.getText());
        for (final Value set : m.getValues()) {
            if (set instanceof BooleanValue) {
                final GuiToggleButton toggleButton = new GuiToggleButton((BooleanValue)set);
                toggleButton.setToggled((Boolean)((BooleanValue)set).getValue());
                toggleButton.addClickListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        set.setValue(toggleButton.isToggled());
                        new Thread(() -> {}).start();
                    }
                });
                this.add(toggleButton);
            }
            if (set instanceof NumberValue) {
                GuiSlider slider = new GuiSlider((NumberValue)set, ((NumberValue)set).getMin(), ((NumberValue)set).getMax(), (Double)((NumberValue)set).getValue(), 2);
                slider.addValueListener(new ValueListener(){

                    @Override
                    public void valueUpdated(double value) {
                        set.setValue(value);
                    }

                    @Override
                    public void valueChanged(double value) {
                        set.setValue(value);
                        new Thread(() -> {}).start();
                    }
                });
                this.add(slider);
            }
            if (!(set instanceof ModeValue)) continue;
            GuiComboBox comboBox = new GuiComboBox((ModeValue)set);
            comboBox.addComboListener(new ComboListener(){

                @Override
                public void comboChanged(String combo) {
                    new Thread(() -> {}).start();
                }
            });
            this.add(comboBox);
        }
        GuiGetKey ggk = new GuiGetKey("KeyBind", m.getKey());
        ggk.addKeyListener(new KeyListener(){

            @Override
            public void keyChanged(int key) {
                m.setKey(key);
                new Thread(() -> {}).start();
            }
        });
        this.add(ggk);
    }
}

