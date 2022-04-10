package com.example.transaction.view.activity.login;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.transaction.R;
import com.example.transaction.databinding.ActivityForgotBinding;
import com.example.transaction.view.activity.BaseActivity;
import com.example.transaction.viewModel.login.ForgotViewModel;

public class ForgotActivity extends BaseActivity {
    private ForgotViewModel viewModel;
    private ActivityForgotBinding binding;
    private Toolbar toolbar;
    private EditText account, password, newPassword, phone, check;
    private String checkNumber;

    @Override
    protected void init() {
        viewModel = ForgotViewModel.setForgotViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        initView();

        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("0")) {
                    int checkIntNumber = (int) ((Math.random() * 9 + 1) * 100000);
                    checkNumber = "" + checkIntNumber;
                    Toast.makeText(getApplicationContext(), "" + checkIntNumber, Toast.LENGTH_SHORT).show();
                } else if (s.equals("1")) {
                    Toast.makeText(getApplicationContext(), "密码重置成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = binding.forgotToolbar;
        account = binding.forgotAccount;
        password = binding.forgotPassword;
        newPassword = binding.forgotNewPassword;
        phone = binding.forgotPhone;
        check = binding.forgotCheck;
        checkNumber = new String();
    }

    public class ProxyClick {
        public void checkMessage() {
            if (account.getText().toString() == null ||
                    account.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_SHORT).show();
            } else if (password.getText().toString() == null ||
                    password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
            } else if (newPassword.getText().toString() == null ||
                    newPassword.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请重新输入新密码", Toast.LENGTH_SHORT).show();
            } else if (phone.getText().toString() == null ||
                    phone.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            } else if (!password.getText().toString().equals(newPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.getAccount().setValue(account.getText().toString());
                viewModel.getPassword().setValue(password.getText().toString());
                viewModel.getPhone().setValue(phone.getText().toString());
                viewModel.checkMessage();
            }
        }

        public void changePassword() {
            if (checkNumber == null || checkNumber.isEmpty()) {
                Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            } else if (check.getText().toString().equals(checkNumber)) {
                viewModel.successChangePassword();
            } else {
                Toast.makeText(getApplicationContext(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
