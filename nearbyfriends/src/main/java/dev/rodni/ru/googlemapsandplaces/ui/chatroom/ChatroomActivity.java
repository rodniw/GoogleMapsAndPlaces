package dev.rodni.ru.googlemapsandplaces.ui.chatroom;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.UserClient;
import dev.rodni.ru.googlemapsandplaces.models.chatdata.ChatMessage;
import dev.rodni.ru.googlemapsandplaces.models.chatdata.Chatroom;
import dev.rodni.ru.googlemapsandplaces.models.userdata.User;
import dev.rodni.ru.googlemapsandplaces.models.userdata.UserLocation;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListFragment;

//TODO: refactor this activity to a fragment and use di, mvvm
public class ChatroomActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ChatroomActivity";

    //parcelable model class of the chatroom
    private Chatroom chatroom;

    //view
    private EditText messageEditText;

    //recycler view and adapter
    private RecyclerView chatMessageRecyclerView;
    private ChatMessageRecyclerAdapter chatMessageRecyclerAdapter;

    //firebase dependencies
    private ListenerRegistration chatMessageEventListener, userListEventListener;
    private FirebaseFirestore firestoreInstance;

    //lists for messages, its id's, users, user's locations
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private Set<String> messageIds = new HashSet<>();
    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayList<UserLocation> userLocations = new ArrayList<>();

    //fragment which shows the list of users in this chat
    private UserListFragment mUserListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init views
        setContentView(R.layout.activity_chatroom);
        messageEditText = findViewById(R.id.input_message);
        chatMessageRecyclerView = findViewById(R.id.chatmessage_recycler_view);
        findViewById(R.id.checkmark).setOnClickListener(this);

        //init firestore
        firestoreInstance = FirebaseFirestore.getInstance();

        getIncomingIntent();
        initChatroomRecyclerView();
        getChatroomUsers();
    }

    private void getChatMessages(){

        CollectionReference messagesRef = firestoreInstance
                .collection(getString(R.string.collection_chatrooms))
                .document(chatroom.getChatroom_id())
                .collection(getString(R.string.collection_chat_messages));

        chatMessageEventListener = messagesRef
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "onEvent: Listen failed.", e);
                        return;
                    }

                    if(queryDocumentSnapshots != null){
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                            ChatMessage message = doc.toObject(ChatMessage.class);
                            if(!messageIds.contains(message.getMessage_id())){
                                messageIds.add(message.getMessage_id());
                                chatMessages.add(message);
                                chatMessageRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                            }

                        }
                        chatMessageRecyclerAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getChatroomUsers(){

        CollectionReference usersRef = firestoreInstance
                .collection(getString(R.string.collection_chatrooms))
                .document(chatroom.getChatroom_id())
                .collection(getString(R.string.collection_chatroom_user_list));

        userListEventListener = usersRef
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "onEvent: Listen failed.", e);
                        return;
                    }
                    if(queryDocumentSnapshots != null){
                        // Clear the list and add all the users again
                        usersList.clear();
                        usersList = new ArrayList<>();

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            User user = doc.toObject(User.class);
                            usersList.add(user);
                            try {
                                getUserLocation(user);
                            } catch (NullPointerException exception) {
                                Toast.makeText(this, "An error occured: " + exception, Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.d(TAG, "onEvent: user list size: " + usersList.size());
                    }
                });
    }

    //this method inflates the list of other user's locations
    private void getUserLocation(User user) throws NullPointerException {
        //fetching from the User Locations collection other users by their Ids
        DocumentReference locationRef = firestoreInstance.collection(getString(R.string.collection_user_locations))
                .document(user.getUser_id());

        //inflating the list of user's location with the help of UserLocation pojo class from firestore
        locationRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().toObject(UserLocation.class) != null) {
                    userLocations.add(task.getResult().toObject(UserLocation.class));
                }
            }
        });
    }

    private void initChatroomRecyclerView(){
        chatMessageRecyclerAdapter = new ChatMessageRecyclerAdapter(chatMessages, new ArrayList<User>(), this);
        chatMessageRecyclerView.setAdapter(chatMessageRecyclerAdapter);
        chatMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatMessageRecyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                chatMessageRecyclerView.postDelayed(() -> {
                    if(chatMessages.size() > 0){
                        chatMessageRecyclerView.smoothScrollToPosition(
                                chatMessageRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 100);
            }
        });

    }


    private void insertNewMessage(){
        String message = messageEditText.getText().toString();

        if(!message.equals("")){
            message = message.replaceAll(System.getProperty("line.separator"), "");

            DocumentReference newMessageDoc = firestoreInstance
                    .collection(getString(R.string.collection_chatrooms))
                    .document(chatroom.getChatroom_id())
                    .collection(getString(R.string.collection_chat_messages))
                    .document();

            ChatMessage newChatMessage = new ChatMessage();
            newChatMessage.setMessage(message);
            newChatMessage.setMessage_id(newMessageDoc.getId());

            User user = ((UserClient)(getApplicationContext())).getUser();
            Log.d(TAG, "insertNewMessage: retrieved user client: " + user.toString());
            newChatMessage.setUser(user);

            newMessageDoc.set(newChatMessage).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    clearMessage();
                }else{
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearMessage(){
        messageEditText.setText("");
    }

    //this method send a user to the UserListFragment with bundled list of this chat room users
    private void inflateUserListFragment(){
        hideSoftKeyboard();

        //sending a list of parcelable User s and parcelable UserLocation s to the UserListFragment by a Bundle
        UserListFragment fragment = UserListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.intent_user_list), usersList);
        bundle.putParcelableArrayList(getString(R.string.intent_user_locations), userLocations);
        fragment.setArguments(bundle);

        //switching to the UserListFragment with transaction and animation with the bundled instance of the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        transaction.replace(R.id.user_list_container, fragment, getString(R.string.fragment_user_list));
        transaction.addToBackStack(getString(R.string.fragment_user_list));
        transaction.commit();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    //this method intercepting and processing an intent if that was send
    private void getIncomingIntent(){
        if(getIntent().hasExtra(getString(R.string.intent_chatroom))){
            chatroom = getIntent().getParcelableExtra(getString(R.string.intent_chatroom));
            setChatroomNameToActionBar();
            joinChatroom();
        }
    }

    private void leaveChatroom(){
        DocumentReference joinChatroomRef = firestoreInstance
                .collection(getString(R.string.collection_chatrooms))
                .document(chatroom.getChatroom_id())
                .collection(getString(R.string.collection_chatroom_user_list))
                .document(FirebaseAuth.getInstance().getUid());

        joinChatroomRef.delete();
    }

    //fetching chatroom's list of users and setting the pojo of the User which is singleton
    private void joinChatroom(){
        DocumentReference joinChatroomRef = firestoreInstance
                .collection(getString(R.string.collection_chatrooms))
                .document(chatroom.getChatroom_id())
                .collection(getString(R.string.collection_chatroom_user_list))
                .document(FirebaseAuth.getInstance().getUid());

        User user = ((UserClient)(getApplicationContext())).getUser();
        joinChatroomRef.set(user); // Don't care about listening for completion.
    }

    //setting the action bar with the name of the chat
    //this method is calling inside getIncomingIntent() which is inside onCreate()
    private void setChatroomNameToActionBar(){
        getSupportActionBar().setTitle(chatroom.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChatMessages();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(chatMessageEventListener != null){
            chatMessageEventListener.remove();
        }
        if(userListEventListener != null){
            userListEventListener.remove();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatroom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                UserListFragment fragment =
                        (UserListFragment) getSupportFragmentManager()
                                .findFragmentByTag(getString(R.string.fragment_user_list));
                if(fragment != null){
                    if(fragment.isVisible()){
                        getSupportFragmentManager().popBackStack();
                        return true;
                    }
                }
                finish();
                return true;
            }
            case R.id.action_chatroom_user_list:{
                inflateUserListFragment();
                return true;
            }
            case R.id.action_chatroom_leave:{
                leaveChatroom();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkmark:{
                insertNewMessage();
            }
        }
    }
}
