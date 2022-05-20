package com.example.foodchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.foodchat.R;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;

public class Menu_dialog extends AppCompatDialogFragment {
    private EditText menuname,menuprice,menuexplain;
    private ImageButton menuimg;
    private TextView menuimg_tv;
    private Menu_dialogListener listener;
    private final int GET_GALLERY_IMAGE = 200;
    private String encodeimg;
    private String strmenuname ="",  strmenuprice ="",  strmenuexplain ="";
    private Bitmap bmmenuimg = null;
    private int position = -1;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addmenu_dialog,null);

        menuname = view.findViewById(R.id.menuname_dialog);
        menuprice = view.findViewById(R.id.menuprice_dialog);
        menuexplain = view.findViewById(R.id.menuexplain_dialog);
        menuimg = view.findViewById(R.id.menuimg_explain);
        menuimg_tv= view.findViewById(R.id.menuimg_tv);
        menuimg_tv.setPaintFlags(menuimg_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        menuname.setText(strmenuname);
        menuprice.setText(strmenuprice);
        menuexplain.setText(strmenuexplain);
        menuimg.setImageBitmap(bmmenuimg);







        menuimg_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();


            }
        });


        builder.setView(view).setTitle("메뉴 추가").setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String strmenuname = menuname.getText().toString();
                String strmenuprice = menuprice.getText().toString();
                String strmenuexplain = menuexplain.getText().toString();
                String strmenuimg = encodeimg;


                listener.applyText(strmenuname,strmenuprice,strmenuexplain,strmenuimg);

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNeutralButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String strmenuname = menuname.getText().toString();
                String strmenuprice = menuprice.getText().toString();
                String strmenuexplain = menuexplain.getText().toString();
                AddmenuActivity a = new AddmenuActivity();

                String strmenuimg = a.menu_image2;



                listener.setText(strmenuname,strmenuprice,strmenuexplain,strmenuimg,position);

            }
        });



        return builder.create();
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();

                    if(data != null && data.getData() != null){
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap =
                                    MediaStore.Images.Media.getBitmap(
                                            this.getContext().getContentResolver(),
                                            selectedImageUri
                                    );
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        menuimg.setImageBitmap(selectedImageBitmap);
                        encodeimg = encodeBitmapImage(selectedImageBitmap);
                    }
                }
            }
    );

    private String encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, b);

        byte[] bytesOfImage = b.toByteArray();
        System.out.println("바이트값"+bytesOfImage);
        String encodeImageString = Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        return encodeImageString;

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
           listener = (Menu_dialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "listener를 implement해야한다");
        }

    }


    public interface Menu_dialogListener{
        void applyText(String s, String strmenuname, String strmenuprice, String strmenuexplain);
        void setText(String s, String strmenuname, String strmenuprice,String strmenuexplain, int position);
    }

    public void setStrmenuname(String strmenuname) {
        this.strmenuname = strmenuname;
    }

    public void setStrmenuprice(String strmenuprice) {
        this.strmenuprice = strmenuprice;
    }

    public void setStrmenuexplain(String strmenuexplain) {
        this.strmenuexplain = strmenuexplain;
    }

    public void setBmmenuimg(Bitmap bmmenuimg) {
        this.bmmenuimg = bmmenuimg;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
