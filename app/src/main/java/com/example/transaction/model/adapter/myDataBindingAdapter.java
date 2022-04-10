package com.example.transaction.model.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.transaction.R;

public class myDataBindingAdapter {
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("imageUrl")
    public static void setSrc(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.drawable.img_false)
                .into(imageView);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(LinearLayout linearLayout, int visible) {
        switch (visible) {
            case 0:
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                linearLayout.setVisibility(View.INVISIBLE);
                break;
            case 2:
                linearLayout.setVisibility(View.GONE);
                break;
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(RelativeLayout relativeLayout, int visible) {
        switch (visible) {
            case 0:
                relativeLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                relativeLayout.setVisibility(View.INVISIBLE);
                break;
            case 2:
                relativeLayout.setVisibility(View.GONE);
                break;
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(RecyclerView recyclerView, int visible) {
        switch (visible) {
            case 0:
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case 1:
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case 2:
                recyclerView.setVisibility(View.GONE);
                break;
        }
    }
}
