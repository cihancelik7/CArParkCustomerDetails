package com.cihancelik.CarParkDetails.HR.hrEmployees


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class HrEmployeesAdapter:RecyclerView.Adapter<HrEmployeesAdapter.HrEmployeesViewHolder>() {

    private var hrEmpList: ArrayList<HrEmployeesModel> = ArrayList()
    private var onClickItem: ((HrEmployeesModel) -> Unit)? = null
    private var onClickDeleteItem: ((HrEmployeesModel) -> Unit)? = null

    fun addItems(items:ArrayList<HrEmployeesModel>){
        this.hrEmpList = items
        notifyDataSetChanged()
    }
    fun setOnclickItem(callback:(HrEmployeesModel)-> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback:(HrEmployeesModel)-> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HrEmployeesViewHolder {
        return HrEmployeesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_items_hr_employees, parent, false)
        )
    }
    override fun onBindViewHolder(holder: HrEmployeesViewHolder, position: Int) {
        val hrEmp = hrEmpList[position]
       holder.bindView(hrEmp)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(hrEmp)
            val intent = Intent(it.context,HrEmployeesMainActivity::class.java)
            intent.putExtra("selectedHrEmployeeInfo",hrEmp)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(hrEmp) }
    }
    override fun getItemCount(): Int {
        return hrEmpList.size
    }
    fun updateHrEmployeeList(hrEmpListUpdate :List<HrEmployeesModel>){
        hrEmpList.clear()
        hrEmpList.addAll(hrEmpListUpdate)
    }
    class HrEmployeesViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var employeeId = view.findViewById<TextView>(R.id.hrEmployeesEmployeeIdTv)
        private var employeeNumber = view.findViewById<TextView>(R.id.hrEmployeesEmployeeNumberTv)
        private var startDate = view.findViewById<TextView>(R.id.hrEmployeesStartDateTv)
        private var endDate = view.findViewById<TextView>(R.id.hrEmployeesEndDateTv)
        private var isActive = view.findViewById<TextView>(R.id.hrEmployeesIsActiveTv)
        private var firstName = view.findViewById<TextView>(R.id.hrEmployeesFirstNameTv)
        private var lastName = view.findViewById<TextView>(R.id.hrEmployeesLastNameTv)
        private var birthDate = view.findViewById<TextView>(R.id.hrEmployeesBirthDateTv)
        private var nationalId = view.findViewById<TextView>(R.id.hrEmployeesNationalIdTv)
        private var martialStatus = view.findViewById<TextView>(R.id.hrEmployeesMartialStatusTv)
        private var gender = view.findViewById<TextView>(R.id.hrEmployeesGenderTv)
        private var addressId = view.findViewById<TextView>(R.id.hrEmployeesAddressIdTv)
        private var emailAddress = view.findViewById<TextView>(R.id.hrEmployeesAddressIdTv)

        var btnDelete = view.findViewById<Button>(R.id.hrEmployeesDeleteBtn)

        fun bindView(hrEmp: HrEmployeesModel) {
            employeeId.text = hrEmp.employeeId.toString()
            employeeNumber.text = hrEmp.employeeNumber.toString()
            startDate.text = hrEmp.startDate
            endDate.text = hrEmp.endDate
            isActive.text = hrEmp.isActive
            firstName.text = hrEmp.firstName
            lastName.text = hrEmp.lastName
            birthDate.text = hrEmp.birthDate
            nationalId.text = hrEmp.nationalId.toString()
            martialStatus.text = hrEmp.martialStatus
            gender.text = hrEmp.gender
            addressId.text = hrEmp.addressId.toString()
            emailAddress.text = hrEmp.emailAddress
        }

    }

}