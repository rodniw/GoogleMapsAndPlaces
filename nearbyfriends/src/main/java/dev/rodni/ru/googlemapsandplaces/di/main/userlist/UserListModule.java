package dev.rodni.ru.googlemapsandplaces.di.main.userlist;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListRecyclerAdapter;

@Module
public class UserListModule {

    @UserListScope
    @Provides
    static UserListRecyclerAdapter provideAdapter() {
        return new UserListRecyclerAdapter();
    }

    @Provides
    static LinearLayoutManager provideLayoutManager(Application application) {
        return new LinearLayoutManager(application.getApplicationContext());
    }
}
