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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Menu_faq_fix_dialog extends AppCompatDialogFragment {
    private EditText ed_q, ed_a;
    private ImageButton menuimg;
    private TextView menuimg_tv;
    private Menu_faq_dialogListener listener;
    private final int GET_GALLERY_IMAGE = 200;
    private String encodeimg;
    private String strmenuname ="",  strmenuprice ="";




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.faq_add_dialog,null);

        ed_q = view.findViewById(R.id.faqname_dialog);
        ed_a = view.findViewById(R.id.faqexplain_dialog);

        ed_q.setText(strmenuname);
        ed_a.setText(strmenuprice);






        builder.setView(view).setTitle("FAQ 수정").setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String strmenuname = ed_q.getText().toString();
                String strmenuprice = ed_a.getText().toString();

                listener.setText(strmenuname,strmenuprice);

            }
        }).setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.destroy();
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
           listener = (Menu_faq_dialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "listener를 implement해야한다");
        }

    }


    public interface Menu_faq_dialogListener{
        void setText(String s, String strmenuname);
        void destroy();
    }

    public void setStrmenuname(String strmenuname) {
        this.strmenuname = strmenuname;
    }

    public void setStrmenuprice(String strmenuprice) {
        this.strmenuprice = strmenuprice;
    }





}
