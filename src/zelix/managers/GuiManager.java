/*
 * Decompiled with CFR 0.152.
 */
package zelix.managers;

import zelix.gui.clickguis.gishcode.ClickGui;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.elements.CheckButton;
import zelix.gui.clickguis.gishcode.elements.Dropdown;
import zelix.gui.clickguis.gishcode.elements.ExpandingButton;
import zelix.gui.clickguis.gishcode.elements.Frame;
import zelix.gui.clickguis.gishcode.elements.KeybindMods;
import zelix.gui.clickguis.gishcode.elements.Slider;
import zelix.gui.clickguis.gishcode.listener.CheckButtonClickListener;
import zelix.gui.clickguis.gishcode.listener.ComponentClickListener;
import zelix.gui.clickguis.gishcode.listener.SliderChangeListener;
import zelix.hack.Hack;
import zelix.hack.HackCategory;
import zelix.managers.FileManager;
import zelix.managers.HackManager;
import zelix.utils.hooks.visual.GLUtils;
import zelix.value.BooleanValue;
import zelix.value.Mode;
import zelix.value.ModeValue;
import zelix.value.NumberValue;
import zelix.value.Value;

public class GuiManager
extends ClickGui {
    public void Init() {
        int right = GLUtils.getScreenWidth();
        int framePosX = 20;
        int framePosY = 20;
        for (HackCategory category : HackCategory.values()) {
            int frameHeight = 180;
            int frameWidth = 100;
            int hacksCount = 0;
            String name = Character.toString(category.toString().toLowerCase().charAt(0)).toUpperCase() + category.toString().toLowerCase().substring(1);
            Frame frame = new Frame(framePosX, framePosY, frameWidth, frameHeight, name);
            for (final Hack mod : HackManager.getHacks()) {
                if (mod.getCategory() != category) continue;
                ExpandingButton expandingButton = new ExpandingButton(0, 0, frameWidth, 14, frame, mod.getName(), mod){

                    @Override
                    public void onUpdate() {
                        this.setEnabled(this.hack.isToggled());
                    }
                };
                expandingButton.addListner(new ComponentClickListener(){

                    @Override
                    public void onComponenetClick(Component component2, int button) {
                        mod.toggle();
                    }
                });
                expandingButton.setEnabled(mod.isToggled());
                if (!mod.getValues().isEmpty()) {
                    for (final Value value : mod.getValues()) {
                        if (value instanceof BooleanValue) {
                            final BooleanValue booleanValue = (BooleanValue)value;
                            CheckButton button = new CheckButton(0, 0, expandingButton.getDimension().width, 14, expandingButton, booleanValue.getRenderName(), (Boolean)booleanValue.getValue(), null);
                            button.addListeners(new CheckButtonClickListener(){

                                @Override
                                public void onCheckButtonClick(CheckButton checkButton) {
                                    for (Value value1 : mod.getValues()) {
                                        if (!value1.getRenderName().equals(booleanValue.getRenderName())) continue;
                                        value1.setValue(checkButton.isEnabled());
                                    }
                                }
                            });
                            expandingButton.addComponent(button);
                            continue;
                        }
                        if (value instanceof NumberValue) {
                            NumberValue doubleValue = (NumberValue)value;
                            Slider slider = new Slider(doubleValue.getMin(), doubleValue.getMax(), (Double)doubleValue.getValue(), expandingButton, doubleValue.getRenderName());
                            slider.addListener(new SliderChangeListener(){

                                @Override
                                public void onSliderChange(Slider slider) {
                                    for (Value value1 : mod.getValues()) {
                                        if (!value1.getRenderName().equals(value.getRenderName())) continue;
                                        value1.setValue(slider.getValue());
                                    }
                                }
                            });
                            expandingButton.addComponent(slider);
                            continue;
                        }
                        if (!(value instanceof ModeValue)) continue;
                        Dropdown dropdown = new Dropdown(0, 0, frameWidth, 14, frame, value.getRenderName());
                        final ModeValue modeValue = (ModeValue)value;
                        for (final Mode mode2 : modeValue.getModes()) {
                            CheckButton button = new CheckButton(0, 0, expandingButton.getDimension().width, 14, expandingButton, mode2.getName(), mode2.isToggled(), modeValue);
                            button.addListeners(new CheckButtonClickListener(){

                                @Override
                                public void onCheckButtonClick(CheckButton checkButton) {
                                    for (Mode mode1 : modeValue.getModes()) {
                                        if (!mode1.getName().equals(mode2.getName())) continue;
                                        mode1.setToggled(checkButton.isEnabled());
                                    }
                                }
                            });
                            dropdown.addComponent(button);
                        }
                        expandingButton.addComponent(dropdown);
                    }
                }
                KeybindMods keybind = new KeybindMods(0, 0, 8, 14, expandingButton, mod);
                expandingButton.addComponent(keybind);
                frame.addComponent(expandingButton);
                ++hacksCount;
            }
            if (framePosX + frameWidth + 10 < right) {
                framePosX += frameWidth + 10;
            } else {
                framePosX = 20;
                framePosY += 60;
            }
            frame.setMaximizible(true);
            frame.setPinnable(true);
            this.addFrame(frame);
        }
        if (!FileManager.CLICKGUI.exists()) {
            FileManager.saveClickGui();
        } else {
            FileManager.loadClickGui();
        }
    }
}

