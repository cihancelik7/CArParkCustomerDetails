package com.cihancelik.CarParkDetails.HR.hrPositions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrJobs
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrPositions
import com.cihancelik.carparkcustomerdetails.R

class HrPositionsMainActivity : AppCompatActivity() {
    private lateinit var etPositionName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etJobId: EditText
    private lateinit var etUpdateDate : EditText
    private lateinit var etCreationDate : EditText

    private lateinit var btnAdd : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqlHelperForPositions : SQLiteHelperForHrPositions
    private lateinit var sqlHelperForJobs : SQLiteHelperForHrJobs
    private var hrPositionInfo1 : HrPositionsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_positions_main)

        initView()

        sqlHelperForJobs = SQLiteHelperForHrJobs(this)
        sqlHelperForPositions = SQLiteHelperForHrPositions(this)

        val selectHrPositionInfo =
            intent.getSerializableExtra("selectedHrPositionInfo") as? HrPositionsModel

        if (selectHrPositionInfo != null){
            etPositionName.setText(selectHrPositionInfo.positionName)
            etStartDate.setText(selectHrPositionInfo.startDate)
            etEndDate.setText(selectHrPositionInfo.endDate)
            etJobId.setText(selectHrPositionInfo.jobId.toString())
            etUpdateDate.setText(selectHrPositionInfo.updateDate)
            etCreationDate.setText(selectHrPositionInfo.creationDate)

            hrPositionInfo1 = selectHrPositionInfo
        }
        btnAdd.setOnClickListener { addPosition() }

        var goToHrPositionViewActivity = Intent(this,HrPositionsViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrPositionViewActivity) }
        var selectHrPositionUpdate =
            intent.getSerializableExtra("selectedHrPositionUpdated") as? HrPositionsModel
        if (selectHrPositionUpdate!=null){
            etPositionName.setText(selectHrPositionUpdate.positionName)
            etStartDate.setText(selectHrPositionUpdate.startDate)
            etEndDate.setText(selectHrPositionUpdate.endDate)
            etJobId.setText(selectHrPositionUpdate.jobId)
            etUpdateDate.setText(selectHrPositionUpdate.updateDate)
            etCreationDate.setText(selectHrPositionUpdate.creationDate)
        }
        btnUpdate.setOnClickListener { updatePosition() }
    }

    private fun updatePosition() {
        val positionName = etPositionName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val jobId = etJobId.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()
        if (hrPositionInfo1!=null){
            val updateHrPosition = HrPositionsModel(
                positionId = hrPositionInfo1!!.positionId,
                positionName = positionName,
                startDate = startDate,
                endDate = endDate,
                jobId = hrPositionInfo1!!.jobId,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdate = isUpdate(updateHrPosition)
            if (isUpdate){
                val status = sqlHelperForPositions.updateHrPosition(updateHrPosition)
                if (status > -1){
                    Toast.makeText(this, "Update Successfull", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HrPositionsViewActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdate(updateHrPosition:HrPositionsModel):Boolean{
        val currentPosition =
            sqlHelperForPositions.getPositionById(updateHrPosition.positionId)
        return currentPosition != updateHrPosition
    }
    private fun addPosition() {
        val positionName = etPositionName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val jobIdText = etJobId.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (positionName.isEmpty() || startDate.isEmpty() || jobIdText.isEmpty() ||
            updateDate.isEmpty()|| creationDate.isEmpty())
        {
            Toast.makeText(this, "Please Enter requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val enteredJobId = jobIdText.toInt()
            val jobId = sqlHelperForJobs.getHrJobById(enteredJobId)

            if (jobId!=null){
                val hrPositionInfo = HrPositionsModel(
                    positionId = 0,
                    positionName = positionName,
                    startDate = startDate,
                    endDate = endDate,
                    jobId = enteredJobId,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                val status : Long = sqlHelperForPositions.insertPosition(hrPositionInfo)
                if (status > -1){
                    Toast.makeText(this, "Hr Position Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                }else{
                    Toast.makeText(this, "Recurd Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(
                    this,
                    "Hr Position With The Given Job Id does not exists",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearEditText() {
        etPositionName.setText("")
        etStartDate.setText("")
        etEndDate.setText("")
        etJobId.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etPositionName.requestFocus()
    }

    private fun initView() {
        etPositionName = findViewById(R.id.hrPositionNameTv)
        etStartDate = findViewById(R.id.hrPositionStartDateTv)
        etEndDate = findViewById(R.id.hrPositionEndDateTv)
        etJobId = findViewById(R.id.hrPositionJobIdTv)
        etUpdateDate = findViewById(R.id.hrPositionUpdateDateTv)
        etCreationDate = findViewById(R.id.hrPositionCreationDateTv)

        btnAdd = findViewById(R.id.hrPositionAddBtn)
        btnView = findViewById(R.id.hrPositionViewBtn)
        btnUpdate = findViewById(R.id.hrPositionUpdateBtn)
    }
}