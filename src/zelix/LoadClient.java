/*
 * Decompiled with CFR 0.152.
 */
package zelix;

import zelix.utils.Cr4sh;

public class LoadClient {
    public static boolean isCheck = false;

    public static void RLoad(String string, String string2) {
        try {
            isCheck = true;
            Class<?> Main = Class.forName(string);
            Main.newInstance();
        }
        catch (IllegalAccessException e) {
            isCheck = false;
            new Cr4sh();
        }
        catch (InstantiationException e) {
            isCheck = false;
            new Cr4sh();
        }
        catch (ClassNotFoundException e) {
            isCheck = false;
            Class<?> Main = null;
            try {
                isCheck = true;
                Main = Class.forName(string2);
                Main.newInstance();
            }
            catch (ClassNotFoundException ex) {
                isCheck = false;
                new Cr4sh();
            }
            catch (IllegalAccessException ex) {
                isCheck = false;
                new Cr4sh();
            }
            catch (InstantiationException ex) {
                isCheck = false;
                new Cr4sh();
            }
        }
    }
}

