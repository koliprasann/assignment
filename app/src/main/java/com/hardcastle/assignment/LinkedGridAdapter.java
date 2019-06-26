package com.hardcastle.assignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LinkedGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<LinkedProductModel> linkedProducts;
    LayoutInflater inflter;
    public LinkedGridAdapter(Context applicationContext, ArrayList<LinkedProductModel> linkedProducts) {
        this.context = applicationContext;
        this.linkedProducts = linkedProducts;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return linkedProducts.size();
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
        ImageView productImg= view.findViewById(R.id.ivProduct);
       TextView title = (TextView) view.findViewById(R.id.tvName);
      TextView  prize = view.findViewById(R.id.tvPrize);
      final ImageView  LikeIv = view.findViewById(R.id.likeIV);
      final ImageView  cartIv = view.findViewById(R.id.addToCartIv);

        final LinkedProductModel products = linkedProducts.get(i);
        title.setText(products.getTitle());
        prize.setText(products.getPrice());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(products.getImage())
                .error(R.drawable.thumb)
                .into(productImg);

        LikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LikeIv.setImageResource(R.drawable.like);
                Toast.makeText(context, "Added To Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

        cartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartIv.setImageResource(R.drawable.addedcart);
                Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

       /* productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("OBJECT", products);
                context.startActivity(intent);
            }
        });*/



        return view;
    }
}