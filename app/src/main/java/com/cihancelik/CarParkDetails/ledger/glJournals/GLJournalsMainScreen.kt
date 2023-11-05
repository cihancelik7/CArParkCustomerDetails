package com.cihancelik.CarParkDetails.ledger.glJournals

import GLJournalsModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJournals
import com.cihancelik.CarParkDetails.ledger.glPeriods.GLPeriodsMainScreen
import com.cihancelik.carparkcustomerdetails.R

class GLJournalsMainScreen : AppCompatActivity() {
    private lateinit var etJournalId: TextView
    private lateinit var etPeriodId: TextView
    private lateinit var etJournalDate: TextView
    private lateinit var etStatus: TextView
    private lateinit var etAmount: TextView
    private lateinit var etUpdateDate: TextView
    private lateinit var etCreationDate: TextView

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelperForGLJournals: SQLHelperForGLJournals
    private var glJournalInfo1 : GLJournalsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gljournals_main_screen)

        initView()

        sqlHelperForGLJournals = SQLHelperForGLJournals(this)
        btnAdd.setOnClickListener { addJournal() }
        var selectedGLJournalInfo =
            intent.getSerializableExtra("selectedGLJournalInfo") as? GLJournalsModel

        if (selectedGLJournalInfo != null) {
            etPeriodId.text = selectedGLJournalInfo.periodId.toString()
            etJournalDate.text = selectedGLJournalInfo.journalId.toString()
            etStatus.text = selectedGLJournalInfo.status
            etAmount.text = selectedGLJournalInfo.amount.toString()
            etUpdateDate.text = selectedGLJournalInfo.updateDate
            etCreationDate.text = selectedGLJournalInfo.creationDate

            glJournalInfo1 = selectedGLJournalInfo
        }
        var goToJournalViewScreen = Intent(this, GLJournalsViewScreen::class.java)
        btnView.setOnClickListener { startActivity(goToJournalViewScreen) }

        var selectedJournalUpdate =
            intent.getSerializableExtra("selectedGLJournalUpdate") as? GLJournalsModel

        if (selectedJournalUpdate!= null){
            etPeriodId.text = selectedGLJournalInfo?.periodId.toString()
            etJournalDate.text = selectedGLJournalInfo?.journalDate
            etStatus.text = selectedGLJournalInfo?.status
            etAmount.text = selectedGLJournalInfo?.amount.toString()
            etUpdateDate.text = selectedGLJournalInfo?.updateDate
            etCreationDate.text = selectedGLJournalInfo?.creationDate
        }
        btnUpdate.setOnClickListener { updateGLJournal() }
    }

    private fun updateGLJournal() {
        val periodId = etPeriodId.text.toString()
        val journalDate = etJournalDate.text.toString()
        val status = etStatus.text.toString()
        val amount = etAmount.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (glJournalInfo1 != null){
            val updateGLJournal = GLJournalsModel(
                journalId = glJournalInfo1!!.journalId,
                periodId = glJournalInfo1!!.periodId,
                journalDate = journalDate,
                status = status,
                amount = glJournalInfo1!!.amount,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdated = isUpdate(updateGLJournal)
            if(isUpdated){
                val status = sqlHelperForGLJournals.updateJournal(updateGLJournal)
                if (status > -1){
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,GLJournalsMainScreen::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isUpdate(updatedGLJournal:GLJournalsModel):Boolean{
        val currentJournal = sqlHelperForGLJournals.getGLPeriodForJournal(updatedGLJournal)
        return currentJournal != updatedGLJournal
    }
    private fun addJournal() {
        val periodId: TextView = etPeriodId
        val journalDate = etJournalDate.text.toString()
        val status = etStatus.text.toString()
        val amount = etAmount.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (periodId.equals("")||journalDate.isEmpty()||status.isEmpty()||amount.isEmpty()||
            updateDate.isEmpty() || creationDate.isEmpty()){
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val glJournalInfo = GLJournalsModel(
                periodId = glJournalInfo1!!.periodId,
                journalDate = journalDate,
                status = status,
                amount = glJournalInfo1!!.amount,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val status : Long = sqlHelperForGLJournals.insertJournal(glJournalInfo)
            if (status > -1){
                Toast.makeText(this, "GLJournal Added", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
            }



        }
    }

    private fun clearEditText() {
        etPeriodId.text = ""
        etJournalDate.text = ""
        etStatus.text = ""
        etAmount.text = ""
        etUpdateDate.text = ""
        etCreationDate.text = ""

        etPeriodId.requestFocus()
    }


    private fun initView() {
        etPeriodId = findViewById(R.id.periodIdTv)
        etJournalDate = findViewById(R.id.journalDateTv)
        etStatus = findViewById(R.id.journalStatusTv)
        etAmount = findViewById(R.id.journalAmountTv)
        etUpdateDate = findViewById(R.id.journalUpdateDateTv)
        etCreationDate = findViewById(R.id.journalCreationDateTv)

        btnAdd = findViewById(R.id.journalAddBtn)
        btnView = findViewById(R.id.journalViewBtn)
        btnUpdate = findViewById(R.id.journalUpdateBtn)
    }
}