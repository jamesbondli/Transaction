package com.example.transaction.view.activity.login;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.transaction.R;
import com.example.transaction.databinding.ActivityRegisterBinding;
import com.example.transaction.view.activity.BaseActivity;
import com.example.transaction.viewModel.login.RegisterViewModel;

public class RegisterActivity extends BaseActivity {
    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;
    private Toolbar toolbar;
    private EditText account, password, newPassword, phone, check;
    private String checkNumber;

    /**
     * Activity初始化和绑定
     */
    @Override
    protected void init() {
        viewModel = RegisterViewModel.setRegisterViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        /*监听message结果，给用户反馈*/
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("0")) {
                    int checkIntNumber = (int) ((Math.random() * 9 + 1) * 100000);
                    checkNumber = "" + checkIntNumber;
                    Toast.makeText(getApplicationContext(), "" + checkIntNumber, Toast.LENGTH_SHORT).show();
                } else if (s.equals("1")) {
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*toolbar设置返回监听*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 控件初始化
     */
    private void initView() {
        toolbar = binding.registerToolbar;
        account = binding.registerAccount;
        password = binding.registerPassword;
        newPassword = binding.registerNewPassword;
        phone = binding.registerPhone;
        check = binding.registerCheck;
        checkNumber = new String();
    }

    /**
     * 内部类点击事件
     */
    public class ProxyClick {

        /*输入信息确认与核对*/
        public void checkMessage() {
            if (account.getText().toString() == null ||
                    account.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString() == null ||
                    password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            } else if (newPassword.getText().toString() == null ||
                    newPassword.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请再次输入密码", Toast.LENGTH_SHORT).show();
            } else if (!password.getText().toString().equals(newPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            } else if (phone.getText().toString() == null ||
                    phone.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            } else if (phone.getText().toString()
                    .equals("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\\\d{8}$")) {
                Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.getAccount().setValue(account.getText().toString());
                viewModel.getPassword().setValue(password.getText().toString());
                viewModel.getPhone().setValue(phone.getText().toString());
                viewModel.checkMessage();
            }
        }

        /*完成核对后进行注册*/
        public void successRegister() {
            if (check.getText().toString() == null ||
                    check.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            } else if (check.getText().toString().equals(checkNumber)) {
                viewModel.successRegister();
            } else {
                Toast.makeText(getApplicationContext(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
