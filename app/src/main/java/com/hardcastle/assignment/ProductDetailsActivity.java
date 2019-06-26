package com.hardcastle.assignment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailsActivity extends Activity {
    ProductModel productModel;
    ImageView productImage;
    TextView Title;
    TextView prize1,prize2;
    GridView linkedGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productModel= getIntent().getParcelableExtra("OBJECT");
        ArrayList<LinkedProductModel> linkedProductModels = (ArrayList<LinkedProductModel>) getIntent().getSerializableExtra("Linked");

        productImage=findViewById(R.id.productIv);
        Title=findViewById(R.id.detailsTitleTv);
        prize1=findViewById(R.id.TvPrize1);
        prize2=findViewById(R.id.TvPrize2);


        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(productModel.getImage())
                .error(R.drawable.thumb)
                .into(productImage);

        Title.setText(productModel.getTitle());
        prize1.setText(productModel.getPrice());
        prize1.setPaintFlags(prize1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        prize2.setText(productModel.getPrice2());


        linkedGrid = (GridView) findViewById(R.id.linkedProductGrid); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        LinkedGridAdapter linkedGridAdapter = new LinkedGridAdapter(getApplicationContext(), linkedProductModels);
        if (linkedProductModels!=null) {
            linkedGrid.setAdapter(linkedGridAdapter);
        }

    }


    public void onShare(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Check out this product");
        share.putExtra(Intent.EXTRA_TEXT, "Product Details");

        startActivity(Intent.createChooser(share, "Share"));
    }

    public void onAddCart(View view) {
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
    }
}
