package dev.rodni.ru.googlemapsandplaces.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.rodni.ru.googlemapsandplaces.MainActivity;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginScope;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.registration.RegisterViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.registration.RegisterModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.registration.RegisterScope;
import dev.rodni.ru.googlemapsandplaces.di.main.MainFragmentBuilderModule;
import dev.rodni.ru.googlemapsandplaces.di.main.MainModule;
import dev.rodni.ru.googlemapsandplaces.di.main.MainScope;
import dev.rodni.ru.googlemapsandplaces.di.main.MainViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity;
import dev.rodni.ru.googlemapsandplaces.ui.registration.RegisterActivity;

//module to provide subcomponents to my activities by ContributesAndroidInjector
//and also to set all the activities scopes
@Module
public abstract class ActivityBuilderModule {

    //subcomponents for login activity
    @LoginScope
    @ContributesAndroidInjector(modules = {
            LoginViewModelsModule.class,
            LoginModule.class,
    })
    abstract LoginActivity contributeLoginActivity();

    //subcomponents for registr activity
    @RegisterScope
    @ContributesAndroidInjector(modules = {
            RegisterViewModelsModule.class,
            RegisterModule.class,
    })
    abstract RegisterActivity contributeRegisterActivity();

    //subcomponents for the main activity
    //many of dependencies are inside main fragment builder module
    @MainScope
    @ContributesAndroidInjector(modules = {
            MainViewModelsModule.class,
            MainFragmentBuilderModule.class,
            MainModule.class,
    })
    abstract MainActivity contributeMainActivity();

}
