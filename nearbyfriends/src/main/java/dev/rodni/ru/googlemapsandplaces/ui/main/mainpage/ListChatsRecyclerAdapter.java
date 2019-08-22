package dev.rodni.ru.googlemapsandplaces.ui.main.mainpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.chatdata.Chatroom;

/*
NOTE: This fragment has its setter method by which its possible to provide dependencies.
 */
public class ListChatsRecyclerAdapter extends RecyclerView.Adapter<ListChatsRecyclerAdapter.ViewHolder>{

    private ArrayList<Chatroom> chatrooms;
    private ChatroomRecyclerClickListener chatroomRecyclerClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chatroom_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view, chatroomRecyclerClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        (holder).chatroomTitle.setText(chatrooms.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return chatrooms.size();
    }

    public void setChatroomAdapter(ArrayList<Chatroom> chatrooms, ChatroomRecyclerClickListener chatroomRecyclerClickListener){
        this.chatrooms = chatrooms;
        this.chatroomRecyclerClickListener = chatroomRecyclerClickListener;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        TextView chatroomTitle;
        ChatroomRecyclerClickListener clickListener;

        public ViewHolder(View itemView, ChatroomRecyclerClickListener clickListener) {
            super(itemView);
            chatroomTitle = itemView.findViewById(R.id.chatroom_title);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onChatroomSelected(getAdapterPosition());
        }
    }

    public interface ChatroomRecyclerClickListener {
        void onChatroomSelected(int position);
    }
}
















