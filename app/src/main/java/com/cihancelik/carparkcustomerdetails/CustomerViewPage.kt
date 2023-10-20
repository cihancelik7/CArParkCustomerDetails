package com.cihancelik.carparkcustomerdetails

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomerViewPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = CustomerAdapter()
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etAddress: EditText
    private lateinit var etCity: EditText
    private lateinit var etCarPlate: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_view_page)

        sqLiteHelper = SQLiteHelper(this)

        recyclerView = findViewById(R.id.customerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        getCustomers()





    }
    private fun getCustomers() {
        val customerList = sqLiteHelper.getAllCustomers()
        Log.e("pppp", "${customerList.size}")

        // we need to display data in recyclerview
        adapter.addItems(customerList)



    }

}
