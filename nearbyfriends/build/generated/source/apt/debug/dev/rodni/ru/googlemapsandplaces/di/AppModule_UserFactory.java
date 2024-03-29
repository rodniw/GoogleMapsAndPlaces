// Generated by Dagger (https://dagger.dev).
package dev.rodni.ru.googlemapsandplaces.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dev.rodni.ru.googlemapsandplaces.data.database.entitiesdb.userdata.User;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_UserFactory implements Factory<User> {
  private static final AppModule_UserFactory INSTANCE = new AppModule_UserFactory();

  @Override
  public User get() {
    return user();
  }

  public static AppModule_UserFactory create() {
    return INSTANCE;
  }

  public static User user() {
    return Preconditions.checkNotNull(AppModule.user(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
