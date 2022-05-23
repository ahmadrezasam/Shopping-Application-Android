package com.paradox.ecommerce.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.paradox.ecommerce.CartActivity;
import com.paradox.ecommerce.MainActivity;
import com.paradox.ecommerce.ProductDetails;
import com.paradox.ecommerce.R;
import com.paradox.ecommerce.SettingsActivity;
import com.paradox.ecommerce.model.CartModel;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<CartModel> cartModelList;



    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        List<CartModel> cartList;
        ProductDetails detail=new ProductDetails();
        cartList=detail.getCartList();
        holder.name.setText(cartModelList.get(position).getName());
        holder.price.setText(cartModelList.get(position).getPrice());
        holder.qty.setText(cartModelList.get(position).getQty());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int actualPosition = holder.getAdapterPosition();
                cartList.remove(actualPosition);
                notifyItemRemoved(position);
            }

        });


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public  static class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView delete;
        TextView name,  price, qty;
        ConstraintLayout bg;
        TextView test;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = (ImageView) itemView.findViewById(R.id.imgDelete);
            test=(TextView) itemView.findViewById(R.id.totalPriceTv);
            name = itemView.findViewById(R.id.nameTv);
            price = itemView.findViewById(R.id.priceTv);
            qty = itemView.findViewById(R.id.qtyTv);


        }
    }
}
