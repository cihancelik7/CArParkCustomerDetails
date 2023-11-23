package com.cihancelik.CarParkDetails.HR.hrEmployees


import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.CarParkDetails.SQL.general.SQLHelperForAddresses
import com.cihancelik.CarParkDetails.general.addressesUpdateScreen.AddressessModel
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
        holder.itemView.setOnClickListener { onClickItem?.invoke(hrEmp) }
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
        private var emailAddress = view.findViewById<TextView>(R.id.hrEmployeesEmailAddressTv)

        var btnDelete = view.findViewById<Button>(R.id.hrEmployeesDeleteBtn)

        fun bindView(hrEmp: HrEmployeesModel) {
            employeeId.text = "EmployeeId: "+hrEmp.employeeId.toString()
            employeeNumber.text = "Employee Number: "+hrEmp.employeeNumber.toString()
            startDate.text = "Start Date: "+hrEmp.startDate
            endDate.text = "End Date: "+hrEmp.endDate
            isActive.text = "Is Active: "+ hrEmp.isActive
            firstName.text = "First Name: "+hrEmp.firstName
            lastName.text = "Last Name: "+hrEmp.lastName
            birthDate.text = "Birth Date: "+hrEmp.birthDate
            nationalId.text = "National Id: "+hrEmp.nationalId.toString()
            martialStatus.text = "Martial Status: "+hrEmp.martialStatus
            gender.text = "Gender: "+hrEmp.gender
            addressId.text = "AddressId"+hrEmp.addressId.toString()
            emailAddress.text = "Email Address: "+hrEmp.emailAddress

           /* val employeeIdText = "EmployeeId: " + hrEmp.employeeId.toString()
            val spannable = SpannableStringBuilder(employeeIdText)

// "EmployeeId: " kısmını kalın yapma
            val boldSpan = StyleSpan(Typeface.BOLD)
            spannable.setSpan(boldSpan, 0, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

// TextView'ye biçimlendirilmiş metni ayarlama
            employeeId.text = spannable*/
        }

    }

}