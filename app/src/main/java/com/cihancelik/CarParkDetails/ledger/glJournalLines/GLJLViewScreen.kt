package com.cihancelik.CarParkDetails.ledger.glJournalLines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJL
import com.cihancelik.CarParkDetails.SQL.ledger.SQLHelperForGLJournals
import com.cihancelik.carparkcustomerdetails.R

class GLJLViewScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = GLJLAdapter()
    private lateinit var sqlHelperForGLJL: SQLHelperForGLJL
    private lateinit var sqlHelperForGLJournals: SQLHelperForGLJournals
    private lateinit var etJournalId: EditText
    private lateinit var etJournalDate: EditText
    private lateinit var etGlCodComId: EditText
    private lateinit var etAccountedCrAmount: EditText
    private lateinit var etAccountedDrAmount: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private var gljl1: GLJLModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gljlview_screen)

        sqlHelperForGLJL = SQLHelperForGLJL(this)
        sqlHelperForGLJournals = SQLHelperForGLJournals(this)

        recyclerView = findViewById(R.id.gljlRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getGLJL()
        adapter.setOnClickItem { GLJL ->
            val intent = Intent(this, GLJLMainScreen::class.java)
            intent.putExtra("selectedGLJLUpdated", GLJL)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteGLJL(it.journalLineId)
        }
    }

    override fun onResume() {
        super.onResume()
        val updateGLJL = intent.getSerializableExtra("selectedGLJLUpdated") as? GLJLModel
        if (updateGLJL != null){
            gljl1 = updateGLJL
            etJournalId.setText(updateGLJL.journalId.toString())
            etJournalDate.setText(updateGLJL.journalDate)
            etGlCodComId.setText(updateGLJL.glCodComId)
            etAccountedCrAmount.setText(updateGLJL.accountedCrAmount.toString())
            etAccountedDrAmount.setText(updateGLJL.accountedDrAmount.toString())
            etUpdateDate.setText(updateGLJL.updateDate)
            etCreationDate.setText(updateGLJL.creationDate)
        }
    }

    private fun getGLJL() {
        val gljlList = sqlHelperForGLJL.getAllGLJL()
        adapter.addItems(gljlList)
    }

    fun deleteGLJL(id: Int) {
        if (id <= 0) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete GLJL Info ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqlHelperForGLJL.deleteGLJLById(id)

            val gljlList = sqlHelperForGLJL.getAllGLJL()
            var deletedGLJLIndex = -1
            for (i in gljlList.indices) {
                if (gljlList[i].journalLineId == id) {
                    deletedGLJLIndex = i
                    break
                }
            }
            adapter.updateGLJLList(gljlList)
            dialog.dismiss()
            var goToGLJLMainScreen = Intent(this, GLJLMainScreen::class.java)
            startActivity(goToGLJLMainScreen)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }
}