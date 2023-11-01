package com.cihancelik.CarParkDetails.SQL.users

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.cihancelik.CarParkDetails.SQL.SQLiteHelperForCarParkDataBase
import com.cihancelik.CarParkDetails.general.userUpdateScreen.UserModel

class SQLiteHelperForUsers(context: Context) :
    SQLiteHelperForCarParkDataBase(context) {

    fun insertUsers(users: UserModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("USER_NAME", users.username)
        values.put("PASSWORD", users.password)
        values.put("EMAIL_ADDRESS", users.email)
        values.put("START_DATE", users.startDate)
        values.put("END_DATE", users.endDate)
        values.put("UPDATE_DATE", users.updateDate)
        values.put("CREATION_DATE", users.creationDate)


        val insertedId = db.insert("USERS", null, values)
        db.close()
        return insertedId
    }

    fun getAllUsers(): ArrayList<UserModel> {
        val usersList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM USERS"

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
        var username: String
        var password: String
        var email: String
        var startDate: String
        var endDate: String
        var updateDate: String
        var creationDate: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("USER_ID"))
                username = cursor.getString(cursor.getColumnIndex("USER_NAME"))
                password = cursor.getString(cursor.getColumnIndex("PASSWORD"))
                email = cursor.getString(cursor.getColumnIndex("EMAIL_ADDRESS"))
                startDate = cursor.getString(cursor.getColumnIndex("START_DATE"))
                endDate = cursor.getString(cursor.getColumnIndex("END_DATE"))
                updateDate = cursor.getString(cursor.getColumnIndex("UPDATE_DATE"))
                creationDate = cursor.getString(cursor.getColumnIndex("CREATION_DATE"))

                val usersInfo = UserModel(
                    userId = id,
                    username = username,
                    password = password,
                    email = email,
                    startDate = startDate,
                    endDate = endDate,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                usersList.add(usersInfo)

            } while (cursor.moveToNext())
        }
        return usersList
    }

    fun updateUsers(users: UserModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put("USER_ID",users.userId)
        contextValues.put("USER_NAME",users.username)
        contextValues.put("PASSWORD",users.password)
        contextValues.put("EMAIL_ADDRESS",users.email)
        contextValues.put("START_DATE",users.startDate)
        contextValues.put("END_DATE",users.endDate)
        contextValues.put("UPDATE_DATE",users.updateDate)
        contextValues.put("CREATION_DATE",users.creationDate)

        val success =
            db.update("USERS",contextValues,"USER_ID="+ users.userId,null)
        db.close()
        return success
    }

    fun deleteUsersById(id:Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("USERS_ID",id)

        val success = db.delete("USERS","USERS_ID= $id",null)
        db.close()
        return success
    }

}