package com.cihancelik.CarParkDetails.general.addressesUpdateScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.SQLHelperForAddresses
import com.cihancelik.carparkcustomerdetails.R

class AddressViewScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = AddressesAdapter()
    private lateinit var sqlHelperForAddresses: SQLHelperForAddresses
    private lateinit var etAddress : EditText
    private lateinit var etStartDate : EditText
    private lateinit var etEndDate : EditText
    private lateinit var etCountry : EditText
    private lateinit var etCity : EditText
    private lateinit var etRegion : EditText
    private lateinit var etPostalCode :EditText
    private lateinit var etAddressLine : EditText
    private lateinit var etUpdateDate : EditText
    private lateinit var etCreationDate : EditText



    private var addresses : AddressessModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_view_screen)

        sqlHelperForAddresses = SQLHelperForAddresses(this)
         recyclerView = findViewById<RecyclerView>(R.id.addressRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getAddresses()

        adapter.setOnclickItem { addresses ->
            val intent = Intent(this,AddressesMainPage::class.java)
            intent.putExtra("selectedAddressesUpdate",addresses)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteAddresses(it.addressId)

        }




    }

    override fun onResume() {
        super.onResume()

        val updatedAddresses = intent.getSerializableExtra("selectedAddressesInfo") as? AddressessModel
        if (updatedAddresses != null) {
            // Guncellenmis veri varsa addresses degiskenini guncelle
            addresses = updatedAddresses
            // Edittextlere guncellenmis verileri yerlestir
            etAddress.setText(updatedAddresses.address)
            etAddressLine.setText(updatedAddresses.addressLine)
            etStartDate.setText(updatedAddresses.startDate)
            etEndDate.setText(updatedAddresses.endDAte)
            etCountry.setText(updatedAddresses.country)
            etCity.setText(updatedAddresses.city)
            etRegion.setText(updatedAddresses.region)
            etPostalCode.setText(updatedAddresses.postalCode)
            etUpdateDate.setText(updatedAddresses.updateDate)
            etCreationDate.setText(updatedAddresses.creationDate)
        } else {
            Toast.makeText(this, "Verileri alırken hata oluştu.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAddresses(){
        val addressesList = sqlHelperForAddresses.getAllAddresses()
        adapter.addItems(addressesList)
    }
    fun  deleteAddresses(id:Int){
        if (id <= 0)return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete Addresses info ")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            // delete to addresses info
            sqlHelperForAddresses.deleteAddressesById(id)

            // give all addresses info
            val addressesList = sqlHelperForAddresses.getAllAddresses()
            var deletedAddressesIndex = -1
            for (i in addressesList.indices){
                if (addressesList[i].addressId == id){
                    deletedAddressesIndex = i
                    break
                }
            }

            adapter.updateAddressesList(addressesList)
            dialog.dismiss()
            var goToAddressesMainPage = Intent(this,AddressesMainPage::class.java)
            startActivity(goToAddressesMainPage)
        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()

    }
}



