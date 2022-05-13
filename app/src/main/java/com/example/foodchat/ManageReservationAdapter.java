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

    @Override
    public int getItemCount() {
        return itemManageReservations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView reser_name,reser_phone,reser_numberres,reser_date;
        Button btn_accept,btn_reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reser_name = itemView.findViewById(R.id.reservation_name);
            reser_date = itemView.findViewById(R.id.reservation_date);
            reser_numberres = itemView.findViewById(R.id.reservation_numberReservation);
            reser_phone = itemView.findViewById(R.id.reservation_phone);
            btn_accept = itemView.findViewById(R.id.reservation_accept_btn);
            btn_reject = itemView.findViewById(R.id.reservation_reject_btn);

        }
        void onBind(ItemManageReservation item){
            reser_name.setText(item.getReservation_name());
            reser_phone.setText(item.getReservation_phone());
            reser_numberres.setText(item.getReservation_number());
            reser_date.setText(item.getReservation_date());

        }
    }


}
