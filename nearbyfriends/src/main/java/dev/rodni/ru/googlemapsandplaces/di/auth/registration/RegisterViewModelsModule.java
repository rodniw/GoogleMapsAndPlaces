package dev.rodni.ru.googlemapsandplaces.di.auth.registration;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.registration.RegistrationViewModel;

@Module
public abstract class RegisterViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel.class)
    public abstract ViewModel bindRegistrViewModel(RegistrationViewModel viewModel);
}
