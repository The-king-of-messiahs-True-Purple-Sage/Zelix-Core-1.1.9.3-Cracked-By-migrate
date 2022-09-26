/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Session
 */
package zelix.utils;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.lang.reflect.Field;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import zelix.utils.Wrapper;
import zelix.utils.system.Mapping;

public class LoginUtils {
    public static String loginAlt(String email, String password) {
        YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        String displayText = null;
        try {
            authentication.logIn();
            try {
                Field f = Minecraft.class.getDeclaredField(Mapping.session);
                f.setAccessible(true);
                f.set(Wrapper.INSTANCE.mc(), new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang"));
                displayText = "Logged [License]: " + Wrapper.INSTANCE.mc().getSession().getUsername();
            }
            catch (Exception e) {
                displayText = "Unknown error.";
                e.printStackTrace();
            }
        }
        catch (AuthenticationUnavailableException e) {
            displayText = "Cannot contact authentication server!";
        }
        catch (AuthenticationException e) {
            displayText = e.getMessage().contains("Invalid username or password.") || e.getMessage().toLowerCase().contains("account migrated") ? "Wrong password!" : "Cannot contact authentication server!";
        }
        catch (NullPointerException e) {
            displayText = "Wrong password!";
        }
        return displayText;
    }

    public static String getName(String email, String password) {
        YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        try {
            authentication.logIn();
            return authentication.getSelectedProfile().getName();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void changeCrackedName(String name) {
        try {
            Field f = Minecraft.class.getDeclaredField(Mapping.session);
            f.setAccessible(true);
            f.set(Wrapper.INSTANCE.mc(), new Session(name, "", "", "mojang"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

