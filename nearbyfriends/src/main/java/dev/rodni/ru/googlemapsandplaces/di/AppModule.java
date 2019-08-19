package dev.rodni.ru.googlemapsandplaces.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.models.userdata.User;

@Module
public class AppModule {

    @Singleton
    @Provides
    static User someUser() {
        return new User();
    }

}
