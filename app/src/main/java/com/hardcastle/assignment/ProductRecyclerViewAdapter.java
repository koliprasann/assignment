package com.hardcastle.assignment;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<ProductModel> ProductssList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, prize;
        public ImageView productIV, LikeIv, cartIv;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvName);
            prize = view.findViewById(R.id.tvPrize);
            productIV = view.findViewById(R.id.ivProduct);
            LikeIv = view.findViewById(R.id.likeIV);
            cartIv = view.findViewById(R.id.addToCartIv);

        }
    }


    public ProductRecyclerViewAdapter(Context applicationContext, ArrayList<ProductModel> ProductssList) {
        this.ProductssList = ProductssList;
        this.context = applicationContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProductModel products = ProductssList.get(position);
        holder.title.setText(products.getTitle());
        holder.prize.setText(products.getPrice());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(products.getImage())
                .error(R.drawable.thumb)
                .into(holder.productIV);

        holder.LikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.LikeIv.setImageResource(R.drawable.like);
                Toast.makeText(context, "Added To Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.cartIv.setImageResource(R.drawable.addedcart);
                Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

        holder.productIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("OBJECT", products);
                intent.putExtra("Linked", products.getLinked_products());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductssList.size();
    }
}