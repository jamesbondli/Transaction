package com.example.transaction.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.transaction.R;
import com.example.transaction.databinding.FragmentPrivateBinding;
import com.example.transaction.model.room.user.Users;
import com.example.transaction.view.activity.SetPrivateActivity;
import com.example.transaction.viewModel.PrivateViewModel;

public class PrivateFragment extends BaseFragment {
    private View rootView;
    private PrivateViewModel viewModel;
    private FragmentPrivateBinding binding;
    private Users currentUser;

    /**
     * fragment创建视图，完成绑定和初始化
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_private, container, false);
        viewModel = PrivateViewModel.setPrivateViewModel(PrivateFragment.this.getContext());
        binding = FragmentPrivateBinding.bind(rootView);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.setUserMessage();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        currentUser = viewModel.getCurrentUser().getUsers();
    }

    /**
     * 内部类点击事件
     */
    public class ProxyClick {

        /*跳转设置个人信息*/
        public void toSetPrivate() {
            Intent intent = new Intent();
            intent.setClass(PrivateFragment.this.getContext(), SetPrivateActivity.class);
            startActivity(intent);
        }

        /*跳转查看全部订单*/
        public void toAllOrder() {

        }

        /*跳转查看等待支付订单*/
        public void toBePaid() {

        }

        /*跳转查看已支付订单*/
        public void toBeEvaluated() {

        }
    }
}
