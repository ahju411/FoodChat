package com.example.foodchat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,Manager.class}, version = 2)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ManagerDao managerDao();
}
