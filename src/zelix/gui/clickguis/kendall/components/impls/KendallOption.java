/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package zelix.gui.clickguis.kendall.components.impls;

import org.lwjgl.input.Mouse;
import zelix.gui.clickguis.kendall.components.Component;
import zelix.gui.clickguis.kendall.components.impls.KendallButton;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.BooleanValue;

public class KendallOption
extends Component {
    public KendallButton parent;
    public int x;
    public BooleanValue value;
    public int y;
    boolean previousmouse = true;
    boolean mouse;

    public KendallOption(BooleanValue value, KendallButton parent) {
        this.value = value;
        this.parent = parent;
    }

    @Override
    public void render(float x, float y, int mouseX, int mouseY) {
        RenderUtils.drawBorderedRect(x, y, x + 250.0f, y + 29.0f, 0.0f, 0, -14737374);
        FontLoaders.default14.drawString(this.value.getRenderName(), x + 10.0f, y + 5.0f, -1711933961);
        if (((Boolean)this.value.getValue()).booleanValue()) {
            RenderUtils.drawBorderedRect(x + 10.0f, y + 14.0f, x + 10.0f + 24.0f, y + 23.0f, 1.0f, ColorUtils.rainbow(50), ColorUtils.rainbow(50));
            RenderUtils.drawBorderedRect(x + 10.0f + 15.0f, y + 15.0f, x + 10.0f + 22.0f, y + 21.0f, 1.0f, -15198184, -15198184);
        } else {
            RenderUtils.drawBorderedRect(x + 10.0f, y + 14.0f, x + 10.0f + 24.0f, y + 23.0f, 1.0f, -13749703, -13749703);
            RenderUtils.drawBorderedRect(x + 10.0f + 2.0f, y + 15.0f, x + 10.0f + 9.0f, y + 21.0f, 1.0f, ColorUtils.rainbow(50), ColorUtils.rainbow(50));
        }
        if (KendallButton.isButtonHovered(x + 10.0f, y + 13.0f, x + 10.0f + 24.0f, y + 22.0f, mouseX, mouseY)) {
            if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                this.previousmouse = true;
                this.mouse = true;
            }
            if (this.mouse) {
                this.value.setValue((Boolean)this.value.getValue() == false);
                this.mouse = false;
            }
        }
        if (!Mouse.isButtonDown((int)0)) {
            this.previousmouse = false;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
    }
}

