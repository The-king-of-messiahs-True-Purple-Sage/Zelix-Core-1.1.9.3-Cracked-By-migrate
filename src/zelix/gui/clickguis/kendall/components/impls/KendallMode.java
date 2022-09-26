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
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.Mode;
import zelix.value.ModeValue;

public class KendallMode
extends Component {
    public ModeValue value;
    public KendallButton parent;
    private int modeIndex;
    boolean previousmouse = true;
    boolean mouse;

    public KendallMode(ModeValue value, KendallButton parent) {
        this.value = value;
        this.parent = parent;
    }

    @Override
    public void render(float x, float y, int mouseX, int mouseY) {
        RenderUtils.drawBorderedRect(x, y, x + 250.0f, y + 29.0f, 0.0f, 0, -14737374);
        FontLoaders.default14.drawString(this.value.getRenderName(), x + 10.0f, y + 5.0f, -1711933961);
        RenderUtils.drawBorderedRect(x + 10.0f, y + 14.0f, x + 250.0f - 10.0f, y + 8.0f + 17.0f, 1.0f, -15658735, -15198184);
        FontLoaders.default16.drawString(this.value.getSelectMode().getName(), x + 10.0f + 2.0f, y + 16.0f, -1);
        if (KendallButton.isButtonHovered(x + 10.0f, y + 14.0f, x + 250.0f - 10.0f, y + 8.0f + 16.0f, mouseX, mouseY)) {
            if (!this.previousmouse && Mouse.isButtonDown((int)0)) {
                this.previousmouse = true;
                this.mouse = true;
            }
            if (this.mouse) {
                for (Mode sjh : this.value.getModes()) {
                    sjh.setToggled(false);
                }
                int maxIndex = this.value.getModes().length;
                ++this.modeIndex;
                if (this.modeIndex + 1 > maxIndex) {
                    this.modeIndex = 0;
                }
                this.value.getModes()[this.modeIndex].setToggled(true);
                this.mouse = false;
            }
        }
        if (!Mouse.isButtonDown((int)0)) {
            this.previousmouse = false;
        }
    }
}

