/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 */
package zelix.gui.Notification;

import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import zelix.Core;
import zelix.gui.Notification.Utils.AnimationUtils;
import zelix.gui.clickguis.huangbai.ClickGuiRender;
import zelix.utils.Wrapper;
import zelix.utils.hooks.visual.Colors;
import zelix.utils.hooks.visual.RenderUtils;
import zelix.utils.hooks.visual.font.render.TTFFontRenderer;

public class Notification {
    public String text;
    TTFFontRenderer sigmaFont = Core.fontManager.getFont("SFB 8");
    public double width = 30.0;
    public double height = 20.0;
    public float x;
    Type type;
    public float y;
    public float position;
    public boolean in = true;
    public AnimationUtils animationUtils = new AnimationUtils();
    AnimationUtils yAnimationUtils = new AnimationUtils();

    public Notification(String text, Type type) {
        this.text = text;
        this.type = type;
        this.width = this.sigmaFont.getWidth(text) + 25.0f;
        this.x = (float)this.width;
    }

    public void onRender() {
        int i = 0;
        for (Notification notification : Core.notificationManager.notifications) {
            if (notification == this) break;
            ++i;
        }
        this.y = this.yAnimationUtils.animate((float)((double)i * (this.height + 5.0)), this.y, 0.1f);
        ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
        RenderUtils.drawRect((double)((float)sr.getScaledWidth() + this.x) - this.width, (double)((float)(sr.getScaledHeight() - 55) - this.y) - this.height, (double)((float)sr.getScaledWidth() + this.x), (double)((float)(sr.getScaledHeight() - 55) - this.y), Colors.getColor(Color.black));
        ClickGuiRender.drawFilledCircle((double)((float)sr.getScaledWidth() + this.x) - this.width, (double)((float)(sr.getScaledHeight() - 45) - this.y) - this.height, 10.0, Colors.getColor(Color.black), 5);
        this.sigmaFont.drawStringWithShadow(this.text, (float)((double)((float)sr.getScaledWidth() + this.x) - this.width + 10.0), (float)sr.getScaledHeight() - 50.0f - this.y - 18.0f, new Color(204, 204, 204, 232).getRGB());
    }

    public static enum Type {
        Success,
        Error,
        Info;

    }
}

