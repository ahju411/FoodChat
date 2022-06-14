package com.example.foodchat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Menu_dialog_faq extends AppCompatDialogFragment {
    private EditText ed_q, ed_a;
    private Menu_faq_dialogListener listener;
    private final int GET_GALLERY_IMAGE = 200;
    private String encodeimg;
    private String strmenuname ="",  strmenuprice ="",  strmenuexplain ="";
    private Bitmap bmmenuimg = null;
    private int position = -1;
    private int id;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.faq_add_dialog,null);

        ed_q = view.findViewById(R.id.faqname_dialog);
        ed_a = view.findViewById(R.id.faqexplain_dialog);
      





        builder.setView(view).setTitle("FAQ 추가").setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String strmenuname = ed_q.getText().toString();
                String strmenuprice = ed_a.getText().toString();
                int strid = ed_a.getId();


                listener.applyText(strmenuname,strmenuprice,strid);

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



        return builder.create();
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
           listener = (Menu_faq_dialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "listener를 implement해야한다");
        }

    }


    public interface Menu_faq_dialogListener {
        void applyText(String s, String strmenuname,int id);
    }

    public void setStrmenuname(String strmenuname) {
        this.strmenuname = strmenuname;
    }

    public void setStrmenuprice(String strmenuprice) {
        this.strmenuprice = strmenuprice;
    }
    public void setId(int id) {
        this.id = id;
    }

}
