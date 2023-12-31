package com.cihancelik.CarParkDetails.HR.hrEmpAssigments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.credentials.BeginCreateCredentialRequest
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmpAssigments
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrPositions
import com.cihancelik.carparkcustomerdetails.R

class HrEmpAssigmentsMainActivity : AppCompatActivity() {
    private lateinit var etEmpId: EditText
    private lateinit var etPositionId: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqliteHelperForEmp: SQLiteHelperForHrEmployees
    private lateinit var sqliteHelperForPosition: SQLiteHelperForHrPositions
    private lateinit var sqLiteHelperForHrEmpAssigments: SQLiteHelperForHrEmpAssigments
    private var hrEmpAssigmentInfo1: HrEmpAssigmentsModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_emp_assigments_main)

        initView()
        sqLiteHelperForHrEmpAssigments = SQLiteHelperForHrEmpAssigments(this)
        sqliteHelperForEmp = SQLiteHelperForHrEmployees(this)
        sqliteHelperForPosition = SQLiteHelperForHrPositions(this)

        val selectedHrEmpAssigmentsInfo =
            intent.getSerializableExtra("selectedHrEmpAssignmentInfo") as? HrEmpAssigmentsModel

        if (selectedHrEmpAssigmentsInfo != null) {
            etEmpId.setText(selectedHrEmpAssigmentsInfo.employeeId.toString())
            etPositionId.setText(selectedHrEmpAssigmentsInfo.positionId.toString())
            etStartDate.setText(selectedHrEmpAssigmentsInfo.startDate)
            etEndDate.setText(selectedHrEmpAssigmentsInfo.endDate)
            etUpdateDate.setText(selectedHrEmpAssigmentsInfo.updateDate)
            etCreationDate.setText(selectedHrEmpAssigmentsInfo.creationDate)

            hrEmpAssigmentInfo1 = selectedHrEmpAssigmentsInfo
        }
        btnAdd.setOnClickListener { addHrEmpAssignment() }
        var goToHrEmpASsigmentViewActivity = Intent(this,HrEmpAssigmentsViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrEmpASsigmentViewActivity) }

        var selectedHrEmpAssigmentUpdate = intent.getSerializableExtra("selectedHrEmpAssignmentUpdated") as? HrEmpAssigmentsModel

        if (selectedHrEmpAssigmentUpdate != null){
            etEmpId.setText(selectedHrEmpAssigmentUpdate.employeeId.toString())
            etPositionId.setText(selectedHrEmpAssigmentUpdate.positionId.toString())
            etStartDate.setText(selectedHrEmpAssigmentUpdate.startDate)
            etEndDate.setText(selectedHrEmpAssigmentUpdate.endDate)
            etUpdateDate.setText(selectedHrEmpAssigmentUpdate.updateDate)
            etCreationDate.setText(selectedHrEmpAssigmentUpdate.creationDate)
            hrEmpAssigmentInfo1 = selectedHrEmpAssigmentUpdate
        }
        btnUpdate.setOnClickListener { updateHrEmpAssigment() }
    }

    private fun updateHrEmpAssigment() {
        val empId = etEmpId.text.toString()
        val positionId = etPositionId.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (hrEmpAssigmentInfo1!=null){
            val updatedHrEmpAssigment = HrEmpAssigmentsModel(
                assigmentId = hrEmpAssigmentInfo1!!.assigmentId,
                employeeId = hrEmpAssigmentInfo1!!.employeeId,
                positionId = hrEmpAssigmentInfo1!!.positionId,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdate = isUpdate(updatedHrEmpAssigment)
            if (isUpdate){
                val status = sqLiteHelperForHrEmpAssigments.updateHrEmpAssignment(updatedHrEmpAssigment)
                if (status>-1){
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HrEmpAssigmentsViewActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdate(updatedHrEmpAssigment: HrEmpAssigmentsModel): Boolean {
        val currentHrEmpAssignment = sqLiteHelperForHrEmpAssigments.getHrEmpAssignmentById(updatedHrEmpAssigment.assigmentId)
        return currentHrEmpAssignment != updatedHrEmpAssigment

    }

    private fun addHrEmpAssignment() {
        val empIdText = etEmpId.text.toString()
        val positionIdText = etPositionId.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (empIdText.isEmpty() || positionIdText.isEmpty() || startDate.isEmpty()
            || updateDate.isEmpty() || creationDate.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val enteredEmpId = empIdText.toInt()
            val empId = sqliteHelperForEmp.getHrEmpById(enteredEmpId)

            val enteredPositionId = positionIdText.toInt()
            val positionId = sqliteHelperForPosition.getPositionById(enteredPositionId)

            if (empId != null && positionId != null){
                val hrEmpAsignmentInfo = HrEmpAssigmentsModel(
                    assigmentId = 0,
                    employeeId = enteredEmpId,
                    positionId = enteredPositionId,
                    startDate = startDate,
                    endDate = endDate,
                    updateDate = updateDate,
                    creationDate =creationDate
                )
                val status  : Long = sqLiteHelperForHrEmpAssigments.insertHrEmpAssigment(hrEmpAsignmentInfo)
                if (status > -1){
                    Toast.makeText(this, "Hr Employee Assignment Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                }else{
                    Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(
                    this,
                    "Hr Employee Assignment with the given employee id or employee id",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    private fun clearEditText() {
        etEmpId.setText("")
        etEmpId.setText("")
        etStartDate.setText("")
        etEndDate.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etEmpId.requestFocus()
    }
    private fun initView() {
        etEmpId = findViewById(R.id.hrEmpAssigmentsEmpIdTv)
        etPositionId = findViewById(R.id.hrEmpAssigmentsPositionIdTv)
        etStartDate = findViewById(R.id.hrEmpAssigmentsStartDateTv)
        etEndDate = findViewById(R.id.hrEmpAssigmentsEndDateTv)
        etUpdateDate = findViewById(R.id.hrEmpAssigmentsUpdateDateTv)
        etCreationDate = findViewById(R.id.hrEmpAssigmentsCreationDateTv)

        btnAdd = findViewById(R.id.hrEmpAssigmentsAddBtn)
        btnView = findViewById(R.id.hrEmpAssigmentsViewBtn)
        btnUpdate = findViewById(R.id.hrEmpAssigmentsUpdateBtn)


    }
}