package com.example.transaction.viewModel.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgotViewModel extends BaseViewModel {

    private MyDatabase myDatabase;
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> message;

    /**
     * 单例模式创建viewModel
     */
    private static ForgotViewModel forgotViewModel;

    public static synchronized ForgotViewModel setForgotViewModel(Context context) {
        if (forgotViewModel == null) {
            forgotViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(ForgotViewModel.class);
        }
        return forgotViewModel;
    }

    /**
     * 初始化viewModel
     *
     * @param application
     */
    public ForgotViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
        account = new MutableLiveData<>();
        password = new MutableLiveData<>();
        phone = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }

    /**
     * 获取账号
     *
     * @return
     */
    public MutableLiveData<String> getAccount() {
        return account;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public MutableLiveData<String> getPassword() {
        return password;
    }

    /**
     * 获取电话号码
     *
     * @return
     */
    public MutableLiveData<String> getPhone() {
        return phone;
    }

    /**
     * 获取反馈信息
     *
     * @return
     */
    public MutableLiveData<String> getMessage() {
        return message;
    }

    /**
     * 核对账号信息
     */
    public void checkMessage() {
        List<String> list = new ArrayList<>();
        list.add(account.getValue());
        list.add(phone.getValue());
        Observable.just(list)
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) throws Throwable {
                        String message = new String();
                        if (myDatabase.getUserDAO().getAccount(strings.get(0)) == null ||
                                myDatabase.getUserDAO().getAccount(strings.get(0)).isEmpty()) {
                            message = "账号不存在";
                        } else {
                            if (!myDatabase.getUserDAO().getPhone(list.get(0)).equals(list.get(1))) {
                                message = "手机号码错误";
                            } else {
                                message = "0";
                            }
                        }
                        return message;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                        message.setValue(s);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 信息确认通过，修改密码
     */
    public void successChangePassword() {
        List<String> list = new ArrayList<>();
        list.add(account.getValue());
        list.add(password.getValue());
        Observable.just(list)
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) throws Throwable {
                        String message = new String();
                        myDatabase.getUserDAO().setNewPassword(list.get(0), list.get(1));
                        message = "1";
                        return message;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                        message.setValue(s);
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
