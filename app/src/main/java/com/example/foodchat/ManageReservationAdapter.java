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

    ArrayList<ItemManageReservation> itemManageReservations = new ArrayList<>();

    public ManageReservationAdapter(){

    }
    //========클릭 이벤트 구현===========
    private ManageReservationListener ClickListener;
    private ManageReservationListener AcceptClickListener;
    private ManageReservationListener RejectClickListener;



    public void setOnAcceptClickListener (ManageReservationListener listener){
        this.ClickListener = listener;
        this.AcceptClickListener = AcceptClickListener;
        this.RejectClickListener = RejectClickListener;

    }




    @NonNull
    @Override
    public ManageReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.store_addreservation_list,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ManageReservationAdapter.ViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        // 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(itemManageReservations.get(position));


    }
    public void setItemManageReservations(ArrayList<ItemManageReservation> list){
        this.itemManageReservations = list;
        notifyDataSetChanged();
    }
    //클릭 이벤트 필요 요소 해당 포지션에 있는 아이템들 가져오기
    public ItemManageReservation getItem(int position){
        return itemManageReservations.get(position);
    }

    @Override
    public int getItemCount() {
        return itemManageReservations.size();
    }


    public void onAcceptClick(ManageReservationAdapter.ViewHolder holder, View view, int position) {
        if(AcceptClickListener != null){
            AcceptClickListener.onAcceptClick(holder,view,position);
        }
    }


    public void onRejectClick(ManageReservationAdapter.ViewHolder holder, View view, int position) {
        if(RejectClickListener != null){
            RejectClickListener.onRejectClick(holder,view,position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView reser_date,reser_usernickname,reser_storename,reser_people,reser_check,reser_time;
        Button btn_accept,btn_reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reser_storename = itemView.findViewById(R.id.reservation_storename);
            reser_usernickname = itemView.findViewById(R.id.reservation_usernickname);
            reser_people = itemView.findViewById(R.id.reservation_people);
            reser_date = itemView.findViewById(R.id.reservation_date);
            reser_time = itemView.findViewById(R.id.reservation_timee);
            reser_check = itemView.findViewById(R.id.reservation_check);
            btn_accept = itemView.findViewById(R.id.reservation_accept_btn);
            btn_reject = itemView.findViewById(R.id.reservation_reject_btn);


            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    System.out.println("수락눌러짐요");
                    if(ClickListener != null){
                        ClickListener.onAcceptClick(ManageReservationAdapter.ViewHolder.this,view,position);
                    }
                }
            });

            btn_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    System.out.println("거절눌러짐요");
                    if(ClickListener != null){
                        ClickListener.onRejectClick(ManageReservationAdapter.ViewHolder.this,view,position);
                    }

                }
            });




        }
        void onBind(ItemManageReservation item){
            reser_storename.setText(item.getStorename());
            reser_usernickname.setText(item.getUsernickname());
            reser_people.setText(item.getReservation_people());
            reser_date.setText(item.getReservation_date());
            reser_time.setText(item.getReservation_time());
            reser_check.setText(item.getReservation_check());
        }
    }


}
