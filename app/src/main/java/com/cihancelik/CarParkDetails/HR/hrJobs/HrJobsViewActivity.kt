package com.cihancelik.CarParkDetails.HR.hrJobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrJobs
import com.cihancelik.carparkcustomerdetails.R

class HrJobsViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : HrJobsAdapter
    private lateinit var sqLiteHelperForHrJobs: SQLiteHelperForHrJobs
    private lateinit var etJobName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private var hrJobs1: HrJobsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_jobs_view)

        sqLiteHelperForHrJobs = SQLiteHelperForHrJobs(this)
        recyclerView = findViewById(R.id.hrJobsRecycllerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter=HrJobsAdapter()
        recyclerView.adapter = adapter

        getHrJobs()

        adapter.setOnclickItem { hrJob ->
            val intent = Intent(this, HrJobsMainActivity::class.java)
            intent.putExtra("selectedHrJobsUpdate", hrJob)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteHrJobs(it.jobId)
        }
    }

    override fun onResume() {
        super.onResume()
        val updateHrJob =
            intent.getSerializableExtra("selectedHrJobsInfo") as? HrJobsModel
        if (updateHrJob != null) {
            hrJobs1 = updateHrJob

            etJobName.setText(updateHrJob.jobName)
            etStartDate.setText(updateHrJob.startDate)
            etEndDate.setText(updateHrJob.endDate)
            etUpdateDate.setText(updateHrJob.updateDate)
            etCreationDate.setText(updateHrJob.creationDate)
        }
    }

    private fun getHrJobs() {
        val hrJobList = sqLiteHelperForHrJobs.getAllHrJobs()
        adapter.addItems(hrJobList)
    }

    fun deleteHrJobs(hrJobid: Int) {
        if (hrJobid < 0) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete HrJob info?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqLiteHelperForHrJobs.deleteHrJobsById(hrJobid)

            val hrJobList = sqLiteHelperForHrJobs.getAllHrJobs()
            var deleteHrJobIndex = -1
            for (i in hrJobList.indices) {
                if (hrJobList[i].jobId == hrJobid) {
                    deleteHrJobIndex = i
                    break
                }
            }
            adapter.updateJobList(hrJobList)
            dialog.dismiss()
            var goToHrJobsMainActivity = Intent(this, HrJobsMainActivity::class.java)
            startActivity(goToHrJobsMainActivity)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
}