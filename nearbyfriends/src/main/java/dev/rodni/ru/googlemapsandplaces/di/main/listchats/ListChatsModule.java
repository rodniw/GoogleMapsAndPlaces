package dev.rodni.ru.googlemapsandplaces.di.main.listchats;

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

}
