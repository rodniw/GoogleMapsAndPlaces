// Generated by Dagger (https://dagger.dev).
package dev.rodni.ru.googlemapsandplaces.ui.registration;

import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity_MembersInjector;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RegisterActivity_MembersInjector implements MembersInjector<RegisterActivity> {
  private final Provider<DispatchingAndroidInjector<Object>> androidInjectorProvider;

  private final Provider<User> userSingletonProvider;

  public RegisterActivity_MembersInjector(
      Provider<DispatchingAndroidInjector<Object>> androidInjectorProvider,
      Provider<User> userSingletonProvider) {
    this.androidInjectorProvider = androidInjectorProvider;
    this.userSingletonProvider = userSingletonProvider;
  }

  public static MembersInjector<RegisterActivity> create(
      Provider<DispatchingAndroidInjector<Object>> androidInjectorProvider,
      Provider<User> userSingletonProvider) {
    return new RegisterActivity_MembersInjector(androidInjectorProvider, userSingletonProvider);}

  @Override
  public void injectMembers(RegisterActivity instance) {
    DaggerAppCompatActivity_MembersInjector.injectAndroidInjector(instance, androidInjectorProvider.get());
    injectUserSingleton(instance, userSingletonProvider.get());
  }

  public static void injectUserSingleton(RegisterActivity instance, User userSingleton) {
    instance.userSingleton = userSingleton;
  }
}
