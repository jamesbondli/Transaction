package com.example.transaction.model.room.good;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GoodsDAO {

    //插入
    @Insert
    void insertGoods(Goods... goods);

    //查询全部
    @Query("SELECT * FROM goods WHERE sold = 1")
    LiveData<List<Goods>> getAllGoods();

    //查找指定关键词商品
    @Query("SELECT * FROM goods WHERE name LIKE '%'||:keyWord||'%' OR des LIKE '%'||:keyWord||'%'")
    List<Goods> getGoods(String keyWord);

    //查找指定id商品
    @Query("SELECT * FROM goods WHERE id=:id")
    Goods getGood(int id);
}
