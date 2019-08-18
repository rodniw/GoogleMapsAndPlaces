package dev.rodni.ru.googlemapsandplaces.ui.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.maps.MapView;

import static androidx.lifecycle.Lifecycle.Event.ON_DESTROY;
import static androidx.lifecycle.Lifecycle.Event.ON_PAUSE;
import static androidx.lifecycle.Lifecycle.Event.ON_RESUME;
import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

public class UserListFragmentLifecycleObserver implements LifecycleObserver {

    private MapView map;

    public UserListFragmentLifecycleObserver(MapView map) {
        this.map = map;
    }

    @OnLifecycleEvent(ON_START)
    void startMapView(LifecycleOwner owner, Lifecycle.Event event) {
        map.onStart();
    }

    @OnLifecycleEvent(ON_RESUME)
    void resumeMapView(LifecycleOwner owner, Lifecycle.Event event) {
        map.onResume();
    }

    @OnLifecycleEvent(ON_PAUSE)
    void pauseMapView(LifecycleOwner owner, Lifecycle.Event event) {
        map.onPause();
    }

    @OnLifecycleEvent(ON_STOP)
    void stopMapView(LifecycleOwner owner, Lifecycle.Event event) {
        map.onStop();
    }

    @OnLifecycleEvent(ON_DESTROY)
    void destroyMapView(LifecycleOwner owner, Lifecycle.Event event) {
        map.onDestroy();
    }

}
