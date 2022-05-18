package com.example.foodchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    private Menu_dialogListener listener;
    private final int GET_GALLERY_IMAGE = 200;
    private String encodeimg;


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



        menuimg.setOnClickListener(new View.OnClickListener() {
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
    }


}
