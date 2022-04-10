package com.example.transaction.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.transaction.R;
import com.example.transaction.databinding.ActivityShopItemBinding;
import com.example.transaction.viewModel.ShopItemViewModel;

public class ShopItemActivity extends BaseActivity {

    private ActivityShopItemBinding binding;
    private ShopItemViewModel viewModel;
    private Toolbar toolbar;
    private ImageView imageView;
    private int goodsId;

    @Override
    protected void init() {
        viewModel = ShopItemViewModel.setShopItemViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_item);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        /*获取图片id*/
        getGoodsId();

        /*将图片id传入ViewModel*/
        viewModel.setGoodsId(goodsId);

        /*监听反馈信息*/
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
            }
        });

        //toolbar设置返回监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取intent中的图片id
     */
    private void getGoodsId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        goodsId = bundle.getInt("id");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        toolbar = binding.shopItemToolbar;
        imageView = binding.shopItemImg;
    }

    public class ProxyClick {
        public void addToCart() {
            if (viewModel.getCurrentUser().getUsers().getAccount().equals("0")) {
                Toast.makeText(getApplicationContext(), "您还未登录！", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addToCart();
            }
        }
    }
}
