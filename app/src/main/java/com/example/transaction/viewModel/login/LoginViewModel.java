package com.example.transaction.viewModel.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.model.CurrentUser;
import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.model.room.user.Users;
import com.example.transaction.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {
    private MyDatabase myDatabase;
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private MutableLiveData<String> message;
    private CurrentUser currentUser;

    /**
     * 单例模式创建viewModel
     */
    private static LoginViewModel loginViewModel;

    public static synchronized LoginViewModel setLoginViewModel(Context context) {
        if (loginViewModel == null) {
            loginViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(LoginViewModel.class);
        }
        return loginViewModel;
    }

    /**
     * 初始化viewModel
     *
     * @param application
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
        message = new MutableLiveData<>();
        currentUser = CurrentUser.setCurrentUser();
    }

    /**
     * 获取用户账号
     *
     * @return
     */
    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<>();
        }
        return account;
    }

    /**
     * 获取用户密码
     *
     * @return
     */
    public MutableLiveData<String> getPassword() {
        if (password == null) {
            password = new MutableLiveData<>();
        }
        return password;
    }

    /**
     * 获取结果信息
     *
     * @return
     */
    public MutableLiveData<String> getMessage() {
        return message;
    }

    /**
     * 用户登录
     */
    public void userLogin() {
        List<String> list = new ArrayList<>();
        list.add(account.getValue());
        list.add(password.getValue());
        Observable.just(list)
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> s) throws Throwable {
                        String message = new String();
                        if (myDatabase.getUserDAO().getAccount(s.get(0)) == null ||
                                myDatabase.getUserDAO().getAccount(s.get(0)).isEmpty()) {
                            message = "账号不存在";
                        } else {
                            if (myDatabase.getUserDAO().getPassword(s.get(0)).equals(s.get(1))) {
                                currentUser.setUsers(
                                        myDatabase.getUserDAO().getCurrentAccount(list.get(0)));
                                message = "0";
                            } else {
                                message = "密码错误";
                            }
                        }
                        return message;
                    }
                }).subscribeOn(Schedulers.io())//为上述操作分配异步线程
                .observeOn(AndroidSchedulers.mainThread())//异步操作结束，回到主线程
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
