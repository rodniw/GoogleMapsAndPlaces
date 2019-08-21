package dev.rodni.ru.googlemapsandplaces.di.main.listchats;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.mainpage.ListChatsViewModel;

@Module
public abstract class ListChatsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListChatsViewModel.class)
    public abstract ViewModel bindListChatsViewModel(ListChatsViewModel viewModel);
}
