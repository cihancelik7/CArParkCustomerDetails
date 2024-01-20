package com.cihancelik.CarParkDetails.HR.hrEmpAssigments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.HR.hrEmployees.HrEmployeesModel
import com.cihancelik.CarParkDetails.SQL.hr.SQLiteHelperForHrEmployees
import com.cihancelik.carparkcustomerdetails.R
import org.w3c.dom.Text

class HrEmpAssigmentsAdapter(private val sqLiteHelperForHrEmployees: SQLiteHelperForHrEmployees) : RecyclerView.Adapter<HrEmpAssigmentsAdapter.HrEmpAssigmentsViewHolder>() {
    private var hrEmpAssigmentList : ArrayList<HrEmpAssigmentsModel> = ArrayList()
    private var onClickItem: ((HrEmpAssigmentsModel)->Unit)? = null
    private var onClickDeleteItem : ((HrEmpAssigmentsModel)->Unit)? = null


    fun addItems(items:ArrayList<HrEmpAssigmentsModel>){
        this.hrEmpAssigmentList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback : (HrEmpAssigmentsModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (HrEmpAssigmentsModel) -> Unit){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HrEmpAssigmentsViewHolder {
     return HrEmpAssigmentsViewHolder(
         LayoutInflater.from(parent.context)
             .inflate(R.layout.card_items_hr_emp_assigments,parent,false)
     )
    }

    override fun getItemCount(): Int {
        return hrEmpAssigmentList.size
    }

    override fun onBindViewHolder(holder: HrEmpAssigmentsViewHolder, position: Int) {
        val hrEmpAssigment = hrEmpAssigmentList[position]
        holder.bindView(hrEmpAssigment,sqLiteHelperForHrEmployees)
        holder.itemView.setOnClickListener { onClickItem?.invoke(hrEmpAssigment)
        val intent = Intent(it.context,HrEmpAssigmentsMainActivity::class.java)
        intent.putExtra("selectedHrEmpAssignmentInfo",hrEmpAssigment)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(hrEmpAssigment)}
    }
    fun updateHrEmpAssigmenList(hrEmpAssigmentListUpdate : List<HrEmpAssigmentsModel>){
        hrEmpAssigmentList.clear()
        hrEmpAssigmentList.addAll(hrEmpAssigmentListUpdate)
    }
    class HrEmpAssigmentsViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var assigmentId = view.findViewById<TextView>(R.id.hrEmpAssigmentsIdTv)
        private var empId = view.findViewById<TextView>(R.id.hrEmpAssigmentsEmpIdTv)
        private var positionId = view.findViewById<TextView>(R.id.hrEmpAssigmentsPositionIdTv)
        private var startDate = view.findViewById<TextView>(R.id.hrEmpAssigmentsStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrEmpAssigmentsEndDateTv)
        private var updateDate  = view.findViewById<TextView>(R.id.hrEmpAssigmentsUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.hrEmpAssigmentsCreationDateTv)
        private var hrEmpName = view.findViewById<TextView>(R.id.hrEmpAssigmentsEmpIdTv)


        var btnDelete = view.findViewById<TextView>(R.id.hrEmpAssigmentsDeleteBtn)

        fun bindView (hrEmpAssigment: HrEmpAssigmentsModel,sqLiteHelper: SQLiteHelperForHrEmployees){

            val employeeName = sqLiteHelper.getEmployeeNameById(hrEmpAssigment.employeeId) ?: "Unknown"
            assigmentId.text = "Assignment Id: "+hrEmpAssigment.assigmentId.toString()
            empId.text = "Employee Id: ${hrEmpAssigment.employeeId} Name: $employeeName"
            positionId.text = "Position Id: "+ hrEmpAssigment.positionId.toString()
            startDate.text = "Start Date: "+hrEmpAssigment.startDate
            endDate.text = "End Date: "+hrEmpAssigment.endDate
            updateDate.text = "Update Date: "+hrEmpAssigment.updateDate
            creationDate.text = "Creation Date: "+hrEmpAssigment.creationDate

        }

    }
}