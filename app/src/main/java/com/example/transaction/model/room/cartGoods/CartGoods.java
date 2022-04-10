package com.example.transaction.model.room.cartGoods;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartGoods {

    /**
     * 商品id，自动递增
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * 商品在库中的id
     */
    private int goods_id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品所属账户
     */
    private String account;

    /**
     * 买家账户
     */
    private String buyer_account;

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
     * 商品数量默认为1
     */
    private int numbers = 1;

    /**
     * 是否支付
     * 默认为未支付
     */
    private int bePaid = 0;

    public CartGoods(int goods_id, String name, String account, String buyer_account) {
        this.goods_id = goods_id;
        this.name = name;
        this.account = account;
        this.buyer_account = buyer_account;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public int getBePaid() {
        return bePaid;
    }

    public void setBePaid(int bePaid) {
        this.bePaid = bePaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
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

    public String getBuyer_account() {
        return buyer_account;
    }

    public void setBuyer_account(String buyer_account) {
        this.buyer_account = buyer_account;
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
}
