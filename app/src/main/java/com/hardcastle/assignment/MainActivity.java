package com.hardcastle.assignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
public class MainActivity extends Activity {
    private static ViewPager mPager = null;
    private SliderAdapter pagerAdapter = null;
    GridView basicGrid,PromoGrid;
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    ProductRecyclerViewAdapter productRecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new GetEventDetails().execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class GetEventDetails extends AsyncTask<Void, Void, Void> {

        String response;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            //pDialog.setMessage(getResources().getString(R.string.get_event_msg));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                NetworkCalls networkCalls = new NetworkCalls();
                response = networkCalls.sendGet(APIUtils.BASE_URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Log.e("Response", response);

            try {

                JSONObject responseObject = new JSONObject(response);

                JSONArray jsonArraySlider = responseObject.getJSONArray("slider");
                JSONArray jsonArrayCatagary = responseObject.getJSONArray("categories");

                final ArrayList<SliderModel> SliderArray = new ArrayList<SliderModel>();
                final ArrayList<CatagoryModel> CatagaryArray = new ArrayList<CatagoryModel>();
                if (jsonArraySlider != null) {
                    for (int i = 0; i < jsonArraySlider.length(); i++) {
                        JSONObject jsonObject = jsonArraySlider.getJSONObject(i);
                        SliderArray.add(new SliderModel(jsonObject.getInt("id"), jsonObject.getString("title"), jsonObject.getString("link"), jsonObject.getString("image")));
                    }
                }
                ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                SliderAdapter adapter = new SliderAdapter(MainActivity.this,SliderArray);
                viewPager.setAdapter(adapter);




                if (jsonArrayCatagary != null) {
                    for (int i = 0; i < jsonArrayCatagary.length(); i++) {
                        JSONObject jsonObject = jsonArrayCatagary.getJSONObject(i);
                        CatagaryArray.add(new CatagoryModel( jsonObject.getString("title"),jsonObject.getString("id"), jsonObject.getString("image"), jsonObject.getString("href")));
                    }
                }
                basicGrid = (GridView) findViewById(R.id.basicGrid); // init GridView
                // Create an object of CustomAdapter and set Adapter to GirdView
                GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), CatagaryArray);
                basicGrid.setAdapter(gridAdapter);
                // implement setOnItemClickListener event on GridView
                basicGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // set an Intent to Another Activity
                    }
                });



                JSONArray jsonArrayPromo = responseObject.getJSONObject("Promotions").getJSONArray("products");

                final ArrayList<ProductModel> PromoArray = new ArrayList<ProductModel>();
                if (jsonArrayPromo != null) {
                    for (int i = 0; i < jsonArrayPromo.length(); i++) {
                        JSONObject jsonObject = jsonArrayPromo.getJSONObject(i);
                        PromoArray.add(new ProductModel(jsonObject.getString("id"),
                                jsonObject.getString("product_id"),
                                jsonObject.getString("image"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                jsonObject.getString("price2"),
                                jsonObject.getBoolean("special"),
                                jsonObject.getBoolean("special2"),
                                jsonObject.getBoolean("tax"),
                                /*jsonObject.getString("product_options"),*/
                                jsonObject.getString("minimum"),
                                jsonObject.getString("rating"),
                                jsonObject.getString("href"),
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }



                LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerView = (RecyclerView)findViewById(R.id.promoRv);

                RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(linearLayoutManager);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,PromoArray);

                recyclerView.setAdapter(productRecyclerViewHorizontalAdapter);



                JSONArray jsonArrayGrocery = responseObject.getJSONObject("Grocery").getJSONArray("products");

                final ArrayList<ProductModel> GroceryArray = new ArrayList<ProductModel>();
                if (jsonArrayPromo != null) {
                    for (int i = 0; i < jsonArrayPromo.length(); i++) {
                        JSONObject jsonObject = jsonArrayPromo.getJSONObject(i);
                        PromoArray.add(new ProductModel(jsonObject.getString("id"),
                                jsonObject.getString("product_id"),
                                jsonObject.getString("image"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                jsonObject.getString("price2"),
                                jsonObject.getBoolean("special"),
                                jsonObject.getBoolean("special2"),
                                jsonObject.getBoolean("tax"),
                                /*jsonObject.getString("product_options"),*/
                                jsonObject.getString("minimum"),
                                jsonObject.getString("rating"),
                                jsonObject.getString("href"),
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }





                RecyclerView recyclerViewGrocery = (RecyclerView)findViewById(R.id.GroceryRv);

                LinearLayoutManager linearLayoutManagerG
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewGrocery.setLayoutManager(linearLayoutManagerG);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,PromoArray);

                recyclerView.setAdapter(productRecyclerViewHorizontalAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }

}

