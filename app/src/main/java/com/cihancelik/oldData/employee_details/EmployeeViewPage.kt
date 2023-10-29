package com.cihancelik.oldData.employee_details

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.carparkcustomerdetails.R


class EmployeeViewPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = EmployeeAdapter()
    private lateinit var sqLiteHelperForEmployee: SQLiteHelperForCarParkDataBase
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDepartment: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText

    private var emp: EmployeeModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_view_page)

        sqLiteHelperForEmployee = SQLiteHelperForCarParkDataBase(this)

        recyclerView = findViewById(R.id.employeeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        

    }

    override fun onResume() {
        super.onResume()
        // main activity sayfasinda guncellenmis veriyi al
        val updatedEmployee = intent.getSerializableExtra("updatedEmployee") as? EmployeeModel
        if (updatedEmployee != null) {
            // eger guncellenmis veri varsa "emp" degiseknini guncelle
            emp = updatedEmployee
            // edittextlere guncellenmis verileri yerlestir
            etName.setText(updatedEmployee.name)
            etLastName.setText(updatedEmployee.lastName)
            etDepartment.setText(updatedEmployee.department)
            etEmail.setText(updatedEmployee.email)
            etAddress.setText(updatedEmployee.address)
        }
    }
}