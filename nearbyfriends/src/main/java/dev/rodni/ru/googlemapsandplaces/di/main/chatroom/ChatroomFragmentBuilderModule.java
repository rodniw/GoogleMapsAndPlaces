package dev.rodni.ru.googlemapsandplaces.di.main.chatroom;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListFragment;

@Module
public abstract class ChatroomFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract UserListFragment contributeUserListFragment();
}
