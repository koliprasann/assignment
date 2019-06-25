package com.hardcastle.assignment;


import android.content.Context;
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

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<ProductModel> ProductssList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, prize;
        public ImageView productIV;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvName);
            prize=view.findViewById(R.id.tvPrize);
            productIV=view.findViewById(R.id.ivProduct);


        }
    }


    public ProductRecyclerViewAdapter(Context applicationContext,ArrayList< ProductModel>  ProductssList) {
        this. ProductssList =  ProductssList;
        this.context=applicationContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductModel  products =  ProductssList.get(position);
        holder.title.setText( products.getTitle());
        holder.prize.setText( products.getPrice());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(products.getImage())
                .error(R.drawable.ic_launcher_background)
                .into(holder.productIV);
    }

    @Override
    public int getItemCount() {
        return  ProductssList.size();
    }
}