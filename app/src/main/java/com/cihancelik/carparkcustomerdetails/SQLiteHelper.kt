package com.cihancelik.carparkcustomerdetails

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "costumer.db"
        private const val TABLE_CUSTOMER = "table_customer"
        private const val ID = "id"
        private const val NAME = "name"
        private const val LASTNAME = "lastname"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val ADDRESS = "address"
        private const val CITY = "city"
        private const val CARPLATE = "carplate"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableCostumer = ("CREATE TABLE " + TABLE_CUSTOMER + "("
                + ID + "INTEGER PRIMARY KEY, " + NAME + "TEXT,"
                + LASTNAME + "TEXT," + EMAIL + " TEXT," + PHONE + " TEXT," + ADDRESS + " TEXT,"
                + CITY + " TEXT," + CARPLATE + " TEXT" + ")")
        db?.execSQL(createTableCostumer)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMER")
        onCreate(db)

    }

    fun insertCustomer(customer: CustomerModel) :Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,customer.id)
        contentValues.put(NAME,customer.name)
        contentValues.put(LASTNAME,customer.lastName)
        contentValues.put(EMAIL,customer.email)
        contentValues.put(PHONE,customer.phone)
        contentValues.put(ADDRESS,customer.address)
        contentValues.put(CITY,customer.city)
        contentValues.put(CARPLATE,customer.carplate)

        val success = db.insert(TABLE_CUSTOMER,null,contentValues)
        db.close()
        return success
    }

}