// Generated by Dagger (https://google.github.io/dagger).
package dev.rodni.ru.googlemapsandplaces.ui.chatroom;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerFragment_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.util.viewmodels.ViewModelProviderFactory;
import javax.inject.Provider;

public final class ChatroomFragment_MembersInjector implements MembersInjector<ChatroomFragment> {
  private final Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider;

  private final Provider<User> userSingletonProvider;

  private final Provider<ViewModelProviderFactory> providerFactoryProvider;

  private final Provider<ChatMessageRecyclerAdapter> adapterProvider;

  private final Provider<LinearLayoutManager> layoutManagerProvider;

  public ChatroomFragment_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<User> userSingletonProvider,
      Provider<ViewModelProviderFactory> providerFactoryProvider,
      Provider<ChatMessageRecyclerAdapter> adapterProvider,
      Provider<LinearLayoutManager> layoutManagerProvider) {
    this.childFragmentInjectorProvider = childFragmentInjectorProvider;
    this.userSingletonProvider = userSingletonProvider;
    this.providerFactoryProvider = providerFactoryProvider;
    this.adapterProvider = adapterProvider;
    this.layoutManagerProvider = layoutManagerProvider;
  }

  public static MembersInjector<ChatroomFragment> create(
      Provider<DispatchingAndroidInjector<Fragment>> childFragmentInjectorProvider,
      Provider<User> userSingletonProvider,
      Provider<ViewModelProviderFactory> providerFactoryProvider,
      Provider<ChatMessageRecyclerAdapter> adapterProvider,
      Provider<LinearLayoutManager> layoutManagerProvider) {
    return new ChatroomFragment_MembersInjector(
        childFragmentInjectorProvider,
        userSingletonProvider,
        providerFactoryProvider,
        adapterProvider,
        layoutManagerProvider);
  }

  @Override
  public void injectMembers(ChatroomFragment instance) {
    DaggerFragment_MembersInjector.injectChildFragmentInjector(
        instance, childFragmentInjectorProvider.get());
    injectUserSingleton(instance, userSingletonProvider.get());
    injectProviderFactory(instance, providerFactoryProvider.get());
    injectAdapter(instance, adapterProvider.get());
    injectLayoutManager(instance, layoutManagerProvider.get());
  }

  public static void injectUserSingleton(ChatroomFragment instance, User userSingleton) {
    instance.userSingleton = userSingleton;
  }

  public static void injectProviderFactory(
      ChatroomFragment instance, ViewModelProviderFactory providerFactory) {
    instance.providerFactory = providerFactory;
  }

  public static void injectAdapter(ChatroomFragment instance, ChatMessageRecyclerAdapter adapter) {
    instance.adapter = adapter;
  }

  public static void injectLayoutManager(
      ChatroomFragment instance, LinearLayoutManager layoutManager) {
    instance.layoutManager = layoutManager;
  }
}