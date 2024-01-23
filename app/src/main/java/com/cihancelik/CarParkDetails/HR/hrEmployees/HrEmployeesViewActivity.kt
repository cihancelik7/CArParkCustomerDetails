package com.cihancelik.CarParkDetails.HR.hrEmployees

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressessModel
import com.cihancelik.carparkcustomerdetails.R

class HrEmployeesViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var  adapter : HrEmployeesAdapter
    private lateinit var sqlHelperForAddresses: SQLHelperForAddresses
    private lateinit var sqlHelperForHrEmployees: SQLiteHelperForHrEmployees
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

    private var hrEmp1 :HrEmployeesModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_employees_view)

        sqlHelperForHrEmployees = SQLiteHelperForHrEmployees(this)
        sqlHelperForAddresses = SQLHelperForAddresses(this)

        recyclerView = findViewById(R.id.hrEmployeesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HrEmployeesAdapter(sqlHelperForAddresses)
        recyclerView.adapter = adapter

        getHrEmp()
        adapter.setOnclickItem { HrEmp ->
            val intent = Intent(this,HrEmployeesMainActivity::class.java)
            intent.putExtra("selectedHrEmployeeUpdated",HrEmp)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteHrEmp(it.employeeId)
        }
    }

    override fun onResume() {
        super.onResume()
        val updateHrEmp =
            intent.getSerializableExtra("selectedHrEmployeeInfo")as? HrEmployeesModel
        if (updateHrEmp!=null){
            hrEmp1 = updateHrEmp
            etEmployeeNumber.setText(updateHrEmp.employeeNumber.toString())
            etStartDate.setText(updateHrEmp.startDate)
            etEndDate.setText(updateHrEmp.endDate)
            etIsActive.setText(updateHrEmp.isActive)
            etFirstName.setText(updateHrEmp.firstName)
            etLastName.setText(updateHrEmp.lastName)
            etBirthDate.setText(updateHrEmp.birthDate)
            etNationalId.setText(updateHrEmp.nationalId.toString())
            etMartialStatus.setText(updateHrEmp.martialStatus)
            etGender.setText(updateHrEmp.gender)
            etAddressId.setText(updateHrEmp.addressId.toString())
            etEmailAddress.setText(updateHrEmp.emailAddress)

        }
    }
    private fun getHrEmp() {
        val hrEmpList = sqlHelperForHrEmployees.getAllHrEmployees()
        adapter.addItems(hrEmpList)
    }

    private fun deleteHrEmp(employeeId: Int) {
        if (employeeId <= 0 )return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete HrEmployee Info?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            sqlHelperForHrEmployees.deleteHrEmpById(employeeId)

            val hrEmpList = sqlHelperForHrEmployees.getAllHrEmployees()
            var deletedHrEmpIndex = -1
            for (i in hrEmpList.indices){
                if (hrEmpList[i].employeeId == employeeId){
                    deletedHrEmpIndex = i
                    break
                }
            }
            adapter.updateHrEmployeeList(hrEmpList)
            dialog.dismiss()
            var goToMainActivity = Intent(this,HrEmployeesMainActivity::class.java)
            startActivity(goToMainActivity)
        }
        builder.setNegativeButton("No") { dialog,_ ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }
}