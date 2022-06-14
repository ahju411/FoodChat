package com.example.foodchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Menu_faq_Adapter extends RecyclerView.Adapter<Menu_faq_Adapter.ViewHolder> implements Menu_faq_Listener{

    ArrayList<Menu_faq_item> itemManageMenus = new ArrayList<>();

    public Menu_faq_Adapter(){

    }
    //========클릭 이벤트 구현===========
    private Menu_faq_Listener itemClickListener;

    public void setOnItemClickListener (Menu_faq_Listener listener){
        this.itemClickListener = listener;
    }




    @NonNull
    @Override
    public Menu_faq_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.faq_list,parent,false);
        Menu_faq_Adapter.ViewHolder viewHolder = new Menu_faq_Adapter.ViewHolder(view);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull Menu_faq_Adapter.ViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        // 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(itemManageMenus.get(position));


    }
    public void setItemManageMenus(ArrayList<Menu_faq_item> list){
        this.itemManageMenus = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemManageMenus.size();
    }

    //클릭 이벤트 필요 요소
    public Menu_faq_item getItem(int position){
        return itemManageMenus.get(position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(holder,view,position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Que,Ans;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Que = itemView.findViewById(R.id.faq_quess);
            Ans = itemView.findViewById(R.id.faq_anss);

            //클릭 이벤트 필요 요소
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if(itemClickListener !=null){
                        itemClickListener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });


        }
        void onBind(Menu_faq_item item){
            Que.setText(item.getFaq_Q());
            Ans.setText(item.getFaq_A());


        }
    }


}
