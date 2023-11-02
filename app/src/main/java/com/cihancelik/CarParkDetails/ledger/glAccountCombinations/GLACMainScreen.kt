package com.cihancelik.CarParkDetails.ledger.glAccountCombinations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLAC
import com.cihancelik.carparkcustomerdetails.R
import java.nio.channels.InterruptedByTimeoutException

class GLACMainScreen : AppCompatActivity() {
    private lateinit var etSegment1: TextView
    private lateinit var etSegment2: TextView
    private lateinit var etSegment3: TextView
    private lateinit var etSegment4: TextView
    private lateinit var etSegment5: TextView
    private lateinit var etSegmentCombination: TextView
    private lateinit var etUpdateDate: TextView
    private lateinit var etCreationDate: TextView

    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqlHelper: SQLHelperForGLAC
    private var glacInfo: GLACModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glacmain_screen)

        initView()

        sqlHelper = SQLHelperForGLAC(this)
        btnAdd.setOnClickListener { addGLAC() }

        var selectedGLACInfo =
            intent.getSerializableExtra("selectedGLACInfo") as? GLACModel

        if (selectedGLACInfo != null) {
            etSegment1.text = selectedGLACInfo.segment1
            etSegment2.text = selectedGLACInfo.segment2
            etSegment3.text = selectedGLACInfo.segment3
            etSegment4.text = selectedGLACInfo.segment4
            etSegment5.text = selectedGLACInfo.segment5
            etSegmentCombination.text = selectedGLACInfo.segmentCombination
            etUpdateDate.text = selectedGLACInfo.updateDate
            etCreationDate.text = selectedGLACInfo.creationDate

            glacInfo = selectedGLACInfo
        }
        var gotoGLACViewScreen = Intent(this, GLACViewScreen::class.java)
        btnView.setOnClickListener { startActivity(gotoGLACViewScreen) }

        var selectedGLACUpdate = intent.getSerializableExtra("selectedGLACUpdate") as? GLACModel
        if (selectedGLACUpdate != null){
            etSegment1.text = selectedGLACUpdate.segment1
            etSegment2.text = selectedGLACUpdate.segment2
            etSegment3.text = selectedGLACUpdate.segment3
            etSegment4.text = selectedGLACUpdate.segment4
            etSegment5.text = selectedGLACUpdate.segment5
            glacInfo = selectedGLACUpdate
        }
        btnUpdate.setOnClickListener { updateGLAC() }

    }

    private fun addGLAC() {
        val segment1 = etSegment1.text.toString()
        val segment2 = etSegment2.text.toString()
        val segment3 = etSegment3.text.toString()
        val segment4 = etSegment4.text.toString()
        val segment5 = etSegment5.text.toString()
        val segmentCombination = etSegmentCombination.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (segment1.isEmpty() || segment2.isEmpty() || segment3.isEmpty() ||
            segment4.isEmpty() || segment5.isEmpty() || segmentCombination.isEmpty() ||
            updateDate.isEmpty() || creationDate.isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        } else {
            val glacInfo = GLACModel(
                segment1 = segment1,
                segment2 = segment2,
                segment3 = segment3,
                segment4 = segment4,
                segment5 = segment5,
                segmentCombination = segmentCombination,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val status: Long = sqlHelper.insertGLAC(glacInfo)
            if (status > -1) {
                Toast.makeText(this, "GLAC Added!!", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateGLAC() {
        val segment1 = etSegment1.text.toString()
        val segment2 = etSegment2.text.toString()
        val segment3 = etSegment3.text.toString()
        val segment4 = etSegment4.text.toString()
        val segment5 = etSegment5.text.toString()
        val segmentCombination = etSegmentCombination.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.toString()

        if (glacInfo != null) {
            val updatedGLAC = GLACModel(
                glCodComId = glacInfo!!.glCodComId,
                segment1 = segment1,
                segment2 = segment2,
                segment3 = segment3,
                segment4 = segment4,
                segment5 = segment5,
                segmentCombination = segmentCombination,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdated = isUpdated(updatedGLAC)
            if (isUpdated) {
                val status = sqlHelper.updateGLAC(updatedGLAC)
                if (status > -1) {
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, GLACMainScreen::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No changes were made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdated(updatedGLAC: GLACModel): Boolean {
        // mevcut verileri al
        val currentGLAC = sqlHelper.getGLACById(updatedGLAC.glCodComId)
        return currentGLAC != updatedGLAC
    }

    private fun clearEditText() {
        etSegment1.text = ""
        etSegment2.text = ""
        etSegment3.text = ""
        etSegment4.text = ""
        etSegment5.text = ""
        etSegmentCombination.text = ""
        etUpdateDate.text = ""
        etCreationDate.text = ""
    }

    private fun initView() {
        etSegment1 = findViewById(R.id.glacSegment1)
        etSegment2 = findViewById(R.id.glacSegment2)
        etSegment3 = findViewById(R.id.glacSegment3)
        etSegment4 = findViewById(R.id.glacSegment4)
        etSegment5 = findViewById(R.id.glacSegment5)
        etSegmentCombination = findViewById(R.id.glacSegmentCombination)
        etUpdateDate = findViewById(R.id.glacUpdateDate)
        etCreationDate = findViewById(R.id.glacCreatonDate)

        btnAdd = findViewById(R.id.btnGLACadd)
        btnView = findViewById(R.id.btnGLACview)
        btnUpdate = findViewById(R.id.btnGLACupdate)
    }
}