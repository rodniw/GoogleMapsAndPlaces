package dev.rodni.ru.googlemapsandplaces.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginScope;
import dev.rodni.ru.googlemapsandplaces.di.auth.login.LoginViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.registration.RegistrViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.registration.RegistrationModule;
import dev.rodni.ru.googlemapsandplaces.di.auth.registration.RegistrationScope;
import dev.rodni.ru.googlemapsandplaces.di.main.MainFragmentBuilderModule;
import dev.rodni.ru.googlemapsandplaces.di.main.MainModule;
import dev.rodni.ru.googlemapsandplaces.di.main.MainScope;
import dev.rodni.ru.googlemapsandplaces.di.main.MainViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomFragmentBuilderModule;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomModule;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomScope;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomActivity;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity;
import dev.rodni.ru.googlemapsandplaces.ui.main.MainActivity;
import dev.rodni.ru.googlemapsandplaces.ui.registration.RegisterActivity;

//module to provide subcomponents to my activities by ContributesAndroidInjector
//and also to set all the activities scopes
@Module
public abstract class ActivityBuilderModule {

    @LoginScope
    @ContributesAndroidInjector(modules = {
            LoginViewModelsModule.class,
            LoginModule.class,
    })
    abstract LoginActivity contributeLoginActivity();

    @RegistrationScope
    @ContributesAndroidInjector(modules = {
            RegistrViewModelsModule.class,
            RegistrationModule.class,
    })
    abstract RegisterActivity contributeRegisterActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainViewModelsModule.class,
            MainFragmentBuilderModule.class,
            MainModule.class,
    })
    abstract MainActivity contributeMainActivity();

}
