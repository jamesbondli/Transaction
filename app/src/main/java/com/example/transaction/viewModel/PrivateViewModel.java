package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.R;
import com.example.transaction.model.CurrentUser;
import com.example.transaction.model.room.MyDatabase;

public class PrivateViewModel extends BaseViewModel {
    private MyDatabase myDatabase;
    private CurrentUser currentUser;
    private MutableLiveData<String> name;
    private MutableLiveData<Integer> head;
    private MutableLiveData<String> signature;

    /**
     * 单例模式创建viewModel
     *
     * @param context
     * @return
     */
    private static PrivateViewModel privateViewModel;

    public static synchronized PrivateViewModel setPrivateViewModel(Context context) {
        if (privateViewModel == null) {
            privateViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(PrivateViewModel.class);
        }
        return privateViewModel;
    }

    /**
     * viewModel初始化
     *
     * @param application
     */
    public PrivateViewModel(@NonNull Application application) {
        super(application);
        myDatabase = MyDatabase.getInstance(application);
        name = new MutableLiveData<>();
        head = new MutableLiveData<>();
        signature = new MutableLiveData<>();
    }

    /**
     * 获取currentUser
     *
     * @return
     */
    public CurrentUser getCurrentUser() {
        currentUser = CurrentUser.setCurrentUser();
        return currentUser;
    }

    /**
     * 获取当前账户的名称
     *
     * @return
     */
    public MutableLiveData<String> getName() {
        currentUser = CurrentUser.setCurrentUser();
        name.setValue("用户名称");
        if (!currentUser.getUsers().getAccount().equals("0")) {
            name.setValue(currentUser.getUsers().getName());
        }
        return name;
    }

    /**
     * 获取当前账户的头像
     *
     * @return
     */
    public MutableLiveData<Integer> getHead() {
        currentUser = CurrentUser.setCurrentUser();
        head.setValue(R.drawable.img_false);
        if (!currentUser.getUsers().getAccount().equals("0")) {
            head.setValue(currentUser.getUsers().getHead());
        }
        return head;
    }

    /**
     * 获取当前账户的个性签名
     *
     * @return
     */
    public MutableLiveData<String> getSignature() {
        currentUser = CurrentUser.setCurrentUser();
        signature.setValue("设置您的个性签名");
        if (!currentUser.getUsers().getAccount().equals("0")) {
            signature.setValue(currentUser.getUsers().getSignature());
        }
        return signature;
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
