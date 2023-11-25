package com.cihancelik.CarParkDetails.HR.hrLocations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrLocations
import com.cihancelik.carparkcustomerdetails.R

class HrLocationsViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = HrLocationsAdapter()
    private lateinit var sqlHelperForAddresses: SQLHelperForAddresses
    private lateinit var sqLiteHelperForHrLocations: SQLiteHelperForHrLocations
    private lateinit var etLocationName: EditText
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var etAddressId: EditText
    private lateinit var etNaceCode: EditText
    private lateinit var etDangerClass: EditText
    private lateinit var etUpdateDate: EditText
    private lateinit var etCreationDate: EditText

    private var hrLocation1: HrLocationsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_locations_view)

        sqLiteHelperForHrLocations = SQLiteHelperForHrLocations(this)
        sqlHelperForAddresses = SQLHelperForAddresses(this)
        recyclerView = findViewById(R.id.hrLocationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getHrLocation()
        adapter.setOnClickItem { HrLocation ->
            val intent = Intent(this, HrLocationsMainActivity::class.java)
            intent.putExtra("selectedHrLocationUpdated", HrLocation)
            startActivity(intent)
        }
        adapter.setOnClickDeleteItem {
            deleteHrLocation(it.locationId)
        }
    }

    override fun onResume() {
        super.onResume()
        val updateHrLocation =
            intent.getSerializableExtra("selectedHrLocationInfo") as? HrLocationsModel
        if (updateHrLocation!=null){
            hrLocation1 = updateHrLocation
            etLocationName.setText(updateHrLocation.locationName)
            etStartDate.setText(updateHrLocation.startDate)
            etEndDate.setText(updateHrLocation.endDate)
            etAddressId.setText(updateHrLocation.addressId.toString())
            etNaceCode.setText(updateHrLocation.naceCode.toString())
            etDangerClass.setText(updateHrLocation.dangerClass)
            etUpdateDate.setText(updateHrLocation.updateDate)
            etCreationDate.setText(updateHrLocation.creationDate)
        }
    }
    private fun getHrLocation() {
        val hrLocationList = sqLiteHelperForHrLocations.getAllHrLocations()
        adapter.addItems(hrLocationList)
    }
    private fun deleteHrLocation(locationId: Int) {
        if (locationId <= 0) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete HrLocation Info???")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqLiteHelperForHrLocations.deleteHrLocationById(locationId)

            val hrLocationList = sqLiteHelperForHrLocations.getAllHrLocations()
            var deletedHrLocationIndex = -1
            for (i in hrLocationList.indices) {
                if (hrLocationList[i].locationId == locationId) {
                    deletedHrLocationIndex = i
                    break
                }
            }
            adapter.updateLocationList(hrLocationList)
            dialog.dismiss()
            var goToHrLocationMainActivity = Intent(this, HrLocationsMainActivity::class.java)
            startActivity(goToHrLocationMainActivity)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        var alert = builder.create()
        alert.show()
    }

}