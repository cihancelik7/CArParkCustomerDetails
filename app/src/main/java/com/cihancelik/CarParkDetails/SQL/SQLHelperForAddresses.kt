package com.cihancelik.CarParkDetails.SQL

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.location.Address
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressessModel



class SQLHelperForAddresses(context: Context) : SQLiteHelperForCarParkDataBase(context) {

    // Adreslerle ilgili SQL işlemlerini burada tanımlayın

    fun createAddress(address: AddressessModel) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("ADDRESS_NAME", address.address)
        values.put("START_DATE", address.startDate)
        values.put("END_DATE", address.endDAte)
        values.put("COUNTRY", address.country)
        values.put("CITY", address.city)
        values.put("REGION", address.region)
        values.put("POSTAL_CODE", address.postalCode)
        values.put("ADDRESS_LINE", address.addressLine)
        values.put("UPDATE_DATE", address.updateDate)
        values.put("CREATION_DATE", address.creationDate)

        db.insert("ADDRESSES", null, values)
        db.close()
    }

    fun getAllAddresses(): ArrayList<AddressessModel> {
        val addressesList: ArrayList<AddressessModel> = ArrayList()
        val selectQuery = "SELECT * FROM table_carparkdatabase"
        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var address: String
        var startDate: String
        var endDate: String
        var country: String
        var city: String
        var region: String
        var postalCode: String
        var addressLine: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("ADDRESS_ID"))
                address = cursor.getString(cursor.getColumnIndex("ADDRESS_NAME"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                country = cursor.getString(cursor.getColumnIndex("COUNTRY"))
                city = cursor.getString(cursor.getColumnIndex("CITY"))
                region = cursor.getString(cursor.getColumnIndex("REGION"))
                postalCode = cursor.getString(cursor.getColumnIndex("POSTAL_CODE"))
                addressLine = cursor.getString(cursor.getColumnIndex("ADDRESS_LINE"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val addressInfo = AddressessModel(
                    addressId = id,
                    address = address,
                    startDate = startDate,
                    endDAte = endDate,
                    country = country,
                    city = city,
                    region = region,
                    postalCode = postalCode,
                    addressLine = addressLine,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                addressesList.add(addressInfo)
            }while (cursor.moveToNext())
        }
        return addressesList

    }

    fun updateAddresses(address: AddressessModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put("ADDRESS_ID", address.addressId)
        contextValues.put("ADDRESS_NAME", address.address)
        contextValues.put("START_DATE", address.updateDate)
        contextValues.put("END_DATE", address.endDAte)
        contextValues.put("COUNTRY", address.country)
        contextValues.put("CITY", address.city)
        contextValues.put("REGION", address.region)
        contextValues.put("POSTAL_CODE", address.postalCode)
        contextValues.put("ADDRESS_LINE", address.addressLine)
        contextValues.put("UPDATE_DATE", address.updateDate)
        contextValues.put("CREATION_DATE", address.creationDate)

        val success =
            db.update("table_carparkdatabase", contextValues, "id= " + address.addressId, null)
        db.close()
        return success
    }

    fun deleteCustomerById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ADDRESS_ID", id)

        val success = db.delete("table_carparkdatabase", "id= $id", null)
        db.close()
        return success
    }


}