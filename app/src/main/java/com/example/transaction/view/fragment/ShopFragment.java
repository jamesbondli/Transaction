package com.example.transaction.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.transaction.R;
import com.example.transaction.databinding.FragmentShopBinding;
import com.example.transaction.model.adapter.GoodsRVAdapter;
import com.example.transaction.model.room.MyDatabase;
import com.example.transaction.model.room.good.Goods;
import com.example.transaction.view.activity.ShopItemActivity;
import com.example.transaction.view.activity.ShopSearchActivity;
import com.example.transaction.viewModel.ShopViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShopFragment extends BaseFragment {

    private View rootView;
    private RecyclerView recyclerView;
    private List<Goods> goodsList;
    private GoodsRVAdapter goodsRVAdapter;
    private ShopViewModel shopViewModel;
    private FragmentShopBinding binding;
    private MyDatabase myDatabase;

    /**
     * 初始化
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shop, container, false);

        //建立绑定和感应
        shopViewModel = ShopViewModel.setShopViewModel(ShopFragment.this.getContext());
        binding = FragmentShopBinding.bind(rootView);
        binding.setViewModel(shopViewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(this);

        /*控件初始化*/
        initView();

        /*监听数据库商品信息，变化时更新列表*/
        shopViewModel.getGoodsList().observe(getViewLifecycleOwner(), new Observer<List<Goods>>() {
            @Override
            public void onChanged(List<Goods> goods) {
                goodsList.clear();
                goodsList.addAll(goods);
                setRecyclerView(goodsList);
            }
        });

        return rootView;
    }

    /**
     * 设置recyclerView
     *
     * @param goodsList
     */
    private void setRecyclerView(List<Goods> goodsList) {

        /*传入数据和设置适配器*/
        goodsRVAdapter = new GoodsRVAdapter(ShopFragment.this.getContext(), goodsList);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(4, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(goodsRVAdapter);

        /*接口回调实现item点击事件*/
        goodsRVAdapter.setOnRecyclerItemClickListener(new GoodsRVAdapter.onRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                intent.setClass(ShopFragment.this.getContext(), ShopItemActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 控件初始化
     */
    private void initView() {
        recyclerView = binding.shopRecyclerView;
        goodsList = new ArrayList<>();
    }

    /**
     * 内部类点击事件
     * 优点：不用传入context，减少内存泄漏的可能
     */
    public class ProxyClick {

        /*跳转到搜索界面*/
        public void toSearch() {
            Intent intent = new Intent();
            intent.setClass(ShopFragment.this.getContext(), ShopSearchActivity.class);
            startActivity(intent);
        }

        /*自定义插入数据库数据,用于测试*/
        public void insertGoods() {
            myDatabase = MyDatabase.getInstance(ShopFragment.this.getContext());
            Goods goods1 = new Goods("吴彦祖", "170604127");
            goods1.setDes("这是一张吴彦祖照片!");
            goods1.setPrice(100.0);
            goods1.setImage(R.drawable.wyz);
            Goods goods2 = new Goods("OPPO phone", "170604127");
            goods2.setDes("这是一张手机图片!");
            goods2.setImage(R.drawable.oppo_phone);
            goods2.setPrice(200.0);
            Goods goods3 = new Goods("shopX", "170604127");
            goods3.setPrice(300.0);
            List<Goods> goodsList = new ArrayList<>();
            goodsList.add(goods1);
            goodsList.add(goods2);
            goodsList.add(goods3);
            goodsList.add(goods1);
            goodsList.add(goods2);
            goodsList.add(goods3);
            goodsList.add(goods1);
            goodsList.add(goods2);
            goodsList.add(goods3);
            goodsList.add(goods1);
            goodsList.add(goods2);
            goodsList.add(goods3);
            Observable.just(goodsList)
                    .map(new Function<List<Goods>, Object>() {
                        @Override
                        public Object apply(List<Goods> goods) throws Throwable {
                            for (Goods good : goods) {
                                myDatabase.getGoodsDAO().insertGoods(good);
                            }
                            return null;
                        }
                    }).subscribeOn(Schedulers.io())//为上述操作分配异步线程
                    .observeOn(AndroidSchedulers.mainThread())//异步操作结束，回到主线程
                    .subscribe(new io.reactivex.rxjava3.core.Observer<Object>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}