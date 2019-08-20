package dev.rodni.ru.googlemapsandplaces.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import dev.rodni.ru.googlemapsandplaces.data.database.daos.UserDao;
import dev.rodni.ru.googlemapsandplaces.data.database.daos.UserLocationDao;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.UserLocation;

@Database(
        entities = {User.class, UserLocation.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    abstract UserDao userDao();
    abstract UserLocationDao userLocationDao();
}
