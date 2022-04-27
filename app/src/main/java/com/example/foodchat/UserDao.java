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
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 삽입
    void InsertUser(User user);

    @Update // 수정
    void UpdateUser(User user);

    @Delete // 삭제
    void DeleteUser(User user);

    //조회
    @Query("select * from user") //DB요청 명령문
    List<User> getUserAll();

    @Query("select id from user where id =:id")
    boolean SelectId(int id);

    @Query("update user set name =:username where id =:userid")
    void UserChangeNick(String username,int userid);

    @Query("select name from user where id= :userid")
    boolean UserNick(int userid);


    @Query("delete from user")
    void DeleteAll();

}
