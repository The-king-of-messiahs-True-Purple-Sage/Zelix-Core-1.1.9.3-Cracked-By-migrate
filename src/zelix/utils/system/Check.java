/*
 * Decompiled with CFR 0.152.
 */
package zelix.utils.system;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import zelix.utils.hooks.visual.ChatUtils;
import zelix.utils.system.WebUtils;

public class Check {
    public static boolean checkuser() {
        try {
            if (WebUtils.get("https://gitee.com/VortexTeam/Zelix/raw/master/1122HWID.txt").contains(Check.getHWID())) {
                ChatUtils.message("OK!");
                return true;
            }
            ChatUtils.error("verification failed! your uuid is" + Check.getHWID());
            return false;
        }
        catch (NoSuchAlgorithmException e) {
            ChatUtils.error("Network Error!");
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            ChatUtils.error("Error");
            e.printStackTrace();
        }
        catch (IOException e) {
            ChatUtils.error("Error");
            e.printStackTrace();
        }
        return true;
    }

    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME");
        byte[] bytes = main.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5 = md.digest(bytes);
        int i = 0;
        for (byte b : md5) {
            sb.append(Integer.toHexString(b & 0xFF | 0x300), 0, 3);
            if (i != md5.length - 1) {
                sb.append("-");
            }
            ++i;
        }
        return sb.toString();
    }
}

