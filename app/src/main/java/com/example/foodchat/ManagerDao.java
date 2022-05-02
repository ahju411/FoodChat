package com.example.foodchat;
// Data Access Object

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ManagerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 삽입
    void InsertManager(Manager manager);

    @Update // 수정
    void UpdateManager(Manager manager);

    @Delete // 삭제
    void DeleteManager(Manager manager);

    //조회
    @Query("select * from manager") //DB요청 명령문
    List<Manager> getManagerAll();

//    @Query("select id from user where id =:id")
//    boolean SelectId(int id);
//
//    @Query("update user set name =:username where id =:userid")
//    void UserChangeNick(String username,int userid);
//
//    @Query("select count(name) from user where id=:userid")
//    int UserNick(int userid);
//
//
//    @Query("delete from user")
//    void DeleteAll();

}
