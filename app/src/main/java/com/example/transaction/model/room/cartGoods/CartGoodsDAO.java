package com.example.transaction.model.room.cartGoods;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartGoodsDAO {
    //插入
    @Insert
    void insertCartGoods(CartGoods... cartGoods);

    //查询全部未支付的商品
    @Query("SELECT * FROM cartgoods WHERE buyer_account = :buyer_account AND bePaid=0")
    List<CartGoods> getAllCartGoods(String buyer_account);

    //查询商品是否已存在
    @Query("SELECT COUNT(goods_id) FROM cartgoods WHERE goods_id = :goods_id")
    int getGoodsIdCount(int goods_id);

    //更新数量
    @Query("UPDATE cartgoods SET numbers = numbers+1 WHERE goods_id = :goods_id")
    void setNewNumber(int goods_id);

    //设置支付
    @Delete
    void setPaid(CartGoods cartGoods);
}