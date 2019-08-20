package dev.rodni.ru.googlemapsandplaces.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.data.database.AppDatabase;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;

@Module
public class AppModule {

    @Singleton
    @Provides
    static AppDatabase appDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "database")
                .build();
    }

    @Singleton
    @Provides
    @Named("app_user")
    static User user() {
        return new User();
    }

}
