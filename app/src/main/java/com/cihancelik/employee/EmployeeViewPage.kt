package com.cihancelik.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.SQL.SQLiteHelperForEmployee
import com.cihancelik.carparkcustomerdetails.R
import java.net.Inet4Address

class EmployeeViewPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = EmployeeAdapter()
    private lateinit var sqLiteHelperForEmployee: SQLiteHelperForEmployee
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDepartment: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText

    private var emp: EmployeeModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_view_page)

        sqLiteHelperForEmployee = SQLiteHelperForEmployee(this)

        recyclerView = findViewById(R.id.employeeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        getEmployee()

        adapter?.setOnClickItem { employee ->
            val intent = Intent(this, EmployeeMainActivity::class.java)
            intent.putExtra("selectedEmployee", employee)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteEmployee(it.id)
        }

    }
    override fun onResume(){
        super.onResume()
        // main activity sayfasinda guncellenmis veriyi al
        val updatedEmployee = intent.getSerializableExtra("updatedEmployee") as? EmployeeModel
        if (updatedEmployee != null){
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


    fun getEmployee() {
        val employeeList = sqLiteHelperForEmployee.getAllEmployee()
        adapter.addItems(employeeList)
    }

    fun deleteEmployee(id: Int) {
        if (id <= 0) return

        var builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete Employee?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            // personeli sil
            sqLiteHelperForEmployee.deleteEmployeeById(id)

            // tum calisan listesini al
            val employeeList = sqLiteHelperForEmployee.getAllEmployee()
            // silinen musterinin idsini bul
            var deletedEmployeeIndex = -1

            for (i in employeeList.indices) {
                if (employeeList[i].id == id) {
                    deletedEmployeeIndex = i
                    break
                }
            }

            // eger silinen calisan lsitede bulunursa listede daha fazla calisan varsa,
            // silinen calisandan sonraki calisanlarin ID degerlerini birer azalt
            if (deletedEmployeeIndex != -1 && deletedEmployeeIndex < employeeList.size - 1) {
                for (i in (deletedEmployeeIndex + 1) until employeeList.size) {
                    val currentEmployee = employeeList[i]
                    currentEmployee.id = currentEmployee.id - 1 // id yi bir azalt
                    sqLiteHelperForEmployee.updateEmployee(currentEmployee)
                }
            }else if (deletedEmployeeIndex != -1){
                // eger silinen musteri listenin son elemaniysa, bu durumda diger musterin id lerine dokunmaniza gerek yok.
                // sadece son musteriyi kaldirin
                employeeList.removeAt(deletedEmployeeIndex)
            }

            // yeniden guncellenmis personel listesini ekranda goster
            adapter.updateEmployeeList(employeeList)// adapter verilerini guncelle
            dialog.dismiss()
        }
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
}