package dev.rodni.ru.googlemapsandplaces.di.auth.login;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginViewModel;

@Module
public abstract class LoginViewModelsModule {

    //we need into map annotation because we are using map and the exact key here for multibinding
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
