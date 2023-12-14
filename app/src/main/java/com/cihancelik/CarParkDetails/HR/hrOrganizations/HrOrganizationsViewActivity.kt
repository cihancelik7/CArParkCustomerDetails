package com.cihancelik.CarParkDetails.HR.hrOrganizations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrLocations
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrOrganizations
import com.cihancelik.carparkcustomerdetails.R

class HrOrganizationsViewActivity : AppCompatActivity() {
    private lateinit var recylerView : RecyclerView
    private val adapter = HrOrganizationsAdapter()
    private lateinit var sqLiteHelperForHrLocations: SQLiteHelperForHrLocations
    private lateinit var sqLiteHelperForHrOrganizations: SQLiteHelperForHrOrganizations
    private lateinit var etOrganizationName : EditText
    private lateinit var etStartDate : EditText
    private lateinit var etEndDate : EditText
    private lateinit var etParentOrganizationId :EditText
    private lateinit var etLocationId : EditText
    private lateinit var etUpdateDate : EditText
    private lateinit var etCreationDate : EditText

    private var hrOrg1 : HrOrganizationsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_organizations_view)

        sqLiteHelperForHrOrganizations = SQLiteHelperForHrOrganizations(this)
        sqLiteHelperForHrLocations = SQLiteHelperForHrLocations(this)
        recylerView = findViewById(R.id.hrOrganizationRecyclerView)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.adapter = adapter

        getHrOrg()
        adapter.setOnClickItem { HrOrg ->
            val intent = Intent(this,HrOrganizationsMainActivity::class.java)
            intent.putExtra("selectedHrOrganizationUpdated",HrOrg)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteHrOrg(it.organizationId)
        }
    }

    override fun onResume() {
        super.onResume()
        val updateHrOrg =
            intent.getSerializableExtra("selectedHrOrganizationInfo") as? HrOrganizationsModel
        if (updateHrOrg!=null){
            hrOrg1 = updateHrOrg
            etOrganizationName.setText(updateHrOrg.organizationName)
            etStartDate.setText(updateHrOrg.startDate)
            etEndDate.setText(updateHrOrg.endDate)
            etParentOrganizationId.setText(updateHrOrg.parentOrgId.toString())
            etLocationId.setText(updateHrOrg.locationId.toString())
            etUpdateDate.setText(updateHrOrg.updateDate)
            etCreationDate.setText(updateHrOrg.creationDate)

        }
    }
    private fun getHrOrg() {
        val hrOrgList = sqLiteHelperForHrOrganizations.getAllHrOrganizations()
        adapter.addItems(hrOrgList)
    }
    private fun deleteHrOrg(organizationId: Int) {
        if (organizationId <= 0)return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete HrOrganization Info ???")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog,_ ->
            sqLiteHelperForHrOrganizations.deleteHrOrganizationById(organizationId)

            val hrOrgList = sqLiteHelperForHrOrganizations.getAllHrOrganizations()
            var deleteHrOrgIndex = -1
            for (i in hrOrgList.indices){
                if (hrOrgList[i].organizationId == organizationId){
                    deleteHrOrgIndex = i
                    break
                }
            }
            adapter.updateOrganizationList(hrOrgList)
            dialog.dismiss()
            var goToOrganizationMainActivity = Intent(this,HrOrganizationsMainActivity::class.java)
            startActivity(goToOrganizationMainActivity)
        }
        builder.setNegativeButton("No"){dialog,_ ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }


}