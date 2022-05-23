package com.paradox.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paradox.ecommerce.model.CartModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    MainActivity activity = new MainActivity();
    static List<CartModel> cartList;
    ImageView img, back;
    ImageView cartBtn;
    TextView proName, proPrice, proDesc, proQty, proUnit;
    Spinner mySpinner;
    String name, price, desc, qty, unit;
    String newPrice;
    int image;
    double q;
    double p;
    String quant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();

        name = i.getStringExtra("name");
        image = i.getIntExtra("image", R.drawable.b1);
        price = i.getStringExtra("price");
        desc = i.getStringExtra("desc");
        qty = i.getStringExtra("qty");
        unit = i.getStringExtra("unit");

        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        img = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);
        proQty = findViewById(R.id.qty);
        proUnit = findViewById(R.id.unit);
        cartBtn=findViewById(R.id.imageView7);
        mySpinner=findViewById(R.id.qtySpinner);
        final String[] quantity = {mySpinner.getSelectedItem().toString()};
        if(cartList==null)
            cartList = new ArrayList<>();

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                quantity[0] =parentView.getItemAtPosition(position).toString();
                q = Double.parseDouble(quantity[0]);
                p = Double.parseDouble(price);
                p=p*q;
                BigDecimal bd = new BigDecimal(p).setScale(2, RoundingMode.HALF_UP);
                newPrice=String.valueOf(bd);
                proPrice.setText(newPrice+" ₺");
                quant=String.valueOf(q);
                proQty.setText(quant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });
        proName.setText(name);
        proDesc.setText(desc);
        proQty.setText(qty);
        proUnit.setText(unit);
        img.setImageResource(image);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartList.add(new CartModel(name,newPrice,quant));
                MediaPlayer ring= MediaPlayer.create(ProductDetails.this,R.raw.ring);
                ring.start();
                Toast.makeText(getApplicationContext(), "Item(s) Successfully added to cart", Toast.LENGTH_LONG).show();
                System.out.println(cartList);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDetails.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public List<CartModel> getCartList(){
        return cartList;
    }
}