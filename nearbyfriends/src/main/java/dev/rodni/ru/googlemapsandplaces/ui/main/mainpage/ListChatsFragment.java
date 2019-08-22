package dev.rodni.ru.googlemapsandplaces.ui.main.mainpage;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;
import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.chatdata.Chatroom;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.UserLocation;
import dev.rodni.ru.googlemapsandplaces.util.viewmodels.ViewModelProviderFactory;

//refactored to the fragment
//i will use navigation here
public class ListChatsFragment extends DaggerFragment implements
        View.OnClickListener, ListChatsRecyclerAdapter.ChatroomRecyclerClickListener {
    private static final String TAG = "ListChatsFragment";
    private static final String TAG_USER = "TAG_USER";

    @Inject @Named("app_user")
    User userSingleton;
    @Inject
    ListChatsRecyclerAdapter listChatsRecyclerAdapter;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    ViewModelProviderFactory providerFactory;

    private ListChatsViewModel viewModel;

    //vars
    private ArrayList<Chatroom> chatrooms = new ArrayList<>();
    private Set<String> chatroomIds = new HashSet<>();

    //recycler view
    //private ListChatsRecyclerAdapter listChatsRecyclerAdapter;
    private RecyclerView chatroomRecyclerView;

    private ListenerRegistration chatroomEventListener;

    private UserLocation userLocation;

    //object for fetching from the database
    private FirebaseFirestore mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        mDb = FirebaseFirestore.getInstance();

        initChatroomRecyclerView();

        //Toast.makeText(this, "User: " + userProvider.getUser().getEmail(), Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bind to the ListChatsViewModel
        viewModel = new ViewModelProvider(this, providerFactory).get(ListChatsViewModel.class);

        chatroomRecyclerView = view.findViewById(R.id.chatrooms_recycler_view);

        view.findViewById(R.id.fab_create_chatroom).setOnClickListener(this);
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
        
        //listChatsRecyclerAdapter = new ListChatsRecyclerAdapter();
        listChatsRecyclerAdapter.setChatroomAdapter(chatrooms, this);
        chatroomRecyclerView.setAdapter(listChatsRecyclerAdapter);
        //chatroomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatroomRecyclerView.setLayoutManager(layoutManager);
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
        chatroomEventListener = chatroomsCollection.addSnapshotListener((queryDocumentSnapshots, e) -> {
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
                    if(!chatroomIds.contains(chatroom.getChatroom_id())){
                        chatroomIds.add(chatroom.getChatroom_id());
                        chatrooms.add(chatroom);
                    }
                }
                Log.d(TAG, "onEvent: number of chatrooms: " + chatrooms.size());
                listChatsRecyclerAdapter.notifyDataSetChanged();
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
                View parentLayout = getView().findViewById(R.id.content);
                Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    //this method builds a dialog where a user can create a new dialog and set its name
    private void newChatroomDialog(){
        Log.d(TAG, "newChatroomDialog: ");
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter a chatroom name");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("CREATE", (dialog, which) -> {
            if(!input.getText().toString().equals("")){
                buildNewChatroom(input.getText().toString());
            }
            else {
                Toast.makeText(getActivity(), "Enter a chatroom name", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    //click listener for dialog's list
    @Override
    public void onChatroomSelected(int position) {
        //navChatroomFragment(chatrooms.get(position));
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
