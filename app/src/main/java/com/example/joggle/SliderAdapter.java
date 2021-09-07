package com.example.joggle;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joggle.R;
import com.example.joggle.SliderData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter <SliderAdapter.SliderAdapterViewHolder>{

    int[] images;
    String[] texts;
//    private final List<SliderData> mSliderItems;

    public SliderAdapter(int[] images,String[] texts) {
        this.images = images;
        this.texts = texts;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliders,null);
        return new SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

//        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        viewHolder.imageViewBackground.setImageResource(images[position]);
        viewHolder.text.setText(texts[position]);
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;
        TextView text;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            text = itemView.findViewById(R.id.text);
            this.itemView = itemView;
        }
    }


    @Override
    public int getCount() {
        return images.length;
    }
}
