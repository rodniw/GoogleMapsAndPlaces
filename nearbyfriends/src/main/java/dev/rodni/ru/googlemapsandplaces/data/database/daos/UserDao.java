package dev.rodni.ru.googlemapsandplaces.data.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.rodni.ru.googlemapsandplaces.data.database.entitiesdb.userdata.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(User user);

    @Delete
    void delete(User user);

    @Query("select * from current_user where user_key = :key")
    LiveData<User> getUser(int key);
}
