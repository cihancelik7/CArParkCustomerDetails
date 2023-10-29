package com.cihancelik.CarParkDetails.firstscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.cihancelik.CarParkDetails.CRM.CRMViewPage
import com.cihancelik.CarParkDetails.HR.HrViewPage
import com.cihancelik.CarParkDetails.debt.DebtViewPage
import com.cihancelik.CarParkDetails.general.GeneralViewPage
import com.cihancelik.CarParkDetails.ledger.LedgerViewPage
import com.cihancelik.CarParkDetails.purchasing.PurchasingViewPage
import com.cihancelik.CarParkDetails.receivable.ReceivableViewPage
import com.cihancelik.oldData.customer_details.CustomerMainActivity
import com.cihancelik.carparkcustomerdetails.R
import com.cihancelik.carparkcustomerdetails.databinding.ActivityEnteranceBinding

class Enterance : AppCompatActivity() {



    private lateinit var binding : ActivityEnteranceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnteranceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var purchasingPage = Intent(this,PurchasingViewPage::class.java)
        binding.purchasingIv.setOnClickListener {  startActivity(purchasingPage)}
        binding.purchasingTv.setOnClickListener {  startActivity(purchasingPage)}

        var crmPage = Intent(this,CRMViewPage::class.java)
        binding.crmIv.setOnClickListener { startActivity(crmPage) }
        binding.crmTv.setOnClickListener { startActivity(crmPage) }

        var ledgerPage = Intent(this,LedgerViewPage::class.java)
        binding.ledgerIv.setOnClickListener { startActivity(ledgerPage) }
        binding.ledgerTv.setOnClickListener { startActivity(ledgerPage) }

        var receivablePage = Intent(this,ReceivableViewPage::class.java)
        binding.receivableIv.setOnClickListener { receivablePage }
        binding.receivableTv.setOnClickListener { receivablePage }

        var hrViewPage = Intent(this,HrViewPage::class.java)
        binding.hrIv.setOnClickListener { hrViewPage }
        binding.hrTv.setOnClickListener { hrViewPage }

        var debtViewPage = Intent(this,DebtViewPage::class.java)
        binding.debtIv.setOnClickListener { debtViewPage }
        binding.debtTv.setOnClickListener { debtViewPage }

        var generalViewPage = Intent(this,GeneralViewPage::class.java)
        binding.generalIv.setOnClickListener { generalViewPage }
        binding.generalTv.setOnClickListener { generalViewPage }



        var customerServices = Intent(this, CustomerMainActivity::class.java)







    }
}