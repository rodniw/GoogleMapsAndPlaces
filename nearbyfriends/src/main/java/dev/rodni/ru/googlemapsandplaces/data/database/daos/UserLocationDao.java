package dev.rodni.ru.googlemapsandplaces.data.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.rodni.ru.googlemapsandplaces.data.database.entitiesdb.userdata.UserLocation;

@Dao
public interface UserLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(UserLocation userLocation);

    @Delete
    void delete(UserLocation userLocation);

    @Query("select * from user_location where id = :key")
    LiveData<UserLocation> getUser(int key);
}
