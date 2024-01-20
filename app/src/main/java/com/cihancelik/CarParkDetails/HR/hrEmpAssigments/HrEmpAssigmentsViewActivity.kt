package com.cihancelik.CarParkDetails.HR.hrEmpAssigments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmpAssigments
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrPositions
import com.cihancelik.carparkcustomerdetails.R

class HrEmpAssigmentsViewActivity : AppCompatActivity() {
    private lateinit var recylerView: RecyclerView

    private lateinit var sqLiteHelperForHrEmployees: SQLiteHelperForHrEmployees
    private lateinit var sqLiteHelperForHrPositions: SQLiteHelperForHrPositions
    private lateinit var sqLiteHelperForHrEmpAssigments: SQLiteHelperForHrEmpAssigments
    private lateinit var etEmpId: EditText
    private lateinit var etPositionId: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText
    private lateinit var empName: String
    private lateinit var adapter : HrEmpAssigmentsAdapter

    private var hrEmpAssignment1: HrEmpAssigmentsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_emp_assigments_view)

        sqLiteHelperForHrEmpAssigments = SQLiteHelperForHrEmpAssigments(this)
        sqLiteHelperForHrEmployees = SQLiteHelperForHrEmployees(this)
        sqLiteHelperForHrPositions = SQLiteHelperForHrPositions(this)
        recylerView = findViewById(R.id.hrEmpAssigmentsRecyclerView)
        recylerView.layoutManager = LinearLayoutManager(this)
        adapter = HrEmpAssigmentsAdapter(sqLiteHelperForHrEmployees)
        recylerView.adapter = adapter

        getHrEmpAssignment()
        adapter.setOnClickItem { hrEmpAssigment ->
            val intent = Intent(this,HrEmpAssigmentsMainActivity::class.java)
            intent.putExtra("selectedHrEmpAssignmentUpdated",hrEmpAssigment)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem { deleteHrEmpAssignment(it.assigmentId) }
    }

    override fun onResume() {
        super.onResume()
        val updateHrEmpAssignment = intent.getSerializableExtra("selectedHrEmpAssignmentInfo") as? HrEmpAssigmentsModel

        if (updateHrEmpAssignment != null){
            hrEmpAssignment1 = updateHrEmpAssignment
            etEmpId.setText(updateHrEmpAssignment.empName)
            etPositionId.setText(updateHrEmpAssignment.positionId.toString())
            etStartDate.setText(updateHrEmpAssignment.startDate)
            etEndDate.setText(updateHrEmpAssignment.endDate)
            etUpdateDate.setText(updateHrEmpAssignment.updateDate)
            etCreationDate.setText(updateHrEmpAssignment.creationDate)

        }

    }
    private fun getHrEmpAssignment() {
        val hrEmpAssignmentList = sqLiteHelperForHrEmpAssigments.getAllHrEmpAssignments()
        adapter.addItems(hrEmpAssignmentList)

    }
    private fun deleteHrEmpAssignment(assigmentId: Int) {
        if (assigmentId<=0)return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete Hr Employee Assignment Info??")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_ ->
            sqLiteHelperForHrEmpAssigments.deleteHrEMpAssignmentById(assigmentId)

            val hrEmpAssignmentList = sqLiteHelperForHrEmpAssigments.getAllHrEmpAssignments()
            var deleteHrEmpAssignmenIndex = -1
            for (i in hrEmpAssignmentList.indices){
                if (hrEmpAssignmentList[i].assigmentId == assigmentId){
                    deleteHrEmpAssignmenIndex = i
                    break
                }
            }
            adapter.updateHrEmpAssigmenList(hrEmpAssignmentList)
            dialog.dismiss()
            val goToHrEmpAssignmentMainActivity = Intent(this,HrEmpAssigmentsMainActivity::class.java)
            startActivity(goToHrEmpAssignmentMainActivity)
        }
        builder.setNegativeButton("No"){dialog,_->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }


}