package com.example.foodchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GetMenuReview_Adapter extends RecyclerView.Adapter<GetMenuReview_Adapter.ViewHolder> implements GetMenuReview_Listener{

    ArrayList<GetMenuReview_Item> Review_item = new ArrayList<>();

    public GetMenuReview_Adapter(){

    }

    //========클릭 이벤트 구현===========
    GetMenuReview_Listener listClickListener;

    public void setListClickListener (GetMenuReview_Listener listener){
        this.listClickListener = listener;
    }

    @NonNull
    @Override
    public GetMenuReview_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.store_review_list,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull GetMenuReview_Adapter.ViewHolder holder, int position) {
//ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
// 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(Review_item.get(position));


    }
    public void setReview_item(ArrayList<GetMenuReview_Item> list){
        this.Review_item = list;
        notifyDataSetChanged();
    }

    //클릭 이벤트 필요 요소 해당 포지션에 있는 아이템들 가져오기
    public GetMenuReview_Item getItem(int position){
        return Review_item.get(position);
    }


    @Override
    public void onListClick(ViewHolder holder, View view, int position) {
        if(listClickListener != null){
            listClickListener.onListClick(holder,view,position);
        }
    }

    @Override
    public int getItemCount() {
        return Review_item.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nickname_tv,date_tv,reviewMension_tv,ceoreviewMension_tv;
        ImageView like_iv,photo_iv1,photo_iv2,photo_iv3;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname_tv = itemView.findViewById((R.id.StoreInfo_nickname_review));
            date_tv = itemView.findViewById(R.id.StoreInfo_date);
            reviewMension_tv = itemView.findViewById(R.id.StoreInfo_review_user);
            ceoreviewMension_tv = itemView.findViewById(R.id.StoreInfo_ceo_mension);

            like_iv = itemView.findViewById(R.id.StoreInfo_revisit_decision);
            photo_iv1 = itemView.findViewById(R.id.StoreInfo_review_img);
            photo_iv2 = itemView.findViewById(R.id.StoreInfo_review_img2);
            photo_iv3 = itemView.findViewById(R.id.StoreInfo_review_img3);


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


        }
        void onBind(GetMenuReview_Item item){
            nickname_tv.setText(item.getUser_nickname());
            date_tv.setText(item.getDate());
            reviewMension_tv.setText(item.getReview());
            ceoreviewMension_tv.setText(item.getCeo_review());

            like_iv.setImageResource(item.getLike_img());
            photo_iv1.setImageBitmap(item.getPhoto_img1());
            photo_iv2.setImageBitmap(item.getPhoto_img2());
            photo_iv3.setImageBitmap(item.getPhoto_img3());


        }
    }


}