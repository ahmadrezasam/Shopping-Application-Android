package com.paradox.ecommerce;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paradox.ecommerce.adapter.CartAdapter;
import com.paradox.ecommerce.model.CartModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ProductDetails detail=new ProductDetails();
    private MainActivity jsonDetail=new MainActivity();

    RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    List<CartModel> cartList;
    List<CartModel> cartList2;
    ImageView back;
    TextView totalTv;
    Double totalPrice=0.0;
    BigDecimal bd;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cartRecycler=findViewById(R.id.cartRecycler);
        totalTv=findViewById(R.id.totalPriceTv);
        checkout=findViewById(R.id.btnCheckOut);
        cartList = detail.getCartList();
        setCartRecycler(cartList);
        bd=getTotalPrice();
        totalTv.setText("Total Price: "+bd);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartList.removeAll(cartList);
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Thank you for your order!");
                builder.setMessage("Your order has been created. Thank you for choosing us :)");
                builder.setPositiveButton("Go back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent a = new Intent(CartActivity.this, MainActivity.class);
                        startActivity(a);
                        finish();
                    }
                });
                builder.show();
            }
        });

    }
    private void setCartRecycler(List<CartModel> cartDataList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartRecycler.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this,cartDataList);
        cartRecycler.setAdapter(cartAdapter);
    }
    public BigDecimal getTotalPrice (){
        for(int i=0;i<cartList.size();i++){
            totalPrice+=Double.parseDouble(cartList.get(i).getPrice());
            bd = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
        }
        totalTv.setText("Total Price: "+bd);
        return bd;
    }
}