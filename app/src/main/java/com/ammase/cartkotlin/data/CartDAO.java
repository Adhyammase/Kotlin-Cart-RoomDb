package com.ammase.cartkotlin.data;


import androidx.room.*;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insert(Cart data);

//    @Update
//    void update(Cart cart);

    @Query("UPDATE cart SET qty=:qty WHERE id = :id")
    void update(int qty, int id);

    @Delete
    void delete(Cart user);

    @Query("DELETE FROM cart")
    void deleteAllUser();

    @Query("select * from cart")
    Single<List<Cart>>  getdataList();

}
