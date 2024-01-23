package com.cihancelik.CarParkDetails.HR.hrSalaries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrSalaries
import com.cihancelik.carparkcustomerdetails.R

class HrSalariesMainActivity : AppCompatActivity() {
    private lateinit var etEmpId : EditText
    private lateinit var etAmount : EditText
    private lateinit var etStartDate : EditText
    private lateinit var etEndDate : EditText
    private lateinit var etUpdateDate : EditText
    private lateinit var etCreationDate :EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqliteHelperForHrSalaries:SQLiteHelperForHrSalaries
    private lateinit var sqLiteHelperForHrEmployees: SQLiteHelperForHrEmployees
    private var hrSalaryInfo1 : HrSalariesModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_salaries_main)
        initView()
        sqliteHelperForHrSalaries = SQLiteHelperForHrSalaries(this)
        sqLiteHelperForHrEmployees = SQLiteHelperForHrEmployees(this)

        val selectedHrsalaryInfo = intent.getSerializableExtra("selectedHrSalaryInfo") as? HrSalariesModel

        if (selectedHrsalaryInfo!=null){
            etEmpId.setText(selectedHrsalaryInfo.employeeId.toString())
            etAmount.setText(selectedHrsalaryInfo.amount.toString())
            etStartDate.setText(selectedHrsalaryInfo.startDate)
            etEndDate.setText(selectedHrsalaryInfo.endDate)
            etUpdateDate.setText(selectedHrsalaryInfo.updateDate)
            etCreationDate.setText(selectedHrsalaryInfo.creationDate)

            hrSalaryInfo1 = selectedHrsalaryInfo
        }
        btnAdd.setOnClickListener { addHrSalary() }
    }

    private fun addHrSalary() {
        val empIdText = etEmpId.text.toString()
        val amount = etAmount.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (empIdText.isEmpty()||amount.isEmpty()||startDate.isEmpty()||updateDate.isEmpty()||creationDate.isEmpty()){
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val enteredEmpId = empIdText.toInt()
            val empId = sqLiteHelperForHrEmployees.getHrEmpById(enteredEmpId)

            if (empId!=null){
                val hrSalaryInfo = HrSalariesModel(
                    salaryId = 0,
                    employeeId = enteredEmpId,
                    amount = amount.toInt(),
                    startDate = startDate,
                    endDate = endDate,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                val status : Long = sqliteHelperForHrSalaries.insertHrSalaries(hrSalaryInfo)
                if (status>-1){
                    Toast.makeText(this, "Hr Salary Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                }
            }
        }
    }

    private fun clearEditText() {
        etEmpId.setText("")
        etAmount.setText("")
        etStartDate.setText("")
        etEndDate.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etEmpId.requestFocus()
    }

    private fun initView() {
        etEmpId = findViewById(R.id.hrSalaryEmployeeIdTv)
        etAmount = findViewById(R.id.hrSalaryAmountTv)
        etStartDate = findViewById(R.id.hrSalaryStartDateTv)
        etEndDate = findViewById(R.id.hrSalaryEndDateTv)
        etUpdateDate = findViewById(R.id.hrSalaryUpdateDateTv)
        etCreationDate = findViewById(R.id.hrSalaryCreationDateTv)

        btnAdd = findViewById(R.id.hrSalaryAddBtn)
        btnView = findViewById(R.id.hrSalaryViewBtn)
        btnUpdate = findViewById(R.id.hrSalaryUpdateBtn)
    }
}