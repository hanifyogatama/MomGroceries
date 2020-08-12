package com.binar.momgroceries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    var mDb: GroceriesDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mDb = GroceriesDatabase.getInstance(this)

        btnSave.setOnClickListener {
            val objectGroceries = Groceries(
                null,
                etNameGroceries.text.toString(),
                etQty.text.toString().toInt(),
                etUnit.text.toString(),
                etUnitPrice.text.toString().toInt()
            )
            GlobalScope.launch {
                val result = mDb?.groceriesDao()?.addItem(objectGroceries)
                runOnUiThread{
                    if(result!=0.toLong()){
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