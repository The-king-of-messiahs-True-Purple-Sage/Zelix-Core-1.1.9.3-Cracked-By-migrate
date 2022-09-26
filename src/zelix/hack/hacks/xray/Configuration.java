/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.config.Config
 *  net.minecraftforge.common.config.Config$Comment
 *  net.minecraftforge.common.config.Config$Name
 *  net.minecraftforge.common.config.Config$RangeDouble
 *  net.minecraftforge.common.config.Config$RangeInt
 *  net.minecraftforge.common.config.Config$Type
 */
package zelix.hack.hacks.xray;

import net.minecraftforge.common.config.Config;

@Config(modid="xray", name="advanced_xray", type=Config.Type.INSTANCE)
public class Configuration {
    @Config.RangeInt(min=0)
    public static int radius_x = 4;
    @Config.RangeInt(min=0)
    public static int radius_y = 4;
    @Config.RangeInt(min=0)
    public static int radius_z = 4;
    @Config.Name(value="Scan Delay")
    @Config.Comment(value={"Send scan packet delay (ms) 1000ms=1s"})
    public static int delay = 20;
    @Config.Name(value="Is Auto Scan")
    public static boolean auto = false;
    @Config.Name(value="Auto Scan when you walk over how many blocks")
    public static int movethreshhold = 5;
    @Config.Comment(value={"DO NOT TOUCH!", "This setting is for memory only and if changed to a value not supported", "the game will crash on start up. This value can be changed in game very simply.", "If you must change it then these are the valid values 0 -> 7", "but please leave it alone :P"})
    @Config.RangeInt(min=0, max=7)
    public static int radius = 3;
    @Config.Name(value="Show XRay overlay")
    @Config.Comment(value={"This allows you hide or show the overlay in the top right of the screen when XRay is enabled"})
    public static boolean showOverlay = true;
    @Config.Name(value="Fading Affect")
    @Config.Comment(value={"By default the blocks will begin to fade out as you get further away, some may not like this as", "it can sometime causes things not to show. If you're a dirty cheater (lol) then you may want", "to disable this as you won't be able to see chests out of your range."})
    public static boolean shouldFade = true;
    @Config.Name(value="Freeze")
    public static boolean freeze = false;
    @Config.Name(value="Outline thickness")
    @Config.Comment(value={"This allows you to set your own outline thickness, I find that 1.0 is perfect but others my", "think differently. The max is 5.0"})
    @Config.RangeDouble(min=0.5, max=5.0)
    public static double outlineThickness = 1.0;
}

