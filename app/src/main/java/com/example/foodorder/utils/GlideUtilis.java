package com.example.foodorder.utils;

import android.widget.ImageView;

import com.example.foodorder.R;
import com.bumptech.glide.Glide;

public class GlideUtilis {
    public static void loadUrlBanner(String url, ImageView imageView) {
        if (url.isEmpty()) {
            imageView.setImageResource(R.drawable.baseline_hide_image_24);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.baseline_hide_image_24)
                .dontAnimate()
                .into(imageView);
    }
}
