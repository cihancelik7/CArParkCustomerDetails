package com.cihancelik.CarParkDetails.employee_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForEmployee
import com.cihancelik.carparkcustomerdetails.R

class EmployeeMainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDepartment: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelperForEmployee

    private var emp: EmployeeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_main)

        initView()

        sqLiteHelper = SQLiteHelperForEmployee(this)

        btnAdd.setOnClickListener { addEmployee() }
        btnView.setOnClickListener {
            val intent = Intent(this, EmployeeViewPage::class.java)
            startActivity(intent)
        }
        val selectedEmployee = intent.getSerializableExtra("selectedEmployee") as? EmployeeModel
        if (selectedEmployee != null){
            etName.setText(selectedEmployee.name)
            etLastName.setText(selectedEmployee.lastName)
            etDepartment.setText(selectedEmployee.department)
            etEmail.setText(selectedEmployee.email)
            etAddress.setText(selectedEmployee.address)
            emp = selectedEmployee
        }
        btnUpdate.setOnClickListener { updateEmployee() }
    }

    private fun updateEmployee() {
        val name = etName.text.toString()
        val lastname = etLastName.text.toString()
        val department = etDepartment.text.toString()
        val email = etEmail.text.toString()
        val address = etAddress.text.toString()

        if (emp != null){
            val updatedEmployee = EmployeeModel(
                id = emp!!.id,
                name = name,
                lastName = lastname,
                department = department,
                email = email,
                address = address
            )
            val status = sqLiteHelper.updateEmployee(updatedEmployee)
            if (status > -1){
                Toast.makeText(this, "Updated Successful", Toast.LENGTH_SHORT).show()
                emp = updatedEmployee
                val intent = Intent(this, EmployeeMainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Updated failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addEmployee() {
        val name = etName.text.toString()
        val lastname = etLastName.text.toString()
        val department = etDepartment.text.toString()
        val email = etEmail.text.toString()
        val address = etAddress.text.toString()

        if (name.isEmpty() || lastname.isEmpty() || department.isEmpty() ||
            email.isEmpty() || address.isEmpty()){

            Toast.makeText(this, "Please Enter Reqirement Field", Toast.LENGTH_SHORT).show()
        }else if (!(department.equals("MUHASEBE") || department.equals("SATINALMA") || department.equals("IK"))){
            Toast.makeText(this, "you should write 'MUHASEBE , SATINALMA or IK'", Toast.LENGTH_SHORT)
                .show()
        }else{
            val emp = EmployeeModel(
                name = name,
                lastName = lastname,
                department = department,
                email = email,
                address = address
            )
            val status  = sqLiteHelper.insertEmployee(emp)
            // check insert employee success or not success
            if (status > -1){
                Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etName.setText("")
        etLastName.setText("")
        etDepartment.setText("")
        etEmail.setText("")
        etAddress.setText("")

        etName.requestFocus()
    }

    private fun initView() {
        etName = findViewById(R.id.etEmpName)
        etLastName = findViewById(R.id.etEmpLastName)
        etDepartment = findViewById(R.id.etEmpDepartment)
        etEmail = findViewById(R.id.etEmpEmail)
        etAddress = findViewById(R.id.etEmpAddress)
        btnAdd = findViewById(R.id.btnEmpAdd)
        btnView = findViewById(R.id.btnEmpView)
        btnUpdate = findViewById(R.id.btnEmpUpdate)

    }
}