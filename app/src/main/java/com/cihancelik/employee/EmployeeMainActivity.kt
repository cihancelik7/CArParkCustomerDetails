package com.cihancelik.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.cihancelik.SQL.SQLiteHelperForCustomer
import com.cihancelik.carparkcustomerdetails.R

class EmployeeMainActivity : AppCompatActivity() {
    private lateinit var etName:EditText
    private lateinit var etLastName:EditText
    private lateinit var etDepartment:EditText
    private lateinit var etEmail:EditText
    private lateinit var etAddress:EditText

    private lateinit var sqLiteHelper : SQLiteHelperForCustomer

    private var emp: EmployeeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_main)
    }

    private fun initView(){

    }
}