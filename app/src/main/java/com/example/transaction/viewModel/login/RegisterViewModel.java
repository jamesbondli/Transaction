package com.example.transaction.viewModel.login;

import android.app.Application;
import android.content.Context;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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

public class RegisterViewModel extends BaseViewModel {

    private MyDatabase myDatabase;
    private MutableLiveData<String> account;
    private MutableLiveData<String> password;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> message;

    /**
     * 单例模式创建viewModel
     */
    private static RegisterViewModel registerViewModel;

    public static synchronized RegisterViewModel setRegisterViewModel(Context context) {
        if (registerViewModel == null) {
            registerViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(RegisterViewModel.class);
        }
        return registerViewModel;
    }

    /**
     * viewModel初始化
     *
     * @param application
     */
    public RegisterViewModel(@NonNull Application application) {
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
     * 获取手机号码
     *
     * @return
     */
    public MutableLiveData<String> getPhone() {
        return phone;
    }

    /**
     * 获取响应结果信息
     *
     * @return
     */
    public MutableLiveData<String> getMessage() {
        return message;
    }

    /**
     * 检查账号是否存在
     */
    public void checkMessage() {
        List<String> list = new ArrayList<>();
        list.add(account.getValue());
        list.add(password.getValue());
        list.add(phone.getValue());
        Observable.just(list)
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) throws Throwable {
                        String message = new String();
                        if (myDatabase.getUserDAO().getAccount(strings.get(0)) == strings.get(0)) {
                            message = "账号已存在";
                        } else {
                            message = "0";
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
     * 注册成功，添加新用户
     */
    public void successRegister() {
        List<String> list = new ArrayList<>();
        list.add(account.getValue());
        list.add(password.getValue());
        list.add(phone.getValue());
        Observable.just(list)
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) throws Throwable {
                        String message = new String();
                        Users users = new Users(list.get(0), list.get(1), list.get(2));
                        myDatabase.getUserDAO().insertUsers(users);
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
