package com.cihancelik.CarParkDetails.ledger.glPeriods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLPeriods
import com.cihancelik.carparkcustomerdetails.R

class GLPeriodsMainScreen : AppCompatActivity() {
    private lateinit var etPeriodName : TextView
    private lateinit var etPeriodYear : TextView
    private lateinit var etUpdateYear : TextView
    private lateinit var etCreationDate : TextView

    private lateinit var btnAdd : Button
    private lateinit var btnView :Button
    private lateinit var btnUpdate :Button

    private lateinit var sqlHelper:SQLHelperForGLPeriods
    private var glPeriodInfo : GLPeriodsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glperiods_main_screen)

        initView()

        sqlHelper = SQLHelperForGLPeriods(this)
        btnAdd.setOnClickListener { addGLperiod() }

        val selectedGLperiodInfo =
            intent.getSerializableExtra("selectedGLPeriodInfo") as? GLPeriodsModel

        if (selectedGLperiodInfo!=null){
            etPeriodName.text = selectedGLperiodInfo.periodName
            etPeriodYear.text = selectedGLperiodInfo.periodYear
            etUpdateYear.text = selectedGLperiodInfo.updateDate
            etCreationDate.text = selectedGLperiodInfo.creationDate

            glPeriodInfo = selectedGLperiodInfo
        }
        val goToGLPeriodViewScreen = Intent(this,GLPeriodsViewScreen::class.java)
        btnView.setOnClickListener { startActivity(goToGLPeriodViewScreen) }

        val selectedGLPeriodUpdate = intent.getSerializableExtra("selectedGLPeriodUpdate") as? GLPeriodsModel
        if (selectedGLPeriodUpdate!=null){
            etPeriodName.text = selectedGLPeriodUpdate.periodName
            etPeriodYear.text = selectedGLPeriodUpdate.periodYear
            etUpdateYear.text = selectedGLPeriodUpdate.updateDate
            etCreationDate.text = selectedGLPeriodUpdate.creationDate
            glPeriodInfo = selectedGLPeriodUpdate
        }
        btnUpdate.setOnClickListener { updateGLPeriod() }
    }

    private fun updateGLPeriod() {
        val periodName = etPeriodName.text.toString()
        val periodYear = etPeriodYear.text.toString()
        val updateDate = etUpdateYear.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (glPeriodInfo != null){
            val updatedGLperiod = GLPeriodsModel(
                periodId = glPeriodInfo!!.periodId,
                periodName = periodName,
                periodYear = periodYear,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdated = isUpdated(updatedGLperiod)
            if (isUpdated){
                val status = sqlHelper.updateGLPeriod(updatedGLperiod)
                if (status > -1){
                    Toast.makeText(this, "update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,GLPeriodsMainScreen::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }
private fun isUpdated(updatedGLperiod:GLPeriodsModel):Boolean{
    // mevcut verileri al
    val currentGLperiod = sqlHelper.getGLPeriodById(updatedGLperiod.periodId)
    return currentGLperiod != updatedGLperiod
}
    private fun addGLperiod() {
        val periodName = etPeriodName.text.toString()
        val periodYear = etPeriodYear.text.toString()
        val updateDate = etUpdateYear.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (periodName.isEmpty()|| periodYear.isEmpty() || updateDate.isEmpty()|| creationDate.isEmpty())
        {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val glPeriodInfo = GLPeriodsModel(
                periodName = periodName,
                periodYear =  periodYear,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val status : Long = sqlHelper.insertGLPeriod(glPeriodInfo)
            if (status > -1){
                Toast.makeText(this, "GLPeriod Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(this, "Record Not Saved!!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etPeriodName.text = ""
        etPeriodYear.text = ""
        etUpdateYear.text = ""
        etCreationDate.text = ""

        etPeriodName.requestFocus()
    }

    private fun initView(){
        etPeriodName = findViewById(R.id.glPeriodNameTv)
        etPeriodYear = findViewById(R.id.glPeriodYearTv)
        etUpdateYear = findViewById(R.id.glPeriodUpdateDateTv)
        etCreationDate = findViewById(R.id.glperiodCreationDateTv)

        btnAdd = findViewById(R.id.btnGLPeriodAdd)
        btnView = findViewById(R.id.btnGLPeriodView)
        btnUpdate = findViewById(R.id.btnGLPeriodUpdate)
    }
}