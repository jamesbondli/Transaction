package com.example.transaction.model.room.good;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.BitSet;

/**
 * 创建表Goods
 */
@Entity
public class Goods {

    /**
     * 商品id，自动递增
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属账户
     */
    private String account;

    /**
     * 商品简单描述
     */
    private String des = "暂无详细信息";

    /**
     * 商品价格
     */
    private Double price = 0.0;

    /**
     * 商品图片
     */
    private int image = 0;

    /**
     * 商品是否可售
     */
    private int sold = 1;

    public Goods(String name, String account) {
        this.name = name;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
