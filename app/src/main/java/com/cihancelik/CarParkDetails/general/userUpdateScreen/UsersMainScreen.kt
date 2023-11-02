package com.cihancelik.CarParkDetails.general.userUpdateScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.users.SQLiteHelperForUsers
import com.cihancelik.carparkcustomerdetails.R

class UsersMainScreen : AppCompatActivity() {
    private lateinit var etUsername: TextView
    private lateinit var etPassword: TextView
    private lateinit var etEmail: TextView
    private lateinit var etStartDate: TextView
    private lateinit var etEndDate: TextView
    private lateinit var etUpdateDate: TextView
    private lateinit var etCreationDate : TextView

    private lateinit var btnAdd : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqlHelper : SQLiteHelperForUsers

    private var usersInfo : UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_main_screen)

        initView()

        sqlHelper = SQLiteHelperForUsers(this)
        btnAdd.setOnClickListener { addUser() }

        val selectedUsersInfo =
            intent.getSerializableExtra("selectedUserInfo") as? UserModel

        if (selectedUsersInfo != null){
            etUsername.text = selectedUsersInfo.username
            etPassword.text = selectedUsersInfo.password
            etEmail.text = selectedUsersInfo.email
            etStartDate.text = selectedUsersInfo.startDate
            etEndDate.text = selectedUsersInfo.endDate
            etUpdateDate.text = selectedUsersInfo.updateDate
            etCreationDate.text = selectedUsersInfo.creationDate

            usersInfo = selectedUsersInfo

            var goToUserViewPage = Intent(this,UsersViewScreen::class.java)
            btnView.setOnClickListener { startActivity(goToUserViewPage) }

            var selectedUserUpdate = intent.getSerializableExtra("selectedUserUpdate") as? UserModel
            if (selectedUserUpdate != null){
                etUsername.text = selectedUserUpdate.username
                etPassword.text = selectedUserUpdate.password
                etEmail.text = selectedUserUpdate.email
                etStartDate.text = selectedUserUpdate.startDate
                etEndDate.text = selectedUserUpdate.endDate
                etUpdateDate.text = selectedUserUpdate.updateDate
                etCreationDate.text = selectedUserUpdate.creationDate

            }
            btnUpdate.setOnClickListener { updateUser() }
        }
    }

    private fun addUser() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val email = etEmail.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() ||
            startDate.isEmpty()|| updateDate.isEmpty()|| creationDate.isEmpty()){
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val userInfo = UserModel(
                username = username,
                password = password,
                email = email,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val status: Long = sqlHelper.insertUsers(userInfo)

            if (status > -1){
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateUser(){
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val email = etEmail.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (usersInfo != null){
            val updatedUser = UserModel(
                userId = usersInfo!!.userId,
                username = username,
                password = password,
                email = email,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val status = sqlHelper.updateUsers(updatedUser)
            if (status > -1){
                Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                etUsername.text = updatedUser.username
                etPassword.text = updatedUser.password
                etEmail.text = updatedUser.email
                etStartDate.text = updatedUser.startDate
                etEndDate.text = updatedUser.endDate
                etUpdateDate.text = updatedUser.updateDate
                etCreationDate.text = updatedUser.creationDate

                usersInfo = updatedUser
            }else{
                Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etUsername.text = ""
        etPassword.text = ""
        etEmail.text = ""
        etStartDate.text = ""
        etEndDate.text = ""
        etUpdateDate.text = ""
        etCreationDate.text = ""

        etUsername.requestFocus()
    }

    private fun initView(){
        etUsername = findViewById(R.id.userUserName)
        etPassword = findViewById(R.id.userPassword)
        etEmail = findViewById(R.id.userEmail)
        etStartDate = findViewById(R.id.userStartDate)
        etEndDate = findViewById(R.id.userEndDate)
        etUpdateDate = findViewById(R.id.userUpdateDate)
        etCreationDate = findViewById(R.id.userCreationDate)

        btnAdd = findViewById(R.id.btnUserAdd)
        btnView = findViewById(R.id.btnUserView)
        btnUpdate = findViewById(R.id.btnUserUpdate)

    }
}















