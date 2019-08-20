package dev.rodni.ru.googlemapsandplaces.ui.main;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;
import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.models.chatdata.Chatroom;
import dev.rodni.ru.googlemapsandplaces.models.userdata.User;
import dev.rodni.ru.googlemapsandplaces.models.userdata.UserLocation;
import dev.rodni.ru.googlemapsandplaces.services.LocationService;
import dev.rodni.ru.googlemapsandplaces.ui.chatroom.ChatroomActivity;
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity;
import dev.rodni.ru.googlemapsandplaces.ui.profile.ProfileActivity;

import static dev.rodni.ru.googlemapsandplaces.util.Constants.ERROR_DIALOG_REQUEST;
import static dev.rodni.ru.googlemapsandplaces.util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static dev.rodni.ru.googlemapsandplaces.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


public class MainActivity extends DaggerAppCompatActivity implements
        View.OnClickListener, ChatroomRecyclerAdapter.ChatroomRecyclerClickListener {
    private static final String TAG = "MainActivity";
    private static final String TAG_USER = "TAG_USER";

    @Inject @Named("app_user")
    User userSingleton;

    //view
    private ProgressBar mProgressBar;

    //vars
    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private Set<String> mChatroomIds = new HashSet<>();

    //recycler view
    private ChatroomRecyclerAdapter mChatroomRecyclerAdapter;
    private RecyclerView mChatroomRecyclerView;

    private ListenerRegistration mChatroomEventListener;

    //object for fetching from the database
    private FirebaseFirestore mDb;

    //the var for location permission
    private boolean mLocationPermissionGranted = false;

    //for location
    private FusedLocationProviderClient fusedLocationClient;
    private UserLocation userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        
        mProgressBar = findViewById(R.id.progressBar);
        mChatroomRecyclerView = findViewById(R.id.chatrooms_recycler_view);

        findViewById(R.id.fab_create_chatroom).setOnClickListener(this);

        //initialize fused location client from the system services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mDb = FirebaseFirestore.getInstance();

        initSupportActionBar();
        initChatroomRecyclerView();

        //Toast.makeText(this, "User: " + userProvider.getUser().getEmail(), Toast.LENGTH_LONG).show();
    }

    //work with the action bar
    private void initSupportActionBar(){
        setTitle("Chatrooms");
    }

    //this onClick handles the big plus button which adds a new chat room
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_create_chatroom:{
                newChatroomDialog();
            }
        }
    }

    //setting the recycler view
    private void initChatroomRecyclerView(){
        Log.d(TAG, "initChatroomRecyclerView: ");
        
        mChatroomRecyclerAdapter = new ChatroomRecyclerAdapter(mChatrooms, this);
        mChatroomRecyclerView.setAdapter(mChatroomRecyclerAdapter);
        mChatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //fetching the existing chat rooms
    private void getChatrooms(){
        Log.d(TAG, "getChatrooms: ");
        
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mDb.setFirestoreSettings(settings);

        CollectionReference chatroomsCollection = mDb
                .collection(getString(R.string.collection_chatrooms));

        //fetching a data
        mChatroomEventListener = chatroomsCollection.addSnapshotListener((queryDocumentSnapshots, e) -> {
            Log.d(TAG, "onEvent: called.");
            if (e != null) {
                Log.e(TAG, "onEvent: Listen failed.", e);
                return;
            }
            //if data exists then we parse it into chatroom object
            if(queryDocumentSnapshots != null){
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Chatroom chatroom = doc.toObject(Chatroom.class);
                    //check if a chat room already exists into the hashset
                    if(!mChatroomIds.contains(chatroom.getChatroom_id())){
                        mChatroomIds.add(chatroom.getChatroom_id());
                        mChatrooms.add(chatroom);
                    }
                }
                Log.d(TAG, "onEvent: number of chatrooms: " + mChatrooms.size());
                mChatroomRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    //this method build some brand new chat room
    private void buildNewChatroom(String chatroomName){
        Log.d(TAG, "buildNewChatroom: ");
        
        //create chatroom object
        final Chatroom chatroom = new Chatroom();
        chatroom.setTitle(chatroomName);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mDb.setFirestoreSettings(settings);

        DocumentReference newChatroomRef = mDb
                .collection(getString(R.string.collection_chatrooms))
                .document();

        chatroom.setChatroom_id(newChatroomRef.getId());

        newChatroomRef.set(chatroom).addOnCompleteListener(task -> {
            hideDialog();
            //if a chat room successfully created then navigate to chat room activity else show error message by the snackbar
            if(task.isSuccessful()){
                navChatroomActivity(chatroom);
            }else{
                View parentLayout = findViewById(R.id.content);
                Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    //simple method that navigates user to a chat room activity
    private void navChatroomActivity(Chatroom chatroom){
        Log.d(TAG, "navChatroomActivity: ");
        
        Intent intent = new Intent(MainActivity.this, ChatroomActivity.class);
        intent.putExtra(getString(R.string.intent_chatroom), chatroom);
        startActivity(intent);
    }

    //this method builds a dialog where a user can create a new dialog and set its name
    private void newChatroomDialog(){
        Log.d(TAG, "newChatroomDialog: ");
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a chatroom name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("CREATE", (dialog, which) -> {
            if(!input.getText().toString().equals("")){
                buildNewChatroom(input.getText().toString());
            }
            else {
                Toast.makeText(MainActivity.this, "Enter a chatroom name", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    //click listener for dialog's list
    @Override
    public void onChatroomSelected(int position) {
        navChatroomActivity(mChatrooms.get(position));
    }

    //sign out
    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_sign_out:{
                signOut();
                return true;
            }
            case R.id.action_profile:{
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    //in this method we receive users uid(then to pojo) and by this we ask for the last known location
    private void getUserDetails(){
        Log.d(TAG, "getUserDetails: ");
        
        if(userLocation == null){
            userLocation = new UserLocation();
            DocumentReference userRef = mDb.collection(getString(R.string.collection_users))
                    .document(FirebaseAuth.getInstance().getUid());

            userRef.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: successfully set the user client.");
                    userSingleton = task.getResult().toObject(User.class);
                    userLocation.setUser(userSingleton);
                    Log.d(TAG_USER, "getUserDetails: " + userSingleton.toString());
                    //((UserProvider)(getApplicationContext())).setUser(user);
                    //userApp = user;
                    getLastKnownLocation();
                }
            });
        }
        else{
            getLastKnownLocation();
        }
    }

    //in this location we get the last known users location, save it and then start to listen to it again
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: called.");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                userLocation.setGeo_point(geoPoint);
                userLocation.setTimestamp(null);
                saveUserLocation();
                startLocationService();
            }
        });

    }

    //we save users location into the firebase database
    private void saveUserLocation(){
        Log.d(TAG, "saveUserLocation: ");
        
        if(userLocation != null){
            DocumentReference locationRef = mDb
                    .collection(getString(R.string.collection_user_locations))
                    .document(FirebaseAuth.getInstance().getUid());

            locationRef.set(userLocation).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Log.d(TAG, "saveUserLocation: \ninserted user location into database." +
                            "\n latitude: " + userLocation.getGeo_point().getLatitude() +
                            "\n longitude: " + userLocation.getGeo_point().getLongitude());
                }
            });
        }
    }

    //return true if we have all the permission granted and google play service available
    private boolean checkMapServices(){
        Log.d(TAG, "checkMapServices: ");
        
        if(isPlayServicesAvailable()){
            if(isGPSEnabled()){
                return true;
            }
        }
        //return false if some of our conditions doesn't match
        return false;
    }

    //showing the dialog which asks to switch of the gps if it is off
    private void buildAlertMessageNoGps() {
        Log.d(TAG, "buildAlertMessageNoGps: ");
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    //page to enable gps
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //this method checks for gps provider availability
    public boolean isGPSEnabled(){
        Log.d(TAG, "isMapsEnabled: ");

        //This class provides access to the system location services.
        //These services allow applications to obtain periodic updates of the
        //device's geographical location, or to fire an application-specified
        //when the device enters the proximity of a given geographical location.
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        //if gps is off we show the dialog which asks to switch it on
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    //start our location listening service by the intent
    //if the user doesnt have already runnable location service
    private void startLocationService(){
        Log.d(TAG, "startLocationService: ");
        if(!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(this, LocationService.class);
            //check for the android version to decide to start foreground service of temporary service
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                //if higher than O then start foreground service
                MainActivity.this.startForegroundService(serviceIntent);
            }else{
                //if less that O then start temporary service
                startService(serviceIntent);
            }
        }
    }

    //this method checks if the user has some connection with google play services
    //return true if he has the connection, return false is the user has not
    //shows the dialog if its available to fix this connection
    public boolean isPlayServicesAvailable(){
        Log.d(TAG, "isServicesOK: checking google services version");
        
        //class GoogleApiAvailability can check availability of some google's features
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        //connection result is a common android.gms class which contains constants and things like constants
        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //this method check if we have our service running at the moment
    private boolean isLocationServiceRunning() {
        Log.d(TAG, "isLocationServiceRunning: ");
        //getting activity manager to check our running services
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            //return true if we have our service in the list of all services
            if("dev.rodni.ru.services.LocationService".equals(service.service.getClassName())) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.");
        //return false if we didnot find our service in the list of running services
        return false;
    }

    //checking if we have location permission
    //then we can get the list of our chat room by getChatrooms() method
    //and get the user's info by the getUserDetails() method that will fill UserLocation model class
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: ");
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getChatrooms();
            getUserDetails();
        } else {
            Log.d(TAG, "requestPermissions: ");
            //this requestPermissions shows us a dialog which asks for the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //asking for the location permission from the system
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    //getting the result of permission dialog
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    getChatrooms();
                    getUserDetails();
                }
                else{
                    getLocationPermission();
                }
            }
        }
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideDialog(){
        mProgressBar.setVisibility(View.GONE);
    }

    //inside of the onResume we
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        //this check for permission granted and gps provider availability
        if(checkMapServices()){
            //check for location permission
            if(mLocationPermissionGranted){
                //getting the chat rooms and the last known location
                getChatrooms();
                getUserDetails();
            }
            else{
                //if we dont have the permission lets ask for it
                getLocationPermission();
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        //if the user ve been listening for the chat rooms(in all the permissions success case)
        //then we disconnect from this listening when the activity is destroying
        if(mChatroomEventListener != null){
            mChatroomEventListener.remove();
        }
    }
}
