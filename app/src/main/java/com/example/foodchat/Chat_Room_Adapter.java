package com.example.foodchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodchat.Fragment_chat_room.OnListFragmentInteractionListener;
import com.example.foodchat.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class Chat_Room_Adapter extends RecyclerView.Adapter<Chat_Room_Adapter.ViewHolder> {


    private final ArrayList<Chat_room_info> chatting_room_list;

    public Chat_Room_Adapter(ArrayList<Chat_room_info> chatting_room_list) {
        this.chatting_room_list = chatting_room_list;
    }

    //========클릭 이벤트 구현===========
    private AddChattingListener itemClickListener;

    public void setOnItemClickListener (AddChattingListener listener){
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_room_line, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

            if (chatting_room_list.get(position).id1.equals(Restaurant_List_Home.logining_user_nickname)) {
                holder.friend_id.setText(chatting_room_list.get(position).id2);
            } else {
                holder.friend_id.setText(chatting_room_list.get(position).id1);
            }

        holder.last_text.setText(chatting_room_list.get(position).last_message);
        holder.last_time.setText(chatting_room_list.get(position).last_message_time);
    }

    @Override
    public int getItemCount() {
        return chatting_room_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView friend_id, last_time, last_text, trash;
        ImageView iv;






        public ViewHolder(View view) {
            super(view);

            friend_id = view.findViewById(R.id.cr_friend_id);
            last_time = view.findViewById(R.id.cr_last_time);
            last_text = view.findViewById(R.id.cr_last_text);
            trash = view.findViewById(R.id.trash);

            //클릭 이벤트 필요 요소
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if(itemClickListener !=null){
                        itemClickListener.onItemClick(Chat_Room_Adapter.ViewHolder.this, view, position);
                    }
                }
            });
        }


    }
    public Chat_room_info getItem(int position){
        return chatting_room_list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
