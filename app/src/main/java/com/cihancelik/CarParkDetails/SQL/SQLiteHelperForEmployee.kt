package com.cihancelik.CarParkDetails.SQL

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.cihancelik.CarParkDetails.employee_details.EmployeeModel

class SQLiteHelperForEmployee(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "employee.db"
        private const val TABLE_EMPLOYEE = "table_employee"
        private const val ID = "id"
        private const val NAME = "name"
        private const val LASTNAME = "lastname"
        private const val DEPARTMENT = "department"
        private const val EMAIL = "email"
        private const val ADDRESS = "address"
       private lateinit var cursor: Cursor

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableEmployee = ("CREATE TABLE " + TABLE_EMPLOYEE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + LASTNAME + " TEXT, "
                + DEPARTMENT + " TEXT, "
                + EMAIL + " TEXT, "
                + ADDRESS + " TEXT" + ")")
        db?.execSQL(createTableEmployee)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_EMPLOYEE ADD COLUMN $LASTNAME TEXT")
        }
    }

    fun insertEmployee(employee: EmployeeModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, employee.name)
        contentValues.put(LASTNAME, employee.lastName)
        contentValues.put(DEPARTMENT, employee.department)
        contentValues.put(EMAIL, employee.email)
        contentValues.put(ADDRESS, employee.address)

        val success = db.insert(TABLE_EMPLOYEE, null, contentValues)
        db.close()
        return success
    }

    fun getAllEmployee(): ArrayList<EmployeeModel> {
        val employeeList: ArrayList<EmployeeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_EMPLOYEE"
        val db = this.readableDatabase
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            // Hata ele alınır
            return ArrayList()
        }






        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var lastname: String
        var department: String
        var email: String
        var address: String



        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                lastname = cursor.getString(cursor.getColumnIndex("lastname"))
                department = cursor.getString(cursor.getColumnIndex("department"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                address = cursor.getString(cursor.getColumnIndex("address"))
                val emp = EmployeeModel(
                    id = id,
                    name = name,
                    lastName = lastname,
                    department = department,
                    email = email,
                    address = address
                )
                employeeList.add(emp)
            } while (cursor.moveToNext())
        }
        return employeeList
    }

    fun updateEmployee(emp: EmployeeModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put(ID, emp.id)
        contextValues.put(NAME, emp.name)
        contextValues.put(LASTNAME, emp.lastName)
        contextValues.put(DEPARTMENT, emp.department)
        contextValues.put(EMAIL, emp.email)
        contextValues.put(ADDRESS, emp.address)

        val success = db.update(TABLE_EMPLOYEE, contextValues, "id=" + emp.id, null)
        db.close()
        return success
    }

    fun deleteEmployeeById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TABLE_EMPLOYEE, "id=$id", null)
        db.close()
        return success
    }

}