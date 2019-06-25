package com.hardcastle.assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;




        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.OkHttp3Downloader;
        import com.squareup.picasso.Picasso;

        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;

public class PromoGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<ProductModel> catagories;
    LayoutInflater inflter;
    public PromoGridAdapter(Context applicationContext, ArrayList<ProductModel> catagories) {
        this.context = applicationContext;
        this.catagories = catagories;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return catagories.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.product_item, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.imgCat);
        TextView catTv=view.findViewById(R.id.catTV);
        catTv.setText(catagories.get(i).getTitle());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(catagories.get(i).getImage())
                .error(R.drawable.ic_launcher_background)
                .into(icon);
        return view;
    }
}