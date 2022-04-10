package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.model.room.good.Goods;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShopSearchViewModel extends BaseViewModel {

    private MutableLiveData<List<Goods>> goodsList;
    private MutableLiveData<String> keyWord;
    private MyDatabase myDatabase;

    //单例模式创建ViewModel
    private static ShopSearchViewModel shopSearchViewModel;

    public static synchronized ShopSearchViewModel setShopSearchViewModel(Context context) {
        if (shopSearchViewModel == null) {
            shopSearchViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(ShopSearchViewModel.class);
        }
        return shopSearchViewModel;
    }

    public ShopSearchViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
        goodsList = new MutableLiveData<>();
    }

    public void setGoodsList(MutableLiveData<List<Goods>> goodsList) {
        this.goodsList = goodsList;
    }

    public MutableLiveData<List<Goods>> getGoodsList() {
        return goodsList;
    }

    public void setKeyWord(MutableLiveData<String> keyWord) {
        Observable.just(keyWord)
                .map(new Function<MutableLiveData<String>, List<Goods>>() {
                    @Override
                    public List<Goods> apply(MutableLiveData<String> s) throws Throwable {
                        List<Goods> goodsList = myDatabase.getGoodsDAO().getGoods(s.getValue());
                        return goodsList;
                    }
                }).subscribeOn(Schedulers.io())//为上述操作分配异步线程
                .observeOn(AndroidSchedulers.mainThread())//异步操作结束，回到主线程
                .subscribe(new Observer<List<Goods>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Goods> goods) {
                        goodsList.setValue(goods);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        this.keyWord = keyWord;
    }

    public MutableLiveData<String> getKeyWord() {
        if (keyWord == null) {
            keyWord = new MutableLiveData<>();
        }
        return keyWord;
    }
}
