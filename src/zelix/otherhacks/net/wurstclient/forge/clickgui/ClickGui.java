/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package zelix.otherhacks.net.wurstclient.forge.clickgui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zelix.otherhacks.net.wurstclient.forge.Category;
import zelix.otherhacks.net.wurstclient.forge.ForgeWurst;
import zelix.otherhacks.net.wurstclient.forge.Hack;
import zelix.otherhacks.net.wurstclient.forge.HackList;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Component;
import zelix.otherhacks.net.wurstclient.forge.clickgui.HackButton;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Popup;
import zelix.otherhacks.net.wurstclient.forge.clickgui.Window;
import zelix.otherhacks.net.wurstclient.forge.compatibility.WMinecraft;
import zelix.otherhacks.net.wurstclient.forge.settings.Setting;
import zelix.otherhacks.net.wurstclient.forge.utils.JsonUtils;

public final class ClickGui {
    private final ArrayList<Window> windows = new ArrayList();
    private final ArrayList<Popup> popups = new ArrayList();
    private final Path windowsFile;
    private float[] bgColor = new float[3];
    private float[] acColor = new float[3];
    private float opacity;
    private int maxHeight;
    private String tooltip;

    public ClickGui(Path windowsFile) {
        this.windowsFile = windowsFile;
    }

    public void init(HackList hax) {
        JsonObject json;
        LinkedHashMap<Category, Window> windowMap = new LinkedHashMap<Category, Window>();
        for (Category category : Category.values()) {
            windowMap.put(category, new Window(category.getName()));
        }
        for (Hack hack : hax.getRegistry()) {
            if (hack.getCategory() == null) continue;
            ((Window)windowMap.get((Object)hack.getCategory())).add(new HackButton(hack));
        }
        this.windows.addAll(windowMap.values());
        Window uiSettings = new Window("UI Settings");
        for (Setting setting : hax.clickGuiHack.getSettings().values()) {
            uiSettings.add(setting.getComponent());
        }
        this.windows.add(uiSettings);
        for (Window window : this.windows) {
            window.setMinimized(true);
        }
        this.windows.add(hax.radarHack.getWindow());
        int x = 5;
        int y = 5;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        for (Window window : this.windows) {
            window.pack();
            if (x + window.getWidth() + 5 > sr.getScaledWidth()) {
                x = 5;
                y += 18;
            }
            window.setX(x);
            window.setY(y);
            x += window.getWidth() + 5;
        }
        try (BufferedReader reader = Files.newBufferedReader(this.windowsFile);){
            json = JsonUtils.jsonParser.parse((Reader)reader).getAsJsonObject();
        }
        catch (NoSuchFileException e) {
            this.saveWindows();
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.saveWindows();
            return;
        }
        for (Window window : this.windows) {
            JsonElement jsonPinned;
            JsonElement jsonMinimized;
            JsonElement jsonY;
            JsonElement jsonWindow = json.get(window.getTitle());
            if (jsonWindow == null || !jsonWindow.isJsonObject()) continue;
            JsonElement jsonX = jsonWindow.getAsJsonObject().get("x");
            if (jsonX.isJsonPrimitive() && jsonX.getAsJsonPrimitive().isNumber()) {
                window.setX(jsonX.getAsInt());
            }
            if ((jsonY = jsonWindow.getAsJsonObject().get("y")).isJsonPrimitive() && jsonY.getAsJsonPrimitive().isNumber()) {
                window.setY(jsonY.getAsInt());
            }
            if ((jsonMinimized = jsonWindow.getAsJsonObject().get("minimized")).isJsonPrimitive() && jsonMinimized.getAsJsonPrimitive().isBoolean()) {
                window.setMinimized(jsonMinimized.getAsBoolean());
            }
            if (!(jsonPinned = jsonWindow.getAsJsonObject().get("pinned")).isJsonPrimitive() || !jsonPinned.getAsJsonPrimitive().isBoolean()) continue;
            window.setPinned(jsonPinned.getAsBoolean());
        }
        this.saveWindows();
    }

