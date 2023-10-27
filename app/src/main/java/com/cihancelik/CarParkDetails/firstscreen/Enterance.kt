package com.cihancelik.CarParkDetails.firstscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.cihancelik.CarParkDetails.customer_details.CustomerMainActivity
import com.cihancelik.CarParkDetails.employee_details.EmployeeMainActivity
import com.cihancelik.carparkcustomerdetails.R

class Enterance : AppCompatActivity() {
    private lateinit var satinalmaIv : ImageView
    private lateinit var muhasebeIv : ImageView
    private lateinit var customerServiceIv : ImageView
    private lateinit var ikIv : ImageView

    private lateinit var satinalmaTv : TextView
    private lateinit var muhasebeTv : TextView
    private lateinit var customerServiceTv : TextView
    private lateinit var ikTv : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterance)

        satinalmaIv = findViewById(R.id.satinalmaIv)
        muhasebeIv = findViewById(R.id.muhasebeIv)
        customerServiceIv = findViewById(R.id.customerServicesIv)
        ikIv = findViewById(R.id.ikIv)

        satinalmaTv = findViewById(R.id.satinalmaTv)
        muhasebeTv = findViewById(R.id.muhasebeTv)
        customerServiceTv = findViewById(R.id.customerServicesTv)
        ikTv = findViewById(R.id.ikTv)


        var customerServices = Intent(this,CustomerMainActivity::class.java)
        var employeeServices = Intent(this,EmployeeMainActivity::class.java)


        customerServiceIv.setOnClickListener { startActivity(customerServices) }
        customerServiceTv.setOnClickListener {  startActivity(customerServices)}
        ikTv.setOnClickListener { startActivity(employeeServices) }
        ikIv.setOnClickListener { startActivity(employeeServices) }



    }
}