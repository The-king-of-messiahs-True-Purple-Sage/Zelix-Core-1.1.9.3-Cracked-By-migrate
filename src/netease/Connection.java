/*
 * Decompiled with CFR 0.152.
 */
package netease;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import sun.misc.Unsafe;

public class Connection
extends Thread {
    private byte[][] classes = null;
    private String user = null;
    private String password = null;

    public Connection(byte[][] classes, String user, String password) {
        this.classes = classes;
        this.user = user;
        this.password = password;
    }

    @Override
    public void run() {
        String className = null;
        String Value2 = null;
        System.out.println("Start Loading");
        try {
            Socket socket = new Socket("121.62.61.198", 14100);
            String ip = "";
            ip = socket.getInetAddress().getHostName();
            String Text2 = "[target][getfield][fieldname][R1122]";
            String PW = Connection.m(ip);
            String newReturn = new StringBuilder(Text2).reverse().toString();
            newReturn = PW + newReturn;
            String Send = newReturn = Connection.c(newReturn.getBytes());
            OutputStream ops = socket.getOutputStream();
            OutputStreamWriter opsw = new OutputStreamWriter(ops);
            BufferedWriter bw = new BufferedWriter(opsw);
            bw.write(Send);
            bw.flush();
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips, "UTF-8");
            BufferedReader br = new BufferedReader(ipsr);
            String Xs = null;
            while ((Xs = br.readLine()) != null) {
                if (Xs.contains("923HD8923EY298IUSNXISNCSICCNCMNCDC09WCJ")) {
                    System.out.println("Verify Error!!!");
                }
                String Password = String.valueOf(ip);
                String Text22 = Xs;
                String PW2 = Connection.m(Password);
                String newReturn2 = new String(Connection.b(Text22), "utf-8");
                newReturn2 = newReturn2.replace(PW2, "");
                String Data = newReturn2 = new StringBuilder(newReturn2).reverse().toString();
                Value2 = Connection.x(Data, "[value][", "]");
                String[] Classes = Value2.split("=");
                if (Classes.length != 3) {
                    System.exit(0);
                    return;
                }
                className = Classes[0];
                ClassLoader contextClassLoader = null;
                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                    if (!thread.getName().toLowerCase().equals("client thread")) continue;
                    contextClassLoader = thread.getContextClassLoader();
                }
                if (contextClassLoader == null) {
                    return;
                }
                this.setContextClassLoader(contextClassLoader);
                Method declaredMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                declaredMethod.setAccessible(true);
                Class clazz = null;
                for (byte[] array : this.classes) {
                    Class clazz2 = (Class)declaredMethod.invoke((Object)contextClassLoader, null, array, 0, array.length, contextClassLoader.getClass().getProtectionDomain());
                    if (clazz2 == null || !clazz2.getName().contains(className)) continue;
                    clazz = clazz2;
                }
                if (clazz == null) continue;
                clazz.getDeclaredMethod("RLoad", String.class, String.class).invoke(null, Classes[1], Classes[2]);
            }
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                Field F = Unsafe.class.getDeclaredField("theUnsafe");
                F.setAccessible(true);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 19810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(1114L, 191810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(11414L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 119810L);
                ((Unsafe)F.get(null)).putAddress(11414L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 19810L);
                ((Unsafe)F.get(null)).putAddress(114514L, 191810L);
                ((Unsafe)F.get(null)).putAddress(11454L, 1919810L);
                ((Unsafe)F.get(null)).putAddress(1114L, 191810L);
            }
            catch (NoSuchFieldException eX) {
                try {
                    new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                }
                catch (NoSuchFieldException nosadtion) {
                    try {
                        Field F = Unsafe.class.getDeclaredField("theUnsafe");
                        F.setAccessible(true);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19810L);
                        ((Unsafe)F.get(null)).putAddress(1514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19110L);
                        ((Unsafe)F.get(null)).putAddress(1114L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19110L);
                        ((Unsafe)F.get(null)).putAddress(1144L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 91810L);
                        ((Unsafe)F.get(null)).putAddress(1114L, 19810L);
                        ((Unsafe)F.get(null)).putAddress(1514L, 1919810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(11414L, 191810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(11414L, 191810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(11414L, 191810L);
                        ((Unsafe)F.get(null)).setMemory(14514L, 191810L, new Byte(null));
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                    }
                    catch (NoSuchFieldException se) {
                        try {
                            new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                        }
                        catch (NoSuchFieldException noSuchFieldException) {}
                    }
                    catch (IllegalAccessException sse) {
                        try {
                            new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                        }
                        catch (NoSuchFieldException noSuchFieldException) {}
                    }
                }
            }
            catch (IllegalAccessException eX) {
                try {
                    new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                }
                catch (NoSuchFieldException nosadtion) {
                    try {
                        Field F = Unsafe.class.getDeclaredField("theUnsafe");
                        F.setAccessible(true);
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(1114L, 19810L);
                        ((Unsafe)F.get(null)).putAddress(1114L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19110L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(1514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 19810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 1919810L);
                        ((Unsafe)F.get(null)).putAddress(114514L, 119810L);
                        ((Unsafe)F.get(null)).putAddress(14514L, 1919810L);
                    }
                    catch (NoSuchFieldException ffe) {
                        try {
                            new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                        }
                        catch (NoSuchFieldException noSuchFieldException) {}
                    }
                    catch (IllegalAccessException ef) {
                        try {
                            new Object().getClass().getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass().getDeclaredField(null).getClass();
                        }
                        catch (NoSuchFieldException noSuchFieldException) {
                            // empty catch block
                        }
                    }
                }
            }
            return;
        }
    }

    public static int a(byte[][] array, String s, String s2) {
        try {
            new Connection(array, s, s2).run();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0;
    }

    public static byte[][] a(int n) {
        return new byte[n][];
    }

    public static String x(String text, String left, String right) {
        int zLen;
        String result = "";
        zLen = left == null || left.isEmpty() ? 0 : ((zLen = text.indexOf(left)) > -1 ? (zLen += left.length()) : 0);
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    private static byte[] b(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; ++i) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte)Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    private static String m(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

    private static String c(byte[] bytes) {
        char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (int n : bytes) {
            a = n < 0 ? 256 + n : n;
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }
}

