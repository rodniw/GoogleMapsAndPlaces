package dev.rodni.ru.googlemapsandplaces.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomModule;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomScope;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsModule;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsScope;
import dev.rodni.ru.googlemapsandplaces.di.main.profile.ProfileModule;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListModule;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListScope;
import dev.rodni.ru.googlemapsandplaces.ui.main.chatroom.ChatroomFragment;
import dev.rodni.ru.googlemapsandplaces.ui.main.mainpage.ListChatsFragment;
import dev.rodni.ru.googlemapsandplaces.ui.main.profile.ProfileFragment;
import dev.rodni.ru.googlemapsandplaces.ui.main.userlist.UserListFragment;

@Module
public abstract class MainFragmentBuilderModule {

    @ListChatsScope
    @ContributesAndroidInjector(modules = {
            ListChatsModule.class,
    })
    abstract ListChatsFragment contributeListChatsFragment();

    @ChatroomScope
    @ContributesAndroidInjector(modules = {
            ChatroomModule.class,
    })
    abstract ChatroomFragment contributeChatroomFragment();

    @UserListScope
    @ContributesAndroidInjector(modules = {
            UserListModule.class,
    })
    abstract UserListFragment contributeUserListFragment();

    @ContributesAndroidInjector(modules = {
            ProfileModule.class,
    })
    abstract ProfileFragment contributeProfileFragment();
}
