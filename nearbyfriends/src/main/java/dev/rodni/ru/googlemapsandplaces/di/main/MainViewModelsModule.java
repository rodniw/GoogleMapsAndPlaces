package dev.rodni.ru.googlemapsandplaces.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.main.MainViewModel;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
