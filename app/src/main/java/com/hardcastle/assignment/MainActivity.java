package com.hardcastle.assignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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

import dmax.dialog.SpotsDialog;

public class MainActivity extends Activity {
    ViewPager viewPager;
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

        ConnectivityManager connec =   (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet
            try {
                new GetEventDetails().execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();


        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, "Please Check Internet connection ", Toast.LENGTH_LONG).show();
        }

    }

    public class GetEventDetails extends AsyncTask<Void, Void, Void> {

        String response;
        private AlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SpotsDialog(MainActivity.this, R.style.Custom);

           // pDialog = new ProgressDialog(MainActivity.this);
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
                /*ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                SliderAdapter adapter = new SliderAdapter(MainActivity.this,SliderArray);
                viewPager.setAdapter(adapter);*/

                viewPager = (ViewPager)findViewById(R.id.pager);

                SliderAdapter sliderAdapter= new SliderAdapter(MainActivity.this, SliderArray);
                viewPager.setAdapter(sliderAdapter);

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

                        JSONArray linkedArray=jsonObject.getJSONArray("linked_products");

                        ArrayList<LinkedProductModel> linkedProductModels =new ArrayList<>();
                        for (int j = 0; j < linkedArray.length(); j++) {
                            JSONObject jsonObjectLinkedModel = linkedArray.getJSONObject(j);

                            linkedProductModels.add(new LinkedProductModel(jsonObjectLinkedModel.getString("id"),jsonObjectLinkedModel.getString("title"),jsonObjectLinkedModel.getString("price2"),jsonObjectLinkedModel.getString("price"),jsonObjectLinkedModel.getString("minimum"),jsonObjectLinkedModel.getString("special2"),jsonObjectLinkedModel.getString("special"),jsonObjectLinkedModel.getString("image"),jsonObjectLinkedModel.getString("href")));
                        }


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
                                linkedProductModels,
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
                if (jsonArrayGrocery != null) {
                    for (int i = 0; i < jsonArrayGrocery.length(); i++) {
                        JSONObject jsonObject = jsonArrayGrocery.getJSONObject(i);


                        JSONArray linkedArray=jsonObject.getJSONArray("linked_products");

                        ArrayList<LinkedProductModel> linkedProductModels =new ArrayList<>();
                        for (int j = 0; j < linkedArray.length(); j++) {
                            JSONObject jsonObjectLinkedModel = linkedArray.getJSONObject(j);

                            linkedProductModels.add(new LinkedProductModel(jsonObjectLinkedModel.getString("id"),jsonObjectLinkedModel.getString("title"),jsonObjectLinkedModel.getString("price2"),jsonObjectLinkedModel.getString("price"),jsonObjectLinkedModel.getString("minimum"),jsonObjectLinkedModel.getString("special2"),jsonObjectLinkedModel.getString("special"),jsonObjectLinkedModel.getString("image"),jsonObjectLinkedModel.getString("href")));
                        }
                        GroceryArray.add(new ProductModel(jsonObject.getString("id"),
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
                                linkedProductModels,
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }





                RecyclerView recyclerViewGrocery = (RecyclerView)findViewById(R.id.GroceryRv);

                LinearLayoutManager linearLayoutManagerG
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewGrocery.setLayoutManager(linearLayoutManagerG);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,GroceryArray);

                recyclerViewGrocery.setAdapter(productRecyclerViewHorizontalAdapter);


                JSONArray jsonArrayHealthBeauty  = responseObject.getJSONObject("Health & Beauty ").getJSONArray("products");

                final ArrayList<ProductModel> HealthBeautyArray = new ArrayList<ProductModel>();
                if (jsonArrayHealthBeauty != null) {
                    for (int i = 0; i < jsonArrayHealthBeauty.length(); i++) {
                        JSONObject jsonObject = jsonArrayHealthBeauty.getJSONObject(i);

                        JSONArray linkedArray=jsonObject.getJSONArray("linked_products");

                        ArrayList<LinkedProductModel> linkedProductModels =new ArrayList<>();
                        for (int j = 0; j < linkedArray.length(); j++) {
                            JSONObject jsonObjectLinkedModel = linkedArray.getJSONObject(j);

                            linkedProductModels.add(new LinkedProductModel(jsonObjectLinkedModel.getString("id"),jsonObjectLinkedModel.getString("title"),jsonObjectLinkedModel.getString("price2"),jsonObjectLinkedModel.getString("price"),jsonObjectLinkedModel.getString("minimum"),jsonObjectLinkedModel.getString("special2"),jsonObjectLinkedModel.getString("special"),jsonObjectLinkedModel.getString("image"),jsonObjectLinkedModel.getString("href")));
                        }


                        HealthBeautyArray.add(new ProductModel(jsonObject.getString("id"),
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
                                linkedProductModels,
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }





                RecyclerView recyclerViewHnB = (RecyclerView)findViewById(R.id.HnBRv);

                LinearLayoutManager linearLayoutManagerHnB
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewHnB.setLayoutManager(linearLayoutManagerHnB);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,HealthBeautyArray);

                recyclerViewHnB.setAdapter(productRecyclerViewHorizontalAdapter);



                JSONArray jsonArrayBaby = responseObject.getJSONObject("Baby").getJSONArray("products");

                final ArrayList<ProductModel> BabyArray = new ArrayList<ProductModel>();
                if (jsonArrayBaby != null) {
                    for (int i = 0; i < jsonArrayBaby.length(); i++) {
                        JSONObject jsonObject = jsonArrayBaby.getJSONObject(i);
                        JSONArray linkedArray=jsonObject.getJSONArray("linked_products");

                        ArrayList<LinkedProductModel> linkedProductModels =new ArrayList<>();
                        for (int j = 0; j < linkedArray.length(); j++) {
                            JSONObject jsonObjectLinkedModel = linkedArray.getJSONObject(j);

                            linkedProductModels.add(new LinkedProductModel(jsonObjectLinkedModel.getString("id"),jsonObjectLinkedModel.getString("title"),jsonObjectLinkedModel.getString("price2"),jsonObjectLinkedModel.getString("price"),jsonObjectLinkedModel.getString("minimum"),jsonObjectLinkedModel.getString("special2"),jsonObjectLinkedModel.getString("special"),jsonObjectLinkedModel.getString("image"),jsonObjectLinkedModel.getString("href")));
                        }
                        BabyArray.add(new ProductModel(jsonObject.getString("id"),
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
                                linkedProductModels,
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }





                RecyclerView recyclerViewBaby = (RecyclerView)findViewById(R.id.BabyRv);

                LinearLayoutManager linearLayoutManagerBaby
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewBaby.setLayoutManager(linearLayoutManagerBaby);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,BabyArray);

                recyclerViewBaby.setAdapter(productRecyclerViewHorizontalAdapter);



                JSONArray jsonArrayHousehold = responseObject.getJSONObject("Household Supplies").getJSONArray("products");

                final ArrayList<ProductModel> HouseholdArray = new ArrayList<ProductModel>();
                if (jsonArrayHousehold != null) {
                    for (int i = 0; i < jsonArrayHousehold.length(); i++) {
                        JSONObject jsonObject = jsonArrayHousehold.getJSONObject(i);

                        JSONArray linkedArray=jsonObject.getJSONArray("linked_products");

                        ArrayList<LinkedProductModel> linkedProductModels =new ArrayList<>();
                        for (int j = 0; j < linkedArray.length(); j++) {
                            JSONObject jsonObjectLinkedModel = linkedArray.getJSONObject(j);

                            linkedProductModels.add(new LinkedProductModel(jsonObjectLinkedModel.getString("id"),jsonObjectLinkedModel.getString("title"),jsonObjectLinkedModel.getString("price2"),jsonObjectLinkedModel.getString("price"),jsonObjectLinkedModel.getString("minimum"),jsonObjectLinkedModel.getString("special2"),jsonObjectLinkedModel.getString("special"),jsonObjectLinkedModel.getString("image"),jsonObjectLinkedModel.getString("href")));
                        }

                        HouseholdArray.add(new ProductModel(jsonObject.getString("id"),
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
                                linkedProductModels,
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }





                RecyclerView recyclerViewHousehold = (RecyclerView)findViewById(R.id.HouseholdRv);

                LinearLayoutManager linearLayoutManagerHousehold
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewHousehold.setLayoutManager(linearLayoutManagerHousehold);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,HouseholdArray);

                recyclerViewHousehold.setAdapter(productRecyclerViewHorizontalAdapter);


                JSONArray jsonArrayStationery = responseObject.getJSONObject("Stationery").getJSONArray("products");

                final ArrayList<ProductModel> StationeryArray = new ArrayList<ProductModel>();
                if (jsonArrayStationery != null) {
                    for (int i = 0; i < jsonArrayStationery.length(); i++) {
                        JSONObject jsonObject = jsonArrayStationery.getJSONObject(i);
                        JSONArray linkedArray=jsonObject.getJSONArray("linked_products");

                        ArrayList<LinkedProductModel> linkedProductModels =new ArrayList<>();
                        for (int j = 0; j < linkedArray.length(); j++) {
                            JSONObject jsonObjectLinkedModel = linkedArray.getJSONObject(j);

                            linkedProductModels.add(new LinkedProductModel(jsonObjectLinkedModel.getString("id"),jsonObjectLinkedModel.getString("title"),jsonObjectLinkedModel.getString("price2"),jsonObjectLinkedModel.getString("price"),jsonObjectLinkedModel.getString("minimum"),jsonObjectLinkedModel.getString("special2"),jsonObjectLinkedModel.getString("special"),jsonObjectLinkedModel.getString("image"),jsonObjectLinkedModel.getString("href")));
                        }
                        StationeryArray.add(new ProductModel(jsonObject.getString("id"),
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
                                linkedProductModels,
                                /*jsonObject.getString("linked_products"),*/
                                jsonObject.getString("combo_products")
                                /*jsonObject.getJSONArray("product_options")*/));
                    }
                }





                RecyclerView recyclerViewStationery = (RecyclerView)findViewById(R.id.StationeryRv);

                LinearLayoutManager linearLayoutManagerStationery
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewStationery.setLayoutManager(linearLayoutManagerStationery);
                productRecyclerViewHorizontalAdapter=new ProductRecyclerViewAdapter(MainActivity.this,StationeryArray);

                recyclerViewStationery.setAdapter(productRecyclerViewHorizontalAdapter);

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

