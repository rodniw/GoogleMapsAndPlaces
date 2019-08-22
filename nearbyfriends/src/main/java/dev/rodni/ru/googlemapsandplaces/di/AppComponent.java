package dev.rodni.ru.googlemapsandplaces.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dev.rodni.ru.googlemapsandplaces.NearbyFriendsApplication;
import dev.rodni.ru.googlemapsandplaces.SessionManager;

//the main comp
@Singleton
@Component( modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        AppModule.class,
        ViewModelFactoryModule.class,
})
public interface AppComponent extends AndroidInjector<NearbyFriendsApplication> {

    //by this we provide the session manager through all the application
    //and its gonna be accessible by any class that will inject it inside the class
    SessionManager sessionManager();

    @Component.Builder
    interface Builder {

        //i provide the instance of the application through all the application
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
