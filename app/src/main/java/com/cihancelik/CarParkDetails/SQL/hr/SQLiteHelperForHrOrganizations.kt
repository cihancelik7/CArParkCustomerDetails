package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrOrganizations.HrOrganizationsModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase

class SQLiteHelperForHrOrganizations(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {
    private val locationHelper = SQLiteHelperForHrLocations(context)
    //private val parentOrgIdHelper = SQLiteHelperForHrOrganizations(context)

    fun insertHrOrganizations(hrOrganization: HrOrganizationsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val locationIdFk = locationHelper.getHrLocationById(hrOrganization.locationId)
        // burada getId fonk gelince yazilacak !!
     //   val parentIdFk = parentOrgIdHelper.getHrOrganizationById(hrOrganization.parentOrgId)

        if (locationIdFk != null) {
            val locationName = locationHelper.getLocationNameById(hrOrganization.locationId)

         //       parentOrgIdHelper.getOrganizationParentIdByOrgId(hrOrganization.organizationId)
            if (locationName != null ) {
                val updateLocationName =
                    "Location Id karsiliginda gelen Location Name: $locationName"
               // val updateParentName = "Organization id karsiliginda gelen ParentId : $parentName "

                var locationIdString = hrOrganization.locationId.toString()
                locationIdString = updateLocationName
                var parentIdStr = hrOrganization.parentOrgId.toString()
             //   parentIdStr = updateParentName
            }
        }
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_ORGANIZATIONS(ORGANIZATION_NAME TEXT,START_DATE DATE," +
                    "END_DATE DATE, PARENT_ORG_ID INTEGER, LOCATION_ID INTEGER, UPDATE_DATE DATE," +
                    "CREATION_DATE DATE)"
        )
        values.put("ORGANIZATION_NAME", hrOrganization.organizationName)
        values.put("START_DATE", hrOrganization.startDate)
        values.put("END_DATE", hrOrganization.endDate)
        values.put("PARENT_ORG_ID", hrOrganization.parentOrgId)
        values.put("LOCATION_ID", hrOrganization.locationId)
        values.put("UPDATE_DATE", hrOrganization.updateDate)
        values.put("CREATION_DATE", hrOrganization.creationDate)

        val insertId = db.insert("HR_ORGANIZATIONS", null, values)
        db.close()
        return insertId

    }

    fun getAllHrOrganizations(): ArrayList<HrOrganizationsModel> {
        val hrOrganizationList: ArrayList<HrOrganizationsModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_ORGANIZATIONS"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var organizationId: Int
        var organizationName: String
        var startDate: String
        var endDate: String
        var parentOrgId: Int
        var locationId: Int
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                organizationId = cursor.getInt(cursor.getColumnIndex("ORGANIZATION_ID"))
                organizationName = cursor.getString(cursor.getColumnIndex("ORGANIZATION_NAME"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                parentOrgId = cursor.getInt(cursor.getColumnIndex("PARENT_ORG_ID"))
                locationId = cursor.getInt(cursor.getColumnIndex("LOCATION_ID"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val hrOrgInfo = HrOrganizationsModel(
                    organizationId = organizationId,
                    organizationName = organizationName,
                    startDate = startDate,
                    endDate = endDate,
                    parentOrgId = parentOrgId,
                    locationId = locationId,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                hrOrganizationList.add(hrOrgInfo)
            } while (cursor.moveToNext())
        }
        return hrOrganizationList
    }

    fun updateHrOrg(hrOrg: HrOrganizationsModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ORGANIZATION_NAME", hrOrg.organizationName)
        contentValues.put("START_DATE", hrOrg.startDate)
        contentValues.put("END_DATE", hrOrg.endDate)
        contentValues.put("PARENT_ORG_ID", hrOrg.parentOrgId)
        contentValues.put("LOCATION_ID", hrOrg.locationId)
        contentValues.put("UPDATE_DATE", hrOrg.updateDate)
        contentValues.put("CREATION_DATE", hrOrg.creationDate)

        val success = db.update(
            "HR_ORGANIZATIONS",
            contentValues,
            "ORGANIZATION_ID=" + hrOrg.organizationId,
            null
        )
        db.close()
        return success
    }

    fun deleteHrOrganizationById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ORGANIZATION_ID", id)
        val success = db.delete("HR_ORGANIZATIONS", "ORGANIZATION_ID=$id", null)
        db.close()
        return success
    }

    fun getHrOrganizationById(hrOrgId: Int): HrOrganizationsModel? {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM HR_ORGANIZATIONS WHERE ORGANIZATION_ID = $hrOrgId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrOrgIfo: HrOrganizationsModel? = null
        if (cursor.moveToFirst()) {
            var organizationId = cursor.getInt(cursor.getColumnIndex("ORGANIZATION_ID"))
            var organizationName = cursor.getString(cursor.getColumnIndex("ORGANIZATION_NAME"))
            var startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            var endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            var parentOrgId = cursor.getInt(cursor.getColumnIndex("PARENT_ORG_ID"))
            var locationId = cursor.getInt(cursor.getColumnIndex("LOCATION_ID"))
            var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            hrOrgIfo = HrOrganizationsModel(
                organizationId = organizationId,
                organizationName = organizationName,
                startDate = startDate,
                endDate = endDate,
                parentOrgId = parentOrgId,
                locationId = locationId,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return hrOrgIfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS HR_ORGANIZATIONS")
        onCreate(db)
    }

    fun getOrganizationParentIdByOrgId(organizationId: Int): Int? {
        val db = this.writableDatabase
        val selectQuery =
            "SELECT ORGANIZATION_ID FROM HR_ORGANIZATIONS WHERE ORGANIZATION_ID = $organizationId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var parentOrgId: Int? = null
        if (cursor.moveToFirst()) {
            parentOrgId = cursor.getInt(cursor.getColumnIndex("PARENT_ORG_ID"))
        }
        cursor?.close()
        db.close()
        return parentOrgId
    }

}