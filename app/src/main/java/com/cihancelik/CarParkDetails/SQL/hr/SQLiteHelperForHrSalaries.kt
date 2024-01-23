package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrSalaries.HrSalariesModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase

class SQLiteHelperForHrSalaries(context: Context) : SQLiteHelperForCarParkDataBase(context) {
    private val employeeHelper = SQLiteHelperForHrEmployees(context)

    fun insertHrSalaries(hrSalaries: HrSalariesModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_SALARIES(" +
                    "EMPLOYEE_ID INTEGER, AMOUNT INTEGER,START_DATE TEXT, END_DATE TEXT,UPDATE_DATE," +
                    "CREATION_DATE TEXT)"
        )
        values.put("EMPLOYEE_ID", hrSalaries.employeeId)
        values.put("AMOUNT", hrSalaries.amount)
        values.put("START_DATE", hrSalaries.startDate)
        values.put("END_DATE", hrSalaries.endDate)
        values.put("UPDATE_DATE", hrSalaries.updateDate)
        values.put("CREATION_DATE", hrSalaries.creationDate)

        val insertId = db.insert("HR_SALARIES", null, values)
        db.close()
        return insertId
    }

    fun getAllHrSalary(): ArrayList<HrSalariesModel> {
        var hrSalaryList: ArrayList<HrSalariesModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_SALARIES"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var salaryId: Int
        var empId: Int
        var amount: Int
        var startDate: String
        var endDate: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                salaryId = cursor.getInt(cursor.getColumnIndex("SALARY_ID"))
                empId = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID"))
                amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val hrSalaryInfo = HrSalariesModel(
                    salaryId = salaryId,
                    employeeId = empId,
                    amount = amount,
                    startDate = startDate,
                    endDate = endDate,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                hrSalaryList.add(hrSalaryInfo)
            } while (cursor.moveToNext())
        }
        return hrSalaryList
    }

    fun updateHrSalary(hrSalary: HrSalariesModel): Int {
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("EMPLOYEE_ID", hrSalary.employeeId)
        content.put("AMOUNT", hrSalary.amount)
        content.put("START_DATE", hrSalary.startDate)
        content.put("END_DATE", hrSalary.endDate)
        content.put("UPDATE_DATE", hrSalary.updateDate)
        content.put("CREATION_DATE", hrSalary.creationDate)

        val success = db.update(
            "HR_SALARIES", content, "SALARY_ID=" + hrSalary.salaryId, null
        )
        db.close()
        return success
    }

    fun deleteHrSalaryById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        val success = db.delete("HR_SALARIES", "SALARY_ID=$id", null)
        db.close()
        return success
    }

    fun getHrSalaryById(hrSalaryId: Int): HrSalariesModel? {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM HR_SALARIES WHERE SALARY_ID = $hrSalaryId"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrSalariesInfo: HrSalariesModel? = null
        if (cursor.moveToFirst()) {
            var salaryId = cursor.getInt(cursor.getColumnIndex("SALARY_ID"))
            var empId = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID"))
            var amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
            var startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            var endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            hrSalariesInfo = HrSalariesModel(
                salaryId = salaryId,
                employeeId = empId,
                amount = amount,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return hrSalariesInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS HR_SALARIES")
        onCreate(db)
    }
}