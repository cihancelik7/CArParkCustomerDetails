package com.cihancelik.CarParkDetails.ledger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cihancelik.CarParkDetails.ledger.glAccountCombinations.GLACMainScreen
import com.cihancelik.CarParkDetails.ledger.glJournalLines.GLJLMainScreen
import com.cihancelik.CarParkDetails.ledger.glJournalLines.GLJLModel
import com.cihancelik.CarParkDetails.ledger.glJournals.GLJournalsMainScreen

import com.cihancelik.CarParkDetails.ledger.glPeriods.GLPeriodsMainScreen
import com.cihancelik.carparkcustomerdetails.R

class LedgerViewPage : AppCompatActivity() {
    private lateinit var btnGLAC: Button
    private lateinit var btnGLPeriods: Button
    private lateinit var btnGLJL: Button
    private lateinit var btnGLJournals: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ledger_view_page)

        btnGLAC = findViewById(R.id.GLACLedgerMainScreenBtn)
        btnGLJL = findViewById(R.id.GLJLMainScreenBtn)
        btnGLJournals = findViewById(R.id.GLJournalsBtn)
        btnGLPeriods = findViewById(R.id.GLPeriodsMainScreenBtn)

        val goToGLACMainScreen = Intent(this,GLACMainScreen::class.java)
        btnGLAC.setOnClickListener { startActivity(goToGLACMainScreen) }

        val goToGLPeriodMainScreen = Intent(this,GLPeriodsMainScreen::class.java)
        btnGLPeriods.setOnClickListener { startActivity(goToGLPeriodMainScreen) }


        val goToGLJournalsMainScreen = Intent(this, GLJournalsMainScreen::class.java)
        btnGLJournals.setOnClickListener { startActivity(goToGLJournalsMainScreen) }

        val goToGLJLMainScreen = Intent(this,GLJLMainScreen::class.java)
        btnGLJL.setOnClickListener { startActivity(goToGLJLMainScreen) }
    }
}