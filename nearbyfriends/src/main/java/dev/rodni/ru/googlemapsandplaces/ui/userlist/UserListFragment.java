package dev.rodni.ru.googlemapsandplaces.ui.userlist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import dagger.android.support.DaggerFragment;
import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.UserLocation;

import static dev.rodni.ru.googlemapsandplaces.util.Constants.MAPVIEW_BUNDLE_KEY;

public class UserListFragment extends DaggerFragment implements OnMapReadyCallback {

    private static final String TAG = "UserListFragment";

    //recycler view
    private RecyclerView userListRecyclerView;

    //map view
    private MapView mapView;

    //lifecycler observer
    private UserListFragmentLifecycleObserver lifecycleObserver;

    //lists
    private List<User> usersList = new ArrayList<>();
    private List<UserLocation> usersLocationsList = new ArrayList<>();

    //recycler adapter
    private UserRecyclerAdapter userRecyclerAdapter;

    //simple static get instance method
    public static UserListFragment newInstance(){
        return new UserListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            usersList = getArguments().getParcelableArrayList(getString(R.string.intent_user_list));
            usersLocationsList = getArguments().getParcelableArrayList(getString(R.string.intent_user_locations));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_user_list, container, false);
        userListRecyclerView = view.findViewById(R.id.user_list_recycler_view);
        mapView = view.findViewById(R.id.user_list_map);

        //initialize the fragment's lifecycle observer and pass there the mapView
        //to handle its lifecycle callback and reduce code boilerplate
        //TODO: make this with dagger2
        lifecycleObserver = new UserListFragmentLifecycleObserver(mapView);
        getLifecycle().addObserver(lifecycleObserver);

        initUserListRecyclerView();

        initGoogleMap(savedInstanceState);

        //checking the list of user's locations
        //for (UserLocation userlocation : usersLocationsList) {
        //    Log.d(TAG, "onCreateView: user location " + userlocation.getUser().getUsername());
        //    Log.d(TAG,
        //            "onCreateView: user geopoint " +
        //                    userlocation.getGeo_point().getLatitude() +
        //                    "\n" +
        //                    userlocation.getGeo_point().getLongitude());
        //}

        return view;
    }

    //this method takes saved bundle and put the point onto the map at the saved place inside onCreateView method
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        //if we have info inside our bundle object then we will restore maps state
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        //i inflate the map view with the bundle
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    //simple init recycler view
    private void initUserListRecyclerView(){
        userRecyclerAdapter = new UserRecyclerAdapter(usersList);
        userListRecyclerView.setAdapter(userRecyclerAdapter);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //here i save my location into the map
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    //the method from the OnMapReadyCallback interface
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //checking for the location permission and if the user hasnt it then show his the access perm dialog
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    //handle mapview when we have onLowMemory callback
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}



















