package com.cihancelik.CarParkDetails.ledger.glPeriods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLPeriods
import com.cihancelik.carparkcustomerdetails.R

class GLPeriodsViewScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = GLPeriodsAdapter()
    private lateinit var sqlHelperForGLPeriods: SQLHelperForGLPeriods
    private lateinit var etPeriodName : EditText
    private lateinit var etPeriodYear : EditText
    private lateinit var etUpdateDate : EditText
    private lateinit var etCreationDate :EditText

    private var glPeriod1 : GLPeriodsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glperiods_view_screen)

        sqlHelperForGLPeriods = SQLHelperForGLPeriods(this)
        recyclerView = findViewById(R.id.glperiodsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getGLPeriod()

        adapter.setOnClickItem { glPeriod ->
            val intent = Intent(this,GLPeriodsMainScreen::class.java)
            intent.putExtra("selectedGLPeriodUpdate",glPeriod)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteGLPeriod(it.periodId)
        }
    }

    override fun onResume() {
        super.onResume()

        val updateGLPeriod =
            intent.getSerializableExtra("selectedGLPeriodInfo") as? GLPeriodsModel
        if (updateGLPeriod != null){
            glPeriod1 = updateGLPeriod

            etPeriodName.setText(updateGLPeriod.periodName)
            etPeriodYear.setText(updateGLPeriod.periodYear)
            etUpdateDate.setText(updateGLPeriod.updateDate)
            etCreationDate.setText(updateGLPeriod.creationDate)
        }
    }
    private fun getGLPeriod() {
        val glPeriodList = sqlHelperForGLPeriods.getAllGLPeriod()
        adapter.addItems(glPeriodList)
    }
    fun deleteGLPeriod(id:Int){
        if (id<=0)return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete GLPeriod info?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            sqlHelperForGLPeriods.deleteGLPeriodById(id)

            val GLPeriodList = sqlHelperForGLPeriods.getAllGLPeriod()
            var deletedGLPeriodIndex = -1
            for (i in GLPeriodList.indices){
                if(GLPeriodList[i].periodId == id){
                    deletedGLPeriodIndex = i
                    break
                }
            }
            adapter.updateGLPeriodList(GLPeriodList)
            dialog.dismiss()
            var goToGLPeriodMainPage = Intent(this,GLPeriodsMainScreen::class.java)
            startActivity(goToGLPeriodMainPage)
        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }



}