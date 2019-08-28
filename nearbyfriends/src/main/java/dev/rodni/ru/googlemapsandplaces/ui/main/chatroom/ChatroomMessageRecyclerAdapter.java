package dev.rodni.ru.googlemapsandplaces.ui.main.chatroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.data.database.entitiesdb.chatdata.ChatMessage;
import dev.rodni.ru.googlemapsandplaces.data.database.entitiesdb.userdata.User;

/*
NOTE: This fragment has its setter method by which its possible to provide lists and context.
 */
public class ChatroomMessageRecyclerAdapter extends RecyclerView.Adapter<ChatroomMessageRecyclerAdapter.ViewHolder>{

    private List<ChatMessage> chatMessages;
    private List<User> usersList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_message_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(FirebaseAuth.getInstance().getUid().equals(chatMessages.get(position).getUser().getUser_id())){
            (holder).username.setTextColor(ContextCompat.getColor(context, R.color.green1));
        }
        else{
            (holder).username.setTextColor(ContextCompat.getColor(context, R.color.blue2));
        }

        (holder).username.setText(chatMessages.get(position).getUser().getUsername());
        (holder).message.setText(chatMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void setChatMessageAdapter(List<ChatMessage> chatMessages, List<User> usersList, Context context){
        this.chatMessages = chatMessages;
        this.usersList = usersList;
        this.context = context;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView message, username;

        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message_message);
            username = itemView.findViewById(R.id.chat_message_username);
        }
    }
}
















