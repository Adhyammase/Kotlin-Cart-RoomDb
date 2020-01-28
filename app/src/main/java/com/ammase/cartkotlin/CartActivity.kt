package com.ammase.cartkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ammase.cartkotlin.adapter.menuAdapter
import com.ammase.cartkotlin.data.Cart
import com.ammase.cartkotlin.data.CartDatabase
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), callback {

    private var userDatabase: CartDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        userDatabase = CartDatabase.getInstance(this)
        readData()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add-> {
                finish()
                true
            }
            R.id.action_del -> {
                deleted()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(id: Int, qty: Int) { update(id, qty) }

    private fun readData() {
        userDatabase!!.userDAO().getdataList().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<Cart>>() {
                override fun onSuccess(result: List<Cart>) {
                    val mainAd = menuAdapter(result, this@CartActivity, this@CartActivity)
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = mainAd
                    }
                }

                override fun onError(e: Throwable) {
                    toast("Empty data")
                }
            })
    }

    private fun deleted() {
        Completable.fromAction {
            userDatabase!!.userDAO().deleteAllUser()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("deleted successfully")
                    readData()
                }

                override fun onError(e: Throwable) {
                    toast("deleted invalid")
                }
            })
    }

    private fun update(id: Int, qty: Int) {
        Completable.fromAction {
            userDatabase!!.userDAO().update(qty, id)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    toast("update successfully")
                    readData()

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
