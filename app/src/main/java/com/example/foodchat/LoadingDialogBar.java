package com.example.foodchat;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

class LoadingDialogBar {
    Context context;
    Dialog dialog;

    public LoadingDialogBar(Context context) {
        this.context = context;
    }


    public void ShowDilaog(String title) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        TextView titleTextView = dialog.findViewById(R.id.textview00);

        titleTextView.setText(title);
        dialog.create();
        dialog.show();

    }

    public void HideDialog() {
        dialog.dismiss();
    }
}