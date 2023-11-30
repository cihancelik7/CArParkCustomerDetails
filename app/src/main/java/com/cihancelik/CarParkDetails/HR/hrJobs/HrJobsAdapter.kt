package com.cihancelik.CarParkDetails.HR.hrJobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class HrJobsAdapter : RecyclerView.Adapter<HrJobsAdapter.HrJobViewHolder>(){
    private var hrJobsList: ArrayList<HrJobsModel> = ArrayList()
    private var onClickItem : ((HrJobsModel)-> Unit)? = null
    private var onClickDeleteItem : ((HrJobsModel)-> Unit)? = null

    fun addItems(items: ArrayList<HrJobsModel>){
        this.hrJobsList = items
        notifyDataSetChanged()
    }
    fun setOnclickItem(callback: (HrJobsModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (HrJobsModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HrJobViewHolder {
        return HrJobViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_hr_jobs,parent,false)
        )
    }

    override fun getItemCount(): Int {
       return hrJobsList.size
    }

    override fun onBindViewHolder(holder: HrJobViewHolder, position: Int) {
        val hrJob = hrJobsList[position]
        holder.bindView(hrJob)
        holder.itemView.setOnClickListener { onClickItem?.invoke(hrJob) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(hrJob) }
    }
    fun updateJobList(hrJobListUpdate:List<HrJobsModel>){
        hrJobsList.clear()
        hrJobsList.addAll(hrJobListUpdate)
    }
    class HrJobViewHolder(var view:View):RecyclerView.ViewHolder(view) {
        private var jobId = view.findViewById<TextView>(R.id.hrJobsIdTv)
        private var jobName = view.findViewById<TextView>(R.id.hrJobsNameTv)
        private var startDate = view.findViewById<TextView>(R.id.hrJobsStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrJobsEndDateTV)
        private var updateDate = view.findViewById<TextView>(R.id.hrJobsUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.hrJobsCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.hrJobsDeleteBtnTv)

        fun bindView(hrJobs:HrJobsModel){
            jobId.text = hrJobs.jobId.toString()
            jobName.text = hrJobs.jobName
            startDate.text = hrJobs.startDate
            endDate.text = hrJobs.endDate
            updateDate.text = hrJobs.updateDate
            creationDate.text = hrJobs.creationDate
        }

    }

}