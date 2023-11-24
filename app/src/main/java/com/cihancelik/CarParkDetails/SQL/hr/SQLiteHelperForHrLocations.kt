package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrLocations.HrLocationsModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses

class SQLiteHelperForHrLocations(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {
    private val addressesHelper = SQLHelperForAddresses(context)


    fun insertHrLocation(hrLocation: HrLocationsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val addressIdFk = addressesHelper.getAddressesById(hrLocation.addressId)

        if (addressIdFk != null) {
            val addressName = addressesHelper.getAddressNameById(hrLocation.addressId)
            if (addressName != null) {
                val updateAddressName = "Address Id karsiliginda gelen AddressName: $addressName"
                var idString = hrLocation.addressId.toString()
                idString = updateAddressName
            }
        }
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_LOCATIONS(LOCATION_NAME TEXT," +
                    "START_DATE DATE,END_DATE DATE, ADDRESS_ID INTEGER,NACE_KODU INTEGER," +
                    "TEHLIKE_SINIFI TEXT, UPDATE_DATE DATE, CREATION_DATE DATE" +
                    ")"
        )
        values.put("LOCATION_NAME", hrLocation.locationName)
        values.put("START_DATE", hrLocation.startDate)
        values.put("END_DATE", hrLocation.endDate)
        values.put("ADDRESS_ID", hrLocation.addressId)
        values.put("NACE_KODU", hrLocation.naceCode)
        values.put("TEHLIKE_SINIFI", hrLocation.dangerClass)
        values.put("UPDATE_DATE", hrLocation.updateDate)
        values.put("CREATION_DATE", hrLocation.creationDate)

        val insertId = db.insert("HR+LOCATIONS", null, values)
        db.close()
        return insertId
    }

    fun getAllHrLocations(): ArrayList<HrLocationsModel> {
        val hrLocationList: ArrayList<HrLocationsModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_LOCATIONS"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var locationId: Int
        var locationName: String
        var startDate: String
        var endDate: String
        var addressId: Int
        var naceCode: Int
        var dangerClass: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                locationId = cursor.getInt(cursor.getColumnIndex("LOCATION_ID"))
                locationName = cursor.getString(cursor.getColumnIndex("LOCATION_NAME"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                addressId = cursor.getInt(cursor.getColumnIndex("ADDRESS_ID"))
                naceCode = cursor.getInt(cursor.getColumnIndex("NACE_KODU"))
                dangerClass = cursor.getString(cursor.getColumnIndex("TEHLIKE_SINIFI"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val hrLocationInfo = HrLocationsModel(
                    locationId = locationId,
                    locationName = locationName,
                    startDate = startDate,
                    endDate = endDate,
                    addressId = addressId,
                    naceCode = naceCode,
                    dangerClass = dangerClass,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                hrLocationList.add(hrLocationInfo)
            } while (cursor.moveToNext())
        }
        return hrLocationList
    }

    fun updateHrLocation(hrLocation: HrLocationsModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("LOCATION_NAME", hrLocation.locationName)
        contentValues.put("START_DATE", hrLocation.startDate)
        contentValues.put("END_DATE", hrLocation.endDate)
        contentValues.put("ADDRESS_ID", hrLocation.addressId)
        contentValues.put("NACE_KODU", hrLocation.naceCode)
        contentValues.put("TEHLIKE_SINIFI", hrLocation.dangerClass)
        contentValues.put("UPDATE_DATE", hrLocation.updateDate)
        contentValues.put("CREATION_DATE", hrLocation.creationDate)

        val success = db.update(
            "HR_LOCATIONS",
            contentValues,
            "LOCATION_ID=" + hrLocation.locationId,
            null
        )
        db.close()
        return success
    }

    fun deleteHrLocationById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("LOCATION_ID", id)
        val success = db.delete("HR_LOCATIONS", "LOCATION_ID=$id", null)
        db.close()
        return success
    }

    fun getHrLocationById(hrLocationId: Int): HrLocationsModel? {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM HR_LOCATIONS WHERE LOCATION_ID = $hrLocationId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrLocationInfo: HrLocationsModel? = null
        if (cursor.moveToFirst()) {
            var locationId = cursor.getInt(cursor.getColumnIndex("LOCATION_ID"))
            var locationName = cursor.getString(cursor.getColumnIndex("LOCATION_NAME"))
            var startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            var endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            var addressId = cursor.getInt(cursor.getColumnIndex("ADDRESS_ID"))
            var naceCode = cursor.getInt(cursor.getColumnIndex("NACE_KODU"))
            var dangerClass = cursor.getString(cursor.getColumnIndex("TEHLIKE_SINIFI"))
            var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            hrLocationInfo = HrLocationsModel(
                locationId = locationId,
                locationName = locationName,
                startDate = startDate,
                endDate = endDate,
                addressId = addressId,
                naceCode = naceCode,
                dangerClass = dangerClass,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return hrLocationInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS HR_LOCATIONS")
        onCreate(db)
    }

}