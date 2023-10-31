package com.cihancelik.CarParkDetails.SQL

import android.content.ContentValues
import android.content.Context
import android.location.Address
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressessModel

class SQLHelperForAddresses (context: Context) : SQLiteHelperForCarParkDataBase(context) {
    // Adreslerle ilgili SQL işlemlerini burada tanımlayın

    fun createAddress(address: AddressessModel) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("ADDRESS_NAME",address.address)
        values.put("START_DATE",address.startDate)
        values.put("END_DATE",address.endDAte)
        values.put("COUNTRY",address.country)
        values.put("CITY",address.city)
        values.put("REGION",address.region)
        values.put("POSTAL_CODE",address.postalCode)
        values.put("ADDRESS_LINE",address.addressLine)
        values.put("UPDATE_DATE",address.updateDate)
        values.put("CREATION_DATE",address.creationDate)

        db.insert("ADDRESSES",null,values)
        db.close()
    }



}