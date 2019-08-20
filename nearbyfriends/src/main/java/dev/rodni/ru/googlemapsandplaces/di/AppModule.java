package dev.rodni.ru.googlemapsandplaces.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;

@Module
public class AppModule {

    @Singleton
    @Provides
    @Named("app_user")
    static User user() {
        return new User();
    }

}
