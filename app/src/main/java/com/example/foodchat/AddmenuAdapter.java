package com.example.foodchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddmenuAdapter extends RecyclerView.Adapter<AddmenuAdapter.ViewHolder> implements AddmenuListener{

    ArrayList<ItemManageMenu> itemManageMenus = new ArrayList<>();

    public AddmenuAdapter(){

    }
    //========클릭 이벤트 구현===========
    private AddmenuListener itemClickListener;

    public void setOnItemClickListener (AddmenuListener listener){
        this.itemClickListener = listener;
    }




    @NonNull
    @Override
    public AddmenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //원하는 layout띄우기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.store_addmenu_list,parent,false);
        AddmenuAdapter.ViewHolder viewHolder = new AddmenuAdapter.ViewHolder(view);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull AddmenuAdapter.ViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        // 보통 onBind 함수 안에 모두 넣어줍니다.
        holder.onBind(itemManageMenus.get(position));


    }
    public void setItemManageMenus(ArrayList<ItemManageMenu> list){
        this.itemManageMenus = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemManageMenus.size();
    }

    //클릭 이벤트 필요 요소
    public ItemManageMenu getItem(int position){
        return itemManageMenus.get(position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(holder,view,position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView menu_name,menu_explain,menu_price;
        ImageView menu_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_name = itemView.findViewById(R.id.menu_name);
            menu_explain = itemView.findViewById(R.id.menu_explain);
            menu_price = itemView.findViewById(R.id.menu_price);
            menu_img = itemView.findViewById(R.id.menu_img);

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
        void onBind(ItemManageMenu item){
            menu_name.setText(item.getMenu_name());
            menu_explain.setText(item.getMenu_explain());
            menu_price.setText(item.getMenu_price());
            menu_img.setImageBitmap(item.getMenu_img());

        }
    }


}
