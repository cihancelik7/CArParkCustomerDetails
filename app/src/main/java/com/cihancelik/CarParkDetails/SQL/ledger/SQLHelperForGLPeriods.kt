package com.cihancelik.CarParkDetails.SQL.ledger

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.ledger.glPeriods.GLPeriodsModel

class SQLHelperForGLPeriods(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {

    fun insertGLPeriod(glPeriod: GLPeriodsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        db.execSQL("CREATE TABLE IF NOT EXISTS GL_PERIODS (PERIOD_NAME TEXT, YEAR TEXT, UPDATE_DATE DATE,CREATION_DATE )")

        values.put("PERIOD_NAME", glPeriod.periodName)
        values.put("YEAR", glPeriod.periodYear)
        values.put("UPDATE_DATE", glPeriod.updateDate)
        values.put("CREATION_DATE", glPeriod.creationDate)

        val insertId = db.insert("GL_PERIODS", null, values)
        db.close()
        return insertId
    }

    fun getAllGLPeriod(): ArrayList<GLPeriodsModel> {
        val glPeriodList: ArrayList<GLPeriodsModel> = ArrayList()
        val selectQuery = "SELECT * FROM GL_PERIODS"

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
        var periodName: String
        var year: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getInt(cursor.getColumnIndex("PERIOD_ID"))
                periodName = cursor.getString(cursor.getColumnIndex("PERIOD_NAME"))
                year = cursor.getString(cursor.getColumnIndex("YEAR"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val glPeriodInfo = GLPeriodsModel(
                    periodId = id,
                    periodName = periodName,
                    periodYear = year,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                glPeriodList.add(glPeriodInfo)
            } while (cursor.moveToNext())
        }
        return glPeriodList
    }

    fun updateGLPeriod(glPeriod: GLPeriodsModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put("PERIOD_NAME", glPeriod.periodName)
        contextValues.put("YEAR", glPeriod.periodYear)
        contextValues.put("UPDATE_DATE", glPeriod.updateDate)
        contextValues.put("CREATION_DATE", glPeriod.creationDate)

        val success =
            db.update("GL_PERIODS", contextValues, "PERIOD_ID=" + glPeriod.periodId, null)
        db.close()
        return success
    }
    fun deleteGLPeriodById(id:Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("PERIOD_ID",id)

        val success = db.delete("GL_PERIODS","PERIOD_ID=$id",null)
        db.close()
        return success
    }
    fun getGLPeriodById(glPeriodId :Int):GLPeriodsModel?{
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM GL_PERIODS WHERE PERIOD_ID = $glPeriodId"

        val cursor : Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var glPeriodInfo : GLPeriodsModel? = null
        if (cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex("PERIOD_ID"))
            val periodName = cursor.getString(cursor.getColumnIndex("PERIOD_NAME"))
            val year = cursor.getString(cursor.getColumnIndex("YEAR"))
            val updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            val creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            glPeriodInfo = GLPeriodsModel(
                periodId = id,
                periodName = periodName,
                periodYear = year,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return glPeriodInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS GL_PERIODS")
        onCreate(db)
    }
}