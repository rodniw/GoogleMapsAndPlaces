package dev.rodni.ru.googlemapsandplaces.di.main.chatroom;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.rodni.ru.googlemapsandplaces.di.ViewModelKey;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomViewModel;

@Module
public abstract class ChatroomViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChatroomViewModel.class)
    public abstract ViewModel bindChatroomViewModel(ChatroomViewModel viewModel);
}
