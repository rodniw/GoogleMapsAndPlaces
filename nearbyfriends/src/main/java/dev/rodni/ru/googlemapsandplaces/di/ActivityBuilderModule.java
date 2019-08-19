package dev.rodni.ru.googlemapsandplaces.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginRegScope;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.MainScope;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity;
import dev.rodni.ru.googlemapsandplaces.ui.main.MainActivity;

//module to provide subcomponents to my activities by ContributesAndroidInjector
//and also to set all the activities scopes
@Module
public abstract class ActivityBuilderModule {

    @LoginRegScope
    @ContributesAndroidInjector(modules = {
            LoginViewModelsModule.class,
            LoginModule.class,
    })
    abstract LoginActivity contributeLoginActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {

    })
    abstract MainActivity contributeMainActivity();
}
