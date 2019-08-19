package dev.rodni.ru.googlemapsandplaces.di.main.userlist;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListViewModel;

@Module
public abstract class UserListViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel.class)
    public abstract ViewModel bindUserListViewModel(UserListViewModel viewModel);
}
