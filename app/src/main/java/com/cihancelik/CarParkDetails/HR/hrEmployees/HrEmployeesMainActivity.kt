package com.cihancelik.CarParkDetails.HR.hrEmployees

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressessModel
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
    private lateinit var sqlHelperForHrEmployees: SQLiteHelperForHrEmployees
    private var hrEmpInfo1: HrEmployeesModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_employees_main)

        initView()

        sqlHelperForHrEmployees = SQLiteHelperForHrEmployees(this)
        sqlHelperForAddresses = SQLHelperForAddresses(this)

        val selectedHrEmployeeInfo = intent.getSerializableExtra("selectedHrEmployeeInfo") as? HrEmployeesModel

        if (selectedHrEmployeeInfo != null) {
            etEmployeeNumber.setText(selectedHrEmployeeInfo.employeeNumber.toString())
            etStartDate.setText(selectedHrEmployeeInfo.startDate)
            etEndDate.setText(selectedHrEmployeeInfo.endDate)
            etIsActive.setText(selectedHrEmployeeInfo.isActive)
            etFirstName.setText(selectedHrEmployeeInfo.firstName)
            etLastName.setText(selectedHrEmployeeInfo.lastName)
            etBirthDate.setText(selectedHrEmployeeInfo.birthDate)
            etNationalId.setText(selectedHrEmployeeInfo.nationalId.toString())
            etMartialStatus.setText(selectedHrEmployeeInfo.martialStatus)
            etGender.setText(selectedHrEmployeeInfo.martialStatus)
            etAddressId.setText(selectedHrEmployeeInfo.addressId.toString())
            etEmailAddress.setText(selectedHrEmployeeInfo.emailAddress)

            hrEmpInfo1 = selectedHrEmployeeInfo
        }
        btnAdd.setOnClickListener { addHrEmployee() }

        var goToHrEmployeeViewActivity = Intent(this, HrEmployeesViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrEmployeeViewActivity) }

        var selectedHrEmployeesUpdate =
            intent.getSerializableExtra("selectedHrEmployeeUpdated") as? HrEmployeesModel

        if (selectedHrEmployeesUpdate != null) {
            etEmployeeNumber.setText(selectedHrEmployeesUpdate.employeeNumber.toString())
            etStartDate.setText(selectedHrEmployeesUpdate.startDate)
            etEndDate.setText(selectedHrEmployeesUpdate.endDate)
            etIsActive.setText(selectedHrEmployeesUpdate.isActive)
            etFirstName.setText(selectedHrEmployeesUpdate.firstName)
            etLastName.setText(selectedHrEmployeesUpdate.lastName)
            etBirthDate.setText(selectedHrEmployeesUpdate.birthDate)
            etNationalId.setText(selectedHrEmployeesUpdate.nationalId.toString())
            etMartialStatus.setText(selectedHrEmployeesUpdate.martialStatus)
            etGender.setText(selectedHrEmployeesUpdate.gender)
            etAddressId.setText(selectedHrEmployeesUpdate.addressId.toString())
            etEmailAddress.setText(selectedHrEmployeesUpdate.emailAddress)

        }
        btnUpdate.setOnClickListener { updateHrEmp() }
    }

    private fun updateHrEmp() {
        val employeeNumber = etEmployeeNumber.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val isActive = etIsActive.text.toString()
        val firsName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val birthdate = etBirthDate.text.toString()
        val nationalId = etNationalId.text.toString()
        val martialStatus = etMartialStatus.text.toString()
        val gender = etGender.text.toString()
        val addressId = etAddressId.text.toString()
        val emailAddress = etEmailAddress.text.toString()

        if (hrEmpInfo1 != null){
            val updateHrEmployees = HrEmployeesModel(
                employeeId = hrEmpInfo1!!.employeeId,
                employeeNumber = employeeNumber.toInt(),
                startDate = startDate,
                endDate = endDate,
                isActive = isActive,
                firstName = firsName,
                lastName = lastName,
                birthDate = birthdate,
                nationalId = nationalId.toLong(),
                martialStatus = martialStatus,
                gender = gender,
                addressId = hrEmpInfo1!!.addressId,
                emailAddress = emailAddress
            )
            val status = sqlHelperForHrEmployees.updateHrEmp(updateHrEmployees)
            if (status > -1){
                Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,HrEmployeesViewActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isUpdate(updateHrEmployees:HrEmployeesModel):Boolean{
        val currentHrEmployee = sqlHelperForHrEmployees.getHrEmpById(updateHrEmployees.employeeId)
        return currentHrEmployee != updateHrEmployees
    }

    private fun addHrEmployee() {
        val employeeNumber = etEmployeeNumber.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val isActive = etIsActive.text.toString()
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val birthDate = etBirthDate.text.toString()
        val nationalId = etNationalId.text.toString()
        val martialStatus = etMartialStatus.text.toString()
        val gender = etGender.text.toString()
        val addressIdText = etAddressId.text.toString()
        val emailAddress = etEmailAddress.text.toString()

        if (employeeNumber.isEmpty() || startDate.isEmpty() ||
             isActive.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || nationalId.isEmpty()
            || martialStatus.isEmpty() || addressIdText.isEmpty() || emailAddress.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val enteredAddressId = addressIdText.toInt()
            val addressId = sqlHelperForAddresses.getAddressesById(enteredAddressId)

            if (addressId != null) {
                val hrEmpInfo = HrEmployeesModel(
                    employeeId = hrEmpInfo1!!.employeeId,
                    employeeNumber = employeeNumber.toInt(),
                    startDate = startDate,
                    endDate = endDate,
                    isActive = isActive,
                    firstName = firstName,
                    lastName = lastName,
                    birthDate = birthDate,
                    nationalId = nationalId.toLong(),
                    martialStatus = martialStatus,
                    gender = gender,
                    addressId = enteredAddressId,
                    emailAddress = emailAddress
                )
                val status: Long = sqlHelperForHrEmployees.insertHrEmployee(hrEmpInfo)
                if (status > -1) {
                    Toast.makeText(this, "Hr Employee Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                } else {
                    Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Hr Employee With the given Address Id does not exist.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearEditText() {
        etEmployeeNumber.setText("")
        etStartDate.setText("")
        etEndDate.setText("")
        etIsActive.setText("")
        etFirstName.setText("")
        etLastName.setText("")
        etBirthDate.setText("")
        etNationalId.setText("")
        etMartialStatus.setText("")
        etGender.setText("")
        etAddressId.setText("")
        etEmailAddress.setText("")
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

        btnAdd = findViewById(R.id.hrEmployeesAddBtn)
        btnView = findViewById(R.id.hrEmployeesViewBtn)
        btnUpdate = findViewById(R.id.hrEmployeesUpdateBtn)
    }
}