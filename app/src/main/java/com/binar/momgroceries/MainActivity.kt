package com.binar.momgroceries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var db : GroceriesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GroceriesDatabase.getInstance(this)?.let{
            db = it
        }

        fabAdd.setOnClickListener {
            val toAddActivity = Intent(this, AddActivity::class.java)
            startActivity(toAddActivity)
        }

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listItem = db?.groceriesDao()?.readAllItem()

            runOnUiThread{
                listItem?.let{
                    val adapter = GroceriesAdapter(it)
                    val layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                    recyclerView.layoutManager =layoutManager
                    recyclerView.adapter= adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GroceriesDatabase.destroyInstance()
    }

}