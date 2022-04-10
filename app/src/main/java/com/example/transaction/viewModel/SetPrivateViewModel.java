package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.R;
import com.example.transaction.model.CurrentUser;

public class SetPrivateViewModel extends BaseViewModel {
    private CurrentUser currentUser;
    private MutableLiveData<Integer> r1;
    private MutableLiveData<Integer> l2;
    private MutableLiveData<Integer> head;
    private MutableLiveData<String> name;
    private MutableLiveData<String> signature;

    /**
     * 单例创建viewModel
     */
    private static SetPrivateViewModel setPrivateViewModel;

    public static synchronized SetPrivateViewModel setSetPrivateViewModel(Context context) {
        if (setPrivateViewModel == null) {
            setPrivateViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(SetPrivateViewModel.class);
        }
        return setPrivateViewModel;
    }

    /**
     * viewModel初始化
     *
     * @param application
     */
    public SetPrivateViewModel(@NonNull Application application) {
        super(application);
        r1 = new MutableLiveData<>();
        l2 = new MutableLiveData<>();
        r1.setValue(0);
        l2.setValue(2);
    }

    /**
     * 获取未登录界面显示状态
     *
     * @return
     */
    public MutableLiveData<Integer> getR1() {
        return r1;
    }

    /**
     * 获取登录界面显示状态
     *
     * @return
     */
    public MutableLiveData<Integer> getL2() {
        return l2;
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public MutableLiveData<Integer> getHead() {
        head = new MutableLiveData<>();
        currentUser = CurrentUser.setCurrentUser();
        head.setValue(R.drawable.img_false);
        if (!currentUser.getUsers().getAccount().equals("0")) {
            head.setValue(currentUser.getUsers().getHead());
        }
        return head;
    }

    /**
     * 获取用户名称
     *
     * @return
     */
    public MutableLiveData<String> getName() {
        currentUser = CurrentUser.setCurrentUser();
        name = new MutableLiveData<>();
        name.setValue("用户名称");
        if (!currentUser.getUsers().getAccount().equals("0")) {
            name.setValue(currentUser.getUsers().getName());
        }
        return name;
    }

    /**
     * 获取用户个性签名
     *
     * @return
     */
    public MutableLiveData<String> getSignature() {
        signature = new MutableLiveData<>();
        currentUser = CurrentUser.setCurrentUser();
        signature.setValue("用户个性签名");
        if (!currentUser.getUsers().getAccount().equals("0")) {
            signature.setValue(currentUser.getUsers().getSignature());
        }
        return signature;
    }

    /**
     * 设置是否可见
     */
    public void setVisible() {
        currentUser = CurrentUser.setCurrentUser();
        r1.setValue(0);
        l2.setValue(2);
        if (!currentUser.getUsers().getAccount().equals("0")) {
            r1.setValue(2);
            l2.setValue(0);
        }
    }

    /**
     * 设置用户信息
     */
    public void setUserMessage() {
        currentUser = CurrentUser.setCurrentUser();
        name.setValue("用户名称");
        head.setValue(R.drawable.img_false);
        signature.setValue("设置您的个性签名");
        if (!currentUser.getUsers().getAccount().equals("0")) {
            name.setValue(currentUser.getUsers().getName());
            head.setValue(currentUser.getUsers().getHead());
            signature.setValue(currentUser.getUsers().getSignature());
        }
    }
}
