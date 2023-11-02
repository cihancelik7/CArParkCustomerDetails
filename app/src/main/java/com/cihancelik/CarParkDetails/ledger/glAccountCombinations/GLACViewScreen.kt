package com.cihancelik.CarParkDetails.ledger.glAccountCombinations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLAC
import com.cihancelik.carparkcustomerdetails.R

class GLACViewScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = GLACAdapter()
    private lateinit var sqlHelperForGLAC: SQLHelperForGLAC
    private lateinit var etSegment1 : EditText
    private lateinit var etSegment2 : EditText
    private lateinit var etSegment3 : EditText
    private lateinit var etSegment4 : EditText
    private lateinit var etSegment5 : EditText
    private lateinit var etSegmentCombination : EditText
    private lateinit var etUpdateDate : EditText
    private lateinit var etCreationDate : EditText

    private var glac1: GLACModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glacview_screen)

        sqlHelperForGLAC = SQLHelperForGLAC(this)
        recyclerView = findViewById(R.id.glacRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getGlac()

        adapter.setOnClickItem { glac ->
            val intent = Intent(this,GLACMainScreen::class.java)
            intent.putExtra("selectedGLACUpdate",glac)
            startActivity(intent)
        }
        adapter.setOnClickItem {
            deleteGLAC(it.glCodComId)
        }

    }

    override fun onResume() {
        super.onResume()

        val updateGLAC =
            intent.getSerializableExtra("selectedGLACInfo") as? GLACModel
        if (updateGLAC!=null){
            glac1 = updateGLAC

            etSegment1.setText(updateGLAC.segment1)
            etSegment2.setText(updateGLAC.segment2)
            etSegment3.setText(updateGLAC.segment3)
            etSegment4.setText(updateGLAC.segment4)
            etSegment5.setText(updateGLAC.segment5)
            etSegmentCombination.setText(updateGLAC.segmentCombination)
            etUpdateDate.setText(updateGLAC.updateDate)
            etCreationDate.setText(updateGLAC.creationDate)
        }
    }
    fun getGlac(){
        val glacList = sqlHelperForGLAC.getAllGLAC()
        adapter.addItems(glacList)
    }
    fun deleteGLAC(id:Int){
        if (id <= 0)return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete GLAC info?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") {dialog, _ ->
            sqlHelperForGLAC.deleteGLACById(id)

            val GLACList = sqlHelperForGLAC.getAllGLAC()
            var deletedGLACIndex = -1
            for (i in GLACList.indices){
                if (GLACList[i].glCodComId == id){
                    deletedGLACIndex = i
                    break
                }
            }
            adapter.updateGLACList(GLACList)
            dialog.dismiss()
            var goToGLACMainPage = Intent(this,GLACMainScreen::class.java)
            startActivity(goToGLACMainPage)
        }
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
}