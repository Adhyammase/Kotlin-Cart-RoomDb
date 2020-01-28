package com.ammase.cartkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ammase.cartkotlin.data.Cart
import com.ammase.cartkotlin.data.CartDatabase
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userDatabase: CartDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDatabase = CartDatabase.getInstance(this)

        btn_save.setOnClickListener {
            var name = et_input_name.text.toString()
            var price = et_input_price.text.toString()
            var desc = et_input_desc.text.toString()
            var stok = et_input_stok.text.toString()

            when {
                name == "" -> toast("name depan is empty")
                price == "" -> toast("price is empty")
                desc == "" -> toast("desc is empty")
                stok == "" -> toast("stok is empty")
                else -> insert(name, price.toInt(), desc, stok.toInt())
            }
        }


        btn_to_cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }


    private fun insert(name: String, price: Int, desc: String, stok: Int) {
        Completable.fromAction { val data = Cart(name, price, desc, stok)
            userDatabase.userDAO().insert(data)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("Data store successfully")
                    et_input_name.setText("")
                    et_input_price.setText("")
                    et_input_desc.setText("")
                    et_input_stok.setText("")
                }

                override fun onError(e: Throwable) {
                    toast("Data not store")
                }
            })

    }

    private fun toast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}
