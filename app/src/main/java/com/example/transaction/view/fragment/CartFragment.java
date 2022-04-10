package com.example.transaction.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transaction.R;
import com.example.transaction.databinding.FragmentCartBinding;
import com.example.transaction.model.adapter.CartGoodsRVAdapter;
import com.example.transaction.model.room.cartGoods.CartGoods;
import com.example.transaction.view.activity.ShopItemActivity;
import com.example.transaction.view.activity.login.LoginActivity;
import com.example.transaction.viewModel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment {

    private View rootView;
    private CartViewModel viewModel;
    private FragmentCartBinding binding;
    private MutableLiveData<String> cartMenu;
    private MutableLiveData<Integer> l1Visible;
    private MutableLiveData<Integer> l2Visible;
    private RecyclerView recyclerView;
    private List<CartGoods> cartGoodsList;
    private CheckBox checkBox;
    private CartGoodsRVAdapter adapter;
    private AlertDialog.Builder builder;

    /**
     * Fragment创建视图，该方法中完成绑定、初始化
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
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        viewModel = CartViewModel.setCartViewModel(CartFragment.this.getContext());
        binding = FragmentCartBinding.bind(rootView);
        binding.setViewModel(viewModel);
        binding.setClick(new ProxyClick());
        binding.setLifecycleOwner(getViewLifecycleOwner());

        /*控件初始化*/
        initView();

        /*获取购物车信息列表*/
        viewModel.setCartGoodsList();

        /*监听购物车列表是否发生变化*/
        viewModel.getCartGoodsList().observe(getViewLifecycleOwner(), new Observer<List<CartGoods>>() {
            @Override
            public void onChanged(List<CartGoods> cartGoods) {
                cartGoodsList.clear();
                cartGoodsList.addAll(cartGoods);
                setRecyclerView(cartGoodsList);
            }
        });

        return rootView;
    }

    /**
     * 传入数据，设置recyclerView
     *
     * @param cartGoodsList
     */
    private void setRecyclerView(List<CartGoods> cartGoodsList) {
        /*给recyclerView设置数据*/
        adapter = new CartGoodsRVAdapter(CartFragment.this.getContext(), cartGoodsList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(CartFragment.this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        /*设置数量*/
        viewModel.getNumbers().setValue(adapter.getGoodsNumbers());

        /*监听adapter中priceCount的变化，变化时赋值给viewModel中的price*/
        adapter.getPriceCount().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                viewModel.getPrice().setValue(aDouble);
            }
        });

        /*给列表每一个item添加跳转监听器*/
        adapter.setOnRecyclerItemClickListener(new CartGoodsRVAdapter.onRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int goods_id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("id", goods_id);
                intent.putExtras(bundle);
                intent.setClass(CartFragment.this.getContext(), ShopItemActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.setCartGoodsList();
        viewModel.setVisible();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        cartMenu = viewModel.getCartMenu();
        l1Visible = viewModel.getL1Visible();
        l2Visible = viewModel.getL2Visible();
        cartGoodsList = new ArrayList<>();
        recyclerView = binding.cartRv;
        checkBox = binding.cartCbStatistic;
    }

    /**
     * 内部类点击事件监听
     */
    public class ProxyClick {
        /*切换菜单*/
        public void Change() {
            switch (cartMenu.getValue()) {
                case "管理":
                    cartMenu.setValue("完成");
                    l1Visible.setValue(2);
                    l2Visible.setValue(0);
                    break;
                case "完成":
                    cartMenu.setValue("管理");
                    l1Visible.setValue(0);
                    l2Visible.setValue(2);
                    break;
            }
        }

        /*跳转登录*/
        public void toLogin() {
            Intent intent = new Intent();
            intent.setClass(CartFragment.this.getContext(), LoginActivity.class);
            startActivity(intent);
        }

        /*结算*/
        public void toPay() {
            if (adapter.getNumbers() > 0) {
                builder = new AlertDialog.Builder(getActivity())
                        .setTitle("结算")
                        .setMessage("总计价格￥" + adapter.getPriceCount().getValue() + ",您确定要结算吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.cartGoodsPay(adapter.getCheckStatus());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        }

        /*设置checkBox是否全选*/
        public void checkBoxAllSelected() {
            if (viewModel.getFlag().getValue()) {
                viewModel.getFlag().setValue(false);
                checkBox.setSelected(false);
                adapter.unSelectAll();
            } else {
                viewModel.getFlag().setValue(true);
                checkBox.setSelected(true);
                adapter.selectAll();
            }
        }
    }
}