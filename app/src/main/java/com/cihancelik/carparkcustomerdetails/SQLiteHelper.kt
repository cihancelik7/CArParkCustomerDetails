package com.cihancelik.carparkcustomerdetails

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
        val createTableCustomer = ("CREATE TABLE " + TABLE_CUSTOMER + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + LASTNAME + " TEXT, "
                + EMAIL + " TEXT, "
                + PHONE + " TEXT, "
                + ADDRESS + " TEXT, "
                + CITY + " TEXT, "
                + CARPLATE + " TEXT" + ")")

        db?.execSQL(createTableCustomer)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Bu sürüme kadar olan tabloyu güncelle
            db?.execSQL("ALTER TABLE $TABLE_CUSTOMER ADD COLUMN $LASTNAME TEXT")
        }
    }


    fun insertCustomer(customer: CustomerModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, customer.name)
        contentValues.put(LASTNAME, customer.lastName)
        contentValues.put(EMAIL, customer.email)
        contentValues.put(PHONE, customer.phone)
        contentValues.put(ADDRESS, customer.address)
        contentValues.put(CITY, customer.city)
        contentValues.put(CARPLATE, customer.carplate)

        val success = db.insert(TABLE_CUSTOMER, null, contentValues)
        db.close()
        return success
    }

    fun getAllCustomers(): ArrayList<CustomerModel> {
        val customerList: ArrayList<CustomerModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_CUSTOMER"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var lastName: String
        var email: String
        var phone: String
        var address: String
        var city: String
        var carplate: String


        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                lastName = cursor.getString(cursor.getColumnIndex("lastname"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))
                address = cursor.getString(cursor.getColumnIndex("address"))
                city = cursor.getString(cursor.getColumnIndex("city"))
                carplate = cursor.getString(cursor.getColumnIndex("carplate"))
                val cst = CustomerModel(
                    id = id,
                    name = name,
                    lastName = lastName,
                    email = email,
                    phone = phone,
                    address = address,
                    city = city,
                    carplate = carplate
                )

                customerList.add(cst)

            } while (cursor.moveToNext())
        }

        return customerList


    }

}