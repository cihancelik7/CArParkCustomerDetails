package com.cihancelik.CarParkDetails.HR.hrEmployees

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    private lateinit var sqlHelperForHrEmployees: SQLiteHelperForHrEmployees
    private var hrEmpInfo1: HrEmployeesModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_employees_main)

        initView()

        sqlHelperForHrEmployees = SQLiteHelperForHrEmployees(this)
        sqlHelperForAddresses = SQLHelperForAddresses(this)

        var selectedHrEmpInfo =
            intent.getSerializableExtra("selectedHrEmployeesInfo") as? HrEmployeesModel

        if (selectedHrEmpInfo != null) {
            etEmployeeNumber.setText(selectedHrEmpInfo.employeeNumber.toString())
            etStartDate.setText(selectedHrEmpInfo.startDate)
            etEndDate.setText(selectedHrEmpInfo.endDate)
            etIsActive.setText(selectedHrEmpInfo.isActive)
            etFirstName.setText(selectedHrEmpInfo.firstName)
            etLastName.setText(selectedHrEmpInfo.lastName)
            etBirthDate.setText(selectedHrEmpInfo.birthDate)
            etNationalId.setText(selectedHrEmpInfo.nationalId.toString())
            etMartialStatus.setText(selectedHrEmpInfo.martialStatus)
            etGender.setText(selectedHrEmpInfo.martialStatus)
            etAddressId.setText(selectedHrEmpInfo.addressId.toString())
            etEmailAddress.setText(selectedHrEmpInfo.emailAddress)

            hrEmpInfo1 = selectedHrEmpInfo
        }
        btnAdd.setOnClickListener { addHrEmployee() }

        var goToHrEmployeeViewActivity = Intent(this, HrEmployeesViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrEmployeeViewActivity) }

        var selectedHrEmployeesUpdate =
            intent.getSerializableExtra("selectedHrEmployeesUpdate") as? HrEmployeesModel

        if (selectedHrEmpInfo != null) {
            etEmployeeNumber.setText(selectedHrEmployeesUpdate?.employeeNumber.toString())
            etStartDate.setText(selectedHrEmployeesUpdate?.startDate)
            etEndDate.setText(selectedHrEmployeesUpdate?.endDate)
            etIsActive.setText(selectedHrEmployeesUpdate?.isActive)
            etFirstName.setText(selectedHrEmployeesUpdate?.firstName)
            etLastName.setText(selectedHrEmployeesUpdate?.lastName)
            etBirthDate.setText(selectedHrEmployeesUpdate?.birthDate)
            etNationalId.setText(selectedHrEmployeesUpdate?.nationalId.toString())
            etMartialStatus.setText(selectedHrEmployeesUpdate?.martialStatus)
            etGender.setText(selectedHrEmployeesUpdate?.gender)
            etAddressId.setText(selectedHrEmployeesUpdate?.addressId.toString())
            etEmailAddress.setText(selectedHrEmployeesUpdate?.emailAddress)

        }
        btnUpdate.setOnClickListener { updateHrEmp() }
    }

    private fun updateHrEmp() {
        val employeeNumber = etEmployeeNumber.text.toString()
        var startDate = etStartDate.text.toString()
        var endDate = etEndDate.text.toString()
        var isActive = etIsActive.text.toString()
        var firsName = etFirstName.text.toString()
        var lastName = etLastName.text.toString()
        var birthdate = etBirthDate.text.toString()
        var nationalId = etNationalId.text.toString()
        var martialStatus = etMartialStatus.text.toString()
        var gender = etGender.text.toString()
        var addressId = etAddressId.text.toString()
        var emailAddress = etEmailAddress.text.toString()

        if (hrEmpInfo1 != null){
            val updateHrEmployees = HrEmployeesModel(
                employeeNumber = employeeNumber.toInt(),
                startDate = startDate,
                endDate = endDate,
                isActive = isActive,
                firstName = firsName,
                lastName = lastName,
                birthDate = birthdate,
                nationalId = nationalId.toInt(),
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
        var employeeNumber = etEmployeeNumber.text.toString()
        var startDate = etStartDate.text.toString()
        var endDate = etEndDate.text.toString()
        var isActive = etIsActive.text.toString()
        var firstName = etFirstName.text.toString()
        var lastName = etLastName.text.toString()
        var birthDate = etBirthDate.text.toString()
        var nationalId = etNationalId.text.toString()
        var martialStatus = etMartialStatus.text.toString()
        var gender = etGender.text.toString()
        var addressIdText = etAddressId.text.toString()
        var emailAddress = etEmailAddress.text.toString()

        if (employeeNumber.isEmpty() || startDate.isEmpty() || endDate.isEmpty()
            || isActive.isEmpty() && isActive.equals("Yes") && isActive.equals("No") ||
            firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || nationalId.isEmpty()
            || martialStatus.isEmpty() || addressIdText.isEmpty() || emailAddress.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val enteredAddressId = addressIdText.toInt()
            val addressId = sqlHelperForAddresses.getAddressesById(enteredAddressId)

            if (addressId != null) {
                val hrEmpInfo = HrEmployeesModel(
                    employeeId = 0,
                    employeeNumber = employeeNumber.toInt(),
                    startDate = startDate,
                    endDate = endDate,
                    isActive = isActive,
                    firstName = firstName,
                    lastName = lastName,
                    birthDate = birthDate,
                    nationalId = nationalId.toInt(),
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