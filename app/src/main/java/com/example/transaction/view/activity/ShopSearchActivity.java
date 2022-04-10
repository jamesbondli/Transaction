package com.example.transaction.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.transaction.R;
import com.example.transaction.databinding.ActivityShopSearchBinding;
import com.example.transaction.model.adapter.GoodsRVAdapter;
import com.example.transaction.model.room.good.Goods;
import com.example.transaction.view.fragment.ShopFragment;
import com.example.transaction.viewModel.ShopSearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopSearchActivity extends BaseActivity {
    private EditText editText;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private GoodsRVAdapter goodsRVAdapter;
    private ShopSearchViewModel shopSearchViewModel;
    private ActivityShopSearchBinding binding;
    private MutableLiveData<String> keyWord;
    private MutableLiveData<List<Goods>> goodsList;

    /**
     * Activity绑定和初始化
     */
    @Override
    protected void init() {
        //建立绑定与感应
        shopSearchViewModel = ShopSearchViewModel.setShopSearchViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_search);
        binding.setViewModel(shopSearchViewModel);
        binding.setLifecycleOwner(this);
        binding.setClick(new ProxyClick());

        /*控件初始化*/
        initView();

        /*toolbar设置返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*监听数据库数据变化，变化时更新列表*/
        goodsList.observe(this, new Observer<List<Goods>>() {
            @Override
            public void onChanged(List<Goods> goods) {
                Log.d("TAG", "onChanged: ");
                setRecyclerView(goods);
            }
        });
    }

    /**
     * 设置列表
     *
     * @param goodsList
     */
    private void setRecyclerView(List<Goods> goodsList) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(4, 1);
        goodsRVAdapter = new GoodsRVAdapter(ShopSearchActivity.this,
                goodsList);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(goodsRVAdapter);
        /*接口回调实现点击事件*/
        goodsRVAdapter.setOnRecyclerItemClickListener(new GoodsRVAdapter.onRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                intent.setClass(ShopSearchActivity.this, ShopItemActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 控件初始化
     */
    private void initView() {
        editText = binding.shopSearchEt;
        recyclerView = binding.shopSearchRecyclerView;
        toolbar = binding.shopSearchToolbar;
        keyWord = shopSearchViewModel.getKeyWord();
        goodsList = shopSearchViewModel.getGoodsList();
    }

    /**
     * 内部类点击事件
     */
    public class ProxyClick {

        /*搜索*/
        public void Search() {
            if (editText.getText().toString() == null ||
                    editText.getText().toString().isEmpty()) {
                Toast.makeText(ShopSearchActivity.this,
                        "请输入关键字！", Toast.LENGTH_SHORT).show();
            } else {
                keyWord.setValue(editText.getText().toString());
                shopSearchViewModel.setKeyWord(keyWord);
            }
        }
    }
}
