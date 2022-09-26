/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.gishcode.elements;

import zelix.gui.clickguis.gishcode.base.Component;
import zelix.gui.clickguis.gishcode.base.ComponentType;

public class Text
extends Component {
    private String[] text;

    public Text(int xPos, int yPos, int width, int height, Component component2, String[] text) {
        super(xPos, yPos, width, height, ComponentType.TEXT, component2, "");
        this.text = text;
    }

    public String[] getMessage() {
        return this.text;
    }
}

