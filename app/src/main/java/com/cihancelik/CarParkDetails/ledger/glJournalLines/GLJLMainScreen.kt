package com.cihancelik.CarParkDetails.ledger.glJournalLines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.ui.setupActionBarWithNavController
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

        if (selectedGJLInfo != null){
            etJournalId.setText(selectedGJLInfo.journalId.toString())
            etJournalDate.setText(selectedGJLInfo.journalDate)
            etGlCodComId.setText(selectedGJLInfo.glCodComId)
            etAccountedCrAmount.setText(selectedGJLInfo.accountedCrAmount)
            etAccountedDrAmount.setText(selectedGJLInfo.accountedDrAmount)
            etUpdateDate.setText(selectedGJLInfo.updateDate)
            etCreationDate.setText(selectedGJLInfo.creationDate)

            gljlInfo1 = selectedGJLInfo
        }
        btnAdd.setOnClickListener { addGLJL() }

        var goToGLJLViewScreen = Intent(this,GLJLViewScreen::class.java)
        btnView.setOnClickListener { startActivity(goToGLJLViewScreen) }

        var selectedGLJLUpdate = intent.getSerializableExtra("selectedGLJLUpdate") as? GLJLModel

        if (selectedGLJLUpdate != null){
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

    private fun addGLJL() {
        val journalIdtext = etJournalId.text.toString()
        val journalDate = etJournalDate.text.toString()
        val glCodComId = etGlCodComId.text
        val accountedCrAmount = etAccountedCrAmount.text
        val accountedDrAmount = etAccountedDrAmount.text
        val updateDate = etUpdateDate.text.toString()
        val createDate = etCreationDate.text.toString()

        if (journalIdtext.isEmpty() || journalDate.isEmpty()|| glCodComId.isEmpty()||
            accountedCrAmount.isEmpty() || accountedDrAmount.isEmpty() || updateDate.isEmpty()
            || createDate.isEmpty()){
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val enteredJournalId = journalIdtext.toInt()
            val glJournal = sqlHelperForGLJournals.getGLJournalById(enteredJournalId)

            if (glJournal != null){
                val gljlInfo = GLJLModel(
                    journalLineId = 0,
                    journalId = enteredJournalId,
                    journalDate = journalDate,
                    glCodComId = 0,
                    accountedCrAmount = 0,
                    accountedDrAmount = 0,
                    updateDate = updateDate,
                    creationDate = createDate
                )
                val status : Long = sqlHelperForGLJL.insertJL(gljlInfo)
                if (status > -1){
            }
        }

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