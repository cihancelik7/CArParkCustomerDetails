package com.cihancelik.CarParkDetails.HR.hrSalaries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrSalaries
import com.cihancelik.carparkcustomerdetails.R

class HrSalariesViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var sqLiteHelperForHrSalaries: SQLiteHelperForHrSalaries
    private lateinit var sqLiteHelperForHrEmployees: SQLiteHelperForHrEmployees
    private lateinit var etEmpId: EditText
    private lateinit var etAmount: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText
    private lateinit var adapter: HrSalariesAdapter

    private var hrSalary1 : HrSalariesModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_salaries_view)

        sqLiteHelperForHrSalaries = SQLiteHelperForHrSalaries(this)
        sqLiteHelperForHrEmployees = SQLiteHelperForHrEmployees(this)
        recyclerView = findViewById(R.id.hrSalaryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HrSalariesAdapter(sqLiteHelperForHrEmployees)
        recyclerView.adapter = adapter

        getHrSalaries()
        adapter.setOnClickItem { hrSalaries ->
            val intent = Intent(this, HrSalariesMainActivity::class.java)
            intent.putExtra("selectedHrSalaryUpdate", hrSalaries)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem { deleteHrSalary(it.salaryId) }
    }

    override fun onResume() {
        super.onResume()
        val updateHrSalary =
            intent.getSerializableExtra("selectedHrSalaryInfo") as? HrSalariesModel
        if (updateHrSalary != null){
            hrSalary1 = updateHrSalary
            etEmpId.setText(updateHrSalary.employeeId.toString())
            etAmount.setText(updateHrSalary.amount.toString())
            etStartDate.setText(updateHrSalary.startDate)
            etEndDate.setText(updateHrSalary.endDate)
            etUpdateDate.setText(updateHrSalary.updateDate)
            etCreationDate.setText(updateHrSalary.creationDate)
        }
    }
    private fun getHrSalaries() {
        val hrSalariesList = sqLiteHelperForHrSalaries.getAllHrSalary()
        adapter.addItems(hrSalariesList)
    }
    private fun deleteHrSalary(salaryId: Int) {
        if (salaryId <= 0) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete Hr Salary Info???")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_ ->
            sqLiteHelperForHrSalaries.deleteHrSalaryById(salaryId)

            val hrSalaryList = sqLiteHelperForHrSalaries.getAllHrSalary()
            var deleteHrSalaryIndex = -1
            for (i in hrSalaryList.indices){
                if (hrSalaryList[i].salaryId == salaryId){
                    deleteHrSalaryIndex = i
                    break
                }
            }
            adapter.updateHrSalaryList(hrSalaryList)
            dialog.dismiss()
            val goToHrSalaryMainActivity = Intent(this,HrSalariesMainActivity::class.java)
            startActivity(goToHrSalaryMainActivity)
        }
        builder.setNegativeButton("No"){dialog,_->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }


}