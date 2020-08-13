package com.binar.momgroceries

import android.app.AlertDialog
import android.graphics.Color
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextSwitcher
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.groceries_layout.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceriesAdapter(private val listGrocery: List<Groceries>) :
    RecyclerView.Adapter<GroceriesAdapter.ViewHolder>() {
    private lateinit var db: GroceriesDatabase

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val cb = itemView.cbBtn
        private val cardView = itemView.cardView

        fun cbCheck(groceries: Groceries) {
            if (groceries.isChecked) {
                cb.isChecked = true
                cardView.setCardBackgroundColor(Color.parseColor("#f5b776"))
            }
        }

        fun count(groceries: Groceries): Int {
            val resultSum = groceries.quantity * groceries.unitPrice
            return resultSum
        }

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
        holder.itemView.etNama.setText(listGrocery[position].name)
        holder.itemView.etQty.setText(listGrocery[position].quantity.toString())
        holder.itemView.etUnit.setText(listGrocery[position].unit)
        holder.itemView.etUnitPrice.setText(listGrocery[position].unitPrice.toString())


        val total = holder.count(listGrocery[position])
        holder.itemView.tvTotalPrice.text = total.toString()

        holder.itemView.tvUnitPrice.text = "/ ${listGrocery[position].unit}"

        holder.cbCheck(listGrocery[position])

        GroceriesDatabase.getInstance(holder.itemView.context)?.let {
            db = it
        }

        holder.itemView.ivDelete.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Ya") { p0, p1 ->
                GlobalScope.launch {
                    val result = db.groceriesDao().deleteItem(listGrocery[position])
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (result > 0) {
                            Toast.makeText(
                                it.context,
                                "Data ${listGrocery[position].name} berhasil ditambahkan",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                it.context, "Data ${listGrocery[position].name} gagal di tambahkan",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    (holder.itemView.context as MainActivity).fetchData()
                }

            }
                .setNegativeButton("Tidak") { p0, p1 -> p0.dismiss() }
                .setMessage("Apakah Anda Yakin ingin menghapus data ${listGrocery[position].name}")
                .setTitle("Konfirmasi Hapus").create().show()
        }


        val cb = holder.itemView.cbBtn

        cb.setOnClickListener {
            if (cb.isChecked) {
                listGrocery[position].apply {
                    isChecked = true
                }
            } else {
                listGrocery[position].apply {
                    isChecked = false
                }
            }

            GlobalScope.launch {
                val rowUpdated = db.groceriesDao().updateItem(listGrocery[position])
                (holder.itemView.context as MainActivity).fetchData()
            }

        }

        holder.itemView.etNama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.groceriesDao().updateItem(listGrocery[position])
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listGrocery[position].apply {
                    name = holder.itemView.etNama.text.toString()
                }
            }

        })

        holder.itemView.etUnit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.groceriesDao().updateItem(listGrocery[position])
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listGrocery[position].apply {
                    unit = holder.itemView.etUnit.text.toString()
                }
            }

        })

        holder.itemView.etQty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.groceriesDao().updateItem(listGrocery[position])
                }

                val count = holder.count(listGrocery[position])
                holder.itemView.tvTotalPrice.text = count.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var txtQty = holder.itemView.etQty.text.toString()
                if (txtQty == "") {
                    txtQty = "0"
                }
                listGrocery[position].apply {
                    quantity = txtQty.toInt()
                }
            }
        })

        holder.itemView.etUnitPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.groceriesDao().updateItem(listGrocery[position])
                }

                val count = holder.count(listGrocery[position])
                holder.itemView.tvTotalPrice.text = count.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var txtUnitPrice = holder.itemView.etUnitPrice.text.toString()
                if (txtUnitPrice == "") {
                    txtUnitPrice = "0"
                }

                listGrocery[position].apply {
                    unitPrice = txtUnitPrice.toInt()
                }
            }
        })

    }

}


