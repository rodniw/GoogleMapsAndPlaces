package dev.rodni.ru.googlemapsandplaces.di.main.chatroom;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatMessageRecyclerAdapter;

@Module
public class ChatroomModule {

    //i need to refactor adapters before inject them
    //@ChatroomScope
    //@Provides
    //static ChatMessageRecyclerAdapter provideAdapter() {
    //    return new ChatMessageRecyclerAdapter();
    //}

    //inject layout manager to the chat room fragment recycler view
    @Provides
    static LinearLayoutManager provideLayoutManager(Application application) {
        return new LinearLayoutManager(application.getApplicationContext());
    }

}
