package com.cihancelik.CarParkDetails.ledger.glJournals

import GLJournalsModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJournals
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLPeriods
import com.cihancelik.CarParkDetails.ledger.glPeriods.GLPeriodsModel
import com.cihancelik.carparkcustomerdetails.R

class GLJournalsViewScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = GLJournalsAdapter()
    private lateinit var sqlHelperForGLJournals: SQLHelperForGLJournals
    private lateinit var sqlHelperForGLPeriods: SQLHelperForGLPeriods
    private lateinit var etPeriodId: EditText
    private lateinit var etJournalDate: EditText
    private lateinit var etStatus: EditText
    private lateinit var etAmount: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private var glJournal1: GLJournalsModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gljournals_view_screen)

        sqlHelperForGLJournals = SQLHelperForGLJournals(this)
        sqlHelperForGLPeriods = SQLHelperForGLPeriods(this)

        recyclerView = findViewById(R.id.glJournalsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getJournal()
        adapter.setOnClickItem { glJournal ->
            val intent = Intent(this, GLJournalsMainScreen::class.java)
            intent.putExtra("selectedJournalUpdated", glJournal)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteJournal(it.journalId)
        }
    }

    override fun onResume() {
        super.onResume()
        val updateGLJournal =
            intent.getSerializableExtra("selectedJournalUpdated") as? GLJournalsModel
        if (updateGLJournal != null) {
            glJournal1 = updateGLJournal
            etPeriodId.setText(updateGLJournal.periodId.toString())
            etJournalDate.setText(updateGLJournal.journalDate)
            etStatus.setText(updateGLJournal.status)
            etAmount.setText(updateGLJournal.amount.toString())
            etUpdateDate.setText(updateGLJournal.updateDate)
            etCreationDate.setText(updateGLJournal.creationDate)
        }
    }


    private fun getJournal() {
        val journalList = sqlHelperForGLJournals.getAllJournal()
        adapter.addItems(journalList)
    }

    fun deleteJournal(id: Int) {
        if (id <= 0) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete Journal Info? ")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqlHelperForGLJournals.deleteGLJournalById(id)

            val journalList = sqlHelperForGLJournals.getAllJournal()
            var deletedGLJournalIndex = -1
            for (i in journalList.indices) {
                if (journalList[i].journalId == id) {
                    deletedGLJournalIndex = i
                    break
                }
            }
            adapter.updateGLJournalLIst(journalList)
            dialog.dismiss()
            var goToJournalMainPage = Intent(this, GLJournalsMainScreen::class.java)
            startActivity(goToJournalMainPage)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }
}