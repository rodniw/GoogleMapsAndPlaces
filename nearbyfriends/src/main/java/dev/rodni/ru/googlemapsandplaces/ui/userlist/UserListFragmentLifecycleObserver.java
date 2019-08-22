package dev.rodni.ru.googlemapsandplaces.ui.userlist;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.maps.MapView;

import javax.inject.Inject;

import static androidx.lifecycle.Lifecycle.Event.ON_ANY;
import static androidx.lifecycle.Lifecycle.Event.ON_DESTROY;
import static androidx.lifecycle.Lifecycle.Event.ON_PAUSE;
import static androidx.lifecycle.Lifecycle.Event.ON_RESUME;
import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

//this class created to reduce amount of the callbacks into the user list fragment
public class UserListFragmentLifecycleObserver implements LifecycleObserver {
    private static final String TAG = "UserListFragmentLifecyc";

    //we need MapView to call its own callback methods when observe to fragments callback methods
    private MapView map;

    //di with MapView with the constructor
    @Inject
    public UserListFragmentLifecycleObserver(MapView map) {
        this.map = map;
        Log.d(TAG, "!Contructor!");
    }

    //this method simply log our callbacks
    @OnLifecycleEvent(ON_ANY)
    void printLog(LifecycleOwner owner, Lifecycle.Event event) {
        Log.d(TAG, "printLog: " + event);
    }

    //all these methods are using to call mapview callback methods
    @OnLifecycleEvent(ON_START)
    void startMapView(LifecycleOwner owner) {
        map.onStart();
    }

    @OnLifecycleEvent(ON_RESUME)
    void resumeMapView(LifecycleOwner owner) {
        map.onResume();
    }

    @OnLifecycleEvent(ON_PAUSE)
    void pauseMapView(LifecycleOwner owner) {
        map.onPause();
    }

    @OnLifecycleEvent(ON_STOP)
    void stopMapView(LifecycleOwner owner) {
        map.onStop();
    }

    @OnLifecycleEvent(ON_DESTROY)
    void destroyMapView(LifecycleOwner owner) {
        map.onDestroy();
    }

}
