package com.cihancelik.CarParkDetails.HR.hrLocations

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.carparkcustomerdetails.R

class HrLocationsAdapter(private val sqlHelperForAddresses: SQLHelperForAddresses) : RecyclerView.Adapter<HrLocationsAdapter.HrLocationViewHolder>() {
    private var hrLocationList: ArrayList<HrLocationsModel> = ArrayList()
    private var onClickItem: ((HrLocationsModel) -> Unit)? = null
    private var onClickDeleteItem: ((HrLocationsModel) -> Unit)? = null

    fun addItems(items: ArrayList<HrLocationsModel>) {
        this.hrLocationList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (HrLocationsModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (HrLocationsModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HrLocationViewHolder {
        return HrLocationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_items_hr_locations, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HrLocationViewHolder, position: Int) {
        val hrLocation = hrLocationList[position]
        holder.bindView(hrLocation, sqlHelperForAddresses)
        holder.itemView.setOnClickListener { onClickItem?.invoke(hrLocation)
        val intent = Intent(it.context,HrLocationsMainActivity::class.java)
        intent.putExtra("selectedHrLocationInfo",hrLocation)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(hrLocation) }
    }

    override fun getItemCount(): Int {
        return hrLocationList.size
    }

    fun updateLocationList(hrLocationListUpdate: List<HrLocationsModel>) {
        hrLocationList.clear()
        hrLocationList.addAll(hrLocationListUpdate)
    }

    class HrLocationViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var locationId = view.findViewById<TextView>(R.id.hrLocationIdTv)
        private var locationName = view.findViewById<TextView>(R.id.hrLocationNameTv)
        private var startDate = view.findViewById<TextView>(R.id.hrLocationStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrLocationEndDateTv)
        private var addressId = view.findViewById<TextView>(R.id.hrLocationAddressIdTv)
        private var naceCode = view.findViewById<TextView>(R.id.hrLocationNaceCodeTv)
        private var dangerClass = view.findViewById<TextView>(R.id.hrLocationDangerClassTv)
        private var updateDate = view.findViewById<TextView>(R.id.hrLocationUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.hrLocationCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.hrLocationDeleteBtn)

        fun bindView(hrLocation: HrLocationsModel,sqlHelperForAddresses: SQLHelperForAddresses) {
            val address = sqlHelperForAddresses.getAddressNameById(hrLocation.addressId)?:"Unknown"
            locationId.text = "Location Id: "+ hrLocation.locationId.toString()
            locationName.text = "Location Name: "+ hrLocation.locationName
            startDate.text = "Start Date: "+ hrLocation.startDate
            endDate.text = "End Date: "+ hrLocation.endDate
            addressId.text = "Address Id: " + hrLocation.addressId.toString() + " Address : $address"
            naceCode.text = "Nace Code: " + hrLocation.naceCode.toString()
            dangerClass.text = "Danger Class: " + hrLocation.dangerClass
            updateDate.text = "Update Date: " + hrLocation.updateDate
            creationDate.text = "Creation Date: " + hrLocation.creationDate


        }
    }
}