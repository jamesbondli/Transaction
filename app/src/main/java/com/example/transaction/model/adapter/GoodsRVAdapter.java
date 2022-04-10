package com.example.transaction.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.transaction.R;
import com.example.transaction.model.room.good.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsRVAdapter extends RecyclerView.Adapter<GoodsRVAdapter.MyViewHolder> {

    private Context context;
    private List<Goods> goodsList;

    public GoodsRVAdapter(Context context, List<Goods> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public GoodsRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recycler_view_goods, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsRVAdapter.MyViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_false)
                .error(R.drawable.img_false);
        if (goodsList.get(position).getImage() != 0) {
            Glide.with(context)
                    .load(goodsList.get(position).getImage())
                    .apply(requestOptions)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.img_false)
                    .apply(requestOptions)
                    .into(holder.imageView);
        }
        holder.name_dec.setText(goodsList.get(position).getName() +
                " " + goodsList.get(position).getDes());
        holder.price.setText("" + goodsList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return goodsList == null ? 0 : goodsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_dec, price;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_dec = itemView.findViewById(R.id.item_rv_goods_name_dec);
            price = itemView.findViewById(R.id.item_rv_goods_price);
            imageView = itemView.findViewById(R.id.item_rv_goods_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClickListener != null) {
                        onRecyclerItemClickListener.onRecyclerItemClick
                                (goodsList.get(getAdapterPosition()).getId());
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
        void onRecyclerItemClick(int id);
    }
}
