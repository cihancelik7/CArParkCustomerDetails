package com.cihancelik.CarParkDetails.ledger.glAccountCombinations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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

    }
}