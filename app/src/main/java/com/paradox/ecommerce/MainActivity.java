package com.paradox.ecommerce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import static com.paradox.ecommerce.R.drawable.*;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.ecommerce.adapter.CategoryAdapter;
import com.paradox.ecommerce.adapter.DiscountedProductAdapter;
import com.paradox.ecommerce.adapter.RecentlyViewedAdapter;
import com.paradox.ecommerce.model.CartModel;
import com.paradox.ecommerce.model.Category;
import com.paradox.ecommerce.model.DiscountedProducts;
import com.paradox.ecommerce.model.RecentlyViewed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    static List<CartModel> cartList;
    ImageView settingsBtn;
    ImageView cartBtn;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;
    TextView allCategory;
    int flag=0;
    int[] imgarr = {b1,b2,b3,b4,b5,b6,b7};
    int[] bigarr = {card1,card2,card3,card4,card5,card6,card7};

    //JSON Parsing
    private String jsonStr;
    private JSONArray prodcuts;
    private JSONObject productJSONObject;
    public static final String TAG_Products = "products";
    public static final String TAG_NAME = "name";
    public static final String TAG_Description = "description";
    public static final String TAG_PRICE = "price";
    public static final String TAG_Quantity = "quantity";
    public static final String TAG_Unit = "unit";
    public static final String TAG_ImageUrl = "imageUrl";
    public static final String TAG_BigImageUrl = "bigimageurl";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        settingsBtn=findViewById(R.id.imageView2);
        cartBtn=findViewById(R.id.cartImage);

        if(cartList==null)
            cartList = new ArrayList<>();

            cartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, CartActivity.class);
                        startActivity(i);
                }
            });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        //Initialize Intent Service
        Intent in = new Intent(this, ParadoxIntentService.class);
        ContextCompat.startForegroundService(this, in);

        /* allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllCategory.class);
                startActivity(i);
            }
        });

         */

        // adding data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, discountberry));
        discountedProductsList.add(new DiscountedProducts(2, discountbrocoli));
        discountedProductsList.add(new DiscountedProducts(3, discountmeat));

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, ic_fruits));
        categoryList.add(new Category(2, ic_fish));
        categoryList.add(new Category(3, ic_dairy));
        categoryList.add(new Category(4, ic_egg));
        categoryList.add(new Category(5, ic_juce));
        categoryList.add(new Category(6, ic_salad));
        int[] imgarr = {b1,b2,b3,b4,b5,b6,b7};
        int[] bigarr = {card1,card2,card3,card4,card5,card6,card7};

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        jsonStr = loadFileFromAssets("actualProducts.json");
        recentlyViewedList=new ArrayList<>();
        new getProducts().execute();
        setRecentlyViewedRecycler(recentlyViewedList);


    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this,recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }


    private class getProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Creating and showing the progress Dialog
        }

        // Main job should be done here
        @Override
        protected Void doInBackground(Void... params) {
            //Log.d("TAG", "HERE.....");

            if (jsonStr != null) {
                try {
                    productJSONObject = new JSONObject(jsonStr);
                    // Getting JSON Array
                    prodcuts = productJSONObject.getJSONArray(TAG_Products);
                    // looping through all books
                    for (int i = 0; i < prodcuts.length(); i++) {

                        JSONObject jsonObj = prodcuts.getJSONObject(i);
                        String name = jsonObj.getString(TAG_NAME);
                        String description = jsonObj.getString(TAG_Description);
                        String price = jsonObj.getString(TAG_PRICE);
                        String quantity = jsonObj.getString(TAG_Quantity);
                        String unit = jsonObj.getString(TAG_Unit);

                        recentlyViewedList.add(new RecentlyViewed(name,description,price,quantity,unit,imgarr[i],bigarr[i]));
                        System.out.println("_________________"+recentlyViewedList.toString());

                    }
                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }

            return null;
        }


    }

    private String loadFileFromRaw(String fileName) {
        String fileContent = null;
        try {
            InputStream is = getResources().openRawResource(
                    getResources().getIdentifier(fileName,
                            "raw", getPackageName()));
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            fileContent = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return fileContent;
    }

    private String loadFileFromAssets(String fileName) {
        String fileContent = null;
        try {
            InputStream is = getBaseContext().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            fileContent = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return fileContent;
    }

    public List<RecentlyViewed> getRecentlyViewedListJSON(){
        return recentlyViewedList;
    }
}