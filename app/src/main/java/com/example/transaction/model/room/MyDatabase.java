package com.example.transaction.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.transaction.model.room.cartGoods.CartGoods;
import com.example.transaction.model.room.cartGoods.CartGoodsDAO;
import com.example.transaction.model.room.good.Goods;
import com.example.transaction.model.room.good.GoodsDAO;
import com.example.transaction.model.room.user.Users;
import com.example.transaction.model.room.user.UsersDAO;

@Database(entities = {Goods.class, CartGoods.class, Users.class}, version = 8, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    //暴露DAO供用户操作
    public abstract GoodsDAO getGoodsDAO();

    public abstract CartGoodsDAO getCartGoodDAO();

    public abstract UsersDAO getUserDAO();

    //单例模式
    private static MyDatabase Instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder
                    (context.getApplicationContext(), MyDatabase.class, "goods_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }

}
