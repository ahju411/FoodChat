package com.example.foodchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManageReservationAdapter extends RecyclerView.Adapter<ManageReservationAdapter.ViewHolder> {

    ArrayList<ItemManageReview> itemManageReviews = new ArrayList<>();

    public ManageReservationAdapter(){

    }

    @NonNull
    @Override
    public ManageReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.store_addreview_list,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ManageReservationAdapter.ViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        // 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(itemManageReviews.get(position));


    }

    @Override
    public int getItemCount() {
        return itemManageReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        EditText editText;
        ImageView img;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        void onBind(ItemManageReview item){

        }
    }


}
