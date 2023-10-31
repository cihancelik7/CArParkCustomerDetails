package com.cihancelik.CarParkDetails.general.addressesUpdateScreen

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
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


    private lateinit var sqlHelper: SQLHelperForAddresses

    private var addressInfo: AddressessModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses_main_page)

        initView()

        sqlHelper = SQLiteHelperForCarParkDataBase(this) as SQLHelperForAddresses


        btnAdd.setOnClickListener { addAddresses() }



        val selectedAddressInfo =
            intent.getSerializableExtra("selectedAddressesInfo") as? AddressessModel
        if (selectedAddressInfo != null) {
            // edittextlere verileri yerlestir
            etAddress.setText(selectedAddressInfo.address)
            etStartDAte.setText(selectedAddressInfo.startDate)
            etEndDate.setText(selectedAddressInfo.endDAte)
            etCountry.setText(selectedAddressInfo.country)
            etCity.setText(selectedAddressInfo.city)
            etRegion.setText(selectedAddressInfo.region)
            etPostalCode.setText(selectedAddressInfo.postalCode)
            etAddressLine.setText(selectedAddressInfo.addressLine)
            etUpdateDate.setText(selectedAddressInfo.updateDate)
            etCreationDate.setText(selectedAddressInfo.creationDate)

            addressInfo = selectedAddressInfo
        }
        btnUpdate.setOnClickListener { updateAddresses() }

    }
    fun onUpgrade(db:SQLiteDatabase?,oldVersion:Int,newVersion:Int){
        if (oldVersion < 2){
            // bu surume kadar olan tabloyu guncelle
            db?.execSQL("ALTER TABLE table_carparkdatabase ADD COLUMN START_DATE TEXT")
        }
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
            val addressInfo = AddressessModel(
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
            val status =
                Toast.makeText(this, "Address added successfully", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateAddresses() {
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

        // guncelleme islemi burada gerceklesecek
        if (addressInfo != null) {
            val updatedAddresses = AddressessModel(
                addressId = addressInfo!!.addressId,
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
            val status = sqlHelper.updateAddresses(updatedAddresses)
            if (status > -1){
                Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                addressInfo = updatedAddresses
                val intent = Intent(this,AddressesMainPage::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "update failed...", Toast.LENGTH_SHORT).show()
            }
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
