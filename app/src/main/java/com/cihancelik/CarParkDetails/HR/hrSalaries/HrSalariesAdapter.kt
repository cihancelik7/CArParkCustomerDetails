package com.cihancelik.CarParkDetails.HR.hrSalaries

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class HrSalariesAdapter : RecyclerView.Adapter<HrSalariesAdapter.HrSalaryViewHolder>() {
    private var hrSalariesList :ArrayList<HrSalariesModel> = ArrayList()
    private var onClickItem : ((HrSalariesModel)-> Unit)? = null
    private var onClickDeleteItem : ((HrSalariesModel)->Unit)? = null

    fun addItems(items:ArrayList<HrSalariesModel>){
        this.hrSalariesList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(HrSalariesModel)->Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (HrSalariesModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HrSalaryViewHolder {
      return HrSalaryViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.card_items_hr_salaries,parent,false)
      )
    }

    override fun onBindViewHolder(holder: HrSalaryViewHolder, position: Int) {
        val hrSalaries = hrSalariesList[position]
        holder.bindView(hrSalaries)
        holder.itemView.setOnClickListener {  onClickItem?.invoke(hrSalaries)
            val intent = Intent(it.context,HrSalariesMainActivity::class.java)
            intent.putExtra("selectedHrSalaryInfo",hrSalaries)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(hrSalaries)}
    }
    fun updateHrSalaryList(hrSalaryListUpdate: List<HrSalariesModel>){
        hrSalariesList.clear()
        hrSalariesList.addAll(hrSalaryListUpdate)
    }

    override fun getItemCount(): Int {
        return hrSalariesList.size
    }

    class HrSalaryViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var salaryId = view.findViewById<TextView>(R.id.hrSalaryIdTv)
        private var employeeId = view.findViewById<TextView>(R.id.hrSalaryEmployeeIdTv)
        private var amount = view.findViewById<TextView>(R.id.hrSalaryAmountTv)
        private var startDate = view.findViewById<TextView>(R.id.hrSalaryStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrSalaryEndDateTv)
        private var updateDate = view.findViewById<TextView>(R.id.hrSalaryUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.hrSalaryCreationDateTv)

        var btnDelete = view.findViewById<TextView>(R.id.hrSalaryDeleteBtn)

        fun bindView(hrSalary: HrSalariesModel) {
            salaryId.text = "Salary Id : ${hrSalary.salaryId.toString()}"
            employeeId.text = "Employee Id: ${hrSalary.employeeId.toString()}"
            amount.text = "Amount: ${hrSalary.amount.toString()}"
            startDate.text ="Start Date: ${hrSalary.startDate}"
            endDate.text = "End Date: ${hrSalary.endDate}"
            updateDate.text = "Update Date: ${hrSalary.updateDate}"
            creationDate.text = "Creation Date: ${hrSalary.creationDate}"

        }
    }
}