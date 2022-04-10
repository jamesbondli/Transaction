package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.model.CurrentUser;
import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.model.room.cartGoods.CartGoods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartViewModel extends BaseViewModel {

    private MyDatabase myDatabase;
    private CurrentUser currentUser;
    private MutableLiveData<String> cartMenu;
    private MutableLiveData<Integer> l1Visible;
    private MutableLiveData<Integer> l2Visible;
    private MutableLiveData<Integer> r1Visible;
    private MutableLiveData<Integer> r2Visible;
    private MutableLiveData<List<CartGoods>> cartGoodsList;
    private MutableLiveData<Boolean> flag;
    private MutableLiveData<Double> price;
    private MutableLiveData<Integer> numbers;

    /**
     * 单例模式创建viewModel
     */
    private static CartViewModel cartViewModel;

    public static synchronized CartViewModel setCartViewModel(Context context) {
        if (cartViewModel == null) {
            cartViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(CartViewModel.class);
        }
        return cartViewModel;
    }

    /**
     * 初始化viewModel
     *
     * @param application
     */
    public CartViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
        cartMenu = new MutableLiveData<>();
        cartMenu.setValue("管理");
        l1Visible = new MutableLiveData<>();
        l1Visible.setValue(0);
        l2Visible = new MutableLiveData<>();
        l2Visible.setValue(2);
        r1Visible = new MutableLiveData<>();
        r1Visible.setValue(0);
        r2Visible = new MutableLiveData<>();
        r2Visible.setValue(2);
        currentUser = CurrentUser.setCurrentUser();
        flag = new MutableLiveData<>();
        flag.setValue(false);
    }

    public MutableLiveData<String> getCartMenu() {
        return cartMenu;
    }

    public MutableLiveData<Integer> getL1Visible() {
        return l1Visible;
    }

    public MutableLiveData<Integer> getL2Visible() {
        return l2Visible;
    }

    public MutableLiveData<Integer> getR1Visible() {
        return r1Visible;
    }

    public MutableLiveData<Integer> getR2Visible() {
        return r2Visible;
    }

    public MutableLiveData<Boolean> getFlag() {
        return flag;
    }

    public MutableLiveData<Integer> getNumbers() {
        if (numbers == null) {
            numbers = new MutableLiveData<>();
        }
        return numbers;
    }

    public CurrentUser getCurrentUser() {
        currentUser = CurrentUser.setCurrentUser();
        return currentUser;
    }

    public MutableLiveData<Double> getPrice() {
        if (price == null) {
            price = new MutableLiveData<>();
        }
        return price;
    }

    public MutableLiveData<List<CartGoods>> getCartGoodsList() {
        if (cartGoodsList == null) {
            cartGoodsList = new MutableLiveData<>();
        }
        return cartGoodsList;
    }

    /**
     * 设置是否可见
     */
    public void setVisible() {
        currentUser = CurrentUser.setCurrentUser();
        r1Visible.setValue(0);
        r2Visible.setValue(2);
        if (!currentUser.getUsers().getAccount().equals("0")) {
            r1Visible.setValue(2);
            r2Visible.setValue(0);
        }
    }

    /**
     * 获取购物车信息列表
     */
    public void setCartGoodsList() {
        currentUser = CurrentUser.setCurrentUser();
        if (!currentUser.getUsers().equals("0")) {
            Observable.just(currentUser.getUsers().getAccount())
                    .map(new Function<String, List<CartGoods>>() {
                        @Override
                        public List<CartGoods> apply(String s) throws Throwable {
                            List<CartGoods> cartGoodsList =
                                    myDatabase.getCartGoodDAO().getAllCartGoods(s);
                            return cartGoodsList;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<CartGoods>>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(List<CartGoods> cartGoods) {
                            cartGoodsList.setValue(cartGoods);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    /**
     * 结算
     *
     * @param checkStatus
     */
    public void cartGoodsPay(Map<Integer, Boolean> checkStatus) {
        currentUser = CurrentUser.setCurrentUser();
        Observable.just(checkStatus)
                .map(new Function<Map<Integer, Boolean>, Object>() {
                    @Override
                    public Object apply(Map<Integer, Boolean> integerBooleanMap) throws Throwable {
                        List<CartGoods> cartGoodsList =
                                myDatabase.getCartGoodDAO().getAllCartGoods(
                                        currentUser.getUsers().getAccount());
                        for (int i = 0; i < cartGoodsList.size(); i++) {
                            if (integerBooleanMap.get(i)) {
                                cartGoodsList.get(i).setBePaid(1);
                                myDatabase.getCartGoodDAO().setPaid(cartGoodsList.get(i));
                            }
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Object o) {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
