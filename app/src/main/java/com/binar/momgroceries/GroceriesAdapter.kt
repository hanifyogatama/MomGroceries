package com.binar.momgroceries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.groceries_layout.view.*

class GroceriesAdapter(private val listGrocery: List<Groceries>) :
    RecyclerView.Adapter<GroceriesAdapter.ViewHolder>() {
    private lateinit var db: GroceriesDatabase

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.groceries_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listGrocery.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvID.text = listGrocery[position].id.toString()
        holder.itemView.etNama.setText(listGrocery[position].name)
        holder.itemView.etQty.setText(listGrocery[position].quantity.toString())
        holder.itemView.etUnit.setText(listGrocery[position].unit)
        holder.itemView.etUnitPrice.setText(listGrocery[position].unitPrice.toString())

        GroceriesDatabase.getInstance(holder.itemView.context)?.let {
            db = it
        }

    }

}