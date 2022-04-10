package com.example.transaction.model.room.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsersDAO {
    //插入
    @Insert
    void insertUsers(Users... users);

    //获取当前账户
    @Query("SELECT * FROM users WHERE account = :account")
    Users getCurrentAccount(String account);

    //根据账户查密码
    @Query("SELECT password FROM users WHERE account =:account")
    String getPassword(String account);

    //查询账号是否存在
    @Query("SELECT account FROM users WHERE account LIKE :account")
    String getAccount(String account);

    //设置新密码
    @Query("UPDATE users SET password =:password WHERE account = :account")
    void setNewPassword(String account, String password);

    //查询手机号码是否正确
    @Query("SELECT phone FROM users WHERE account = :account")
    String getPhone(String account);
}
