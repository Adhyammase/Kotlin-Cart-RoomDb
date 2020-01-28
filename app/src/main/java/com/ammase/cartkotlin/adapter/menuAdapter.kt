package com.ammase.cartkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ammase.cartkotlin.R
import com.ammase.cartkotlin.callback
import com.ammase.cartkotlin.data.Cart
import kotlinx.android.synthetic.main.item_list_cart.view.*
import java.text.NumberFormat
import java.util.*

class menuAdapter (private val items: List<Cart>, private val context: Context, val callback: callback) :
    RecyclerView.Adapter<menuAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_cart, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindUI(items[position])




    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val btnAction = view.cardViewCartRoot

        val btn_minus = view.btn_minus
        val btn_plus = view.btn_plus

        fun bindUI(result: Cart) = with(itemView) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

            tv_cart_name.text = result.name
            tv_cart_price.text = formatRupiah.format(result.price)
            tv_cart_desk.text = result.desc
            tv_cart_qty.text = result.qty.toString()


            btn_minus.setOnClickListener {
                callback.onClick(result.id, result.qty - 1)
            }

            btn_plus.setOnClickListener {
                callback.onClick(result.id, result.qty + 1)
            }

        }

    }
}