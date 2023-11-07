package com.cihancelik.CarParkDetails.ledger.glJournals

import GLJournalsModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJournals
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLPeriods
import com.cihancelik.carparkcustomerdetails.R

class GLJournalsMainScreen : AppCompatActivity() {
    private lateinit var etPeriodId: EditText
    private lateinit var etJournalDate: EditText
    private lateinit var etStatus: EditText
    private lateinit var etAmount: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelperForGLJournals: SQLHelperForGLJournals
    private lateinit var sqlHelperForGLPeriods: SQLHelperForGLPeriods
    private var glJournalInfo1: GLJournalsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gljournals_main_screen)

        initView()

        sqlHelperForGLJournals = SQLHelperForGLJournals(this)
        sqlHelperForGLPeriods = SQLHelperForGLPeriods(this)

        var selectedGLJournalInfo =
            intent.getSerializableExtra("selectedGLJournalInfo") as? GLJournalsModel

        if (selectedGLJournalInfo != null) {
            etPeriodId.setText(selectedGLJournalInfo.periodId.toString())
            etJournalDate.setText(selectedGLJournalInfo.journalDate)
            etStatus.setText(selectedGLJournalInfo.status)
            etAmount.setText(selectedGLJournalInfo.amount)
            etUpdateDate.setText(selectedGLJournalInfo.updateDate)
            etCreationDate.setText(selectedGLJournalInfo.creationDate)

            glJournalInfo1 = selectedGLJournalInfo
        }

        btnAdd.setOnClickListener { addJournal() }

        var goToJournalViewScreen = Intent(this, GLJournalsViewScreen::class.java)
        btnView.setOnClickListener { startActivity(goToJournalViewScreen) }

        var selectedJournalUpdate =
            intent.getSerializableExtra("selectedGLJournalUpdate") as? GLJournalsModel

        if (selectedJournalUpdate != null) {
            etPeriodId.setText(selectedJournalUpdate.periodId.toString())
            etJournalDate.setText(selectedJournalUpdate.journalDate)
            etStatus.setText(selectedJournalUpdate.status)
            etAmount.setText(selectedJournalUpdate.amount)
            etUpdateDate.setText(selectedJournalUpdate.updateDate)
            etCreationDate.setText(selectedJournalUpdate.creationDate)
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

        if (glJournalInfo1 != null) {
            val updateGLJournal = GLJournalsModel(
                journalId = glJournalInfo1!!.journalId,
                periodId = glJournalInfo1!!.periodId,
                journalDate = journalDate,
                status = status,
                amount = amount,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdated = isUpdate(updateGLJournal)
            if (isUpdated) {
                val status = sqlHelperForGLJournals.updateJournal(updateGLJournal)
                if (status > -1) {
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, GLJournalsViewScreen::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdate(updatedGLJournal: GLJournalsModel): Boolean {
        val currentJournal = sqlHelperForGLJournals.getGLJournalById(updatedGLJournal.journalId)
        return currentJournal != updatedGLJournal
    }

    private fun addJournal() {
        val periodIdText = etPeriodId.text.toString()
        val journalDate = etJournalDate.text.toString()
        val status = etStatus.text.toString()
        val amount = etAmount.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (periodIdText.isEmpty() || journalDate.isEmpty() || status.isEmpty() || amount.isEmpty() || updateDate.isEmpty() || creationDate.isEmpty()) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val enteredPeriodId = periodIdText.toInt()

            val glPeriod = sqlHelperForGLPeriods.getGLPeriodById(enteredPeriodId)

            if (glPeriod != null) {
                val glJournalInfo = GLJournalsModel(
                    journalId = 0,
                    periodId = enteredPeriodId,
                    journalDate = journalDate,
                    status = glPeriod.periodName,
                    amount = amount,
                    updateDate = updateDate,
                    creationDate = creationDate
                )

                val status: Long = sqlHelperForGLJournals.insertJournal(glJournalInfo)
                if (status > -1) {
                    Toast.makeText(this, "GLJournal Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                } else {
                    Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "GLPeriod with the given periodId does not exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etPeriodId.setText("")
        etJournalDate.setText("")
        etStatus.setText("")
        etAmount.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etPeriodId.requestFocus()
    }

    private fun initView() {
        etPeriodId = findViewById(R.id.journalPeriodIdTv)
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
