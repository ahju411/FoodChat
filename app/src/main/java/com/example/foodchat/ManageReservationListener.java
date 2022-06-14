package com.example.foodchat;

import android.view.View;

public interface ManageReservationListener {
    public void onAcceptClick(ManageReservationAdapter.ViewHolder holder, View view, int position);
    public void onRejectClick(ManageReservationAdapter.ViewHolder holder, View view, int position);
}
