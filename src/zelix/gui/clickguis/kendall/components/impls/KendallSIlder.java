/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.gui.clickguis.kendall.components.impls;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.gui.clickguis.N3ro.Utils.RenderUtil;
import zelix.gui.clickguis.kendall.components.Component;
import zelix.gui.clickguis.kendall.components.impls.KendallButton;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.value.NumberValue;

public class KendallSIlder
extends Component {
    public NumberValue value;
    public KendallButton parent;
    public float x;
    public float y;
    public double moveSSL;
    public int per = 0;

    public KendallSIlder(NumberValue value, KendallButton parent) {
        this.value = value;
        this.parent = parent;
    }

    @Override
    public void render(float x, float y, int mouseX, int mouseY) {
        RenderUtils.drawBorderedRect(x, y, x + 250.0f, y + 29.0f, 0.0f, 0, -14737374);
        FontLoaders.default14.drawString(this.value.getRenderName(), x + 10.0f, y + 5.0f, -1711933961);
        FontLoaders.default14.drawString(((Double)this.value.getValue()).toString(), x + 240.0f - (float)FontLoaders.default14.getStringWidth(((Double)this.value.getValue()).toString()), y + 5.0f, -1711933961);
        RenderUtils.drawRoundRect(x + 10.0f, y + 15.0f, x + 10.0f + 230.0f, y + 20.0f, 2, -15198184);
        float range = this.value.getMax().floatValue() - this.value.getMin().floatValue();
        float precent = range / 100.0f;
        this.per = (int)((((Double)this.value.getValue()).floatValue() - this.value.getMin().floatValue()) / precent);
        GL11.glEnable((int)259);
        GL11.glEnable((int)3042);
        RenderUtil.drawRoundRect(x + 10.0f, y + 15.0f, (double)(x + 10.0f) + 2.3 * (double)this.per, y + 20.0f, 1, 2, ColorUtils.rainbow(20));
        RenderUtil.drawRoundRect((double)(x + 10.0f) + 2.3 * (double)this.per - 3.0, y + 11.0f, (double)(x + 10.0f) + 2.3 * (double)this.per + 3.0, y + 24.0f, 0, 1, ColorUtils.rainbow(20));
        GL11.glDisable((int)3042);
        GL11.glDisable((int)259);
        if (KendallButton.isButtonHovered(x + 10.0f, y + 11.0f, x + 10.0f + 230.0f, y + 24.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            double inc = 0.01;
            double valAbs = (double)mouseX - ((double)x + 10.0 + 1.0);
            double perc = valAbs / 230.0;
            perc = Math.min(Math.max(0.0, perc), 1.0);
            double valRel = (this.value.getMax() - this.value.getMin()) * perc;
            double val = this.value.getMin() + valRel;
            val = (double)Math.round(val * (1.0 / inc)) / (1.0 / inc);
            this.value.setValue(val);
        }
    }
}

