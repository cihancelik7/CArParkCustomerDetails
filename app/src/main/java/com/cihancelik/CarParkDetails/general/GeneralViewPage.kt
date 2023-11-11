package com.cihancelik.CarParkDetails.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressesMainScreen
import com.cihancelik.CarParkDetails.general.userUpdateScreen.UsersMainScreen
import com.cihancelik.carparkcustomerdetails.R

class GeneralViewPage : AppCompatActivity() {

    private lateinit var btnGeneralUsers : Button
    private lateinit var btnGeneralAddresses : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_view_page)

        btnGeneralAddresses = findViewById(R.id.hrEmployees)
        btnGeneralUsers = findViewById(R.id.usersGeneralViewPage)

        val goToAddressUpdateScreenIntent = Intent(this,AddressesMainScreen::class.java)
        btnGeneralAddresses.setOnClickListener { startActivity(goToAddressUpdateScreenIntent) }

        val goToUsersUpdateScreenIntent = Intent(this,UsersMainScreen::class.java)
        btnGeneralUsers.setOnClickListener { startActivity(goToUsersUpdateScreenIntent) }
    }
}