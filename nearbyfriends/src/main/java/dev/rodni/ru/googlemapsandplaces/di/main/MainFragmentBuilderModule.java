package dev.rodni.ru.googlemapsandplaces.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomModule;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomScope;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsScope;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.profile.ProfileModule;
import dev.rodni.ru.googlemapsandplaces.di.main.profile.ProfileViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListModule;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListScope;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListViewModelsModule;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomFragment;
import dev.rodni.ru.googlemapsandplaces.ui.mainpage.ListChatsFragment;
import dev.rodni.ru.googlemapsandplaces.ui.profile.ProfileFragment;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListFragment;

@Module
public abstract class MainFragmentBuilderModule {

    @ListChatsScope
    @ContributesAndroidInjector(modules = {
            ListChatsModule.class,
            ListChatsViewModelsModule.class,
    })
    abstract ListChatsFragment contributeListChatsFragment();

    @ChatroomScope
    @ContributesAndroidInjector(modules = {
            ChatroomModule.class,
            ChatroomViewModelsModule.class,
    })
    abstract ChatroomFragment contributeChatroomFragment();

    @UserListScope
    @ContributesAndroidInjector(modules = {
            UserListModule.class,
            UserListViewModelsModule.class,
    })
    abstract UserListFragment contributeUserListFragment();

    @ContributesAndroidInjector(modules = {
            ProfileModule.class,
            ProfileViewModelsModule.class,
    })
    abstract ProfileFragment contributeProfileFragment();
}
