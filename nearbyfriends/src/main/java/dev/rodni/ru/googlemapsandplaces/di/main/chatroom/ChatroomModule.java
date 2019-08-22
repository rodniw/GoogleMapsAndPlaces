package dev.rodni.ru.googlemapsandplaces.di.main.chatroom;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomMessageRecyclerAdapter;

@Module
public class ChatroomModule {

    @ChatroomScope
    @Provides
    static ChatroomMessageRecyclerAdapter provideAdapter() {
        return new ChatroomMessageRecyclerAdapter();
    }

}
