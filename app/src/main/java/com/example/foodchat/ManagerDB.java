package com.example.foodchat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Manager.class}, version = 1)
public abstract class ManagerDB extends RoomDatabase {
    public abstract ManagerDao managerDao();
}
