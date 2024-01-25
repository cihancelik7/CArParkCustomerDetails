package com.cihancelik.CarParkDetails.general.addressesUpdateScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.firstscreen.Enterance
import com.cihancelik.carparkcustomerdetails.R

class AddressesMainScreen : AppCompatActivity() {
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

        sqlHelper = SQLHelperForAddresses(this) // SQLiteHelperForCarParkDataBase dönüşümü kaldırıldı

        btnAdd.setOnClickListener { addAddresses() }

        val selectedAddressInfo =
            intent.getSerializableExtra("selectedAddressesInfo") as? AddressessModel
        if (selectedAddressInfo != null) {
            // edittextlere verileri yerleştir
            etAddress.text = selectedAddressInfo.address
            etStartDAte.text = selectedAddressInfo.startDate
            etEndDate.text = selectedAddressInfo.endDAte
            etCountry.text = selectedAddressInfo.country
            etCity.text = selectedAddressInfo.city
            etRegion.text = selectedAddressInfo.region
            etPostalCode.text = selectedAddressInfo.postalCode
            etAddressLine.text = selectedAddressInfo.addressLine
            etUpdateDate.text = selectedAddressInfo.updateDate
            etCreationDate.text = selectedAddressInfo.creationDate

            addressInfo = selectedAddressInfo
        }



        var goToAddressesViewPage = Intent(this, AddressViewScreen::class.java)
        btnView.setOnClickListener { startActivity(goToAddressesViewPage) }

        val selectedAddressesUpdate =
            intent.getSerializableExtra("selectedAddressesUpdate") as? AddressessModel
        if (selectedAddressesUpdate != null) {
            etAddress.text = selectedAddressesUpdate.address
            etStartDAte.text = selectedAddressesUpdate.startDate
            etEndDate.text = selectedAddressesUpdate.endDAte
            etCountry.text = selectedAddressesUpdate.country
            etCity.text = selectedAddressesUpdate.city
            etRegion.text = selectedAddressesUpdate.region
            etPostalCode.text = selectedAddressesUpdate.postalCode
            etAddressLine.text = selectedAddressesUpdate.addressLine
            etUpdateDate.text = selectedAddressesUpdate.updateDate
            etCreationDate.text = selectedAddressesUpdate.creationDate
            addressInfo = selectedAddressesUpdate
        }
        btnUpdate.setOnClickListener {
            updateAddresses()
        }
    }


    // onUpdate fonksiyonu burada yer alıyor

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

        if (address.isEmpty() || startDate.isEmpty() || country.isEmpty() ||
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
            val status: Long = sqlHelper.insertAddress(addressInfo)
            // check insert success or not success
            if (status > -1) {
                Toast.makeText(this, "Address Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }

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
            val isUpdated = isDataUpdated(updatedAddresses)
            if (isUpdated) {
                val status = sqlHelper.updateAddresses(updatedAddresses)
                if (status > -1) {
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    // MainScreen'e dönüş yap
                    val intent = Intent(this, AddressesMainScreen::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No changes were made.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isDataUpdated(updatedAddresses: AddressessModel): Boolean {
        // Mevcut verileri al
        val currentAddresses = sqlHelper.getAddressesById(updatedAddresses.addressId)

        // Yeni veriler ile mevcut verileri karşılaştır
        return currentAddresses != updatedAddresses
    }

    private fun clearEditText() {
        etAddress.text = ""
        etStartDAte.text = ""
        etEndDate.text = ""
        etCountry.text = ""
        etCity.text = ""
        etRegion.text = ""
        etPostalCode.text = ""
        etAddressLine.text = ""
        etUpdateDate.text = ""
        etCreationDate.text = ""

        etAddress.requestFocus()
    }

    private fun initView() {
        etAddress = findViewById(R.id.addressName)
        etStartDAte = findViewById(R.id.addressStartDate)
        etEndDate = findViewById(R.id.addressEndDate)
        etCountry = findViewById(R.id.addressCountry)
        etCity = findViewById(R.id.tvAddressesCity)
        etRegion = findViewById(R.id.addressRegion)
        etPostalCode = findViewById(R.id.addressPostalCode)
        etAddressLine = findViewById(R.id.addressLine)
        etUpdateDate = findViewById(R.id.addressUpdateDate)
        etCreationDate = findViewById(R.id.tvAddressesCreationDate)

        btnAdd = findViewById(R.id.addBtnAddresses)
        btnView = findViewById(R.id.viewBtnAddresses)
        btnUpdate = findViewById(R.id.btnUpdateAddresses)
    }
}