    private void saveWindows() {
        JsonObject json = new JsonObject();
        for (Window window : this.windows) {
            if (window.isClosable()) continue;
            JsonObject jsonWindow = new JsonObject();
            jsonWindow.addProperty("x", (Number)window.getX());
            jsonWindow.addProperty("y", (Number)window.getY());
            jsonWindow.addProperty("minimized", Boolean.valueOf(window.isMinimized()));
            jsonWindow.addProperty("pinned", Boolean.valueOf(window.isPinned()));
            json.add(window.getTitle(), (JsonElement)jsonWindow);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(this.windowsFile, new OpenOption[0]);){
            JsonUtils.prettyGson.toJson((JsonElement)json, (Appendable)writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        boolean popupClicked = this.handlePopupMouseClick(mouseX, mouseY, mouseButton);
        if (!popupClicked) {
            this.handleWindowMouseClick(mouseX, mouseY, mouseButton);
        }
        for (Popup popup : this.popups) {
            if (!popup.getOwner().getParent().isClosing()) continue;
            popup.close();
        }
        this.windows.removeIf(w -> w.isClosing());
        this.popups.removeIf(p -> p.isClosing());
    }

    private boolean handlePopupMouseClick(int mouseX, int mouseY, int mouseButton) {
        for (int i = this.popups.size() - 1; i >= 0; --i) {
            Popup popup = this.popups.get(i);
            Component owner = popup.getOwner();
            Window parent = owner.getParent();
            int x0 = parent.getX() + owner.getX();
            int y0 = parent.getY() + 13 + parent.getScrollOffset() + owner.getY();
            int x1 = x0 + popup.getX();
            int y1 = y0 + popup.getY();
            int x2 = x1 + popup.getWidth();
            int y2 = y1 + popup.getHeight();
            if (mouseX < x1 || mouseY < y1 || mouseX >= x2 || mouseY >= y2) continue;
            int cMouseX = mouseX - x0;
            int cMouseY = mouseY - y0;
            popup.handleMouseClick(cMouseX, cMouseY, mouseButton);
            this.popups.remove(i);
            this.popups.add(popup);
            return true;
        }
        return false;
    }

    private void handleWindowMouseClick(int mouseX, int mouseY, int mouseButton) {
        for (int i = this.windows.size() - 1; i >= 0; --i) {
            Window window = this.windows.get(i);
            if (window.isInvisible()) continue;
            int x1 = window.getX();
            int y1 = window.getY();
            int x2 = x1 + window.getWidth();
            int y2 = y1 + window.getHeight();
            int y3 = y1 + 13;
            if (mouseX < x1 || mouseY < y1 || mouseX >= x2 || mouseY >= y2) continue;
            if (mouseY < y3) {
                this.handleTitleBarMouseClick(window, mouseX, mouseY, mouseButton);
            } else {
                if (window.isMinimized()) continue;
                window.validate();
                int cMouseX = mouseX - x1;
                int cMouseY = mouseY - y3;
                if (window.isScrollingEnabled() && mouseX >= x2 - 3) {
                    this.handleScrollbarMouseClick(window, cMouseX, cMouseY, mouseButton);
                } else {
                    if (window.isScrollingEnabled()) {
                        cMouseY -= window.getScrollOffset();
                    }
                    this.handleComponentMouseClick(window, cMouseX, cMouseY, mouseButton);
                }
            }
            this.windows.remove(i);
            this.windows.add(window);
            break;
        }
    }

    private void handleTitleBarMouseClick(Window window, int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return;
        }
        if (mouseY < window.getY() + 2 || mouseY >= window.getY() + 11) {
            window.startDragging(mouseX, mouseY);
            return;
        }
        int x3 = window.getX() + window.getWidth();
        if (window.isClosable() && mouseX >= (x3 -= 11) && mouseX < x3 + 9) {
            window.close();
            return;
        }
        if (window.isPinnable() && mouseX >= (x3 -= 11) && mouseX < x3 + 9) {
            window.setPinned(!window.isPinned());
            this.saveWindows();
            return;
        }
        if (window.isMinimizable() && mouseX >= (x3 -= 11) && mouseX < x3 + 9) {
            window.setMinimized(!window.isMinimized());
            this.saveWindows();
            return;
        }
        window.startDragging(mouseX, mouseY);
    }

    private void handleScrollbarMouseClick(Window window, int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return;
        }
        if (mouseX >= window.getWidth() - 1) {
            return;
        }
        double outerHeight = window.getHeight() - 13;
        double innerHeight = window.getInnerHeight();
        double maxScrollbarHeight = outerHeight - 2.0;
        int scrollbarY = (int)(outerHeight * ((double)(-window.getScrollOffset()) / innerHeight) + 1.0);
        int scrollbarHeight = (int)(maxScrollbarHeight * outerHeight / innerHeight);
        if (mouseY < scrollbarY || mouseY >= scrollbarY + scrollbarHeight) {
            return;
        }
        window.startDraggingScrollbar(window.getY() + 13 + mouseY);
    }

