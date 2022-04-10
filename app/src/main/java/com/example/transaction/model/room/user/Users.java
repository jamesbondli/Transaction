package com.example.transaction.model.room.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.transaction.R;

@Entity
public class Users {

    /**
     * 用户id，自动递增
     */
    @PrimaryKey(autoGenerate = true)
    private int id;


    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String phone;

    /**
     * 名称
     */
    private String name = "";

    /**
     * 头像
     */
    private int head = R.drawable.img_false;

    /**
     * 个性签名
     */
    private String signature = "";

    public Users(String account, String password, String phone) {
        this.account = account;
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", head=" + head +
                ", signature='" + signature + '\'' +
                '}';
    }
}
