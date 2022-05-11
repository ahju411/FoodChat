//package com.example.foodchat;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class ManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    Context context;
//    private ArrayList<ItemData> items = new ArrayList<>();
//    private ArrayList<ItemManageReview> itemManageReviews = new ArrayList<>();
//    private ArrayList<ItemManageReservation> reservationItems = new ArrayList<>();
//    private ViewValue sel_type;
//
//    public ManagerAdapter(ViewValue sel_type){
//        this.sel_type = sel_type;
//    }
//
//    @NonNull
//    @Override
//    public ManageReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //원하는 layout띄우기
//        View view;
//        context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        if(sel_type == ViewValue.MANAGEREVIEW){
//            Log.d("LLLL","리스트뷰 받음");
//            view = inflater.inflate(R.layout.store_addreview_list,parent,false);
//            return new ManageReviewViewHolder(view);
//        }else if(sel_type == ViewValue.MANAGERESERVATION){
//            Log.d("LLLL","예약 받아야지");
//            view = inflater.inflate(R.layout.store_addreservation_list,parent,false);
//            return new ManageReviewViewHolder(view);
//        }
//        Log.d("LLLL","아무 일도 없었어");
//
//        return null;
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
//        // 보통 onBind 함수 안에 모두 넣어줍니다.
//        if(holder instanceof ManageReviewViewHolder){
//            ManageReviewViewHolder manageReviewViewHolder = (ManageReviewViewHolder) holder;
//            manageReviewViewHolder.onBind(itemManageReviews.get(position));
//        }else if (holder instanceof  ManageReservationViewHolder){
//            ManageReservationViewHolder manageReservationViewHolder = (ManageReservationViewHolder) holder;
//            manageReservationViewHolder.onBind(itemManageReviews.get(position));
//        }
//    }
//
//
//
//    void setItems(ItemData data){
//        items.add(data);
//    }
//
//    public void setItemManageReviews(ArrayList<ItemManageReview> list){
//        this.itemManageReviews = list;
//        notifyDataSetChanged();
//    }
//    public void setItemManageReservations(ArrayList<ItemManageReservation> list){
//        this.reservationItems = list;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    class ManageReviewViewHolder extends RecyclerView.ViewHolder{
//        TextView nicknamereview_tv,revisit_tv,review_date,review_user;
//        EditText review_answer_et;
//        ImageView review_img,revisitdecision_img;
//        Button reviewanswer_btn;
//        ItemManageReview data;
//
//        public ManageReviewViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nicknamereview_tv = itemView.findViewById(R.id.nickname_review);
//            revisit_tv = itemView.findViewById(R.id.revisit);
//            review_user = itemView.findViewById(R.id.review_user);
//            review_answer_et = itemView.findViewById(R.id.review_answer);
//            review_img = itemView.findViewById(R.id.review_img);
//            review_date = itemView.findViewById(R.id.review_date);
//            revisitdecision_img = itemView.findViewById(R.id.revisit_decision);
//            reviewanswer_btn = itemView.findViewById(R.id.reviewAnswer_btn);
//
//        }
//        void onBind(ItemData data){
//            this.data = (ItemManageReview) data;
//            nicknamereview_tv.setText(this.data.getNickname_review());
//            revisitdecision_img.setImageResource(this.data.getRevisitdecision_img());
//            review_img.setImageResource(this.data.getReview_img());
//            review_user.setText(this.data.getReview_user());
//            review_date.setText(this.data.getReview_date());
//        }
//    }
//    class ManageReservationViewHolder extends RecyclerView.ViewHolder{
//        TextView reservationname_tv,reservationphone_tv,reservationnumber_tv,reservationdate_tv;
//        Button reservationtrue_btn,reservationfalse_btn;
//        ItemManageReservation data;
//
//
//        public ManageReservationViewHolder(@NonNull View itemView) {
//            super(itemView);
//            reservationname_tv = itemView.findViewById(R.id.reservation_name);
//            reservationphone_tv = itemView.findViewById(R.id.reservation_phonenum);
//            reservationnumber_tv = itemView.findViewById(R.id.reservation_number);
//            reservationdate_tv = itemView.findViewById(R.id.reservation_time);
//            reservationtrue_btn = itemView.findViewById(R.id.reservation_accept_btn);
//            reservationfalse_btn = itemView.findViewById(R.id.reservation_reject_btn);
//        }
//        void onBind(ItemData data){
//            this.data = (ItemManageReservation) data;
//            reservationname_tv.setText(this.data.getReservation_name());
//            reservationphone_tv.setText(this.data.getReservation_phone());
//            reservationnumber_tv.setText(this.data.reservation_number);
//            reservationdate_tv.setText(this.data.reservation_date);
//        }
//    }
//
//}
