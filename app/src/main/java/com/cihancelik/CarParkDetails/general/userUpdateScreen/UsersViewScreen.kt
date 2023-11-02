package com.cihancelik.CarParkDetails.general.userUpdateScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.users.SQLiteHelperForUsers
import com.cihancelik.carparkcustomerdetails.R

class UsersViewScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var adapter = UserAdapter()
    private lateinit var sqlHelperForUsers: SQLiteHelperForUsers
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private var usersA: UserModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_view_screen)

        sqlHelperForUsers = SQLiteHelperForUsers(this)
        recyclerView = findViewById(R.id.userRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getUsers()

        adapter.setOnClickItem { users ->
            val intent = Intent(this, UsersMainScreen::class.java)
            intent.putExtra("selectedUserUpdate", users)
            startActivity(intent)
        }
        adapter.setOnclickDeleteItem {
            deleteteUser(it.userId)
        }
    }

    override fun onResume() {
        super.onResume()

        val updatedUsers =
            intent.getSerializableExtra("selectedUserInfo") as? UserModel
        if (updatedUsers != null){
            // guncellenmis veri varsa user degiskenini guncelle
            usersA = updatedUsers

            // edittextlere guncellenmis verileri yerlestir
            etUsername.setText(updatedUsers.username)
            etPassword.setText(updatedUsers.password)
            etEmail.setText(updatedUsers.email)
            etStartDate.setText(updatedUsers.startDate)
            etEndDate.setText(updatedUsers.endDate)
            etUpdateDate.setText(updatedUsers.updateDate)
            etCreationDate.setText(updatedUsers.creationDate)
        }

    }


    private fun getUsers() {
        val userList = sqlHelperForUsers.getAllUsers()
        adapter.addItems(userList)
    }
    private fun deleteteUser(id: Int) {
        if (id <= 0) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete User info?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yeap") { dialog, _ ->
            // delete to user info
            sqlHelperForUsers.deleteUsersById(id)

            // give all users info
            val usersList = sqlHelperForUsers.getAllUsers()
            var deletedUsersIndex = -1
            for (i in usersList.indices) {
                if (usersList[i].userId == id) {
                    deletedUsersIndex = i
                    break
                }
            }
            adapter.updateUsersList(usersList)
            dialog.dismiss()
            var goToUsersMainScreen = Intent(this,UsersMainScreen::class.java)
            startActivity(goToUsersMainScreen)
        }
        builder.setNegativeButton("No"){ dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

}