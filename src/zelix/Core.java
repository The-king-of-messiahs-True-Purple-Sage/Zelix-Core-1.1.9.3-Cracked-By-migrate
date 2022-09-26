/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  org.lwjgl.opengl.Display
 */
package zelix;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.opengl.Display;
import zelix.EventsHandler;
import zelix.LoadClient;
import zelix.gui.Notification.NotificationManager;
import zelix.managers.CapeManager;
import zelix.managers.FileManager;
import zelix.managers.FontManager;
import zelix.managers.HackManager;
import zelix.utils.Cr4sh;
import zelix.utils.hooks.visual.font.FontLoaders;
import zelix.utils.resourceloader.Strings;
import zelix.utils.system.Nan0EventRegister;

public class Core {
    public static final String MODID = "rsaaaaaa";
    public static final String NAME = "Zelix Cracked By The king of messiahs True Purple Sage Team - migrate Github: https://github.com/The-king-of-messiahs-True-Purple-Sage";
    public static final String VERSION = "1.1.6";
    public static final String MCVERSION = "1.12.2";
    public static int initCount = 0;
    public static HackManager hackManager;
    public static FileManager fileManager;
    public static EventsHandler eventsHandler;
    public static NotificationManager notificationManager;
    public static CapeManager capeManager;
    public static FontLoaders fontLoaders;
    public static FontManager fontManager;
    public static String UN;
    public static String UP;
    public static String Love;

    public Core() {
        this.Sort_Verify();
    }

    public static void main(String[] args) {
    }

    public void Sort_Verify() {
        if (initCount > 0) {
            return;
        }
        if (!LoadClient.isCheck) {
            new Cr4sh();
            return;
        }
        Display.setTitle((String)"Zelix Cracked By The king of messiahs True Purple Sage Team - migrate Github: https://github.com/The-king-of-messiahs-True-Purple-Sages");
        ++initCount;
        UN = "Cracked By The king of messiahs True Purple Sage Team - migrate Github: https://github.com/The-king-of-messiahs-True-Purple-Sage";
        UP = "Cracked By The king of messiahs True Purple Sage Team - migrate Github: https://github.com/The-king-of-messiahs-True-Purple-Sage";
        Love = "Cracked By The king of messiahs True Purple Sage Team - migrate Github: https://github.com/The-king-of-messiahs-True-Purple-Sage";
        hackManager = new HackManager();
        fileManager = new FileManager();
        eventsHandler = new EventsHandler();
        capeManager = new CapeManager();
        notificationManager = new NotificationManager();
        Nan0EventRegister.register(MinecraftForge.EVENT_BUS, eventsHandler);
        Nan0EventRegister.register(FMLCommonHandler.instance().bus(), eventsHandler);
        try {
            Strings.loadTranslation();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

