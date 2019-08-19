package dev.rodni.ru.googlemapsandplaces.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dev.rodni.ru.googlemapsandplaces.NearbyFriendsApplication;

//the main comp
@Singleton
@Component( modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
})
public interface AppComponent extends AndroidInjector<NearbyFriendsApplication> {

    @Component.Builder
    interface Builder {

        //i provide the instance of the application through all the application
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
