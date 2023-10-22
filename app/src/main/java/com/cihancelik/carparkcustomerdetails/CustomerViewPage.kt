package com.cihancelik.carparkcustomerdetails

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    private var cst: CustomerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_view_page)

        sqLiteHelper = SQLiteHelper(this)

        recyclerView = findViewById(R.id.customerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        getCustomers()

        adapter?.setOnClickItem { customer ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("selectedCustomer", customer)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // "MainActivity" sayfasından güncellenmiş veriyi al
        val updatedCustomer = intent.getSerializableExtra("updatedCustomer") as? CustomerModel
        if (updatedCustomer != null) {
            // Eğer güncellenmiş veri varsa, "cst" değişkenini güncelle
            cst = updatedCustomer
            // EditText'lere güncellenmiş verileri yerleştir
            etName.setText(updatedCustomer.name)
            etLastName.setText(updatedCustomer.lastName)
            etEmail.setText(updatedCustomer.email)
            etPhone.setText(updatedCustomer.phone)
            etAddress.setText(updatedCustomer.address)
            etCity.setText(updatedCustomer.city)
            etCarPlate.setText(updatedCustomer.carplate)
        }
    }

    fun getCustomers() {
        val customerList = sqLiteHelper.getAllCustomers()
        adapter.addItems(customerList)
    }
}
