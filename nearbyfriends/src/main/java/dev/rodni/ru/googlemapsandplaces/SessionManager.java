package dev.rodni.ru.googlemapsandplaces;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.rodni.ru.googlemapsandplaces.data.database.entitiesdb.userdata.User;
import dev.rodni.ru.googlemapsandplaces.ui.auth.AuthResource;

//By this class i will handle users login status in that classes where i will inject
//this session manager. By this i will not care too much about users logout.
@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    //our holder of observable user
    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    //inject the contructor to have an access to this class in other parts of our app
    @Inject
    public SessionManager() {
    }

    //by this we will put our authed user into out holder
    public void authenticateWithId(final LiveData<AuthResource<User>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading(null));
            cachedUser.addSource(source, userAuthResource -> {
                cachedUser.setValue(userAuthResource);
                cachedUser.removeSource(source);
            });
        }
    }

    //changing auth resource status when we log out to say in all other places
    //that it needs to change view and application state after users logout
    public void logOut() {
        Log.d(TAG, "Logging out..");
        cachedUser.setValue(AuthResource.logout());
    }

    //simple getter
    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
