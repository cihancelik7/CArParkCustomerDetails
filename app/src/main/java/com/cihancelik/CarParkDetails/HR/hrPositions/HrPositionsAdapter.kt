package com.cihancelik.CarParkDetails.HR.hrPositions

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrJobs
import com.cihancelik.carparkcustomerdetails.R

class HrPositionsAdapter(private var sqLiteHelperForHrJobs: SQLiteHelperForHrJobs) : RecyclerView.Adapter<HrPositionsAdapter.HrPositionViewHolder>() {
    private var hrPositionList: ArrayList<HrPositionsModel> = ArrayList()
    private var onClickItem: ((HrPositionsModel) -> Unit)? = null
    private var onClickDeleteItem: ((HrPositionsModel) -> Unit)? = null

    fun addItems(items:ArrayList<HrPositionsModel>){
        this.hrPositionList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(HrPositionsModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (HrPositionsModel) -> Unit){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HrPositionViewHolder {
        return HrPositionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_items_hr_positions,parent,false)
        )
    }

    override fun onBindViewHolder(holder: HrPositionViewHolder, position: Int) {
        val hrPosition = hrPositionList[position]
        holder.bindView(hrPosition,sqLiteHelperForHrJobs)
        holder.itemView.setOnClickListener { onClickItem?.invoke(hrPosition)
        val intent = Intent(it.context,HrPositionsMainActivity::class.java)
            intent.putExtra("selectedHrPositionInfo",hrPosition)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(hrPosition)}
    }

    override fun getItemCount(): Int {
        return hrPositionList.size
    }
    fun updatePositionList(hrPositionListUpdate : List<HrPositionsModel>){
        hrPositionList.clear()
        hrPositionList.addAll(hrPositionListUpdate)
    }

    class HrPositionViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var positionId = view.findViewById<TextView>(R.id.hrPositionId)
        private var positionName = view.findViewById<TextView>(R.id.hrPositionNameTv)
        private var startDate = view.findViewById<TextView>(R.id.hrPositionStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrPositionEndDateTv)
        private var jobId = view.findViewById<TextView>(R.id.hrPositionJobIdTv)
        private var updateDate = view.findViewById<TextView>(R.id.hrPositionUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.hrPositionCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.hrPositionDeleteBtn)

        fun bindView(hrPosition:HrPositionsModel,sqLiteHelperForHrJobs:SQLiteHelperForHrJobs){
            var jobName = sqLiteHelperForHrJobs.getHrJobNameById(hrPosition.jobId)?:"Unknown"
            positionId.text = "Position Id: "+hrPosition.positionId.toString()
            positionName.text = "Position Name: "+hrPosition.positionName
            startDate.text = "Start Date: "+hrPosition.startDate
            endDate.text = "End Date: "+hrPosition.endDate
            jobId.text = "Job Id: "+hrPosition.jobId.toString()+" $jobName"
            updateDate.text = "Update Date: "+hrPosition.updateDate
            creationDate.text = "Creation Date: "+hrPosition.creationDate

        }
    }
}