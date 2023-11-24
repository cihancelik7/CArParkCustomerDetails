package com.cihancelik.CarParkDetails.HR.hrLocations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    private var hrLocationInfo1 : HrLocationsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_locations_main)

        initView()
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