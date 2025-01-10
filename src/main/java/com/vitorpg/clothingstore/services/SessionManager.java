package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.User;

public class SessionManager {
    private static SessionManager instance;
    private User userLogged;

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.userLogged = user;
    }

    public void logout() {
        this.userLogged = null;
    }

    public boolean isLoggedIn() {
        return userLogged != null;
    }

    public User getUser() {
        return userLogged;
    }
}
