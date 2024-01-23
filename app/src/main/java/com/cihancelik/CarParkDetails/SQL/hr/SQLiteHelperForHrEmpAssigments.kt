package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrEmpAssigments.HrEmpAssigmentsModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase

class SQLiteHelperForHrEmpAssigments(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {
    private val employeeHelper = SQLiteHelperForHrEmployees(context)
    private val positionHelper = SQLiteHelperForHrPositions(context)

    fun insertHrEmpAssigment(hrEmpAssigment: HrEmpAssigmentsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val positionIdFk = positionHelper.getPositionById(hrEmpAssigment.positionId)


        if (positionIdFk != null) {
            val positionName = positionHelper.getPositionNameById(hrEmpAssigment.positionId)
            if (positionName != null) {
                val updatePositionName = "Position Id Karsiligi gelen Position Name: $positionName"
                var idString = hrEmpAssigment.positionId.toString()
                idString = updatePositionName
            }
        }
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_EMP_ASSIGNMENTS (" +
                    "EMPLOYEE_ID INTEGER, POSITION_ID INTEGER, START_DATE DATE, END_DATE DATE, UPDATE_DATE DATE," +
                    "CREATION_DATE DATE)"
        )
        values.put("EMPLOYEE_ID", hrEmpAssigment.employeeId)
        values.put("POSITION_ID", hrEmpAssigment.positionId)
        values.put("START_DATE", hrEmpAssigment.startDate)
        values.put("END_DATE", hrEmpAssigment.endDate)
        values.put("UPDATE_DATE", hrEmpAssigment.updateDate)
        values.put("CREATION_DATE", hrEmpAssigment.creationDate)

        val insertId = db.insert("HR_EMP_ASSIGNMENTS", null, values)
        db.close()
        return insertId
    }

    fun getAllHrEmpAssignments(): ArrayList<HrEmpAssigmentsModel> {
        val hrEmpAssigmentList: ArrayList<HrEmpAssigmentsModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_EMP_ASSIGNMENTS"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var assignmentId: Int
        var employeeId: Int
        var positionId: Int
        var startDate: String
        var endDate: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                assignmentId = cursor.getInt(cursor.getColumnIndex("ASSIGNMENT_ID"))
                employeeId = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID"))
                positionId = cursor.getInt(cursor.getColumnIndex("POSITION_ID"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val hrEmpAssigmentsInfo = HrEmpAssigmentsModel(
                    assigmentId = assignmentId,
                    employeeId = employeeId,
                    positionId = positionId,
                    startDate = startDate,
                    endDate = endDate,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                hrEmpAssigmentList.add(hrEmpAssigmentsInfo)
            } while (cursor.moveToNext())
        }
        return hrEmpAssigmentList
    }

    fun updateHrEmpAssignment(hrEmpAssignment: HrEmpAssigmentsModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("EMPLOYEE_ID", hrEmpAssignment.employeeId)
        contentValues.put("POSITION_ID", hrEmpAssignment.positionId)
        contentValues.put("START_DATE", hrEmpAssignment.startDate)
        contentValues.put("END_DATE", hrEmpAssignment.endDate)
        contentValues.put("UPDATE_DATE", hrEmpAssignment.updateDate)
        contentValues.put("CREATION_DATE", hrEmpAssignment.creationDate)

        val success = db.update(
            "HR_EMP_ASSIGNMENTS", contentValues, "ASSIGNMENT_ID=" + hrEmpAssignment.assigmentId,
            null
        )
        db.close()
        return success
    }

    fun deleteHrEMpAssignmentById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        val success = db.delete("HR_EMP_ASSIGNMENTS", "ASSIGNMENT_ID=$id", null)
        db.close()
        return success
    }

    fun getHrEmpAssignmentById(hrEmpAssignmentId: Int): HrEmpAssigmentsModel? {
        val db = this.writableDatabase
        val selectQuery =
            "SELECT * FROM HR_EMP_ASSIGNMENTS WHERE ASSIGNMENT_ID = $hrEmpAssignmentId"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrEmpAssignmentInfo: HrEmpAssigmentsModel? = null
        if (cursor.moveToFirst()) {
            var assignmentId = cursor.getInt(cursor.getColumnIndex("ASSIGNMENT_ID"))
            var empId = cursor.getInt(cursor.getColumnIndex("EMPLOYEE_ID"))
            var positionId = cursor.getInt(cursor.getColumnIndex("POSITION_ID"))
            var startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            var endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            hrEmpAssignmentInfo = HrEmpAssigmentsModel(
                assigmentId = assignmentId,
                employeeId = empId,
                positionId = positionId,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return hrEmpAssignmentInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS HR_EMP_ASSIGNMENTS")
        onCreate(db)
    }
}