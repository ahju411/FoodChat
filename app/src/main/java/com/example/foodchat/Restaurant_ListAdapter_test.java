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

public class Restaurant_ListAdapter_test extends RecyclerView.Adapter<Restaurant_ListAdapter_test.ViewHolder> {

    ArrayList<Restaurant_List_Item_test> res_list_item = new ArrayList<>();

    public Restaurant_ListAdapter_test(){

    }

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