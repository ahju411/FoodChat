package com.example.foodchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GetCEO_MenuReview_Adapter extends RecyclerView.Adapter<GetCEO_MenuReview_Adapter.ViewHolder> implements GetCEO_MenuReview_Listener{
    String test="";
    ArrayList<GetCEO_MenuReview_Item> Review_item = new ArrayList<>();

    public GetCEO_MenuReview_Adapter(){

    }

    //========클릭 이벤트 구현===========
    GetCEO_MenuReview_Listener listClickListener;

    public void setListClickListener (GetCEO_MenuReview_Listener listener){
        this.listClickListener = listener;
    }

    @NonNull
    @Override
    public GetCEO_MenuReview_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.store_review_list_answer,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull GetCEO_MenuReview_Adapter.ViewHolder holder, int position) {
//ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
// 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(Review_item.get(position));


    }
    public void setReview_item(ArrayList<GetCEO_MenuReview_Item> list){
        this.Review_item = list;
        notifyDataSetChanged();
    }

    //클릭 이벤트 필요 요소 해당 포지션에 있는 아이템들 가져오기
    public GetCEO_MenuReview_Item getItem(int position){
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

    public String getTest() { return test;}



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nickname_tv,date_tv,reviewMension_tv;
        EditText ceoreviewMension_tv;
        ImageView like_iv,photo_iv1,photo_iv2,photo_iv3;
        Button btn_submit;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname_tv = itemView.findViewById((R.id.CEOReview_nickname_review));
            date_tv = itemView.findViewById(R.id.CEOReview_date);
            reviewMension_tv = itemView.findViewById(R.id.CEOReview_review_user);
            ceoreviewMension_tv = itemView.findViewById(R.id.CEOReview_ceo_mension);

            like_iv = itemView.findViewById(R.id.CEOReview_revisit_decision);
            photo_iv1 = itemView.findViewById(R.id.CEOReview_review_img);
            photo_iv2 = itemView.findViewById(R.id.CEOReview_review_img2);
            photo_iv3 = itemView.findViewById(R.id.CEOReview_review_img3);
            btn_submit = itemView.findViewById(R.id.review_answer_btn);
            //버튼클릭 이벤트 구현
            btn_submit.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    test =ceoreviewMension_tv.getText().toString();
                    System.out.println("값뭐죠"+test);
                    if(listClickListener != null){
                        listClickListener.onListClick(GetCEO_MenuReview_Adapter.ViewHolder.this,view,position);
                    }
                }
            }));

            //아이템 클릭 이벤트 구현
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    test =ceoreviewMension_tv.getText().toString();
                    if(listClickListener != null){
                        listClickListener.onListClick(ViewHolder.this,view,position);
                    }
                }
            });


        }
        void onBind(GetCEO_MenuReview_Item item){
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