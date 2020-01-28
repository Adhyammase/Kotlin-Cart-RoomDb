package com.ammase.cartkotlin.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "qty")
    private int qty;


    public Cart(int id, String name, int price, String desc, int qty){
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.qty = qty;
    }

    @Ignore
    public Cart(String name, int price, String desc , int qty){
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.qty = qty;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public int getQty() {
        return qty;
    }
}
