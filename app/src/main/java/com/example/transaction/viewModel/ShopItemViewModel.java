package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.R;
import com.example.transaction.model.CurrentUser;
import com.example.transaction.model.SingleLiveEvent;
import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.model.room.cartGoods.CartGoods;
import com.example.transaction.model.room.good.Goods;
import com.example.transaction.model.room.user.Users;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ShopItemViewModel extends BaseViewModel {

    private MyDatabase myDatabase;
    private MutableLiveData<String> name;
    private MutableLiveData<String> des;
    private MutableLiveData<Integer> img;
    private MutableLiveData<Double> price;
    private SingleLiveEvent<String> message;
    private CurrentUser currentUser;
    private int goodsId;

    /**
     * 单例模式创建ViewModel
     */
    private static ShopItemViewModel shopItemViewModel;

    public static synchronized ShopItemViewModel setShopItemViewModel(Context context) {
        if (shopItemViewModel == null) {
            shopItemViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(ShopItemViewModel.class);
        }
        return shopItemViewModel;
    }

    /**
     * viewModel初始化
     *
     * @param application
     */
    public ShopItemViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }

    public MutableLiveData<String> getDes() {
        if (des == null) {
            des = new MutableLiveData<>();
        }
        return des;
    }

    public MutableLiveData<Integer> getImg() {
        if (img == null) {
            img = new MutableLiveData<>();
        }
        return img;
    }

    public MutableLiveData<Double> getPrice() {
        if (price == null) {
            price = new MutableLiveData<>();
        }
        return price;
    }

    public SingleLiveEvent<String> getMessage() {
        if (message == null) {
            message = new SingleLiveEvent<>();
        }
        return message;
    }

    public CurrentUser getCurrentUser() {
        currentUser = CurrentUser.setCurrentUser();
        return currentUser;
    }

    /**
     * 添加进入购物车
     */
    public void addToCart() {
        currentUser = CurrentUser.setCurrentUser();
        Observable.just(goodsId)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Throwable {
                        String message = new String();
                        if (myDatabase.getCartGoodDAO().getGoodsIdCount(integer) == 0) {
                            Goods goods = myDatabase.getGoodsDAO().getGood(goodsId);
                            CartGoods cartGoods = new CartGoods(
                                    goods.getId(),
                                    goods.getName(),
                                    goods.getAccount(),
                                    currentUser.getUsers().getAccount());
                            cartGoods.setDes(goods.getDes());
                            cartGoods.setPrice(goods.getPrice());
                            cartGoods.setImage(goods.getImage());
                            myDatabase.getCartGoodDAO().insertCartGoods(cartGoods);
                        } else {
                            myDatabase.getCartGoodDAO().setNewNumber(integer);
                        }
                        message = "添加成功!";
                        return message;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        message.setValue(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 显示商品信息
     *
     * @param goodsId
     */
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
        Observable.just(goodsId)
                .map(new Function<Integer, Goods>() {
                    @Override
                    public Goods apply(Integer integer) throws Throwable {
                        Goods good = myDatabase.getGoodsDAO().getGood(integer);
                        return good;
                    }
                }).subscribeOn(Schedulers.io())//为上述操作分配异步线程
                .observeOn(AndroidSchedulers.mainThread())//异步操作结束，回到主线程
                .subscribe(new Observer<Goods>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Goods goods) {
                        name.setValue(goods.getName());
                        des.setValue(goods.getDes());
                        price.setValue(goods.getPrice());
                        img.setValue(goods.getImage());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
