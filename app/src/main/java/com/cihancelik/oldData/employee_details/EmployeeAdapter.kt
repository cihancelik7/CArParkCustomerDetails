package com.cihancelik.oldData.employee_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private var empList:ArrayList<EmployeeModel> = ArrayList()
    private var onClickItem : ((EmployeeModel) -> Unit)? = null
    private var onClickDeleteItem : ((EmployeeModel) -> Unit)? = null

    fun addItems(items:ArrayList<EmployeeModel>){
        this.empList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (EmployeeModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (EmployeeModel) -> Unit){
        this.onClickDeleteItem = callback
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    )= EmployeeViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_employee,parent,false)
        )

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val emp = empList[position]
        holder.bindView(emp)
        holder.itemView.setOnClickListener { onClickItem?.invoke(emp) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(emp) }
    }

    override fun getItemCount(): Int {
        return empList.size
    }
    fun removeItem(position: Int){
        if (position in 0 until empList.size){
            val deleteEmployee = empList.removeAt(position)
            notifyDataSetChanged()

            val deletedEmployeeId = deleteEmployee.id
            for (i in position until empList.size){
                val employee = empList[i]
                employee.id  = deletedEmployeeId + i
            }
        }
    }
    fun updateEmployeeList(employeeList:List<EmployeeModel>){
        empList.clear()
        empList.addAll(employeeList)
        notifyDataSetChanged()
    }

    class EmployeeViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvIdC)
        private var name = view.findViewById<TextView>(R.id.tvNameC)
        private var lastName = view.findViewById<TextView>(R.id.tvLastNameC)
        private var department = view.findViewById<TextView>(R.id.tvDepartmentC)
        private var email = view.findViewById<TextView>(R.id.tvEmailC)
        private var address = view.findViewById<TextView>(R.id.tvAddressC)

        var btnDelete  = view.findViewById<Button>(R.id.btnDeleteC)

        fun bindView(emp: EmployeeModel){
            id.text = emp.id.toString()
            name.text = emp.name
            lastName.text = emp.lastName
            department.text = emp.department
            email.text = emp.email
            address.text = emp.address
        }

    }
}