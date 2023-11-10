package com.cihancelik.CarParkDetails.ledger.glJournalLines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJL
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJournals
import com.cihancelik.carparkcustomerdetails.R

class GLJLMainScreen : AppCompatActivity() {
    private lateinit var etJournalId: EditText
    private lateinit var etJournalDate: EditText
    private lateinit var etGlCodComId: EditText
    private lateinit var etAccountedCrAmount: EditText
    private lateinit var etAccountedDrAmount: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelperForGLJL: SQLHelperForGLJL
    private lateinit var sqlHelperForGLJournals: SQLHelperForGLJournals
    private var gljlInfo1: GLJLModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gljlmain_screen)

        initView()

        sqlHelperForGLJL = SQLHelperForGLJL(this)
        sqlHelperForGLJournals = SQLHelperForGLJournals(this)

        var selectedGJLInfo = intent.getSerializableExtra("selectedGLJLInfo") as? GLJLModel

        if (selectedGJLInfo != null) {
            etJournalId.setText(selectedGJLInfo.journalId.toString())
            etJournalDate.setText(selectedGJLInfo.journalDate)
            etGlCodComId.setText(selectedGJLInfo.glCodComId.toString())
            etAccountedCrAmount.setText(selectedGJLInfo.accountedCrAmount.toString())
            etAccountedDrAmount.setText(selectedGJLInfo.accountedDrAmount.toString())
            etUpdateDate.setText(selectedGJLInfo.updateDate)
            etCreationDate.setText(selectedGJLInfo.creationDate)

            gljlInfo1 = selectedGJLInfo
        }
        btnAdd.setOnClickListener { addGLJL() }

        var goToGLJLViewScreen = Intent(this, GLJLViewScreen::class.java)
        btnView.setOnClickListener { startActivity(goToGLJLViewScreen) }

        var selectedGLJLUpdate = intent.getSerializableExtra("selectedGLJLUpdate") as? GLJLModel

        if (selectedGLJLUpdate != null) {
            etJournalId.setText(selectedGLJLUpdate.journalId.toString())
            etJournalDate.setText(selectedGLJLUpdate.journalDate)
            etGlCodComId.setText(selectedGLJLUpdate.glCodComId)
            etAccountedCrAmount.setText(selectedGLJLUpdate.accountedCrAmount)
            etAccountedDrAmount.setText(selectedGLJLUpdate.accountedDrAmount)
            etUpdateDate.setText(selectedGLJLUpdate.updateDate)
            etCreationDate.setText(selectedGLJLUpdate.creationDate)
        }
        btnUpdate.setOnClickListener { updateGLJL() }
    }

    // ...

    private fun updateGLJL() {
        val journalId = etJournalId.text.toString()
        val journalDate = etJournalDate.text.toString()
        val glCodComId = etGlCodComId.text.toString()
        val accountedCrAmount = etAccountedCrAmount.text.toString()
        val accountedDrAmount = etAccountedDrAmount.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (gljlInfo1 != null) {
            val updateGLJL = GLJLModel(
                journalLineId = gljlInfo1!!.journalLineId,
                journalId = gljlInfo1!!.journalId,
                journalDate = journalDate,
                glCodComId = glCodComId.toInt(),
                accountedCrAmount = accountedCrAmount.toInt(),
                accountedDrAmount = accountedDrAmount.toInt(),
                updateDate = updateDate,
                creationDate = creationDate
            )

            val status = sqlHelperForGLJL.updateGLJL(updateGLJL)
            if (status > -1) {
                Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, GLJLViewScreen::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun isUpdate(updatedGLJL: GLJLModel): Boolean {
        val currentJournalLine = sqlHelperForGLJL.getGLJLById(updatedGLJL.journalLineId)
        return currentJournalLine != updatedGLJL
    }

    private fun addGLJL() {
        val journalIdtext = etJournalId.text.toString()
        val journalDate = etJournalDate.text.toString()
        val glCodComId = etGlCodComId.text.toString()
        val accountedCrAmount = etAccountedCrAmount.text.toString()
        val accountedDrAmount = etAccountedDrAmount.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val createDate = etCreationDate.text.toString()

        if (journalIdtext.isEmpty() || journalDate.isEmpty() || glCodComId.isEmpty() ||
            accountedCrAmount.isEmpty() || accountedDrAmount.isEmpty() || updateDate.isEmpty()
            || createDate.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val enteredJournalId = journalIdtext.toInt()
            val glJournal = sqlHelperForGLJournals.getGLJournalById(enteredJournalId)

            if (glJournal != null) {

                val gljlInfo = GLJLModel(
                    journalLineId = 0,
                    journalId = enteredJournalId,
                    journalDate = journalDate,
                    glCodComId = glCodComId.toInt(),
                    accountedCrAmount = accountedCrAmount.toInt(),
                    accountedDrAmount = accountedDrAmount.toInt(),
                    updateDate = updateDate,
                    creationDate = createDate
                )
                val status: Long = sqlHelperForGLJL.insertJL(gljlInfo)
                if (status > -1) {
                    Toast.makeText(this, "GLJournalLine Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                } else {
                    Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "GLJournalLine with the given journal Id does not exist.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearEditText() {
        etJournalId.setText("")
        etJournalDate.setText("")
        etGlCodComId.setText("")
        etAccountedCrAmount.setText("")
        etAccountedDrAmount.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

    }

    private fun initView() {
        etJournalId = findViewById(R.id.journalIdForLinesTv)
        etJournalDate = findViewById(R.id.gljlJournalDateTv)
        etGlCodComId = findViewById(R.id.gljlGlCodComIdTv)
        etAccountedCrAmount = findViewById(R.id.gljlAccountedCrAmountTv)
        etAccountedDrAmount = findViewById(R.id.gljlAccountedDrAmountTv)
        etUpdateDate = findViewById(R.id.gljlUpdateDateTv)
        etCreationDate = findViewById(R.id.gljlCreationDateTv)


        btnAdd = findViewById(R.id.gljlAddBtn)
        btnView = findViewById(R.id.gljlViewBtn)
        btnUpdate = findViewById(R.id.gljlUpdateBtn)


    }
}
