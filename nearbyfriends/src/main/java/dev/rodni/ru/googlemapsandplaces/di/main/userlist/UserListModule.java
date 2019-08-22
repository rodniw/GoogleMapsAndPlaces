package dev.rodni.ru.googlemapsandplaces.di.main.userlist;

import android.app.Application;

import com.google.android.gms.maps.MapView;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.ui.main.userlist.UserListFragmentLifecycleObserver;
import dev.rodni.ru.googlemapsandplaces.ui.main.userlist.UserListRecyclerAdapter;

@Module
public class UserListModule {

    @UserListScope
    @Provides
    static UserListRecyclerAdapter provideAdapter() {
        return new UserListRecyclerAdapter();
    }

    @UserListScope
    @Provides
    static MapView provideMapView(Application application) {
        return new MapView(application);
    }

    @UserListScope
    @Provides
    static UserListFragmentLifecycleObserver provideLifecyclerObserver(MapView mapView) {
        return new UserListFragmentLifecycleObserver(mapView);
    }
}
