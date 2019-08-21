package dev.rodni.ru.googlemapsandplaces.di.main.chatroom;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

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

    //inject layout manager to the chat room fragment recycler view
    @Provides
    static LinearLayoutManager provideLayoutManager(Application application) {
        return new LinearLayoutManager(application.getApplicationContext());
    }

}
