package com.example.transaction.view.activity;

import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.transaction.R;
import com.example.transaction.databinding.ActivityTableBinding;
import com.example.transaction.viewModel.TableViewModel;

public class TableActivity extends BaseActivity {

    private TableViewModel viewModel;
    private ActivityTableBinding binding;
    private Fragment currentFragment;
    private int currentTable;
    private TextView textView1, textView2, textView3, textView4;

    /**
     * Activity初始化和绑定
     */
    @Override
    protected void init() {
        viewModel = TableViewModel.setTableViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_table);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        /*选中当前table*/
        selectCurrentTable(currentTable);

        /*切换到当前Fragment*/
        replaceFragment(currentFragment);
    }

    /**
     * 控件初始化
     */
    private void initView() {
        currentFragment = viewModel.getCurrentFragment();
        currentTable = viewModel.getCurrentTable();
        textView1 = binding.tabShop;
        textView2 = binding.tabMessage;
        textView3 = binding.tabCart;
        textView4 = binding.tabPrivate;
    }

    /**
     * 选择对应的table，置于选中状态
     *
     * @param i
     */
    private void selectCurrentTable(int i) {
        textView1.setSelected(false);
        textView2.setSelected(false);
        textView3.setSelected(false);
        textView4.setSelected(false);
        switch (i) {
            case 1:
                textView1.setSelected(true);
                break;
            case 2:
                textView2.setSelected(true);
                break;
            case 3:
                textView3.setSelected(true);
                break;
            case 4:
                textView4.setSelected(true);
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tab_fl, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 内部类点击事件
     */
    public class ProxyClick {

        /*切换shop界面*/
        public void toShop() {
            selectCurrentTable(1);
            replaceFragment(viewModel.getShopFragment());
            viewModel.setCurrentTable(1);
            viewModel.setCurrentFragment(viewModel.getShopFragment());
        }

        /*切换聊天室界面*/
        public void toMessage() {
            selectCurrentTable(2);
            replaceFragment(viewModel.getMessageFragment());
            viewModel.setCurrentTable(2);
            viewModel.setCurrentFragment(viewModel.getMessageFragment());
        }

        /*切换购物车界面*/
        public void toCart() {
            selectCurrentTable(3);
            replaceFragment(viewModel.getCartFragment());
            viewModel.setCurrentTable(3);
            viewModel.setCurrentFragment(viewModel.getCartFragment());
        }

        /*切换个人信息界面*/
        public void toPrivate() {
            selectCurrentTable(4);
            replaceFragment(viewModel.getPrivateFragment());
            viewModel.setCurrentTable(4);
            viewModel.setCurrentFragment(viewModel.getPrivateFragment());
        }
    }
}
