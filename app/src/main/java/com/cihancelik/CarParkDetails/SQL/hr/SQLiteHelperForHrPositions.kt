package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrPositions.HrPositionsModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase

class SQLiteHelperForHrPositions(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {

    private val jobHelper = SQLiteHelperForHrJobs(context)

    fun insertPosition(hrPosition: HrPositionsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val jobIdFk = jobHelper.getHrJobById(hrPosition.jobId)

        if (jobIdFk != null) {
            val jobName = jobHelper.getHrJobNameById(hrPosition.jobId)
            if (jobName != null) {
                val updateJobName = "Job Id karsiliginda gelen Job Name: $jobName"
                var idStr = hrPosition.jobId.toString()
                idStr = updateJobName
            }
        }
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_POSITIONS(POSITION_NAME TEXT," +
                    "START_DATE DATE,END_DATE DATE, JOB_ID INTEGER, UPDATE_DATE DATE," +
                    "CREATION_DATE DATE" +
                    ")"
        )
        values.put("POSITION_NAME", hrPosition.positionName)
        values.put("START_DATE", hrPosition.startDate)
        values.put("END_DATE", hrPosition.endDate)
        values.put("JOB_ID", hrPosition.jobId)
        values.put("UPDATE_DATE", hrPosition.updateDate)
        values.put("CREATION_DATE", hrPosition.creationDate)
        val insertId = db.insert("HR_POSITIONS", null, values)
        db.close()
        return insertId
    }

    fun getAllPositions(): ArrayList<HrPositionsModel> {
        val hrPositionList: ArrayList<HrPositionsModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_POSITIONS"
        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var positionId: Int
        var positionName: String
        var startDate: String
        var endDate: String
        var jobId: Int
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {


                positionId = cursor.getInt(cursor.getColumnIndex("POSITION_ID"))
                positionName = cursor.getString(cursor.getColumnIndex("POSITION_NAME"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                jobId = cursor.getInt(cursor.getColumnIndex("JOB_ID"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val hrpositionInfo = HrPositionsModel(
                    positionId = positionId,
                    positionName = positionName,
                    startDate = startDate,
                    endDate = endDate,
                    jobId = jobId,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                hrPositionList.add(hrpositionInfo)
            } while (cursor.moveToNext())
        }
        return hrPositionList
    }

    fun updateHrPosition(hrPosition: HrPositionsModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("POSITION_NAME", hrPosition.positionName)
        contentValues.put("START_DATE", hrPosition.startDate)
        contentValues.put("END_DATE", hrPosition.endDate)
        contentValues.put("JOB_ID", hrPosition.jobId)
        contentValues.put("UPDATE_DATE", hrPosition.updateDate)
        contentValues.put("CREATION_DATE", hrPosition.creationDate)

        val success = db.update(
            "HR_POSITIONS",
            contentValues,
            "POSITION_ID=" + hrPosition.positionId,
            null
        )
        db.close()
        return success
    }

    fun deleteHrPositionById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("POSITION_ID", id)
        val success = db.delete("HR_POSITIONS", "POSITION_ID=$id", null)
        db.close()
        return success
    }
    fun getPositionById(hrPositionId:Int):HrPositionsModel?{
        val db = writableDatabase
        val selectQuery = "SELECT * FROM HR_POSITIONS WHERE POSITION_ID = $hrPositionId"

        val cursor : Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrPositionInfo : HrPositionsModel? = null
        if (cursor.moveToFirst()){
            var positionId = cursor.getInt(cursor.getColumnIndex("POSITION_ID"))
            var positionName = cursor.getString(cursor.getColumnIndex("POSITION_NAME"))
            var startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            var endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            var jobId = cursor.getInt(cursor.getColumnIndex("JOB_ID"))
            var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            hrPositionInfo = HrPositionsModel(
                positionId = positionId,
                positionName = positionName,
                startDate = startDate,
                endDate = endDate,
                jobId = jobId,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return hrPositionInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF NOT EXISTS HR_POSITIONS")
        onCreate(db)
    }
    fun getPositionNameById(positionId : Int):String?{
        val db = this.readableDatabase
        val selectQuery = "SELECT POSITION_NAME FROM HR_POSITIONS WHERE POSITION_ID = ?"
        var positionName : String? = null
        val cursor : Cursor? = db.rawQuery(selectQuery, arrayOf(positionId.toString()))

    if (cursor!=null) {
        if (cursor.moveToFirst()) {
            positionName = cursor.getString(cursor.getColumnIndex("POSITION_NAME"))
        }
        cursor.close()
    }
        db.close()
        return positionName
    }
}

