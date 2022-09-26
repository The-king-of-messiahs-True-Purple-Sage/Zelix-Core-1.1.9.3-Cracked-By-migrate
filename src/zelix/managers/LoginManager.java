/*
 * Decompiled with CFR 0.152.
 */
package zelix.managers;

import zelix.utils.LoginUtils;

public class LoginManager {
    private String email;
    private String name;
    private String password;
    private boolean cracked;
    private boolean favourites;

    public LoginManager(String email, String password, boolean favourites) {
        this.email = email;
        this.favourites = favourites;
        if (password == null || password.isEmpty()) {
            this.name = email;
            this.password = null;
            this.cracked = true;
        } else {
            this.name = LoginUtils.getName(email, password);
            this.password = password;
            this.cracked = false;
        }
    }

    public LoginManager(String email, String password) {
        this(email, password, false);
    }

    public LoginManager(String email, String name, String password, boolean favourites) {
        this.email = email;
        this.favourites = favourites;
        if (password == null || password.isEmpty()) {
            name = email;
            this.password = null;
            this.cracked = true;
        } else {
            this.name = name;
            this.password = password;
            this.cracked = false;
        }
    }

    public LoginManager(String name, boolean favourites) {
        this.email = name;
        this.name = name;
        this.password = null;
        this.cracked = true;
        this.favourites = favourites;
    }

    public LoginManager(String name) {
        this(name, false);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        if (this.password == null || this.password.isEmpty()) {
            this.name = email;
            this.password = null;
            this.cracked = true;
        } else {
            this.name = LoginUtils.getName(email, this.password);
            this.cracked = false;
        }
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        if (this.email != null) {
            return this.email;
        }
        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        if (this.password == null || this.password.isEmpty()) {
            this.cracked = true;
            return "";
        }
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
        if (password == null || password.isEmpty()) {
            this.name = this.email;
            password = null;
            this.cracked = true;
        } else {
            this.name = LoginUtils.getName(this.email, password);
            this.password = password;
            this.cracked = false;
        }
    }

    public boolean isCracked() {
        return this.cracked;
    }

    public boolean isFavourites() {
        return this.favourites;
    }

    public void setFavourites(boolean favourites) {
        this.favourites = favourites;
    }

    public void setCracked() {
        this.name = this.email;
        this.password = null;
        this.cracked = true;
    }
}

