package com.cihancelik.CarParkDetails.SQL.hr

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.HR.hrJobs.HrJobsModel
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase

class SQLiteHelperForHrJobs(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {

    fun insertHrJob(hrJob: HrJobsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS HR_JOBS (JOB_NAME TEXT,START_DATE DATE," +
                    "END_DATE DATE, UPDATE_DATE DATE, CREATION_DATE DATE)"
        )

        values.put("JOB_NAME", hrJob.jobName)
        values.put("START_DATE", hrJob.startDate)
        values.put("END_DATE", hrJob.endDate)
        values.put("UPDATE_DATE", hrJob.updateDate)
        values.put("CREATION_DATE", hrJob.creationDate)

        val insertId = db.insert("HR_JOBS", null, values)
        db.close()
        return insertId
    }

    fun getAllHrJobs(): ArrayList<HrJobsModel> {
        val hrJobsList: ArrayList<HrJobsModel> = ArrayList()
        val selectQuery = "SELECT * FROM HR_JOBS"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var jobId: Int
        var jobName: String
        var startDate: String
        var endDate: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                jobId = cursor.getInt(cursor.getColumnIndex("JOB_ID"))
                jobName = cursor.getString(cursor.getColumnIndex("JOB_NAME"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val hrJpbsInfo = HrJobsModel(
                    jobId = jobId,
                    jobName = jobName,
                    startDate = startDate,
                    endDate = endDate,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                hrJobsList.add(hrJpbsInfo)
            } while (cursor.moveToNext())
        }
        return hrJobsList
    }

    fun updateHrJob(hrJob: HrJobsModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put("JOB_NAME", hrJob.jobName)
        contextValues.put("START_DATE", hrJob.startDate)
        contextValues.put("END_DATE", hrJob.endDate)
        contextValues.put("UPDATE_DATE", hrJob.updateDate)
        contextValues.put("CREATION_DATE", hrJob.creationDate)

        val success = db.update("HR_JOBS", contextValues, "JOB_ID=" + hrJob.jobId, null)
        db.close()
        return success
    }

    fun deleteHrJobsById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("JOB_ID", id)

        val success = db.delete("HR_JOBS", "JOB_ID=$id", null)
        db.close()
        return success
    }

    fun getHrJobById(hrJobId: Int): HrJobsModel? {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM HR_JOBS WHERE JOB_ID = $hrJobId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var hrJobsInfo: HrJobsModel? = null
        if (cursor.moveToFirst()) {
            val jobId = cursor.getInt(cursor.getColumnIndex("JOB_ID"))
            val jobName = cursor.getString(cursor.getColumnIndex("JOB_NAME"))
            val startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
            val endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
            val updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            val creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            hrJobsInfo = HrJobsModel(
                jobId = jobId,
                jobName = jobName,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return hrJobsInfo
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS HR_JOBS")
        onCreate(db)
    }

    fun getHrJobNameById(jobId: Int): String? {
        val db = this.writableDatabase
        val selectQuery = "SELECT JOB_NAME FROM HR_JOBS WHERE JOB_ID = $jobId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var jobName: String? = null
        if (cursor.moveToFirst()) {
            jobName = cursor.getString(cursor.getColumnIndex("JOB_NAME"))
        }
        cursor?.close()
        db.close()
        return jobName
    }
}