package dev.rodni.ru.googlemapsandplaces.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.main.chatroom.ChatroomViewModel;
import dev.rodni.ru.googlemapsandplaces.ui.main.mainpage.ListChatsViewModel;
import dev.rodni.ru.googlemapsandplaces.ui.main.profile.ProfileViewModel;
import dev.rodni.ru.googlemapsandplaces.ui.main.userlist.UserListViewModel;

//view models map for fragments inside the main activity
@Module
public abstract class MainViewModelsModule {

    //for chatroom fragment
    @Binds
    @IntoMap
    @ViewModelKey(ChatroomViewModel.class)
    public abstract ViewModel bindChatroomViewModel(ChatroomViewModel viewModel);

    //for list of chats fragments
    @Binds
    @IntoMap
    @ViewModelKey(ListChatsViewModel.class)
    public abstract ViewModel bindListChatsViewModel(ListChatsViewModel viewModel);

    //for profile page fragment
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    //for list of users inside a chatroom
    //inside this its gonna be work with google map
    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel.class)
    public abstract ViewModel bindUserListViewModel(UserListViewModel viewModel);
}
