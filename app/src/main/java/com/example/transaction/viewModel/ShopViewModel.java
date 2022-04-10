package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.model.room.good.Goods;

import java.util.List;

public class ShopViewModel extends BaseViewModel {

    private LiveData<List<Goods>> goodsList;
    private MyDatabase myDatabase;

    /**
     * 单例模式创建ViewModel
     */
    private static ShopViewModel shopViewModel;

    public static synchronized ShopViewModel setShopViewModel(Context context) {
        if (shopViewModel == null) {
            shopViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(ShopViewModel.class);
        }
        return shopViewModel;
    }

    public ShopViewModel(Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
        goodsList = myDatabase.getGoodsDAO().getAllGoods();
    }

    public LiveData<List<Goods>> getGoodsList() {
        return goodsList;
    }
}
