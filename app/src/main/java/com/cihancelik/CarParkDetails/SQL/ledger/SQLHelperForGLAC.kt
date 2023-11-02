package com.cihancelik.CarParkDetails.SQL.ledger

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager.OnChecksumsReadyListener
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.ledger.glAccountCombinations.GLACModel

class SQLHelperForGLAC(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {

    fun insertGLAC(glac: GLACModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("SEGMENT1", glac.segment1)
        values.put("SEGMENT2", glac.segment2)
        values.put("SEGMENT3", glac.segment3)
        values.put("SEGMENT4", glac.segment4)
        values.put("SEGMENT5", glac.segment5)
        values.put("SEGMENT_COMBINATION", glac.segmentCombination)
        values.put("UPDATE_DATE", glac.updateDate)
        values.put("CREATION_DATE", glac.creationDate)

        val insertedId = db.insert("GL_ACCOUNT_COMBINATIONS",null, values)
        db.close()
        return insertedId
    }

    fun getAllGLAC(): ArrayList<GLACModel> {
        val glacList: ArrayList<GLACModel> = ArrayList()
        val selectQuery = "SELECT * FROM GL_ACCOUNT_COMBINATIONS"

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
        var segment1: String
        var segment2: String
        var segment3: String
        var segment4: String
        var segment5: String
        var segmentCombination: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()){
            do {
                id  = cursor.getInt(cursor.getColumnIndex("GL_COD_COM_ID"))
                segment1 = cursor.getString(cursor.getColumnIndex("SEGMENT1"))
                segment2 = cursor.getString(cursor.getColumnIndex("SEGMENT2"))
                segment3 = cursor.getString(cursor.getColumnIndex("SEGMENT3"))
                segment4 = cursor.getString(cursor.getColumnIndex("SEGMENT4"))
                segment5 = cursor.getString(cursor.getColumnIndex("SEGMENT5"))
                segmentCombination = cursor.getString(cursor.getColumnIndex("SEGMENT_COMBINATION"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val glacInfo = GLACModel(
                    glCodComId = id,
                    segment1 = segment1,
                    segment2 = segment2,
                    segment3 = segment3,
                    segment4 = segment4,
                    segment5 = segment5,
                    segmentCombination = segmentCombination,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                glacList.add(glacInfo)

            }while (cursor.moveToNext())
        }
        return glacList
    }
    fun updateGLAC(glac:GLACModel):Int{
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put("GL_COD_COM_ID",glac.glCodComId)
        contextValues.put("SEGMENT1",glac.segment1)
        contextValues.put("SEGMENT2",glac.segment2)
        contextValues.put("SEGMENT3",glac.segment3)
        contextValues.put("SEGMENT4",glac.segment4)
        contextValues.put("SEGMENT5",glac.segment5)
        contextValues.put("SEGMENT_COMBINATION",glac.segmentCombination)
        contextValues.put("UPDATE_DATE",glac.updateDate)
        contextValues.put("CREATION_DATE",glac.creationDate)

        val success =
            db.update("GL_ACCOUNT_COMBINATIONS",contextValues,"GL_COD_COM_ID="+glac.glCodComId,null)
        db.close()
        return success
    }
    fun deleteGLACById(id:Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("GL_COD_COM_ID",id)

        val success = db.delete("GL_ACCOUNT_COMBINATIONS", "GL_COD_COM_ID$id",null)
        db.close()
        return success
    }
    fun getGLACById(glacId:Int):GLACModel?{
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM GL_ACCOUNT_COMBINATIONS WHERE GL_COD_COM_ID = $glacId"

        val cursor : Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var glacInfo : GLACModel? = null
        if (cursor.moveToFirst()){
            val id  = cursor.getInt(cursor.getColumnIndex("GL_COD_COM_ID"))
            val segment1 = cursor.getString(cursor.getColumnIndex("SEGMENT1"))
            val segment2 = cursor.getString(cursor.getColumnIndex("SEGMENT2"))
            val segment3 = cursor.getString(cursor.getColumnIndex("SEGMENT3"))
            val segment4 = cursor.getString(cursor.getColumnIndex("SEGMENT4"))
            val segment5 = cursor.getString(cursor.getColumnIndex("SEGMENT5"))
            val segmentCombination = cursor.getString(cursor.getColumnIndex("SEGMENT_COMBINATION"))
            val updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            val creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            glacInfo = GLACModel(
                glCodComId = id,
                segment1 = segment1,
                segment2 = segment2,
                segment3 = segment3,
                segment4 = segment4,
                segment5 = segment5,
                segmentCombination = segmentCombination,
                updateDate = updateDate,
                creationDate = creationDate
            )
        }
        cursor?.close()
        db.close()
        return glacInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS GL_ACCOUNT_COMBINATIONS")
        onCreate(db)
    }
}