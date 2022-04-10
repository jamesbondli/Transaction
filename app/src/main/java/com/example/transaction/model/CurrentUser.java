package com.example.transaction.model;

import android.app.Application;

import com.example.transaction.model.room.user.Users;

public class CurrentUser extends Application {
    private Users users;

    /**
     * 单例模式创建当前用户对象
     */
    private static CurrentUser currentUser;

    public static synchronized CurrentUser setCurrentUser() {
        if (currentUser == null) {
            currentUser = new CurrentUser();
        }
        return currentUser;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public Users getUsers() {
        if (users == null) {
            users = new Users("0", "0", "0");
        }
        return users;
    }

    /**
     * 设置当前用户
     *
     * @param users
     */
    public void setUsers(Users users) {
        this.users = users;
    }
}
