package com.example.foodchat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Restaurant_ListAdapter_test extends RecyclerView.Adapter<Restaurant_ListAdapter_test.ViewHolder> implements Restaurant_listListener{

    ArrayList<Restaurant_List_Item_test> res_list_item = new ArrayList<>();

    public Restaurant_ListAdapter_test(){

    }

    //========클릭 이벤트 구현===========
    Restaurant_listListener listClickListener;
    Restaurant_listListener StarClickListener;
    Restaurant_listListener ChatClickListener;

    public void setListClickListener (Restaurant_listListener listener){
        this.listClickListener = listener;
        this.StarClickListener = listener;
        this.ChatClickListener = listener;
    }
//    public void setStarClickListener (Restaurant_listListener listener){
//        this.StarClickListener = listener;
//    }
//
//    public void setChatClickListener (Restaurant_listListener listener){
//        this.ChatClickListener = listener;
//    }



    @NonNull
    @Override
    public Restaurant_ListAdapter_test.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.restaurant_rowui_list2,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull Restaurant_ListAdapter_test.ViewHolder holder, int position) {
//ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
// 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(res_list_item.get(position));


    }
    public void setRes_list_item(ArrayList<Restaurant_List_Item_test> list){
        this.res_list_item = list;
        notifyDataSetChanged();
    }

    //클릭 이벤트 필요 요소 해당 포지션에 있는 아이템들 가져오기
    public Restaurant_List_Item_test getItem(int position){
        return res_list_item.get(position);
    }


    @Override
    public void onListClick(ViewHolder holder, View view, int position) {
        if(listClickListener != null){
            listClickListener.onListClick(holder,view,position);
        }
    }

    @Override
    public void onStarClick(ViewHolder holder, View view, int position) {
        if(listClickListener != null){
            StarClickListener.onStarClick(holder,view,position);
        }
    }
    @Override
    public void onChatClick(ViewHolder holder, View view, int position) {
        if(listClickListener != null){
            ChatClickListener.onChatClick(holder,view,position);
        }
    }

    @Override
    public int getItemCount() {
        return res_list_item.size();
    }





    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name_tv,info_tv;

        ImageView logo_iv,chat_iv,star_iv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById((R.id.res_name));
            info_tv = itemView.findViewById((R.id.res_info));
            logo_iv = itemView.findViewById(R.id.res_logo);
            chat_iv = itemView.findViewById(R.id.res_chat);
            star_iv = itemView.findViewById(R.id.res_star);

            //아이템 클릭 이벤트 구현
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if(listClickListener != null){
                        listClickListener.onListClick(ViewHolder.this,view,position);
                    }
                }
            });

            //즐겨찾기 클릭 이벤트 구현
            star_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    System.out.println("즐겨찾기이 눌러짐!");
                    if(StarClickListener != null){
                        StarClickListener.onStarClick(ViewHolder.this,view,position);
                    }
                }
            });

            //채팅 클릭 이벤트 구현
            chat_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    System.out.println("채팅이 눌러짐!");
                    if(StarClickListener != null){
                        ChatClickListener.onChatClick(ViewHolder.this,view,position);
                    }
                }
            });


        }
        void onBind(Restaurant_List_Item_test item){
            name_tv.setText(item.getRes_name());
            info_tv.setText(item.getRes_info());
            logo_iv.setImageBitmap(item.getLogo_img());
            chat_iv.setImageResource(item.getChat_img());
            star_iv.setImageResource(item.getStar_img());

        }
    }


}