package com.cihancelik.CarParkDetails.HR.hrJobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrJobs
import com.cihancelik.carparkcustomerdetails.R

class HrJobsMainActivity : AppCompatActivity() {
    private lateinit var etJobName: TextView
    private lateinit var etStartDate: TextView
    private lateinit var etEndDate: TextView
    private lateinit var etUpdateDate: TextView
    private lateinit var etCreationDate: TextView

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelper: SQLiteHelperForHrJobs
    private var hrJobInfo: HrJobsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_jobs_main)

        initView()

        sqlHelper = SQLiteHelperForHrJobs(this)
        btnAdd.setOnClickListener { addHrJob() }

        val selectedHrJobInfo =
            intent.getSerializableExtra("selectedHrJobsInfo") as? HrJobsModel

        if (selectedHrJobInfo != null) {
            etJobName.text = selectedHrJobInfo.jobName
            etStartDate.text = selectedHrJobInfo.startDate
            etEndDate.text = selectedHrJobInfo.endDate
            etUpdateDate.text = selectedHrJobInfo.updateDate
            etCreationDate.text = selectedHrJobInfo.creationDate

            hrJobInfo = selectedHrJobInfo
        }
        val goToHrJobsViewActivity = Intent(this, HrJobsViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrJobsViewActivity) }

        val selectedHrJobsUpdate =
            intent.getSerializableExtra("selectedHrJobsUpdate") as? HrJobsModel
        if (selectedHrJobsUpdate != null){
            etJobName.text = selectedHrJobsUpdate.jobName
            etStartDate.text = selectedHrJobsUpdate.startDate
            etEndDate.text = selectedHrJobsUpdate.endDate
            etUpdateDate.text = selectedHrJobsUpdate.updateDate
            etCreationDate.text = selectedHrJobsUpdate.creationDate

            hrJobInfo = selectedHrJobsUpdate
        }
        btnUpdate.setOnClickListener { updateHrJobs() }
    }

    private fun updateHrJobs() {
        val jobName = etJobName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (hrJobInfo!=null){
            var updatedHrJob = HrJobsModel(
                jobId = hrJobInfo!!.jobId,
                jobName = jobName,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdated = isUpdated(updatedHrJob)
            if (isUpdated){
                val status = sqlHelper.updateHrJob(updatedHrJob)
                if (status > -1){
                    Toast.makeText(this, "Update Succesful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HrJobsMainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdated(updatedHrJob: HrJobsModel): Boolean {
        val currentHrJobs = sqlHelper.getHrJobById(updatedHrJob.jobId)
        return currentHrJobs != updatedHrJob
    }

    private fun addHrJob() {
        val jobName = etJobName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (jobName.isEmpty() || startDate.isEmpty() || updateDate.isEmpty() || creationDate.isEmpty()) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val hrJobInfo = HrJobsModel(
                jobName = jobName,
                startDate = startDate,
                endDate = endDate,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val status: Long = sqlHelper.insertHrJob(hrJobInfo)
            if (status > -1) {
                Toast.makeText(this, "Hr Job Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Record Not Saved!!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etJobName.text = ""
        etStartDate.text = ""
        etEndDate.text = ""
        etUpdateDate.text = ""
        etCreationDate.text = ""

        etJobName.requestFocus()
    }

    private fun initView() {
        etJobName = findViewById(R.id.hrJobsNameTv)
        etStartDate = findViewById(R.id.hrJobsStartDateTv)
        etEndDate = findViewById(R.id.hrJobsEndDateTV)
        etUpdateDate = findViewById(R.id.hrJobsUpdateDateTv)
        etCreationDate = findViewById(R.id.hrJobsCreationDateTv)

        btnAdd = findViewById(R.id.hrJobAddBtnTv)
        btnView = findViewById(R.id.hrJobViewBtnTv)
        btnUpdate = findViewById(R.id.hrJobUpdateBtnTv)
    }
}