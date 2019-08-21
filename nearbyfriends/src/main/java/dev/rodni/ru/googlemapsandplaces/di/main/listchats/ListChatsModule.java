package dev.rodni.ru.googlemapsandplaces.di.main.listchats;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import dev.rodni.ru.googlemapsandplaces.ui.mainpage.ListChatsRecyclerAdapter;

@Module
public class ListChatsModule {

    @ListChatsScope
    @Provides
    static ListChatsRecyclerAdapter provideAdapter() {
        return new ListChatsRecyclerAdapter();
    }

    @Provides
    static LinearLayoutManager provideLayoutManager(Application application) {
        return new LinearLayoutManager(application.getApplicationContext());
    }
}
