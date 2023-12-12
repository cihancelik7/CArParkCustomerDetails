package com.cihancelik.CarParkDetails.HR.hrOrganizations

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class HrOrganizationsAdapter :
    RecyclerView.Adapter<HrOrganizationsAdapter.HrOrganizationViewHolder>() {
    private var hrOrganizationList: ArrayList<HrOrganizationsModel> = ArrayList()
    private var onClickItem: ((HrOrganizationsModel) -> Unit)? = null
    private var onClickDeleteItem: ((HrOrganizationsModel) -> Unit)? = null

    fun addItems(items:ArrayList<HrOrganizationsModel>){
        this.hrOrganizationList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(HrOrganizationsModel)->Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (HrOrganizationsModel) -> Unit){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HrOrganizationsAdapter.HrOrganizationViewHolder {
        return HrOrganizationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_items_hr_organizations,parent,false)
        )
    }

    override fun onBindViewHolder(
        holder: HrOrganizationsAdapter.HrOrganizationViewHolder,
        position: Int
    ) {
     val hrOrg = hrOrganizationList[position]
        holder.bindView(hrOrg)
        holder.itemView.setOnClickListener { onClickItem?.invoke(hrOrg)
        val intent = Intent(it.context,HrOrganizationsMainActivity::class.java)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(hrOrg) }

    }

    override fun getItemCount(): Int {
    return hrOrganizationList.size
    }
    fun updateOrganizationList(hrOrganizationUpdate:List<HrOrganizationsModel>){
        hrOrganizationList.clear()
        hrOrganizationList.addAll(hrOrganizationUpdate)
    }


    class HrOrganizationViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var organizationId = view.findViewById<TextView>(R.id.hrOrganizationLocationIdTv)
        private var organizationName = view.findViewById<TextView>(R.id.hrOrganizationNameTv)
        private var startDate = view.findViewById<TextView>(R.id.hrOrganizationStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrOrganizationEndDateTv)
        private var parentOrgId =
            view.findViewById<TextView>(R.id.hrOrganizationParentOrganizationIdTv)
        private var locationId = view.findViewById<TextView>(R.id.hrOrganizationLocationIdTv)
        private var updateDate = view.findViewById<TextView>(R.id.hrOrganizationUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.hrOrganizationCreationDateTv)

        var btnDelete = view.findViewById<TextView>(R.id.hrOrganizationDeleteBtn)

        fun bindView(hrOrganzation: HrOrganizationsModel) {
            organizationId.text = "Organization Id: " + hrOrganzation.organizationId.toString()
            organizationName.text = "Organization Name: " + hrOrganzation.organizationName
            startDate.text = "Start Date: " + hrOrganzation.startDate
            endDate.text = "End Date: " + hrOrganzation.endDate
            parentOrgId.text = "Parent Org Id: " + hrOrganzation.parentOrgId.toString()
            locationId.text = "Location Id: " + hrOrganzation.locationId.toString()
            updateDate.text = "Update Date: " + hrOrganzation.updateDate
            creationDate.text = "Creation Date: " + hrOrganzation.creationDate
        }


    }
}