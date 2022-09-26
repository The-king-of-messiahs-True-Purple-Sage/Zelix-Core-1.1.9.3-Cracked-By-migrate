/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.gishcode.elements;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import zelix.gui.clickguis.gishcode.ClickGui;
import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;
import zelix.gui.clickguis.gishcode.listener.SliderChangeListener;

public class Slider
extends Component {
    public boolean dragging = false;
    public double min;
    public double max;
    public double value;
    public double percent = 0.0;
    private ArrayList<SliderChangeListener> listeners = new ArrayList();

    public Slider(double min, double max, double value, Component component2, String text) {
        super(0, 0, 100, 20, ComponentType.SLIDER, component2, text);
        this.min = min;
        this.max = max;
        this.value = value;
        this.percent = value / (max - min);
    }

    public void addListener(SliderChangeListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        int x1 = (int)this.getDimension().getWidth();
        this.percent = (double)(x -= this.getX()) / (double)x1;
        this.value = this.round((this.max - this.min) * this.percent + this.min, 2);
        this.dragging = true;
        this.fireListeners();
    }

    private void fireListeners() {
        for (SliderChangeListener listener : this.listeners) {
            listener.onSliderChange(this);
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int buttonID) {
        this.dragging = false;
    }

    @Override
    public void onUpdate() {
        int[] mouse = ClickGui.mouse;
        this.dragging = false;
        if (this.dragging && !this.isMouseOver(mouse[0], mouse[1])) {
            if (mouse[0] <= this.getX()) {
                this.percent = 0.0;
                this.value = this.min;
                this.fireListeners();
            } else {
                this.percent = 1.0;
                this.value = this.max;
                this.fireListeners();
            }
        }
        if (Mouse.isButtonDown((int)0) && this.isMouseOver(mouse[0], mouse[1])) {
            this.dragging = true;
        }
    }

    @Override
    public void onMouseDrag(int x, int y) {
        if (this.dragging) {
            int x1 = (int)this.getDimension().getWidth();
            this.percent = (double)(x -= this.getX()) / (double)x1;
            this.value = this.round((this.max - this.min) * this.percent + this.min, 2);
            this.fireListeners();
        }
    }

    public ArrayList<SliderChangeListener> getListeners() {
        return this.listeners;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public double getMin() {
        return this.min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPercent() {
        return this.percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    private double round(double valueToRound, int numberOfDecimalPlaces) {
        double multipicationFactor = Math.pow(10.0, numberOfDecimalPlaces);
        double interestedInZeroDPs = valueToRound * multipicationFactor;
        return (double)Math.round(interestedInZeroDPs) / multipicationFactor;
    }
}

