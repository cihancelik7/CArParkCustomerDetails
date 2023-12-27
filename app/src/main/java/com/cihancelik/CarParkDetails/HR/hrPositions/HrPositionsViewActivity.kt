package com.cihancelik.CarParkDetails.HR.hrPositions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.HR.hrJobs.HrJobsModel
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrJobs
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrPositions
import com.cihancelik.carparkcustomerdetails.R

class HrPositionsViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = HrPositionsAdapter()
    private lateinit var sqlHelperForPositions: SQLiteHelperForHrPositions
    private lateinit var sqlHelperForJobs: SQLiteHelperForHrJobs
    private lateinit var etPositionName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etJobId: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private var hrPosition1: HrPositionsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_positions_view)

        sqlHelperForPositions = SQLiteHelperForHrPositions(this)
        sqlHelperForJobs = SQLiteHelperForHrJobs(this)
        recyclerView = findViewById(R.id.hrPositionRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getHrPosition()
        adapter.setOnClickItem { HrPosition ->
            val intent = Intent(this,HrPositionsMainActivity::class.java)
            intent.putExtra("selectedPositionUpdated",HrPosition)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem { deleteHrPosition(it.positionId) }
    }

    override fun onResume() {
        super.onResume()
        val updateHrPosition =
            intent.getSerializableExtra("selectedHrPositionInfo") as? HrPositionsModel
        if (updateHrPosition!=null){
            hrPosition1 = updateHrPosition
            etPositionName.setText(updateHrPosition.positionName)
            etStartDate.setText(updateHrPosition.startDate)
            etEndDate.setText(updateHrPosition.endDate)
            etJobId.setText(updateHrPosition.jobId.toString())
            etUpdateDate.setText(updateHrPosition.updateDate)
            etCreationDate.setText(updateHrPosition.creationDate)
        }
    }

    private fun getHrPosition() {
        val hrPositionList = sqlHelperForPositions.getAllPositions()
        adapter.addItems(hrPositionList)
    }
    private fun deleteHrPosition(positionId : Int){
        if (positionId<=0)return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete HrPosition Info??")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_ ->
            sqlHelperForPositions.deleteHrPositionById(positionId)

            val hrPositionList = sqlHelperForPositions.getAllPositions()
            var deleteHrPositionIndex = -1
            for (i in hrPositionList.indices){
                if (hrPositionList[i].positionId == positionId){
                    deleteHrPositionIndex = i
                    break
                }
            }
            adapter.updatePositionList(hrPositionList)
            dialog.dismiss()
            var goToPositionMainActivity = Intent(this,HrPositionsMainActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }

}