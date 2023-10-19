package com.cihancelik.carparkcustomerdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etAddress: EditText
    private lateinit var etCity: EditText
    private lateinit var etCarPlate: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        sqLiteHelper = SQLiteHelper(this)


        btnAdd.setOnClickListener { addCustomer() }
        btnView.setOnClickListener {  }

    }

    private fun addCustomer() {
        val name = etName.text.toString()
        val lastName = etLastName.text.toString()
        val email = etEmail.text.toString()
        val phone = etPhone.text.toString()
        val address = etAddress.text.toString()
        val city = etCity.text.toString()
        val carPlate = etCarPlate.text.toString()

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()
            || address.isEmpty() || city.isEmpty() || carPlate.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val cst = CustomerModel(
                name = name,
                lastName = lastName,
                email = email,
                phone = phone,
                address = address,
                city = city,
                carplate = carPlate
            )
            val status = sqLiteHelper.insertCustomer(cst)
            // check insert success or not success
            if (status > -1) {
                Toast.makeText(this, "Customer Added", Toast.LENGTH_SHORT).show()
                clearEditText()

            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearEditText() {
        etName.setText("")
        etLastName.setText("")
        etEmail.setText("")
        etPhone.setText("")
        etAddress.setText("")
        etCity.setText("")
        etCarPlate.setText("")

        etName.requestFocus()


    }

    private fun initView() {

        etName = findViewById(R.id.cstName)
        etLastName = findViewById(R.id.cstLastName)
        etEmail = findViewById(R.id.cstEmail)
        etPhone = findViewById(R.id.cstPhone)
        etAddress = findViewById(R.id.cstAddress)
        etCity = findViewById(R.id.cstCity)
        etCarPlate = findViewById(R.id.cstCarNumberPlate)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)

    }
}