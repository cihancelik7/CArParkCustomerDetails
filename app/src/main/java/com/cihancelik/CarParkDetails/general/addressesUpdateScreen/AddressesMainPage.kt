package com.cihancelik.CarParkDetails.general.addressesUpdateScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.carparkcustomerdetails.R

class AddressesMainPage : AppCompatActivity() {
    private lateinit var etAddress: TextView
    private lateinit var etStartDAte: TextView
    private lateinit var etEndDate: TextView
    private lateinit var etCountry: TextView
    private lateinit var etCity: TextView
    private lateinit var etRegion: TextView
    private lateinit var etPostalCode: TextView
    private lateinit var etAddressLine: TextView
    private lateinit var etUpdateDate: TextView
    private lateinit var etCreationDate: TextView

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button




    private lateinit var sqlHelper: SQLiteHelperForCarParkDataBase

    private var addresses: AddressessModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses_main_page)

        initView()

        sqlHelper = SQLiteHelperForCarParkDataBase(this)


        btnAdd.setOnClickListener { addAddresses() }




    }

    private fun addAddresses() {
        val address = etAddress.text.toString()
        val startDate = etStartDAte.text.toString()
        val endDate = etEndDate.text.toString()
        val country = etCountry.text.toString()
        val city = etCity.text.toString()
        val region = etRegion.text.toString()
        val postalCode = etPostalCode.text.toString()
        val addressLine = etAddressLine.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (address.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || country.isEmpty() ||
            city.isEmpty() || region.isEmpty() || postalCode.isEmpty() || addressLine.isEmpty() ||
            updateDate.isEmpty() || creationDate.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val addresses = AddressessModel(
                address = address,
                startDate = startDate,
                endDAte = endDate,
                country = country,
                city = city,
                region = region,
                postalCode = postalCode,
                addressLine = addressLine,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val dbHelper = SQLHelperForAddresses(this)
            dbHelper.createAddress(addresses)
            clearEditText()
            Toast.makeText(this, "Address added successfully", Toast.LENGTH_SHORT).show()
        }

    }

    private fun clearEditText() {
        etAddress.setText("")
        etStartDAte.setText("")
        etEndDate.setText("")
        etCountry.setText("")
        etCity.setText("")
        etRegion.setText("")
        etPostalCode.setText("")
        etAddressLine.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etAddress.requestFocus()
    }

    private fun initView() {
        etAddress = findViewById(R.id.tvAddresses)
        etStartDAte = findViewById(R.id.tvStartDateAddessess)
        etEndDate = findViewById(R.id.tvEndDateAddresses)
        etCountry = findViewById(R.id.tvCountryAddresses)
        etCity = findViewById(R.id.tvAddressesCity)
        etRegion = findViewById(R.id.tvRegionAddresses)
        etPostalCode = findViewById(R.id.tvPostalCodeAddresses)
        etAddressLine = findViewById(R.id.tvAddressLineAddresses)
        etUpdateDate = findViewById(R.id.tvUpdateDateAddresses)
        etCreationDate = findViewById(R.id.tvAddressesCreationDate)

        btnAdd = findViewById(R.id.addBtnAddresses)
        btnView = findViewById(R.id.viewBtnAddresses)
        btnUpdate = findViewById(R.id.btnUpdateAddresses)


    }
}
