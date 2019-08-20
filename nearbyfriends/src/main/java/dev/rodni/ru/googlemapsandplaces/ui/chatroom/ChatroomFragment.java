package dev.rodni.ru.googlemapsandplaces.ui.chatroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;
import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.chatdata.ChatMessage;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.chatdata.Chatroom;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.UserLocation;
import dev.rodni.ru.googlemapsandplaces.ui.userlist.UserListFragment;
import dev.rodni.ru.googlemapsandplaces.util.viewmodels.ViewModelProviderFactory;

//TODO: refactor this activity to a fragment and use di, mvvm
public class ChatroomFragment extends DaggerFragment implements View.OnClickListener {
    private static final String TAG = "ChatroomFragment";
    private static final String TAG_USER = "TAG_USER";

    @Inject @Named("app_user")
    User userSingleton;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    ChatMessageRecyclerAdapter adapter;
    @Inject
    LinearLayoutManager layoutManager;

    private ChatroomViewModel viewModel;

    //parcelable model class of the chatroom
    private Chatroom chatroom;

    //view
    private EditText messageEditText;

    //recycler view and adapter
    private RecyclerView chatMessageRecyclerView;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init firestore
        firestoreInstance = FirebaseFirestore.getInstance();

        Log.d(TAG_USER, "onCreate: " + userSingleton.toString());

        initChatroomRecyclerView();
        getChatroomUsers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chatroom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        messageEditText = view.findViewById(R.id.input_message);
        chatMessageRecyclerView = view.findViewById(R.id.chatmessage_recycler_view);

        view.findViewById(R.id.checkmark).setOnClickListener(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(ChatroomViewModel.class);

        super.onViewCreated(view, savedInstanceState);
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
                        adapter.notifyDataSetChanged();
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
                                Toast.makeText(getActivity(), "An error occured: " + exception, Toast.LENGTH_SHORT).show();
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
        adapter = new ChatMessageRecyclerAdapter(chatMessages, new ArrayList<User>(), getActivity());
        chatMessageRecyclerView.setAdapter(adapter);
        chatMessageRecyclerView.setLayoutManager(layoutManager);

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

            //User user = userProvider.getUser();
            //User user = ((UserProvider)(getApplicationContext())).getUser();
            Log.d(TAG, "insertNewMessage: retrieved user client: " + userSingleton.toString());
            newChatMessage.setUser(userSingleton);

            newMessageDoc.set(newChatMessage).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    clearMessage();
                }else{
                    //View parentLayout = findViewById(android.R.id.content);
                    //Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearMessage(){
        messageEditText.setText("");
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        Log.d(TAG_USER, "joinChatroom: " + userSingleton.toString());
        //User user = ((UserProvider)(getApplicationContext())).getUser();
        User user = userSingleton;
        joinChatroomRef.set(user); // Don't care about listening for completion.
    }

    @Override
    public void onResume() {
        super.onResume();
        getChatMessages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(chatMessageEventListener != null){
            chatMessageEventListener.remove();
        }
        if(userListEventListener != null){
            userListEventListener.remove();
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
