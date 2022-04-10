package com.example.transaction.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.transaction.R;
import com.example.transaction.databinding.ActivitySetPrivateBinding;
import com.example.transaction.view.activity.login.LoginActivity;
import com.example.transaction.viewModel.SetPrivateViewModel;

public class SetPrivateActivity extends BaseActivity {

    private SetPrivateViewModel viewModel;
    private ActivitySetPrivateBinding binding;
    private Toolbar toolbar;

    /**
     * Activity绑定和初始化
     */
    @Override
    protected void init() {
        viewModel = SetPrivateViewModel.setSetPrivateViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_private);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        /*toolbar设置返回监听*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Activity可见可交互
     */
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setVisible();
        viewModel.setUserMessage();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        toolbar = binding.privateInfoToolbar;
    }

    /**
     * 内部类点击事件
     */
    public class ProxyClick {

        /*跳转设置个人信息*/
        public void toSet() {

        }

        /*跳转前往登录*/
        public void toLogin() {
            Intent intent = new Intent();
            intent.setClass(SetPrivateActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
