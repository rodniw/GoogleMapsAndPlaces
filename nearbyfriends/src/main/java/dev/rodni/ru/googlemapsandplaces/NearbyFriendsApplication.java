package dev.rodni.ru.googlemapsandplaces;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import dev.rodni.ru.googlemapsandplaces.di.DaggerAppComponent;

public class NearbyFriendsApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
