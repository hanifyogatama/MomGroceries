package com.binar.momgroceries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var db : GroceriesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        GroceriesDatabase.getInstance(this)?.let{
            db = it
        }

        btnSave.setOnClickListener {
            val objectGroceries = Groceries(
                null,
                etNameGroceries.text.toString(),
                etQty.text.toString().toInt(),
                etUnit.text.toString(),
                etUnitPrice.text.toString().toInt(),
                false
            )

            GlobalScope.launch {
                val result = db.groceriesDao().addItem(objectGroceries)
                runOnUiThread{
                    if(result > 0){
                        // success
                        Toast.makeText(this@AddActivity,"Sukses menambahkan data ${objectGroceries.name}",
                            Toast.LENGTH_LONG).show()
                    } else{
                        Toast.makeText(this@AddActivity,"gagal menambahkan data ${objectGroceries.name}",
                            Toast.LENGTH_LONG).show()
                    }
                    finish()
                }

            }

        }
    }
}