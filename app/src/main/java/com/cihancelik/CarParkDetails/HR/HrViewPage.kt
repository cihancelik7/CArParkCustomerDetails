package com.cihancelik.CarParkDetails.HR

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cihancelik.CarParkDetails.HR.hrEmpAssigments.HrEmpAssigmentsMainActivity
import com.cihancelik.CarParkDetails.HR.hrEmployees.HrEmployeesMainActivity
import com.cihancelik.CarParkDetails.HR.hrJobs.HrJobsMainActivity
import com.cihancelik.CarParkDetails.HR.hrLocations.HrLocationsMainActivity
import com.cihancelik.CarParkDetails.HR.hrOrganizations.HrOrganizationsMainActivity
import com.cihancelik.CarParkDetails.HR.hrPositions.HrPositionsMainActivity
import com.cihancelik.CarParkDetails.HR.hrSalaries.HrSalariesMainActivity
import com.cihancelik.carparkcustomerdetails.R

class HrViewPage : AppCompatActivity() {
    private lateinit var btnHrEmp : Button
    private lateinit var btnHrEmpAssignments: Button
    private lateinit var btnHrJobs:Button
    private lateinit var btnHrPositions : Button
    private lateinit var btnHrLocations : Button
    private lateinit var btnHrSalaries : Button
    private lateinit var btnHrOrganization : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_view_page)

        btnHrEmp = findViewById(R.id.hrEmployeesMainScreenBtn)
        btnHrEmpAssignments = findViewById(R.id.hrEmpAssigmentsMainScreenBtn)
        btnHrJobs = findViewById(R.id.hrJobsMainScreenBtn)
        btnHrPositions = findViewById(R.id.hrPositionsMainScreenBtn)
        btnHrLocations = findViewById(R.id.hrLocationsMainScreenBtn)
        btnHrSalaries = findViewById(R.id.hrSalariesMainScreenBtn)
        btnHrOrganization = findViewById(R.id.hrOrganizationsMainScreenBtn)

        val goToHrEmpMainActivity = Intent(this,HrEmployeesMainActivity::class.java)
        btnHrEmp.setOnClickListener { startActivity(goToHrEmpMainActivity) }

        val goToHrEmpAssigmentsMainActivity = Intent(this,HrEmpAssigmentsMainActivity::class.java)
        btnHrEmpAssignments.setOnClickListener { startActivity(goToHrEmpAssigmentsMainActivity) }

        val goToHrJobsMainActivity = Intent(this,HrJobsMainActivity::class.java)
        btnHrJobs.setOnClickListener { startActivity(goToHrJobsMainActivity) }

        val goToHrPositionsMainActivity = Intent(this,HrPositionsMainActivity::class.java)
        btnHrPositions.setOnClickListener { startActivity(goToHrPositionsMainActivity) }

        val goToHrLocationsMainActivity = Intent(this,HrLocationsMainActivity::class.java)
        btnHrLocations.setOnClickListener { startActivity(goToHrLocationsMainActivity) }

        val goToHrSalariesMainActivity = Intent(this,HrSalariesMainActivity::class.java)
        btnHrSalaries.setOnClickListener { startActivity(goToHrSalariesMainActivity) }

        val goToHrOrganizationsMainActivity = Intent(this,HrOrganizationsMainActivity::class.java)
        btnHrOrganization.setOnClickListener { startActivity(goToHrOrganizationsMainActivity) }

    }
}