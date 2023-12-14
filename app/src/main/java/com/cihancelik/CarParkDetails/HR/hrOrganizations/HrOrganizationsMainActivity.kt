package com.cihancelik.CarParkDetails.HR.hrOrganizations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrLocations
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrOrganizations
import com.cihancelik.carparkcustomerdetails.R

class HrOrganizationsMainActivity : AppCompatActivity() {
    private lateinit var etOrganizationName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etParentOrgId: EditText
    private lateinit var etLocationId: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private lateinit var btnAdd : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate :Button

    private lateinit var sqlHelperForOrganizations:SQLiteHelperForHrOrganizations
    private lateinit var sqlHelperForLocations:SQLiteHelperForHrLocations
    private var hrOrgInfo1 : HrOrganizationsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_organizations_main)

        initView()

        sqlHelperForOrganizations = SQLiteHelperForHrOrganizations(this)
        sqlHelperForLocations = SQLiteHelperForHrLocations(this)

        val selectedHrOrgInfo =
            intent.getSerializableExtra("selectedHrOrganizationInfo") as? HrOrganizationsModel

        if (selectedHrOrgInfo != null){
            etOrganizationName.setText(selectedHrOrgInfo.organizationName)
            etStartDate.setText(selectedHrOrgInfo.startDate)
            etEndDate.setText(selectedHrOrgInfo.endDate)
            etParentOrgId.setText(selectedHrOrgInfo.parentOrgId.toString())
            etLocationId.setText(selectedHrOrgInfo.locationId.toString())
            etUpdateDate.setText(selectedHrOrgInfo.updateDate)
            etCreationDate.setText(selectedHrOrgInfo.creationDate)

            hrOrgInfo1 = selectedHrOrgInfo
        }
        btnAdd.setOnClickListener { addHrOrganization() }

        var goToHrOrgViewActivity = Intent(this,HrOrganizationsViewActivity::class.java)
        btnView.setOnClickListener { startActivity(goToHrOrgViewActivity) }

        var selectedHrOrgUpdate =
            intent.getSerializableExtra("selectedHrOrganizationUpdated") as? HrOrganizationsModel

        if(selectedHrOrgUpdate != null){
            etOrganizationName.setText(selectedHrOrgUpdate.organizationName)
            etStartDate.setText(selectedHrOrgUpdate.startDate)
            etEndDate.setText(selectedHrOrgUpdate.endDate)
            etParentOrgId.setText(selectedHrOrgUpdate.parentOrgId.toString())
            etLocationId.setText(selectedHrOrgUpdate.locationId.toString())
            etUpdateDate.setText(selectedHrOrgUpdate.updateDate)
            etCreationDate.setText(selectedHrOrgUpdate.creationDate)
        }
        btnUpdate.setOnClickListener { updateOrganization() }
    }

    private fun updateOrganization() {
        val organizationName = etOrganizationName.text.toString()
        val startDate = etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val parentOrgId = etParentOrgId.text.toString()
        val locationId = etLocationId.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (hrOrgInfo1 != null){
            val updateHrOrganization = HrOrganizationsModel(
                organizationId = hrOrgInfo1!!.organizationId,
                organizationName = organizationName,
                startDate = startDate,
                endDate = endDate,
                parentOrgId = hrOrgInfo1!!.parentOrgId,
                locationId = hrOrgInfo1!!.locationId,
                updateDate = updateDate,
                creationDate = creationDate
            )
            val isUpdate = isUpdate(updateHrOrganization)
            if (isUpdate){
                val status = sqlHelperForOrganizations.updateHrOrg(updateHrOrganization)
                if (status > -1){
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HrOrganizationsViewActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Update Failed...", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Changes Were Made", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isUpdate(updateHrOrg : HrOrganizationsModel):Boolean{
        val currentHrOrg = sqlHelperForOrganizations.getHrOrganizationById(updateHrOrg.organizationId)
        return currentHrOrg != updateHrOrg
    }

    private fun addHrOrganization() {
        val organizationName = etOrganizationName.text.toString()
        val startDate =  etStartDate.text.toString()
        val endDate = etEndDate.text.toString()
        val parentOrgId = etParentOrgId.text.toString()
        val locationIdText = etLocationId.text.toString()
        val updateDate = etUpdateDate.text.toString()
        val creationDate = etCreationDate.text.toString()

        if (organizationName.isEmpty()|| startDate.isEmpty()|| parentOrgId.isEmpty()|| locationIdText.isEmpty()||
            updateDate.isEmpty()||creationDate.isEmpty()){
            Toast.makeText(this, "Please Enter Requirement Field", Toast.LENGTH_SHORT).show()
        }else{
            val enteredLocationId = locationIdText.toInt()
            val locationId = sqlHelperForLocations.getHrLocationById(enteredLocationId)

            if (locationId!= null){
                val hrOrgInfo = HrOrganizationsModel(
                    organizationId = 0,
                    organizationName = organizationName,
                    startDate = startDate,
                    endDate = endDate,
                    parentOrgId = parentOrgId.toInt(),
                    locationId = enteredLocationId,
                    updateDate = updateDate,
                    creationDate = creationDate
                )
                val status : Long = sqlHelperForOrganizations.insertHrOrganizations(hrOrgInfo)
                if (status > -1){
                    Toast.makeText(this, "Hr Location Added", Toast.LENGTH_SHORT).show()
                    clearEditText()
                }else{
                    Toast.makeText(this, "Record Not Saved!!!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(
                    this,
                    "Hr Location With The Given Location Id does not exists.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearEditText() {
        etOrganizationName.setText("")
        etStartDate.setText("")
        etEndDate.setText("")
        etParentOrgId.setText("")
        etLocationId.setText("")
        etUpdateDate.setText("")
        etCreationDate.setText("")

        etOrganizationName.requestFocus()
    }

    private fun initView() {
        etOrganizationName = findViewById(R.id.hrOrganizationNameTv)
        etStartDate = findViewById(R.id.hrOrganizationStartDateTv)
        etEndDate = findViewById(R.id.hrOrganizationEndDateTv)
        etParentOrgId = findViewById(R.id.hrOrganizationParentOrganizationIdTv)
        etLocationId = findViewById(R.id.hrOrganizationLocationIdTv)
        etUpdateDate = findViewById(R.id.hrOrganizationUpdateDateTv)
        etCreationDate = findViewById(R.id.hrOrganizationCreationDateTv)

        btnAdd = findViewById(R.id.hrOrganizationAddBtn)
        btnView = findViewById(R.id.hrOrganizationViewBtn)
        btnUpdate = findViewById(R.id.hrOrganizationUpdateBtn)
    }
}