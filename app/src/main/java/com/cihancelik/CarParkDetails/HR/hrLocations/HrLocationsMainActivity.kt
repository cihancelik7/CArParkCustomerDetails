package com.cihancelik.CarParkDetails.HR.hrLocations

import android.content.Intent
import android.net.wifi.WifiManager.LocalOnlyHotspotCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrLocations
import com.cihancelik.carparkcustomerdetails.R

class HrLocationsMainActivity : AppCompatActivity() {
    private lateinit var etLocationName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etAddressId: EditText
    private lateinit var etNaceCode: EditText
    private lateinit var etDangerClass: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelperForAddresses: SQLHelperForAddresses
    private lateinit var sqlHelperForHrLocations: SQLiteHelperForHrLocations
    private var hrLocationInfo1: HrLocationsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_locations_main)

        initView()

        sqlHelperForHrLocations = SQLiteHelperForHrLocations(this)
        sqlHelperForAddresses = SQLHelperForAddresses(this)

        val selectedHrLocationInfo =
            intent.getSerializableExtra("selectedHrLocationInfo") as? HrLocationsModel

        if (selectedHrLocationInfo != null) {
            etLocationName.setText(selectedHrLocationInfo.locationName)
            etStartDate.setText(selectedHrLocationInfo.startDate)
            etEndDate.setText(selectedHrLocationInfo.endDate)
            etAddressId.setText(selectedHrLocationInfo.addressId.toString())
            etNaceCode.setText(selectedHrLocationInfo.naceCode.toString())
            etDangerClass.setText(selectedHrLocationInfo.dangerClass)
            etUpdateDate.setText(selectedHrLocationInfo.updateDate)
            etCreationDate.setText(selectedHrLocationInfo.creationDate)

            hrLocationInfo1 = selectedHrLocationInfo
        }
        btnAdd.setOnClickListener { addHrLocation() }

        var goToHrLocationViewActivity = Intent(this, HrLocationsViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrLocationViewActivity) }

        var selectedHrLocationUpdate =
            intent.getSerializableExtra("selectedHrLocationUpdated") as? HrLocationsModel

        if (selectedHrLocationUpdate != null) {
            etLocationName.setText(selectedHrLocationUpdate.locationName)
            etStartDate.setText(selectedHrLocationUpdate.startDate)
            etEndDate.setText(selectedHrLocationUpdate.endDate)
            etAddressId.setText(selectedHrLocationUpdate.addressId.toString())
            etNaceCode.setText(selectedHrLocationUpdate.naceCode.toString())
            etDangerClass.setText(selectedHrLocationUpdate.dangerClass)
            etUpdateDate.setText(selectedHrLocationUpdate.updateDate)
            hrLocationInfo1 = selectedHrLocationUpdate
        }
        btnUpdate.setOnClickListener { updateLocation() }
    }

    private fun updateLocation() {
        val locationName = etLocationName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val addressId = etAddressId.text.toString()
        val naceCode = etNaceCode.text.toString()
        val dangerClass = etDangerClass.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (hrLocationInfo1 != null) {
            val updateHrLocation = HrLocationsModel(
                locationId = hrLocationInfo1!!.locationId,
                locationName = locationName,
                startDate = startDate,
                endDate = endDate,
                addressId = hrLocationInfo1!!.addressId,
                naceCode = naceCode.toInt(),
                dangerClass = dangerClass,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdate = isUpdate(updateHrLocation)
            if (isUpdate) {
                val status = sqlHelperForHrLocations.updateHrLocation(updateHrLocation)
                if (status > -1) {
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HrLocationsViewActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdate(updateHrLocation: HrLocationsModel): Boolean {
        val currentHrLocation =
            sqlHelperForHrLocations.getHrLocationById(updateHrLocation.locationId)
        return currentHrLocation != updateHrLocation

    }

    private fun addHrLocation() {
        val locationName = etLocationName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val addressIdText = etAddressId.text.toString()
        val naceCode = etNaceCode.text.toString()
        val dangerClass = etDangerClass.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (locationName.isEmpty() || startDate.isEmpty() || addressIdText.isEmpty()
            || naceCode.isEmpty() || dangerClass.isEmpty() || updateDate.isEmpty()
            || creationDate.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            //   val enteredAddressIdtoStr = sqlHelperForAddresses.getAddressNameById(addressIdText.toInt())
            //  sqlHelperForAddresses.getAddressNameById(addressIdText.toInt())
            val enteredAddressId = addressIdText.toInt()
            val addressId = sqlHelperForAddresses.getAddressesById(enteredAddressId)


            if (addressId != null) {
                val hrLocationInfo = HrLocationsModel(
                    locationId = 0,
                    locationName = locationName,
                    startDate = startDate,
                    endDate = endDate,
                    addressId = enteredAddressId,
                    naceCode = naceCode.toInt(),
                    dangerClass = dangerClass,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                val status: Long = sqlHelperForHrLocations.insertHrLocation(hrLocationInfo)
                if (status > -1) {
                    Toast.makeText(this, "Hr Location Added", Toast.LENGTH_SHORT).show()
                    crearEditText()
                } else {
                    Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Hr Location With the Given Address Id does not exists.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun crearEditText() {
        etLocationName.setText("")
        etStartDate.setText("")
        etEndDate.setText("")
        etAddressId.setText("")
        etNaceCode.setText("")
        etDangerClass.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etLocationName.requestFocus()
    }

    private fun initView() {
        etLocationName = findViewById(R.id.hrLocationNameTv)
        etStartDate = findViewById(R.id.hrLocationStartDateTv)
        etEndDate = findViewById(R.id.hrLocationEndDateTv)
        etAddressId = findViewById(R.id.hrLocationAddressIdTv)
        etNaceCode = findViewById(R.id.hrLocationNaceCodeTv)
        etDangerClass = findViewById(R.id.hrLocationDangerClassTv)
        etUpdateDate = findViewById(R.id.hrLocationUpdateDateTv)
        etCreationDate = findViewById(R.id.hrLocationCreationDateTv)

        btnAdd = findViewById(R.id.hrLocationAddBtn)
        btnView = findViewById(R.id.hrLocationViewBtn)
        btnUpdate = findViewById(R.id.hrLocationUpdateBtn)

    }
}