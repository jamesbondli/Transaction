package com.example.transaction.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.transaction.R;
import com.example.transaction.model.room.cartGoods.CartGoods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartGoodsRVAdapter extends RecyclerView.Adapter<CartGoodsRVAdapter.MyViewHolder> {

    private Context context;
    private List<CartGoods> cartGoodsList;
    private Map<Integer, Boolean> checkStatus = new HashMap<>();
    private MutableLiveData<Double> priceCount;

    public CartGoodsRVAdapter(Context context, List<CartGoods> cartGoodsList) {
        this.context = context;
        this.cartGoodsList = cartGoodsList;
        initCheck(false);
    }

    private void initCheck(boolean flag) {
        for (int i = 0; i < cartGoodsList.size(); i++) {
            //更改指定位置的数据
            checkStatus.put(i, flag);
        }
    }

    //全选
    public void selectAll() {
        for (int i = 0; i < checkStatus.size(); i++) {
            if (!checkStatus.get(i)) {
                priceCount.setValue(priceCount.getValue() +
                        cartGoodsList.get(i).getPrice() *
                                cartGoodsList.get(i).getNumbers());
            }
        }
        initCheck(true);
        notifyDataSetChanged();
    }

    //全不选
    public void unSelectAll() {
        for (int i = 0; i < checkStatus.size(); i++) {
            if (checkStatus.get(i)) {
                priceCount.setValue(priceCount.getValue() -
                        cartGoodsList.get(i).getPrice() *
                                cartGoodsList.get(i).getNumbers());
            }
        }
        initCheck(false);
        notifyDataSetChanged();
    }

    public MutableLiveData<Double> getPriceCount() {
        if (priceCount == null) {
            priceCount = new MutableLiveData<>();
            priceCount.setValue(0.0);
        }
        return priceCount;
    }

    public int getGoodsNumbers() {
        int numbers = 0;
        for (int i = 0; i < cartGoodsList.size(); i++) {
            numbers += cartGoodsList.get(i).getNumbers();
        }
        return numbers;
    }

    public int getNumbers() {
        int numbers = 0;
        for (int i = 0; i < cartGoodsList.size(); i++) {
            if (checkStatus.get(i)) {
                numbers += cartGoodsList.get(i).getNumbers();
            }
        }
        return numbers;
    }

    public Map<Integer, Boolean> getCheckStatus() {
        return checkStatus;
    }

    @NonNull
    @Override
    public CartGoodsRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recycler_view_cart_goods, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartGoodsRVAdapter.MyViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_false)
                .error(R.drawable.img_false);
        if (cartGoodsList.get(position).getImage() != 0) {
            Glide.with(context)
                    .load(cartGoodsList.get(position).getImage())
                    .apply(requestOptions)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.img_false)
                    .apply(requestOptions)
                    .into(holder.imageView);
        }
        holder.textView1.setText("" + cartGoodsList.get(position).getName() +
                " " + cartGoodsList.get(position).getDes());
        holder.textView2.setText("数量：" + cartGoodsList.get(position).getNumbers() +
                "    单价：" + cartGoodsList.get(position).getPrice());

        //清除监听器
        holder.checkBox.setOnCheckedChangeListener(null);
        //设置选中状态
        holder.checkBox.setChecked(checkStatus.get(position));
        //再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在Map中
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //check状态一旦改变，保存的check值也要发生相应的变化
                checkStatus.put(position, isChecked);
                if (isChecked) {
                    priceCount.setValue(priceCount.getValue() +
                            (cartGoodsList.get(position).getPrice() *
                                    cartGoodsList.get(position).getNumbers()));
                } else {
                    priceCount.setValue(priceCount.getValue() -
                            (cartGoodsList.get(position).getPrice() *
                                    cartGoodsList.get(position).getNumbers()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartGoodsList == null ? 0 : cartGoodsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cart_goods_check);
            imageView = itemView.findViewById(R.id.cart_goods_image);
            textView1 = itemView.findViewById(R.id.cart_goods_name_des);
            textView2 = itemView.findViewById(R.id.cart_goods_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClickListener != null) {
                        onRecyclerItemClickListener.onRecyclerItemClick
                                (cartGoodsList.get(getAdapterPosition()).getGoods_id());
                    }
                }
            });
        }
    }

    private onRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(onRecyclerItemClickListener listener) {
        onRecyclerItemClickListener = listener;
    }

    public interface onRecyclerItemClickListener {
        void onRecyclerItemClick(int goods_id);
    }
}
