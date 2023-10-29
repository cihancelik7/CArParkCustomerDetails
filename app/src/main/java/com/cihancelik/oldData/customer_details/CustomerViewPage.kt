package com.cihancelik.oldData.customer_details

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCustomer
import com.cihancelik.carparkcustomerdetails.R

class CustomerViewPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = CustomerAdapter()
    private lateinit var sqLiteHelperForCustomer: SQLiteHelperForCustomer
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etAddress: EditText
    private lateinit var etCity: EditText
    private lateinit var etCarPlate: EditText

    private var cst: CustomerModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_view_page)

        sqLiteHelperForCustomer = SQLiteHelperForCustomer(this)

        recyclerView = findViewById(R.id.customerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        getCustomers()

        adapter?.setOnClickItem { customer ->
            val intent = Intent(this, CustomerMainActivity::class.java)
            intent.putExtra("selectedCustomer", customer)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteCustomer(it.id)

        }
    }

    override fun onResume() {
        super.onResume()
        // "MainActivity" sayfasından güncellenmiş veriyi al
        val updatedCustomer = intent.getSerializableExtra("updatedCustomer") as? CustomerModel
        if (updatedCustomer != null) {
            // Eğer güncellenmiş veri varsa, "cst" değişkenini güncelle
            cst = updatedCustomer
            // EditText'lere güncellenmiş verileri yerleştir
            etName.setText(updatedCustomer.name)
            etLastName.setText(updatedCustomer.lastName)
            etEmail.setText(updatedCustomer.email)
            etPhone.setText(updatedCustomer.phone)
            etAddress.setText(updatedCustomer.address)
            etCity.setText(updatedCustomer.city)
            etCarPlate.setText(updatedCustomer.carplate)
        }
    }

    fun getCustomers() {
        val customerList = sqLiteHelperForCustomer.getAllCustomers()
        adapter.addItems(customerList)
    }

    fun deleteCustomer(id: Int) {
        if (id <= 0) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete Customer?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            // Müşteriyi sil
            sqLiteHelperForCustomer.deleteCustomerById(id)

            // Tüm müşteri listesini al
            val customerList = sqLiteHelperForCustomer.getAllCustomers()

            // Silinen müşterinin indisini bul
            var deletedCustomerIndex = -1

            for (i in customerList.indices) {
                if (customerList[i].id == id) {
                    deletedCustomerIndex = i
                    break
                }
            }

            // Eğer silinen müşteri listede bulunursa ve listede daha fazla müşteri varsa,
            // silinen müşteriden sonraki müşterilerin ID değerlerini birer azalt
            if (deletedCustomerIndex != -1 && deletedCustomerIndex < customerList.size - 1) {
                for (i in (deletedCustomerIndex + 1) until customerList.size) {
                    val currentCustomer = customerList[i]
                    currentCustomer.id = currentCustomer.id - 1 // ID'yi bir azalt
                    sqLiteHelperForCustomer.updateCustomer(currentCustomer)
                }
            } else if (deletedCustomerIndex != -1) {
                // Eğer silinen müşteri listenin son elemanıysa, bu durumda diğer müşterilerin ID'lerine dokunmanıza gerek yok.
                // Sadece son müşteriyi kaldırın.
                customerList.removeAt(deletedCustomerIndex)
            }

            // Yeniden güncellenmiş müşteri listesini ekranda göster
            adapter.updateCustomerList(customerList) // Adapter verilerini güncelle
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


}
