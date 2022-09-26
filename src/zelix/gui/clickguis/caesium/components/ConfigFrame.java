/*
 * Decompiled with CFR 0.152.
 */
package zelix.gui.clickguis.caesium.components;

import zelix.Core;
import zelix.gui.clickguis.caesium.components.GuiComponent;
import zelix.gui.cloudconfig.CloudConfig;
import zelix.managers.FontManager;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.hooks.visual.ColorUtils;
import zelix.utils.hooks.visual.HGLUtils;

public class ConfigFrame
implements GuiComponent {
    public String name;
    public String author;
    public String des;
    public String uuid;
    public int posX;
    public int posY;
    public static FontManager font = new FontManager();

    public ConfigFrame(String nameX, String authorX, String uuidX) {
        this.name = nameX;
        this.author = authorX;
        this.uuid = uuidX;
    }

    public boolean isUserDev() {
        return this.author.equalsIgnoreCase(Core.UN);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        this.posX = posX;
        this.posY = posY;
        HGLUtils.drawGradientRect(posX, posY, posX + 120, posY + 100, ColorUtils.getColor(51, 153, 255));
        font.getFont("SFR 11").drawString("[Config-Name]" + this.name, posX + 5, posY + 10, ColorUtils.getColor(32, 32, 32));
        font.getFont("SFR 11").drawString("[Config-Author]" + this.author, posX + 5, posY + 30, ColorUtils.getColor(32, 32, 32));
        font.getFont("SFR 11").drawString("[Config-UUID]" + this.uuid, posX + 5, posY + 50, ColorUtils.getColor(32, 32, 32));
        if (this.author.equalsIgnoreCase(Core.UN)) {
            HGLUtils.drawGradientRect(posX + 10, posY + 65, posX + 55, posY + 85, ColorUtils.getColor(224, 224, 224));
            font.getFont("SFR 14").drawString("Load", posX + 21, posY + 70, ColorUtils.getColor(32, 32, 32));
            HGLUtils.drawGradientRect(posX + 65, posY + 65, posX + 110, posY + 85, ColorUtils.getColor(224, 224, 224));
            font.getFont("SFR 14").drawString("UPDATE", posX + 70, posY + 70, ColorUtils.getColor(32, 32, 32));
        } else {
            HGLUtils.drawGradientRect(posX + 30, posY + 65, posX + 90, posY + 85, ColorUtils.getColor(224, 224, 224));
            font.getFont("SFR 14").drawString("Load", posX + 50, posY + 70, ColorUtils.getColor(32, 32, 32));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.author.equalsIgnoreCase(Core.UN)) {
            if (CloudConfig.isHovered(this.posX + 10, this.posY + 65, this.posX + 55, this.posY + 85, mouseX, mouseY) && mouseButton == 0) {
                ChatUtils.message("Start Loading... Config: " + this.uuid);
                CloudConfig.LoadConfig(this.uuid);
            }
            if (CloudConfig.isHovered(this.posX + 65, this.posY + 65, this.posX + 110, this.posY + 85, mouseX, mouseY) && mouseButton == 0) {
                ChatUtils.message("Start Updating... Config: " + this.uuid);
                CloudConfig.UpdateConfig(this.uuid, this.name);
            }
        } else if (CloudConfig.isHovered(this.posX + 30, this.posY + 65, this.posX + 90, this.posY + 85, mouseX, mouseY) && mouseButton == 0) {
            ChatUtils.message("Start Loading... Config: " + this.uuid);
            CloudConfig.LoadConfig(this.uuid);
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}

