/*
 * Decompiled with CFR 0.152.
 */
package zelix.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;
import zelix.command.Command;
import zelix.utils.Utils;
import zelix.utils.hooks.visual.ChatUtils;

public class DumpClasses
extends Command {
    public DumpClasses() {
        super("dumpclasses");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            ArrayList<String> list = new ArrayList<String>();
            Field f = ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Vector classes = (Vector)f.get(classLoader);
            for (Class clazz : classes) {
                String className = clazz.getName();
                if (args.length > 0) {
                    if (!className.contains(args[0])) continue;
                    list.add("\n" + className);
                    continue;
                }
                list.add("\n" + className);
            }
            if (list.isEmpty()) {
                ChatUtils.error("List is empty.");
            } else {
                Utils.copy(list.toString());
                ChatUtils.message("List copied to clipboard.");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Get classes from ClassLoader by regex.";
    }

    @Override
    public String getSyntax() {
        return "dumpclasses <regex>";
    }
}

