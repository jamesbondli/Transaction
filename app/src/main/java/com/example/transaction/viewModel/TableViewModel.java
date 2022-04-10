package com.example.transaction.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.transaction.view.fragment.CartFragment;
import com.example.transaction.view.fragment.MessageFragment;
import com.example.transaction.view.fragment.PrivateFragment;
import com.example.transaction.view.fragment.ShopFragment;

public class TableViewModel extends AndroidViewModel {

    private Fragment currentFragment;
    private ShopFragment shopFragment;
    private MessageFragment messageFragment;
    private CartFragment cartFragment;
    private PrivateFragment privateFragment;
    private int currentTable;

    /**
     * 单例模式创建ViewModel
     */
    private static TableViewModel tableViewModel;

    public static synchronized TableViewModel setTableViewModel(Context context) {
        if (tableViewModel == null) {
            tableViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) context).get(TableViewModel.class);
        }
        return tableViewModel;
    }

    /**
     * 初始化viewModel
     *
     * @param application
     */
    public TableViewModel(@NonNull Application application) {
        super(application);
        shopFragment = new ShopFragment();
        messageFragment = new MessageFragment();
        cartFragment = new CartFragment();
        privateFragment = new PrivateFragment();
        currentFragment = shopFragment;
        currentTable = 1;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public int getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(int currentTable) {
        this.currentTable = currentTable;
    }

    public ShopFragment getShopFragment() {
        return shopFragment;
    }

    public MessageFragment getMessageFragment() {
        return messageFragment;
    }

    public CartFragment getCartFragment() {
        return cartFragment;
    }

    public PrivateFragment getPrivateFragment() {
        return privateFragment;
    }


}
