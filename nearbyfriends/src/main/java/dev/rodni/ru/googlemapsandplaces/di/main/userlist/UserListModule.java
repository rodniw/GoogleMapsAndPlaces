package dev.rodni.ru.googlemapsandplaces.di.main.userlist;

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

}
