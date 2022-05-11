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
    public void setItemManageReviews(ArrayList<ItemManageReview> list){
        this.itemManageReviews = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemManageReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nicknamereview_tv,revisit_tv,review_date,review_user;
        EditText review_answer_et;
        ImageView review_img,revisitdecision_img;
        Button reviewanswer_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nicknamereview_tv = itemView.findViewById(R.id.nickname_review);
            revisit_tv = itemView.findViewById(R.id.revisit);
            review_user = itemView.findViewById(R.id.review_user);
            review_answer_et = itemView.findViewById(R.id.review_answer);
            review_img = itemView.findViewById(R.id.review_img);
            review_date = itemView.findViewById(R.id.review_date);
            revisitdecision_img = itemView.findViewById(R.id.revisit_decision);
            reviewanswer_btn = itemView.findViewById(R.id.reviewAnswer_btn);

        }
        void onBind(ItemManageReview item){
            nicknamereview_tv.setText(item.getNickname_review());
            revisitdecision_img.setImageResource(item.getRevisitdecision_img());
            review_img.setImageResource(item.getReview_img());
            review_user.setText(item.getReview_user());
            review_date.setText(item.getReview_date());



        }
    }


}
