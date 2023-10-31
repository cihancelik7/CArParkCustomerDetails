package com.cihancelik.CarParkDetails.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressesMainPage
import com.cihancelik.CarParkDetails.general.userUpdateScreen.UserUpdateScreen
import com.cihancelik.carparkcustomerdetails.R

class GeneralViewPage : AppCompatActivity() {

    private lateinit var btnGeneralUsers : Button
    private lateinit var btnGeneralAddresses : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_view_page)

        btnGeneralAddresses = findViewById(R.id.addressesButtonIdGeneralViewPage)
        btnGeneralUsers = findViewById(R.id.usersGeneralViewPage)

        val goToAddressUpdateScreenIntent = Intent(this,AddressesMainPage::class.java)
        btnGeneralAddresses.setOnClickListener { startActivity(goToAddressUpdateScreenIntent) }

        val goToUsersUpdateScreenIntent = Intent(this,UserUpdateScreen::class.java)
        btnGeneralUsers.setOnClickListener { startActivity(goToUsersUpdateScreenIntent) }
    }
}