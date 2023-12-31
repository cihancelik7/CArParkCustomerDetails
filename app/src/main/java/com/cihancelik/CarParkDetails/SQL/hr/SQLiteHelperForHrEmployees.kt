package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrEmployees.HrEmployeesModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses

class SQLiteHelperForHrEmployees(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {
    private val addressesHelper = SQLHelperForAddresses(context)

    fun insertHrEmployee(hrEmp: HrEmployeesModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val addressIdFk = addressesHelper.getAddressesById(hrEmp.addressId)

        if (addressIdFk != null) {
            val addressName = addressesHelper.getAddressNameById(hrEmp.addressId)
            if (addressName != null) {
                val updatedStatus = "Address Id karsiliginda gelen AddressName: $addressName"
                var idString = hrEmp.addressId.toString()
                idString = updatedStatus
            }
        }
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_EMPLOYEES(EMPLOYEE_NUMBER INTEGER," +
                    "START_DATE DATE,END_DATE DATE,IS_ACTIVE TEXT,FIRST_NAME TEXT,LAST_NAME TEXT,BIRTH_DATE DATE,NATIONAL_ID INTEGER," +
                    "MARTIAL_STATUS TEXT,GENDER TEXT,ADDRESS_ID INTEGER,EMAIL_ADDRESS TEXT)"
        )
        values.put("EMPLOYEE_NUMBER", hrEmp.employeeNumber)
        values.put("START_DATE", hrEmp.startDate)
        values.put("END_DATE", hrEmp.endDate)
        values.put("IS_ACTIVE", hrEmp.isActive)
        values.put("FIRST_NAME", hrEmp.firstName)
        values.put("LAST_NAME", hrEmp.lastName)
        values.put("BIRTH_DATE", hrEmp.birthDate)
        values.put("NATIONAL_ID", hrEmp.nationalId)
        values.put("MARITAL_STATUS", hrEmp.martialStatus)
        values.put("GENDER", hrEmp.gender)
        values.put("ADDRESS_ID", hrEmp.addressId)
        values.put("EMAIL_ADDRESS", hrEmp.emailAddress)

        val insertId = db.insert("HR_EMPLOYEES", null, values)
        db.close()
        return insertId
    }

    fun getAllHrEmployees(): ArrayList<HrEmployeesModel> {
        val hrEmpList: ArrayList<HrEmployeesModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_EMPLOYEES"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var employeeId: Int
        var employeeNumber: Int
        var startDate: String
        var endDate: String
        var isActive: String
        var firstName: String
        var lastName: String
        var birthDate: String
        var nationalId: Long
        var martialStatus: String
        var gender: String
        var addressId: Int
        var emailAddress: String

        if (cursor.moveToFirst()) {
            do {
                employeeId = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID"))
                employeeNumber = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_NUMBER"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                isActive = cursor.getString(cursor.getColumnIndex("IS_ACTIVE"))
                firstName = cursor.getString(cursor.getColumnIndex("FIRST_NAME"))
                lastName = cursor.getString(cursor.getColumnIndex("LAST_NAME"))
                birthDate = cursor.getString(cursor.getColumnIndex("BIRTH_DATE"))
                nationalId = cursor.getLong(cursor.getColumnIndex("NATIONAL_ID"))
                martialStatus = cursor.getString(cursor.getColumnIndex("MARITAL_STATUS"))
                gender = cursor.getString(cursor.getColumnIndex("GENDER"))
                addressId = cursor.getInt(cursor.getColumnIndex("ADDRESS_ID"))
                emailAddress = cursor.getString(cursor.getColumnIndex("EMAIL_ADDRESS"))

                val hrEmpInfo = HrEmployeesModel(
                    employeeId = employeeId,
                    employeeNumber = employeeNumber,
                    startDate = startDate,
                    endDate = endDate,
                    isActive = isActive,
                    firstName = firstName,
                    lastName = lastName,
                    birthDate = birthDate,
                    nationalId = nationalId,
                    martialStatus = martialStatus,
                    gender = gender,
                    addressId = addressId,
                    emailAddress = emailAddress
                )
                hrEmpList.add(hrEmpInfo)
            } while (cursor.moveToNext())
        }
        return hrEmpList
    }

    fun updateHrEmp(hrEmp: HrEmployeesModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("EMPLOYEE_NUMBER", hrEmp.employeeId)
        contentValues.put("START_DATE", hrEmp.startDate)
        contentValues.put("END_DATE", hrEmp.endDate)
        contentValues.put("IS_ACTIVE", hrEmp.isActive)
        contentValues.put("FIRST_NAME", hrEmp.firstName)
        contentValues.put("LAST_NAME", hrEmp.lastName)
        contentValues.put("BIRTH_DATE", hrEmp.birthDate)
        contentValues.put("NATIONAL_ID", hrEmp.nationalId)
        contentValues.put("MARITAL_STATUS", hrEmp.martialStatus)
        contentValues.put("GENDER", hrEmp.gender)
        contentValues.put("ADDRESS_ID", hrEmp.addressId)
        contentValues.put("EMAIL_ADDRESS", hrEmp.emailAddress)

        val success = db.update(
            "HR_EMPLOYEES",
            contentValues,
            "EMPLOYEE_ID=" + hrEmp.employeeId,
            null
        )
        db.close()
        return success
    }

    fun deleteHrEmpById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("EMPLOYEE_ID", id)
        val success = db.delete("HR_EMPLOYEES", "EMPLOYEE_ID=$id", null)
        db.close()
        return success
    }

    fun getHrEmpById(hrEmpId: Int): HrEmployeesModel? {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM HR_EMPLOYEES WHERE EMPLOYEE_ID = $hrEmpId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrEmpInfo: HrEmployeesModel? = null
        if (cursor.moveToFirst()) {
            var employeeId = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID"))
            var employeeNumber = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_NUMBER"))
            var startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            var endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            var isActive = cursor.getString(cursor.getColumnIndex("IS_ACTIVE"))
            var firstName = cursor.getString(cursor.getColumnIndex("FIRST_NAME"))
            var lastName = cursor.getString(cursor.getColumnIndex("LAST_NAME"))
            var birthDate = cursor.getString(cursor.getColumnIndex("BIRTH_DATE"))
            var nationalId = cursor.getLong(cursor.getColumnIndex("NATIONAL_ID"))
            var martialStatus = cursor.getString(cursor.getColumnIndex("MARITAL_STATUS"))
            var gender = cursor.getString(cursor.getColumnIndex("GENDER"))
            var addressId = cursor.getInt(cursor.getColumnIndex("ADDRESS_ID"))
            var emailAddress = cursor.getString(cursor.getColumnIndex("EMAIL_ADDRESS"))

            hrEmpInfo = HrEmployeesModel(
                employeeId = employeeId,
                employeeNumber = employeeNumber,
                startDate = startDate,
                endDate = endDate,
                isActive = isActive,
                firstName = firstName,
                lastName = lastName,
                birthDate = birthDate,
                nationalId = nationalId,
                martialStatus = martialStatus,
                gender = gender,
                addressId = addressId,
                emailAddress = emailAddress
            )
        }
        cursor?.close()
        db.close()
        return hrEmpInfo
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS HR_EMPLOYEES")
        onCreate(db)
    }
    fun getEmployeeNumberById(empId:Int):Int?{
        val db = this.writableDatabase
        val selectQuery = "SELECT EMPLOYEE_NUMBER FROM HR_EMPLOYEES WHERE EMPLOYEE_ID = $empId"
        val cursor : Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var employeeNumber : Int? = null
        if (cursor.moveToFirst()){
            employeeNumber = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_NUMBER"))
        }
        cursor?.close()
        db.close()
        return employeeNumber
    }

}

