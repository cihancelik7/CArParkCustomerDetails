package com.cihancelik.CarParkDetails.SQL.ledger

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.ledger.glJournalLines.GLJLModel

class SQLHelperForGLJL(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {
    private val journalHelper = SQLHelperForGLJournals(context)
    fun insertJL(gljl: GLJLModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val glJournal = journalHelper.getGLJournalById(gljl.journalId)
        if (glJournal != null) {
            val status = journalHelper.getGLJournalNameById(gljl.journalId)
            if (status != null) {
                val updatedStatus = "Period Id Karsiligida gelen Status: $status"
                var idString = gljl.journalId.toString()
                idString = updatedStatus
            }
        }
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS GL_JOURNAL_LINES(JOURNAL_ID INTEGER, JOURNAL_DATE DATE,GL_COD_COM_ID INTEGER," +
                        " ACCOUNTED_CR_AMOUNT INTEGER, ACCOUNTED_DR_AMOUNT INTEGER,UPDATE_DATE DATE,CREATION_DATE DATE)"
            )
            values.put("JOURNAL_ID", gljl.journalId)
            values.put("JOURNAL_DATE", gljl.journalDate)
            values.put("GL_COD_COM_ID", gljl.glCodComId)
            values.put("ACCOUNTED_CR_AMOUNT", gljl.accountedCrAmount)
            values.put("ACCOUNTED_DR_AMOUNT", gljl.accountedDrAmount)
            values.put("UPDATE_DATE", gljl.updateDate)
            values.put("CREATION_DATE", gljl.creationDate)

            val insertId = db.insert("GL_JOURNAL_LINES", null, values)
            db.close()
            return insertId
        }
        fun getAllGLJL(): ArrayList<GLJLModel> {
            val gljlList: ArrayList<GLJLModel> = ArrayList()
            val selectQuery = "SELECT * FROM GL_JOURNAL_LINES"

            val db = this.writableDatabase
            val cursor: Cursor?
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: Exception) {
                e.printStackTrace()
                db.execSQL(selectQuery)
                return ArrayList()
            }
            var journalLineId: Int
            var journalId: Int
            var journalDate: String
            var glCodComId: Int
            var accountedDrAmount: Int
            var accountedCrAmount: Int
            var updateDate: String
            var creationDate: String

            if (cursor.moveToFirst()) {
                do {
                    journalLineId = cursor.getInt(cursor.getColumnIndex("JOURNAL_LINE_ID"))
                    journalId = cursor.getInt(cursor.getColumnIndex("JOURNAL_ID"))
                    journalDate = cursor.getString(cursor.getColumnIndex("JOURNAL_DATE"))
                    glCodComId = cursor.getInt(cursor.getColumnIndex("GL_COD_COM_ID"))
                    accountedDrAmount = cursor.getInt(cursor.getColumnIndex("ACCOUNTED_DR_AMOUNT"))
                    accountedCrAmount = cursor.getInt(cursor.getColumnIndex("ACCOUNTED_CR_AMOUNT"))
                    updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                    creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                    val gljlInfo = GLJLModel(
                        journalLineId = journalLineId,
                        journalId = journalId,
                        journalDate = journalDate,
                        glCodComId = glCodComId,
                        accountedDrAmount = accountedDrAmount,
                        accountedCrAmount = accountedCrAmount,
                        updateDate = updateDate,
                        creationDate = creationDate
                    )
                    gljlList.add(gljlInfo)
                } while (cursor.moveToNext())
            }
            return gljlList
        }

        fun updateGLJL(gljl: GLJLModel): Int {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("JOURNAL_ID", gljl.journalId)
            contentValues.put("JOURNAL_DATE", gljl.journalDate)
            contentValues.put("GL_COD_COM_ID", gljl.glCodComId)
            contentValues.put("ACCOUNTED_CR_AMOUNT", gljl.accountedCrAmount)
            contentValues.put("ACCOUNTED_DR_AMOUNT", gljl.accountedDrAmount)
            contentValues.put("UPDATE_DATE", gljl.updateDate)
            contentValues.put("CREATION_DATE", gljl.creationDate)

            val success =
                db.update(
                    "GL_JOURNAL_LINES",
                    contentValues,
                    "JOURNAL_LINE_ID=" + gljl.journalLineId,
                    null
                )
            db.close()
            return success
        }

        fun deleteGLJLById(id: Int): Int {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("JOURNAL_LINE_ID", id)

            val success = db.delete("GL_JOURNAL_LINES", "JOURNAL_LINE_ID=$id", null)
            db.close()
            return success
        }

        fun getGLJLById(gljlId: Int): GLJLModel? {
            val db = this.writableDatabase
            val selectQuery = "SELECT * FROM GL_JOURNAL_LINES WHERE JOURNAL_LINE_ID = $gljlId"

            val cursor: Cursor?
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: Exception) {
                e.printStackTrace()
                db.execSQL(selectQuery)
                return null
            }
            var gljlInfo: GLJLModel? = null
            if (cursor.moveToFirst()) {
                var journalLineId = cursor.getInt(cursor.getColumnIndex("JOURNAL_LINE_ID"))
                var journalId = cursor.getInt(cursor.getColumnIndex("JOURNAL_ID"))
                var journalDate = cursor.getString(cursor.getColumnIndex("JOURNAL_DATE"))
                var glCodComId = cursor.getInt(cursor.getColumnIndex("GL_COD_COM_ID"))
                var accountedDrAmount = cursor.getInt(cursor.getColumnIndex("ACCOUNTED_DR_AMOUNT"))
                var accountedCrAmount = cursor.getInt(cursor.getColumnIndex("ACCOUNTED_CR_AMOUNT"))
                var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val gljlinfo = GLJLModel(
                    journalLineId = journalLineId,
                    journalId = journalId,
                    journalDate = journalDate,
                    glCodComId = glCodComId,
                    accountedDrAmount = accountedDrAmount,
                    accountedCrAmount = accountedCrAmount,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                gljlInfo = gljlinfo
            }
            cursor?.close()
            db.close()
            return gljlInfo
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            super.onUpgrade(db, oldVersion, newVersion)
            db!!.execSQL("DROP TABLE IF EXISTS GL_JOURNAL_LINES")
            onCreate(db)

        }

        /*fun getGLJLNameById(gljlId: Int): String? {
           // FK OLURSA EGER BURAYA YAZILACAK!!!!

        }*/
    }