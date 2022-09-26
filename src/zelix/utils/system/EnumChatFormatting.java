/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package zelix.utils.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumChatFormatting {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),
    RESET('r');

    private static final Map formattingCodeMapping;
    private static final Map nameMapping;
    private static final Pattern formattingCodePattern;
    private final char formattingCode;
    private final boolean fancyStyling;
    private final String controlString;
    private static final String __OBFID = "CL_00000342";

    private EnumChatFormatting(char p_i1336_3_) {
        this(p_i1336_3_, false);
    }

    private EnumChatFormatting(char p_i1337_3_, boolean p_i1337_4_) {
        this.formattingCode = p_i1337_3_;
        this.fancyStyling = p_i1337_4_;
        this.controlString = "\u00a7" + p_i1337_3_;
    }

    public char getFormattingCode() {
        return this.formattingCode;
    }

    public boolean isFancyStyling() {
        return this.fancyStyling;
    }

    public boolean isColor() {
        return !this.fancyStyling && this != RESET;
    }

    public String getFriendlyName() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.controlString;
    }

    @SideOnly(value=Side.CLIENT)
    public static String getTextWithoutFormattingCodes(String p_110646_0_) {
        return p_110646_0_ == null ? null : formattingCodePattern.matcher(p_110646_0_).replaceAll("");
    }

    public static EnumChatFormatting getValueByName(String p_96300_0_) {
        return p_96300_0_ == null ? null : (EnumChatFormatting)((Object)nameMapping.get(p_96300_0_.toLowerCase()));
    }

    public static Collection getValidValues(boolean p_96296_0_, boolean p_96296_1_) {
        ArrayList<String> arraylist = new ArrayList<String>();
        for (EnumChatFormatting enumchatformatting : EnumChatFormatting.values()) {
            if (enumchatformatting.isColor() && !p_96296_0_ || enumchatformatting.isFancyStyling() && !p_96296_1_) continue;
            arraylist.add(enumchatformatting.getFriendlyName());
        }
        return arraylist;
    }

    static {
        formattingCodeMapping = new HashMap();
        nameMapping = new HashMap();
        formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
        for (EnumChatFormatting var3 : EnumChatFormatting.values()) {
            formattingCodeMapping.put(Character.valueOf(var3.getFormattingCode()), var3);
            nameMapping.put(var3.getFriendlyName(), var3);
        }
    }
}

