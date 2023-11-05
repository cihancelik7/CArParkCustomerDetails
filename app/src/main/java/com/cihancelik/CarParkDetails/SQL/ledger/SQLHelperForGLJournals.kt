package com.cihancelik.CarParkDetails.SQL.ledger

import GLJournalsModel
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.ledger.glPeriods.GLPeriodsModel


class SQLHelperForGLJournals(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {

    private val glPeriodsHelper = SQLHelperForGLPeriods(context)
    fun insertJournal(glJournal: GLJournalsModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        val glPeriod = glPeriodsHelper.getGLPeriodById(glJournal.periodId)
        if (glPeriod != null) {
            // Örnek olarak GL_PERIODS verisinden bir alanı kullanarak GL_JOURNALS verisini güncelleyebiliriz.
            val updatedStatus = "Updated Status"
            glJournal.status = updatedStatus
        }

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS GL_JOURNALS(PERIOD_ID INTEGER,JOURNAL_DATE DATE, STATUS TEXT," +
                    "AMOUNT NUMBER(10), UPDATE_DATE DATE, CREATION_DATE DATE)"
        )

        values.put("PERIOD_ID", glJournal.periodId)
        values.put("JOURNAL_DATE", glJournal.journalDate)
        values.put("STATUS", glJournal.status)
        values.put("AMOUNT", glJournal.amount)
        values.put("UPDATE_DATE", glJournal.updateDate)
        values.put("CREATION_DATE", glJournal.creationDate)

        val insertId = db.insert("GL_JOURNALS", null, values)
        db.close()
        return insertId
    }
    fun getGLPeriodForJournal(journal: GLJournalsModel): GLPeriodsModel? {
        // GL_PERIODS tablosundan veriyi çekmek için SQLHelperForGLPeriods sınıfını kullanabiliriz.
        return glPeriodsHelper.getGLPeriodById(journal.periodId)
    }

    fun getAllPeriod(): ArrayList<GLJournalsModel> {
        val glJournalsList: ArrayList<GLJournalsModel> = ArrayList()
        val selectQuery = "SELECT * FROM GL_JOURNALS"

        val db = this.writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var journalId: Int
        var periodId: Int
        var journalDate: String
        var status: String
        var amount: Int // Değişiklik yapıldı
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                journalId = cursor.getInt(cursor.getColumnIndex("JOURNAL_ID"))
                periodId = cursor.getInt(cursor.getColumnIndex("PERIOD_ID"))
                journalDate = cursor.getString(cursor.getColumnIndex("JOURNAL_DATE"))
                status = cursor.getString(cursor.getColumnIndex("STATUS"))
                amount = cursor.getInt(cursor.getColumnIndex("AMOUNT")) // Değişiklik yapıldı
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val glJournalInfo = GLJournalsModel(
                    journalId = journalId,
                    periodId = periodId,
                    journalDate = journalDate,
                    status = status,
                    amount = amount,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                glJournalsList.add(glJournalInfo) // add ile düzeltildi
            } while (cursor.moveToNext())
        }
        return glJournalsList
    }

    fun updateJournal(journal: GLJournalsModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("PERIOD_ID", journal.periodId)
        contentValues.put("JOURNAL_DATE", journal.journalDate)
        contentValues.put("STATUS", journal.status)
        contentValues.put("AMOUNT", journal.amount)
        contentValues.put("UPDATE_DATE", journal.updateDate)
        contentValues.put("CREATION_DATE", journal.creationDate)

        val success =
            db.update("GL_JOURNALS", contentValues, "JOURNAL_ID=" + journal.journalId, null)
        db.close()
        return success
    }

    fun deleteJournalById(journalId: Int): GLJournalsModel? {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM GL_JOURNALS WHERE JOURNAL_ID = $journalId"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return null
        }
        var journalInfo: GLJournalsModel? = null
        if (cursor.moveToFirst()) {
            var journalId = cursor.getInt(cursor.getColumnIndex("JOURNAL_ID"))
            var periodId = cursor.getInt(cursor.getColumnIndex("PERIOD_ID"))
            var journalDate = cursor.getString(cursor.getColumnIndex("JOURNAL_DATE"))
            var status = cursor.getString(cursor.getColumnIndex("STATUS"))
            var amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
            var updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
            var creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

            val glJournalInfo = GLJournalsModel(
                journalId = journalId,
                periodId = periodId,
                journalDate = journalDate,
                status = status,
                amount = amount,
                updateDate = updateDate,
                creationDate = creationDate
            )
            journalInfo = glJournalInfo
        }
        cursor?.close()
        db.close()
        return journalInfo
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db!!.execSQL("DROP TABLE IF EXISTS GL_JOURNALS")
        onCreate(db)
    }
}
