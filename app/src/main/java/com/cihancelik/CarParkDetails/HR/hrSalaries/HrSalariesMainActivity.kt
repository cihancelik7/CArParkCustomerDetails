package com.cihancelik.CarParkDetails.HR.hrSalaries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrSalaries
import com.cihancelik.carparkcustomerdetails.R

class HrSalariesMainActivity : AppCompatActivity() {
    private lateinit var etEmpId : EditText
    private lateinit var etAmount : EditText
    private lateinit var etStartDate : EditText
    private lateinit var etEndDate : EditText
    private lateinit var etUpdateDate : EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqliteHelperForHrSalaries:SQLiteHelperForHrSalaries
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_salaries_main)
    }
}