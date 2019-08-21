// Generated by Dagger (https://dagger.dev).
package dev.rodni.ru.googlemapsandplaces.di;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication_MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.android.support.DaggerAppCompatActivity_MembersInjector;
import dagger.android.support.DaggerFragment_MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dev.rodni.ru.googlemapsandplaces.MainActivity;
import dev.rodni.ru.googlemapsandplaces.NearbyFriendsApplication;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.di.main.MainFragmentBuilderModule_ContributeChatroomFragment;
import dev.rodni.ru.googlemapsandplaces.di.main.MainFragmentBuilderModule_ContributeListChatsFragment;
import dev.rodni.ru.googlemapsandplaces.di.main.MainFragmentBuilderModule_ContributeProfileFragment;
import dev.rodni.ru.googlemapsandplaces.di.main.MainFragmentBuilderModule_ContributeUserListFragment;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomModule_ProvideAdapterFactory;
import dev.rodni.ru.googlemapsandplaces.di.main.chatroom.ChatroomModule_ProvideLayoutManagerFactory;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsModule_ProvideAdapterFactory;
import dev.rodni.ru.googlemapsandplaces.di.main.listchats.ListChatsModule_ProvideLayoutManagerFactory;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListModule_ProvideAdapterFactory;
import dev.rodni.ru.googlemapsandplaces.di.main.userlist.UserListModule_ProvideLayoutManagerFactory;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomFragment;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomFragment_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomMessageRecyclerAdapter;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomViewModel;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomViewModel_Factory;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.ui.mainpage.ListChatsFragment;
import dev.rodni.ru.googlemapsandplaces.ui.mainpage.ListChatsFragment_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.ui.mainpage.ListChatsRecyclerAdapter;
import dev.rodni.ru.googlemapsandplaces.ui.profile.ProfileFragment;
import dev.rodni.ru.googlemapsandplaces.ui.profile.ProfileFragment_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.ui.registration.RegisterActivity;
import dev.rodni.ru.googlemapsandplaces.ui.registration.RegisterActivity_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListFragment;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListFragment_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListRecyclerAdapter;
import dev.rodni.ru.googlemapsandplaces.util.viewmodels.ViewModelProviderFactory;
import java.util.Map;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerAppComponent implements AppComponent {
  private final Application application;

  private Provider<ActivityBuilderModule_ContributeLoginActivity.LoginActivitySubcomponent.Factory> loginActivitySubcomponentFactoryProvider;

  private Provider<ActivityBuilderModule_ContributeRegisterActivity.RegisterActivitySubcomponent.Factory> registerActivitySubcomponentFactoryProvider;

  private Provider<ActivityBuilderModule_ContributeMainActivity.MainActivitySubcomponent.Factory> mainActivitySubcomponentFactoryProvider;

  private Provider<User> userProvider;

  private DaggerAppComponent(Application applicationParam) {
    this.application = applicationParam;
    initialize(applicationParam);
  }

  public static AppComponent.Builder builder() {
    return new Builder();
  }

  private Map<Class<?>, Provider<AndroidInjector.Factory<?>>> getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(
      ) {
    return ImmutableMap.<Class<?>, Provider<AndroidInjector.Factory<?>>>of(LoginActivity.class, (Provider) loginActivitySubcomponentFactoryProvider, RegisterActivity.class, (Provider) registerActivitySubcomponentFactoryProvider, MainActivity.class, (Provider) mainActivitySubcomponentFactoryProvider);}

  private DispatchingAndroidInjector<Object> getDispatchingAndroidInjectorOfObject() {
    return DispatchingAndroidInjector_Factory.newInstance(getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(), ImmutableMap.<String, Provider<AndroidInjector.Factory<?>>>of());}

  @SuppressWarnings("unchecked")
  private void initialize(final Application applicationParam) {
    this.loginActivitySubcomponentFactoryProvider = new Provider<ActivityBuilderModule_ContributeLoginActivity.LoginActivitySubcomponent.Factory>() {
      @Override
      public ActivityBuilderModule_ContributeLoginActivity.LoginActivitySubcomponent.Factory get() {
        return new LoginActivitySubcomponentFactory();}
    };
    this.registerActivitySubcomponentFactoryProvider = new Provider<ActivityBuilderModule_ContributeRegisterActivity.RegisterActivitySubcomponent.Factory>() {
      @Override
      public ActivityBuilderModule_ContributeRegisterActivity.RegisterActivitySubcomponent.Factory get(
          ) {
        return new RegisterActivitySubcomponentFactory();}
    };
    this.mainActivitySubcomponentFactoryProvider = new Provider<ActivityBuilderModule_ContributeMainActivity.MainActivitySubcomponent.Factory>() {
      @Override
      public ActivityBuilderModule_ContributeMainActivity.MainActivitySubcomponent.Factory get() {
        return new MainActivitySubcomponentFactory();}
    };
    this.userProvider = DoubleCheck.provider(AppModule_UserFactory.create());
  }

  @Override
  public void inject(NearbyFriendsApplication arg0) {
    injectNearbyFriendsApplication(arg0);}

  @CanIgnoreReturnValue
  private NearbyFriendsApplication injectNearbyFriendsApplication(
      NearbyFriendsApplication instance) {
    DaggerApplication_MembersInjector.injectAndroidInjector(instance, getDispatchingAndroidInjectorOfObject());
    return instance;
  }

  private static final class Builder implements AppComponent.Builder {
    private Application application;

    @Override
    public Builder application(Application application) {
      this.application = Preconditions.checkNotNull(application);
      return this;
    }

    @Override
    public AppComponent build() {
      Preconditions.checkBuilderRequirement(application, Application.class);
      return new DaggerAppComponent(application);
    }
  }

  private final class LoginActivitySubcomponentFactory implements ActivityBuilderModule_ContributeLoginActivity.LoginActivitySubcomponent.Factory {
    @Override
    public ActivityBuilderModule_ContributeLoginActivity.LoginActivitySubcomponent create(
        LoginActivity arg0) {
      Preconditions.checkNotNull(arg0);
      return new LoginActivitySubcomponentImpl(arg0);
    }
  }

  private final class LoginActivitySubcomponentImpl implements ActivityBuilderModule_ContributeLoginActivity.LoginActivitySubcomponent {
    private LoginActivitySubcomponentImpl(LoginActivity arg0) {

    }

    @Override
    public void inject(LoginActivity arg0) {
      injectLoginActivity(arg0);}

    @CanIgnoreReturnValue
    private LoginActivity injectLoginActivity(LoginActivity instance) {
      LoginActivity_MembersInjector.injectUserSingleton(instance, DaggerAppComponent.this.userProvider.get());
      return instance;
    }
  }

  private final class RegisterActivitySubcomponentFactory implements ActivityBuilderModule_ContributeRegisterActivity.RegisterActivitySubcomponent.Factory {
    @Override
    public ActivityBuilderModule_ContributeRegisterActivity.RegisterActivitySubcomponent create(
        RegisterActivity arg0) {
      Preconditions.checkNotNull(arg0);
      return new RegisterActivitySubcomponentImpl(arg0);
    }
  }

  private final class RegisterActivitySubcomponentImpl implements ActivityBuilderModule_ContributeRegisterActivity.RegisterActivitySubcomponent {
    private RegisterActivitySubcomponentImpl(RegisterActivity arg0) {

    }

    @Override
    public void inject(RegisterActivity arg0) {
      injectRegisterActivity(arg0);}

    @CanIgnoreReturnValue
    private RegisterActivity injectRegisterActivity(RegisterActivity instance) {
      DaggerAppCompatActivity_MembersInjector.injectAndroidInjector(instance, DaggerAppComponent.this.getDispatchingAndroidInjectorOfObject());
      RegisterActivity_MembersInjector.injectUserSingleton(instance, DaggerAppComponent.this.userProvider.get());
      return instance;
    }
  }

  private final class MainActivitySubcomponentFactory implements ActivityBuilderModule_ContributeMainActivity.MainActivitySubcomponent.Factory {
    @Override
    public ActivityBuilderModule_ContributeMainActivity.MainActivitySubcomponent create(
        MainActivity arg0) {
      Preconditions.checkNotNull(arg0);
      return new MainActivitySubcomponentImpl(arg0);
    }
  }

  private final class MainActivitySubcomponentImpl implements ActivityBuilderModule_ContributeMainActivity.MainActivitySubcomponent {
    private Provider<MainFragmentBuilderModule_ContributeListChatsFragment.ListChatsFragmentSubcomponent.Factory> listChatsFragmentSubcomponentFactoryProvider;

    private Provider<MainFragmentBuilderModule_ContributeChatroomFragment.ChatroomFragmentSubcomponent.Factory> chatroomFragmentSubcomponentFactoryProvider;

    private Provider<MainFragmentBuilderModule_ContributeUserListFragment.UserListFragmentSubcomponent.Factory> userListFragmentSubcomponentFactoryProvider;

    private Provider<MainFragmentBuilderModule_ContributeProfileFragment.ProfileFragmentSubcomponent.Factory> profileFragmentSubcomponentFactoryProvider;

    private MainActivitySubcomponentImpl(MainActivity arg0) {

      initialize(arg0);
    }

    private Map<Class<?>, Provider<AndroidInjector.Factory<?>>> getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(
        ) {
      return ImmutableMap.<Class<?>, Provider<AndroidInjector.Factory<?>>>builderWithExpectedSize(7).put(LoginActivity.class, (Provider) DaggerAppComponent.this.loginActivitySubcomponentFactoryProvider).put(RegisterActivity.class, (Provider) DaggerAppComponent.this.registerActivitySubcomponentFactoryProvider).put(MainActivity.class, (Provider) DaggerAppComponent.this.mainActivitySubcomponentFactoryProvider).put(ListChatsFragment.class, (Provider) listChatsFragmentSubcomponentFactoryProvider).put(ChatroomFragment.class, (Provider) chatroomFragmentSubcomponentFactoryProvider).put(UserListFragment.class, (Provider) userListFragmentSubcomponentFactoryProvider).put(ProfileFragment.class, (Provider) profileFragmentSubcomponentFactoryProvider).build();}

    private DispatchingAndroidInjector<Object> getDispatchingAndroidInjectorOfObject() {
      return DispatchingAndroidInjector_Factory.newInstance(getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf(), ImmutableMap.<String, Provider<AndroidInjector.Factory<?>>>of());}

    @SuppressWarnings("unchecked")
    private void initialize(final MainActivity arg0) {
      this.listChatsFragmentSubcomponentFactoryProvider = new Provider<MainFragmentBuilderModule_ContributeListChatsFragment.ListChatsFragmentSubcomponent.Factory>() {
        @Override
        public MainFragmentBuilderModule_ContributeListChatsFragment.ListChatsFragmentSubcomponent.Factory get(
            ) {
          return new ListChatsFragmentSubcomponentFactory();}
      };
      this.chatroomFragmentSubcomponentFactoryProvider = new Provider<MainFragmentBuilderModule_ContributeChatroomFragment.ChatroomFragmentSubcomponent.Factory>() {
        @Override
        public MainFragmentBuilderModule_ContributeChatroomFragment.ChatroomFragmentSubcomponent.Factory get(
            ) {
          return new ChatroomFragmentSubcomponentFactory();}
      };
      this.userListFragmentSubcomponentFactoryProvider = new Provider<MainFragmentBuilderModule_ContributeUserListFragment.UserListFragmentSubcomponent.Factory>() {
        @Override
        public MainFragmentBuilderModule_ContributeUserListFragment.UserListFragmentSubcomponent.Factory get(
            ) {
          return new UserListFragmentSubcomponentFactory();}
      };
      this.profileFragmentSubcomponentFactoryProvider = new Provider<MainFragmentBuilderModule_ContributeProfileFragment.ProfileFragmentSubcomponent.Factory>() {
        @Override
        public MainFragmentBuilderModule_ContributeProfileFragment.ProfileFragmentSubcomponent.Factory get(
            ) {
          return new ProfileFragmentSubcomponentFactory();}
      };
    }

    @Override
    public void inject(MainActivity arg0) {
      injectMainActivity(arg0);}

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity(MainActivity instance) {
      DaggerAppCompatActivity_MembersInjector.injectAndroidInjector(instance, getDispatchingAndroidInjectorOfObject());
      return instance;
    }

    private final class ListChatsFragmentSubcomponentFactory implements MainFragmentBuilderModule_ContributeListChatsFragment.ListChatsFragmentSubcomponent.Factory {
      @Override
      public MainFragmentBuilderModule_ContributeListChatsFragment.ListChatsFragmentSubcomponent create(
          ListChatsFragment arg0) {
        Preconditions.checkNotNull(arg0);
        return new ListChatsFragmentSubcomponentImpl(arg0);
      }
    }

    private final class ListChatsFragmentSubcomponentImpl implements MainFragmentBuilderModule_ContributeListChatsFragment.ListChatsFragmentSubcomponent {
      private Provider<ListChatsRecyclerAdapter> provideAdapterProvider;

      private ListChatsFragmentSubcomponentImpl(ListChatsFragment arg0) {

        initialize(arg0);
      }

      private LinearLayoutManager getLinearLayoutManager() {
        return ListChatsModule_ProvideLayoutManagerFactory.provideLayoutManager(DaggerAppComponent.this.application);}

      @SuppressWarnings("unchecked")
      private void initialize(final ListChatsFragment arg0) {
        this.provideAdapterProvider = DoubleCheck.provider(ListChatsModule_ProvideAdapterFactory.create());
      }

      @Override
      public void inject(ListChatsFragment arg0) {
        injectListChatsFragment(arg0);}

      @CanIgnoreReturnValue
      private ListChatsFragment injectListChatsFragment(ListChatsFragment instance) {
        DaggerAppCompatActivity_MembersInjector.injectAndroidInjector(instance, MainActivitySubcomponentImpl.this.getDispatchingAndroidInjectorOfObject());
        ListChatsFragment_MembersInjector.injectUserSingleton(instance, DaggerAppComponent.this.userProvider.get());
        ListChatsFragment_MembersInjector.injectListChatsRecyclerAdapter(instance, provideAdapterProvider.get());
        ListChatsFragment_MembersInjector.injectLayoutManager(instance, getLinearLayoutManager());
        return instance;
      }
    }

    private final class ChatroomFragmentSubcomponentFactory implements MainFragmentBuilderModule_ContributeChatroomFragment.ChatroomFragmentSubcomponent.Factory {
      @Override
      public MainFragmentBuilderModule_ContributeChatroomFragment.ChatroomFragmentSubcomponent create(
          ChatroomFragment arg0) {
        Preconditions.checkNotNull(arg0);
        return new ChatroomFragmentSubcomponentImpl(arg0);
      }
    }

    private final class ChatroomFragmentSubcomponentImpl implements MainFragmentBuilderModule_ContributeChatroomFragment.ChatroomFragmentSubcomponent {
      private Provider<ChatroomMessageRecyclerAdapter> provideAdapterProvider;

      private ChatroomFragmentSubcomponentImpl(ChatroomFragment arg0) {

        initialize(arg0);
      }

      private Map<Class<? extends ViewModel>, Provider<ViewModel>> getMapOfClassOfAndProviderOfViewModel(
          ) {
        return ImmutableMap.<Class<? extends ViewModel>, Provider<ViewModel>>of(ChatroomViewModel.class, (Provider) ChatroomViewModel_Factory.create());}

      private ViewModelProviderFactory getViewModelProviderFactory() {
        return new ViewModelProviderFactory(getMapOfClassOfAndProviderOfViewModel());}

      private LinearLayoutManager getLinearLayoutManager() {
        return ChatroomModule_ProvideLayoutManagerFactory.provideLayoutManager(DaggerAppComponent.this.application);}

      @SuppressWarnings("unchecked")
      private void initialize(final ChatroomFragment arg0) {
        this.provideAdapterProvider = DoubleCheck.provider(ChatroomModule_ProvideAdapterFactory.create());
      }

      @Override
      public void inject(ChatroomFragment arg0) {
        injectChatroomFragment(arg0);}

      @CanIgnoreReturnValue
      private ChatroomFragment injectChatroomFragment(ChatroomFragment instance) {
        DaggerFragment_MembersInjector.injectAndroidInjector(instance, MainActivitySubcomponentImpl.this.getDispatchingAndroidInjectorOfObject());
        ChatroomFragment_MembersInjector.injectUserSingleton(instance, DaggerAppComponent.this.userProvider.get());
        ChatroomFragment_MembersInjector.injectProviderFactory(instance, getViewModelProviderFactory());
        ChatroomFragment_MembersInjector.injectAdapter(instance, provideAdapterProvider.get());
        ChatroomFragment_MembersInjector.injectLayoutManager(instance, getLinearLayoutManager());
        return instance;
      }
    }

    private final class UserListFragmentSubcomponentFactory implements MainFragmentBuilderModule_ContributeUserListFragment.UserListFragmentSubcomponent.Factory {
      @Override
      public MainFragmentBuilderModule_ContributeUserListFragment.UserListFragmentSubcomponent create(
          UserListFragment arg0) {
        Preconditions.checkNotNull(arg0);
        return new UserListFragmentSubcomponentImpl(arg0);
      }
    }

    private final class UserListFragmentSubcomponentImpl implements MainFragmentBuilderModule_ContributeUserListFragment.UserListFragmentSubcomponent {
      private Provider<UserListRecyclerAdapter> provideAdapterProvider;

      private UserListFragmentSubcomponentImpl(UserListFragment arg0) {

        initialize(arg0);
      }

      private LinearLayoutManager getLinearLayoutManager() {
        return UserListModule_ProvideLayoutManagerFactory.provideLayoutManager(DaggerAppComponent.this.application);}

      @SuppressWarnings("unchecked")
      private void initialize(final UserListFragment arg0) {
        this.provideAdapterProvider = DoubleCheck.provider(UserListModule_ProvideAdapterFactory.create());
      }

      @Override
      public void inject(UserListFragment arg0) {
        injectUserListFragment(arg0);}

      @CanIgnoreReturnValue
      private UserListFragment injectUserListFragment(UserListFragment instance) {
        DaggerFragment_MembersInjector.injectAndroidInjector(instance, MainActivitySubcomponentImpl.this.getDispatchingAndroidInjectorOfObject());
        UserListFragment_MembersInjector.injectUserListRecyclerAdapter(instance, provideAdapterProvider.get());
        UserListFragment_MembersInjector.injectLayoutManager(instance, getLinearLayoutManager());
        return instance;
      }
    }

    private final class ProfileFragmentSubcomponentFactory implements MainFragmentBuilderModule_ContributeProfileFragment.ProfileFragmentSubcomponent.Factory {
      @Override
      public MainFragmentBuilderModule_ContributeProfileFragment.ProfileFragmentSubcomponent create(
          ProfileFragment arg0) {
        Preconditions.checkNotNull(arg0);
        return new ProfileFragmentSubcomponentImpl(arg0);
      }
    }

    private final class ProfileFragmentSubcomponentImpl implements MainFragmentBuilderModule_ContributeProfileFragment.ProfileFragmentSubcomponent {
      private ProfileFragmentSubcomponentImpl(ProfileFragment arg0) {

      }

      @Override
      public void inject(ProfileFragment arg0) {
        injectProfileFragment(arg0);}

      @CanIgnoreReturnValue
      private ProfileFragment injectProfileFragment(ProfileFragment instance) {
        DaggerFragment_MembersInjector.injectAndroidInjector(instance, MainActivitySubcomponentImpl.this.getDispatchingAndroidInjectorOfObject());
        ProfileFragment_MembersInjector.injectUserSingleton(instance, DaggerAppComponent.this.userProvider.get());
        return instance;
      }
    }
  }
}
