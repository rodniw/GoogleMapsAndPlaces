package dev.rodni.ru.googlemapsandplaces.ui.mainpage;

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
import dev.rodni.ru.googlemapsandplaces.ui.login.LoginActivity;
import dev.rodni.ru.googlemapsandplaces.ui.profile.ProfileFragment;

import static dev.rodni.ru.googlemapsandplaces.util.Constants.ERROR_DIALOG_REQUEST;
import static dev.rodni.ru.googlemapsandplaces.util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static dev.rodni.ru.googlemapsandplaces.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


public class ListChatsFragment extends DaggerAppCompatActivity implements
        View.OnClickListener, ChatroomRecyclerAdapter.ChatroomRecyclerClickListener {
    private static final String TAG = "ListChatsFragment";
    private static final String TAG_USER = "TAG_USER";

    @Inject @Named("app_user")
    User userSingleton;

    //vars
    private ArrayList<Chatroom> mChatrooms = new ArrayList<>();
    private Set<String> mChatroomIds = new HashSet<>();

    //recycler view
    private ChatroomRecyclerAdapter mChatroomRecyclerAdapter;
    private RecyclerView mChatroomRecyclerView;

    private ListenerRegistration mChatroomEventListener;

    private UserLocation userLocation;

    //object for fetching from the database
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_of_chats);
        Log.d(TAG, "onCreate: ");

        mChatroomRecyclerView = findViewById(R.id.chatrooms_recycler_view);

        findViewById(R.id.fab_create_chatroom).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        initChatroomRecyclerView();

        //Toast.makeText(this, "User: " + userProvider.getUser().getEmail(), Toast.LENGTH_LONG).show();
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
            /*hideDialog();*/
            //if a chat room successfully created then navigate to chat room activity else show error message by the snackbar
            if(task.isSuccessful()){
                //navChatroomFragment(chatroom);
            }else{
                View parentLayout = findViewById(R.id.content);
                Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
            }
        });
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
                Toast.makeText(ListChatsFragment.this, "Enter a chatroom name", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    //click listener for dialog's list
    @Override
    public void onChatroomSelected(int position) {
        //navChatroomFragment(mChatrooms.get(position));
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
                    /*getLastKnownLocation();*/
                }
            });
        }
        else{
            /*getLastKnownLocation();*/
        }
    }
}
