package com.paradox.ecommerce.model;

public class CartModel {
    String name;
    String price;
    String qty;


    public CartModel(String name, String price, String qty){
        this.name=name;
        this.price=price;
        this.qty=qty;
    }

    public String getQty(){
        return qty;
    }
    public String getPrice() {
        return price;
    }
    public String getName(){
        return name;
    }
    public void setName(){ this.name=name; }
    public void setPrice(){ this.price=price; }
    public void setQty(){ this.qty=qty; }


}