    private void handleComponentMouseClick(Window window, int mouseX, int mouseY, int mouseButton) {
        for (int i2 = window.countChildren() - 1; i2 >= 0; --i2) {
            Component c = window.getChild(i2);
            if (mouseX < c.getX() || mouseY < c.getY() || mouseX >= c.getX() + c.getWidth() || mouseY >= c.getY() + c.getHeight()) continue;
            c.handleMouseClick(mouseX, mouseY, mouseButton);
            break;
        }
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)1.0f);
        int dWheel = Mouse.getDWheel();
        if (dWheel != 0) {
            for (int i = this.windows.size() - 1; i >= 0; --i) {
                Window window = this.windows.get(i);
                if (!window.isScrollingEnabled() || window.isMinimized() || window.isInvisible() || mouseX < window.getX() || mouseY < window.getY() + 13 || mouseX >= window.getX() + window.getWidth() || mouseY >= window.getY() + window.getHeight()) continue;
                int scroll = window.getScrollOffset() + dWheel / 16;
                scroll = Math.min(scroll, 0);
                scroll = Math.max(scroll, -window.getInnerHeight() + window.getHeight() - 13);
                window.setScrollOffset(scroll);
                break;
            }
        }
        this.tooltip = null;
        for (Window window : this.windows) {
            if (window.isInvisible()) continue;
            if (window.isDragging()) {
                if (Mouse.isButtonDown((int)0)) {
                    window.dragTo(mouseX, mouseY);
                } else {
                    window.stopDragging();
                    this.saveWindows();
                }
            }
            if (window.isDraggingScrollbar()) {
                if (Mouse.isButtonDown((int)0)) {
                    window.dragScrollbarTo(mouseY);
                } else {
                    window.stopDraggingScrollbar();
                }
            }
            this.renderWindow(window, mouseX, mouseY, partialTicks);
        }
        GL11.glDisable((int)3553);
        for (Popup popup : this.popups) {
            Component owner = popup.getOwner();
            Window parent = owner.getParent();
            int x1 = parent.getX() + owner.getX();
            int y1 = parent.getY() + 13 + parent.getScrollOffset() + owner.getY();
            GL11.glPushMatrix();
            GL11.glTranslated((double)x1, (double)y1, (double)0.0);
            int cMouseX = mouseX - x1;
            int cMouseY = mouseY - y1;
            popup.render(cMouseX, cMouseY);
            GL11.glPopMatrix();
        }
        if (this.tooltip != null) {
            String[] lines = this.tooltip.split("\n");
            Minecraft mc = Minecraft.getMinecraft();
            FontRenderer fr = WMinecraft.getFontRenderer();
            int tw = 0;
            int th = lines.length * fr.FONT_HEIGHT;
            for (String line : lines) {
                int lw = fr.getStringWidth(line);
                if (lw <= tw) continue;
                tw = lw;
            }
            int sw = mc.currentScreen.width;
            int sh = mc.currentScreen.height;
            int xt1 = mouseX + tw + 11 <= sw ? mouseX + 8 : mouseX - tw - 8;
            int xt2 = xt1 + tw + 3;
            int yt1 = mouseY + th - 2 <= sh ? mouseY - 4 : mouseY - th - 4;
            int yt2 = yt1 + th + 2;
            GL11.glDisable((int)3553);
            GL11.glColor4f((float)this.bgColor[0], (float)this.bgColor[1], (float)this.bgColor[2], (float)0.75f);
            GL11.glBegin((int)7);
            GL11.glVertex2i((int)xt1, (int)yt1);
            GL11.glVertex2i((int)xt1, (int)yt2);
            GL11.glVertex2i((int)xt2, (int)yt2);
            GL11.glVertex2i((int)xt2, (int)yt1);
            GL11.glEnd();
            GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)0.5f);
            GL11.glBegin((int)2);
            GL11.glVertex2i((int)xt1, (int)yt1);
            GL11.glVertex2i((int)xt1, (int)yt2);
            GL11.glVertex2i((int)xt2, (int)yt2);
            GL11.glVertex2i((int)xt2, (int)yt1);
            GL11.glEnd();
            GL11.glEnable((int)3553);
            for (int i = 0; i < lines.length; ++i) {
                fr.drawString(lines[i], xt1 + 2, yt1 + 2 + i * fr.FONT_HEIGHT, 0xFFFFFF);
            }
        }
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public void renderPinnedWindows(float partialTicks) {
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)1.0f);
        for (Window window : this.windows) {
            if (!window.isPinned() || window.isInvisible()) continue;
            this.renderWindow(window, Integer.MIN_VALUE, Integer.MIN_VALUE, partialTicks);
        }
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3553);
    }

    public void updateColors() {
        HackList hax = ForgeWurst.getForgeWurst().getHax();
        this.opacity = hax.clickGuiHack.getOpacity();
        this.bgColor = hax.clickGuiHack.getBgColor();
        this.maxHeight = hax.clickGuiHack.getMaxHeight();
        if (hax.rainbowUiHack.isEnabled()) {
            float x = (float)(System.currentTimeMillis() % 2000L) / 1000.0f;
            this.acColor[0] = 0.5f + 0.5f * (float)Math.sin((double)x * Math.PI);
            this.acColor[1] = 0.5f + 0.5f * (float)Math.sin((double)(x + 1.3333334f) * Math.PI);
            this.acColor[2] = 0.5f + 0.5f * (float)Math.sin((double)(x + 2.6666667f) * Math.PI);
        } else {
            this.acColor = hax.clickGuiHack.getAcColor();
        }
    }

    private void renderWindow(Window window, int mouseX, int mouseY, float partialTicks) {
        boolean hovering;
        int x4;
        boolean hoveringY;
        int x3;
        int x1 = window.getX();
        int y1 = window.getY();
        int x2 = x1 + window.getWidth();
        int y2 = y1 + window.getHeight();
        int y3 = y1 + 13;
        if (window.isMinimized()) {
            y2 = y3;
        }
        if (mouseX >= x1 && mouseY >= y1 && mouseX < x2 && mouseY < y2) {
            this.tooltip = null;
        }
        GL11.glDisable((int)3553);
        if (!window.isMinimized()) {
            int yc1;
            window.setMaxHeight(this.maxHeight);
            window.validate();
            if (window.isScrollingEnabled()) {
                int xs1 = x2 - 3;
                int xs2 = xs1 + 2;
                int xs3 = x2;
                double outerHeight = y2 - y3;
                double innerHeight = window.getInnerHeight();
                double maxScrollbarHeight = outerHeight - 2.0;
                double scrollbarY = outerHeight * ((double)(-window.getScrollOffset()) / innerHeight) + 1.0;
                double scrollbarHeight = maxScrollbarHeight * outerHeight / innerHeight;
                int ys1 = y3;
                int ys2 = y2;
                int ys3 = ys1 + (int)scrollbarY;
                int ys4 = ys3 + (int)scrollbarHeight;
                GL11.glColor4f((float)this.bgColor[0], (float)this.bgColor[1], (float)this.bgColor[2], (float)this.opacity);
                GL11.glBegin((int)7);
                GL11.glVertex2i((int)xs2, (int)ys1);
                GL11.glVertex2i((int)xs2, (int)ys2);
                GL11.glVertex2i((int)xs3, (int)ys2);
                GL11.glVertex2i((int)xs3, (int)ys1);
                GL11.glVertex2i((int)xs1, (int)ys1);
                GL11.glVertex2i((int)xs1, (int)ys3);
                GL11.glVertex2i((int)xs2, (int)ys3);
                GL11.glVertex2i((int)xs2, (int)ys1);
                GL11.glVertex2i((int)xs1, (int)ys4);
                GL11.glVertex2i((int)xs1, (int)ys2);
                GL11.glVertex2i((int)xs2, (int)ys2);
                GL11.glVertex2i((int)xs2, (int)ys4);
                GL11.glEnd();
                boolean hovering2 = mouseX >= xs1 && mouseY >= ys3 && mouseX < xs2 && mouseY < ys4;
                GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)(hovering2 ? this.opacity * 1.5f : this.opacity));
                GL11.glBegin((int)7);
                GL11.glVertex2i((int)xs1, (int)ys3);
                GL11.glVertex2i((int)xs1, (int)ys4);
                GL11.glVertex2i((int)xs2, (int)ys4);
                GL11.glVertex2i((int)xs2, (int)ys3);
                GL11.glEnd();
                GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)0.5f);
                GL11.glBegin((int)2);
                GL11.glVertex2i((int)xs1, (int)ys3);
                GL11.glVertex2i((int)xs1, (int)ys4);
                GL11.glVertex2i((int)xs2, (int)ys4);
                GL11.glVertex2i((int)xs2, (int)ys3);
                GL11.glEnd();
            }
            x3 = x1 + 2;
            int x42 = window.isScrollingEnabled() ? x2 - 3 : x2;
            int x5 = x42 - 2;
            int y4 = y3 + window.getScrollOffset();
            GL11.glColor4f((float)this.bgColor[0], (float)this.bgColor[1], (float)this.bgColor[2], (float)this.opacity);
            GL11.glBegin((int)7);
            GL11.glVertex2i((int)x1, (int)y3);
            GL11.glVertex2i((int)x1, (int)y2);
            GL11.glVertex2i((int)x3, (int)y2);
            GL11.glVertex2i((int)x3, (int)y3);
            GL11.glVertex2i((int)x5, (int)y3);
            GL11.glVertex2i((int)x5, (int)y2);
            GL11.glVertex2i((int)x42, (int)y2);
            GL11.glVertex2i((int)x42, (int)y3);
            GL11.glEnd();
            if (window.isScrollingEnabled()) {
                ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
                int sf = sr.getScaleFactor();
                GL11.glScissor((int)(x1 * sf), (int)((int)((sr.getScaledHeight_double() - (double)y2) * (double)sf)), (int)(window.getWidth() * sf), (int)((y2 - y3) * sf));
                GL11.glEnable((int)3089);
            }
            GL11.glPushMatrix();
            GL11.glTranslated((double)x1, (double)y4, (double)0.0);
            GL11.glColor4f((float)this.bgColor[0], (float)this.bgColor[1], (float)this.bgColor[2], (float)this.opacity);
            GL11.glBegin((int)7);
            int xc1 = 2;
            int xc2 = x5 - x1;
            for (int i = 0; i < window.countChildren(); ++i) {
                int yc12 = window.getChild(i).getY();
                int yc2 = yc12 - 2;
                GL11.glVertex2i((int)xc1, (int)yc2);
                GL11.glVertex2i((int)xc1, (int)yc12);
                GL11.glVertex2i((int)xc2, (int)yc12);
                GL11.glVertex2i((int)xc2, (int)yc2);
            }
            if (window.countChildren() == 0) {
                yc1 = 0;
            } else {
                Component lastChild = window.getChild(window.countChildren() - 1);
                yc1 = lastChild.getY() + lastChild.getHeight();
            }
            int yc2 = yc1 + 2;
            GL11.glVertex2i((int)xc1, (int)yc2);
            GL11.glVertex2i((int)xc1, (int)yc1);
            GL11.glVertex2i((int)xc2, (int)yc1);
            GL11.glVertex2i((int)xc2, (int)yc2);
            GL11.glEnd();
            int cMouseX = mouseX - x1;
            int cMouseY = mouseY - y4;
            for (int i = 0; i < window.countChildren(); ++i) {
                window.getChild(i).render(cMouseX, cMouseY, partialTicks);
            }
            GL11.glPopMatrix();
            if (window.isScrollingEnabled()) {
                GL11.glDisable((int)3089);
            }
        }
        GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        if (!window.isMinimized()) {
            GL11.glBegin((int)1);
            GL11.glVertex2i((int)x1, (int)y3);
            GL11.glVertex2i((int)x2, (int)y3);
            GL11.glEnd();
        }
        x3 = x2;
        int y4 = y1 + 2;
        int y5 = y3 - 2;
        boolean bl = hoveringY = mouseY >= y4 && mouseY < y5;
        if (window.isClosable()) {
            x4 = (x3 -= 11) + 9;
            hovering = hoveringY && mouseX >= x3 && mouseX < x4;
            this.renderCloseButton(x3, y4, x4, y5, hovering);
        }
        if (window.isPinnable()) {
            x4 = (x3 -= 11) + 9;
            hovering = hoveringY && mouseX >= x3 && mouseX < x4;
            this.renderPinButton(x3, y4, x4, y5, hovering, window.isPinned());
        }
        if (window.isMinimizable()) {
            x4 = (x3 -= 11) + 9;
            hovering = hoveringY && mouseX >= x3 && mouseX < x4;
            this.renderMinimizeButton(x3, y4, x4, y5, hovering, window.isMinimized());
        }
        GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)this.opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glVertex2i((int)x3, (int)y4);
        GL11.glVertex2i((int)x2, (int)y4);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glVertex2i((int)x3, (int)y5);
        GL11.glVertex2i((int)x3, (int)y3);
        GL11.glVertex2i((int)x2, (int)y3);
        GL11.glVertex2i((int)x2, (int)y5);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y3);
        GL11.glVertex2i((int)x3, (int)y3);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        FontRenderer fontRenderer = WMinecraft.getFontRenderer();
        String title = fontRenderer.trimStringToWidth(window.getTitle(), x3 - x1);
        fontRenderer.drawString(title, x1 + 2, y1 + 3, 0xF0F0F0);
    }

    private void renderTitleBarButton(int x1, int y1, int x2, int y2, boolean hovering) {
        int x3 = x2 + 2;
        GL11.glColor4f((float)this.bgColor[0], (float)this.bgColor[1], (float)this.bgColor[2], (float)(hovering ? this.opacity * 1.5f : this.opacity));
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)this.opacity);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x3, (int)y2);
        GL11.glVertex2i((int)x3, (int)y1);
        GL11.glEnd();
        GL11.glColor4f((float)this.acColor[0], (float)this.acColor[1], (float)this.acColor[2], (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glVertex2i((int)x1, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glVertex2i((int)x2, (int)y1);
        GL11.glEnd();
    }

    private void renderMinimizeButton(int x1, int y1, int x2, int y2, boolean hovering, boolean minimized) {
        double ya2;
        double ya1;
        this.renderTitleBarButton(x1, y1, x2, y2, hovering);
        double xa1 = x1 + 1;
        double xa2 = (double)(x1 + x2) / 2.0;
        double xa3 = x2 - 1;
        if (minimized) {
            ya1 = y1 + 3;
            ya2 = (double)y2 - 2.5;
            GL11.glColor4f((float)0.0f, (float)(hovering ? 1.0f : 0.85f), (float)0.0f, (float)1.0f);
        } else {
            ya1 = y2 - 3;
            ya2 = (double)y1 + 2.5;
            GL11.glColor4f((float)(hovering ? 1.0f : 0.85f), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GL11.glBegin((int)4);
        GL11.glVertex2d((double)xa1, (double)ya1);
        GL11.glVertex2d((double)xa3, (double)ya1);
        GL11.glVertex2d((double)xa2, (double)ya2);
        GL11.glEnd();
        GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)xa1, (double)ya1);
        GL11.glVertex2d((double)xa3, (double)ya1);
        GL11.glVertex2d((double)xa2, (double)ya2);
        GL11.glEnd();
    }

    private void renderPinButton(int x1, int y1, int x2, int y2, boolean hovering, boolean pinned) {
        float h;
        this.renderTitleBarButton(x1, y1, x2, y2, hovering);
        float f = h = hovering ? 1.0f : 0.85f;
        if (pinned) {
            double xk1 = x1 + 2;
            double xk2 = x2 - 2;
            double xk3 = x1 + 1;
            double xk4 = x2 - 1;
            double yk1 = y1 + 2;
            double yk2 = y2 - 2;
            double yk3 = (double)y2 - 0.5;
            GL11.glColor4f((float)h, (float)0.0f, (float)0.0f, (float)0.5f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)xk1, (double)yk1);
            GL11.glVertex2d((double)xk2, (double)yk1);
            GL11.glVertex2d((double)xk2, (double)yk2);
            GL11.glVertex2d((double)xk1, (double)yk2);
            GL11.glVertex2d((double)xk3, (double)yk2);
            GL11.glVertex2d((double)xk4, (double)yk2);
            GL11.glVertex2d((double)xk4, (double)yk3);
            GL11.glVertex2d((double)xk3, (double)yk3);
            GL11.glEnd();
            double xn1 = (double)x1 + 3.5;
            double xn2 = (double)x2 - 3.5;
            double yn1 = (double)y2 - 0.5;
            double yn2 = y2;
            GL11.glColor4f((float)h, (float)h, (float)h, (float)1.0f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)xn1, (double)yn1);
            GL11.glVertex2d((double)xn2, (double)yn1);
            GL11.glVertex2d((double)xn2, (double)yn2);
            GL11.glVertex2d((double)xn1, (double)yn2);
            GL11.glEnd();
            GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xk1, (double)yk1);
            GL11.glVertex2d((double)xk2, (double)yk1);
            GL11.glVertex2d((double)xk2, (double)yk2);
            GL11.glVertex2d((double)xk1, (double)yk2);
            GL11.glEnd();
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xk3, (double)yk2);
            GL11.glVertex2d((double)xk4, (double)yk2);
            GL11.glVertex2d((double)xk4, (double)yk3);
            GL11.glVertex2d((double)xk3, (double)yk3);
            GL11.glEnd();
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xn1, (double)yn1);
            GL11.glVertex2d((double)xn2, (double)yn1);
            GL11.glVertex2d((double)xn2, (double)yn2);
            GL11.glVertex2d((double)xn1, (double)yn2);
            GL11.glEnd();
        } else {
            double xk1 = (double)x2 - 3.5;
            double xk2 = (double)x2 - 0.5;
            double xk3 = x2 - 3;
            double xk4 = x1 + 3;
            double xk5 = x1 + 2;
            double xk6 = x2 - 2;
            double xk7 = x1 + 1;
            double yk1 = (double)y1 + 0.5;
            double yk2 = (double)y1 + 3.5;
            double yk3 = y2 - 3;
            double yk4 = y1 + 3;
            double yk5 = y1 + 2;
            double yk6 = y2 - 2;
            double yk7 = y2 - 1;
            GL11.glColor4f((float)0.0f, (float)h, (float)0.0f, (float)1.0f);
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)xk1, (double)yk1);
            GL11.glVertex2d((double)xk2, (double)yk2);
            GL11.glVertex2d((double)xk3, (double)yk3);
            GL11.glVertex2d((double)xk4, (double)yk4);
            GL11.glVertex2d((double)xk5, (double)yk5);
            GL11.glVertex2d((double)xk6, (double)yk6);
            GL11.glVertex2d((double)xk3, (double)yk7);
            GL11.glVertex2d((double)xk7, (double)yk4);
            GL11.glEnd();
            double xn1 = x1 + 3;
            double xn2 = x1 + 4;
            double xn3 = x1 + 1;
            double yn1 = y2 - 4;
            double yn2 = y2 - 3;
            double yn3 = y2 - 1;
            GL11.glColor4f((float)h, (float)h, (float)h, (float)1.0f);
            GL11.glBegin((int)4);
            GL11.glVertex2d((double)xn1, (double)yn1);
            GL11.glVertex2d((double)xn2, (double)yn2);
            GL11.glVertex2d((double)xn3, (double)yn3);
            GL11.glEnd();
            GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xk1, (double)yk1);
            GL11.glVertex2d((double)xk2, (double)yk2);
            GL11.glVertex2d((double)xk3, (double)yk3);
            GL11.glVertex2d((double)xk4, (double)yk4);
            GL11.glEnd();
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xk5, (double)yk5);
            GL11.glVertex2d((double)xk6, (double)yk6);
            GL11.glVertex2d((double)xk3, (double)yk7);
            GL11.glVertex2d((double)xk7, (double)yk4);
            GL11.glEnd();
            GL11.glBegin((int)2);
            GL11.glVertex2d((double)xn1, (double)yn1);
            GL11.glVertex2d((double)xn2, (double)yn2);
            GL11.glVertex2d((double)xn3, (double)yn3);
            GL11.glEnd();
        }
    }

    private void renderCloseButton(int x1, int y1, int x2, int y2, boolean hovering) {
        this.renderTitleBarButton(x1, y1, x2, y2, hovering);
        double xc1 = x1 + 2;
        double xc2 = x1 + 3;
        double xc3 = x2 - 2;
        double xc4 = x2 - 3;
        double xc5 = (double)x1 + 3.5;
        double xc6 = (double)(x1 + x2) / 2.0;
        double xc7 = (double)x2 - 3.5;
        double yc1 = y1 + 3;
        double yc2 = y1 + 2;
        double yc3 = y2 - 3;
        double yc4 = y2 - 2;
        double yc5 = (double)y1 + 3.5;
        double yc6 = (double)(y1 + y2) / 2.0;
        double yc7 = (double)y2 - 3.5;
        GL11.glColor4f((float)(hovering ? 1.0f : 0.85f), (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)xc1, (double)yc1);
        GL11.glVertex2d((double)xc2, (double)yc2);
        GL11.glVertex2d((double)xc3, (double)yc3);
        GL11.glVertex2d((double)xc4, (double)yc4);
        GL11.glVertex2d((double)xc3, (double)yc1);
        GL11.glVertex2d((double)xc4, (double)yc2);
        GL11.glVertex2d((double)xc6, (double)yc5);
        GL11.glVertex2d((double)xc7, (double)yc6);
        GL11.glVertex2d((double)xc6, (double)yc7);
        GL11.glVertex2d((double)xc5, (double)yc6);
        GL11.glVertex2d((double)xc1, (double)yc3);
        GL11.glVertex2d((double)xc2, (double)yc4);
        GL11.glEnd();
        GL11.glColor4f((float)0.0625f, (float)0.0625f, (float)0.0625f, (float)0.5f);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)xc1, (double)yc1);
        GL11.glVertex2d((double)xc2, (double)yc2);
        GL11.glVertex2d((double)xc6, (double)yc5);
        GL11.glVertex2d((double)xc4, (double)yc2);
        GL11.glVertex2d((double)xc3, (double)yc1);
        GL11.glVertex2d((double)xc7, (double)yc6);
        GL11.glVertex2d((double)xc3, (double)yc3);
        GL11.glVertex2d((double)xc4, (double)yc4);
        GL11.glVertex2d((double)xc6, (double)yc7);
        GL11.glVertex2d((double)xc2, (double)yc4);
        GL11.glVertex2d((double)xc1, (double)yc3);
        GL11.glVertex2d((double)xc5, (double)yc6);
        GL11.glEnd();
    }

    public float[] getBgColor() {
        return this.bgColor;
    }

    public float[] getAcColor() {
        return this.acColor;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public void addWindow(Window window) {
        this.windows.add(window);
    }

    public void addPopup(Popup popup) {
        this.popups.add(popup);
    }
}

