package dev.rodni.ru.googlemapsandplaces.di;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dev.rodni.ru.googlemapsandplaces.util.viewmodels.ViewModelProviderFactory;

//i provide view model provider factory in this module
@Module
public abstract class ViewModelFactoryModule {

    //binds is all the same as static provides if we do not make smth inside method body
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
