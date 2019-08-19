package dev.rodni.ru.googlemapsandplaces;

import android.app.Application;

import dev.rodni.ru.googlemapsandplaces.models.userdata.User;


public class UserClient extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
