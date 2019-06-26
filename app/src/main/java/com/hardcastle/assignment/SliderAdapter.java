package com.hardcastle.assignment;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter{
    Context context;
ArrayList<SliderModel> sliderModels;
    LayoutInflater layoutInflater;


    public SliderAdapter(Context context,ArrayList<SliderModel> sliderModels) {
        this.context = context;
        this.sliderModels = sliderModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sliderModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.slider_layouts, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivSlider);
        TextView textView=itemView.findViewById(R.id.tvSliderText);
        textView.setText(sliderModels.get(position).getTitle());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(sliderModels.get(position).getImage())
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}