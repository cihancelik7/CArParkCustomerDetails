package com.cihancelik.CarParkDetails.HR.hrEmployees

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJL
import com.cihancelik.carparkcustomerdetails.R

class HrEmployeesMainActivity : AppCompatActivity() {
    private lateinit var etEmployeeNumber: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etIsActive: EditText
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etBirthDate: EditText
    private lateinit var etNationalId: EditText
    private lateinit var etMartialStatus: EditText
    private lateinit var etGender: EditText
    private lateinit var etAddressId: EditText
    private lateinit var etEmailAddress: EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelperForAddresses: SQLHelperForAddresses
    private lateinit var sqlHelperForHrEmployees:SQLiteHelperForHrEmployees
    private var hrEmpInfo1 : HrEmployeesModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_employees_main)

        initView()
    }

    private fun initView() {
        etEmployeeNumber = findViewById(R.id.hrEmployeesEmployeeNumberTv)
        etStartDate = findViewById(R.id.hrEmployeesStartDateTv)
        etEndDate = findViewById(R.id.hrEmployeesEndDateTv)
        etIsActive = findViewById(R.id.hrEmployeesIsActiveTv)
        etFirstName = findViewById(R.id.hrEmployeesFirstNameTv)
        etLastName = findViewById(R.id.hrEmployeesLastNameTv)
        etBirthDate = findViewById(R.id.hrEmployeesBirthDateTv)
        etNationalId = findViewById(R.id.hrEmployeesNationalIdTv)
        etMartialStatus = findViewById(R.id.hrEmployeesMartialStatusTv)
        etGender = findViewById(R.id.hrEmployeesGenderTv)
        etAddressId = findViewById(R.id.hrEmployeesAddressIdTv)
        etEmailAddress = findViewById(R.id.hrEmployeesEmailAddressTv)
    }
}