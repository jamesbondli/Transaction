package com.example.transaction.view.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.transaction.R;
import com.example.transaction.databinding.ActivityLoginBinding;
import com.example.transaction.view.activity.BaseActivity;
import com.example.transaction.viewModel.login.LoginViewModel;

public class LoginActivity extends BaseActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    private Toolbar toolbar;
    private EditText account;
    private EditText password;

    /**
     * Activity初始化和绑定
     */
    @Override
    protected void init() {
        viewModel = LoginViewModel.setLoginViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        /*toolbar设置返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*监听viewModel中message的结果信息，根据结果弹出提示*/
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("0")) {
                    Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 控件初始化
     */
    private void initView() {
        toolbar = binding.loginToolbar;
        account = binding.loginAccount;
        password = binding.loginPasswrod;
    }

    /**
     * 内部类点击事件
     */
    public class ProxyClick {

        /*登录逻辑*/
        public void login() {
            if (account.getText().toString() == null ||
                    account.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString() == null ||
                    password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.getAccount().setValue(account.getText().toString());
                viewModel.getPassword().setValue(password.getText().toString());
                viewModel.userLogin();
            }
        }

        /*跳转修改密码*/
        public void toForgot() {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, ForgotActivity.class);
            startActivity(intent);
        }

        /*跳转注册*/
        public void toRegister() {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
