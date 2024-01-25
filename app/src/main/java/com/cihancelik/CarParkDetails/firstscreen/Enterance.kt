package com.cihancelik.CarParkDetails.firstscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.cihancelik.CarParkDetails.CRM.CRMViewPage
import com.cihancelik.CarParkDetails.HR.HrViewPage
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.CarParkDetails.debt.DebtViewPage
import com.cihancelik.CarParkDetails.general.GeneralViewPage
import com.cihancelik.CarParkDetails.ledger.LedgerViewPage
import com.cihancelik.CarParkDetails.purchasing.PurchasingViewPage
import com.cihancelik.CarParkDetails.receivable.ReceivableViewPage
import com.cihancelik.carparkcustomerdetails.R
import com.cihancelik.carparkcustomerdetails.databinding.ActivityEnteranceBinding

class Enterance : AppCompatActivity() {
    private lateinit var hrEmployeeCountTextView: TextView
    private lateinit var binding: ActivityEnteranceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnteranceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var purchasingPage = Intent(this, PurchasingViewPage::class.java)
        binding.purchasingIv.setOnClickListener { startActivity(purchasingPage) }


        var crmPage = Intent(this, CRMViewPage::class.java)
        binding.crmIv.setOnClickListener { startActivity(crmPage) }


        var ledgerPage = Intent(this, LedgerViewPage::class.java)
        binding.ledgerIv.setOnClickListener { startActivity(ledgerPage) }


        var receivablePage = Intent(this, ReceivableViewPage::class.java)
        binding.receivableIv.setOnClickListener { startActivity(receivablePage) }


        var hrViewPage = Intent(this, HrViewPage::class.java)
        binding.hrIv.setOnClickListener { startActivity(hrViewPage) }


        var debtViewPage = Intent(this, DebtViewPage::class.java)
        binding.debtIv.setOnClickListener { startActivity(debtViewPage) }


        var generalViewPage = Intent(this, GeneralViewPage::class.java)
        binding.generalIv.setOnClickListener { startActivity(generalViewPage) }

        hrEmployeeCountTextView = findViewById<TextView>(R.id.hrEmployeeCount)
        val employeeCount = intent.getIntExtra("employeeCount", 0)
        hrEmployeeCountTextView.text = employeeCount.toString()

        binding.refreshBtn.setOnClickListener { updateEmployeeCount() }

        // İlk yüklemede çalışan sayısını güncelle
        updateEmployeeCount()
    }

    private fun updateEmployeeCount() {
        val sqLiteHelperForHrEmployees = SQLiteHelperForHrEmployees(this)
        val count = sqLiteHelperForHrEmployees.getEmployeeCount()
        hrEmployeeCountTextView.text = "Employee: $count"
    }
}



