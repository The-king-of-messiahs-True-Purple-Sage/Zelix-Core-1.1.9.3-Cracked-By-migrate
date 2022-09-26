/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 */
package zelix.hack.hacks.xray.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import zelix.hack.hacks.xray.XRay;
import zelix.hack.hacks.xray.gui.GuiSelectionScreen;
import zelix.hack.hacks.xray.gui.utils.GuiBase;

public class GuiHelp
extends GuiBase {
    private List<LinedText> areas = new ArrayList<LinedText>();

    public GuiHelp() {
        super(false);
        this.setSize(380, 210);
    }

    public void initGui() {
        super.initGui();
        this.areas.clear();
        this.areas.add(new LinedText("xray.message.help.state"));
        this.areas.add(new LinedText("xray.message.help.gui"));
        this.areas.add(new LinedText("xray.message.help.warning"));
        this.func_189646_b(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 80, I18n.format((String)"xray.single.close", (Object[])new Object[0])));
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        float lineY = (float)this.height / 2.0f - 85.0f;
        for (LinedText linedText : this.areas) {
            for (String line : linedText.getLines()) {
                this.getFontRender().drawStringWithShadow(line, (float)this.width / 2.0f - 176.0f, lineY += 12.0f, Color.WHITE.getRGB());
            }
            lineY += 10.0f;
        }
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            XRay.mc.thePlayer.closeScreen();
            XRay.mc.displayGuiScreen((GuiScreen)new GuiSelectionScreen());
        }
    }

    @Override
    public boolean hasTitle() {
        return true;
    }

    @Override
    public Color colorBackground() {
        return Color.LIGHT_GRAY;
    }

    @Override
    public String title() {
        return I18n.format((String)"xray.single.help", (Object[])new Object[0]);
    }

    private static class LinedText {
        private String[] lines;

        public LinedText(String key) {
            this.lines = I18n.format((String)key, (Object[])new Object[0]).split("\n");
        }

        public String[] getLines() {
            return this.lines;
        }
    }
}

