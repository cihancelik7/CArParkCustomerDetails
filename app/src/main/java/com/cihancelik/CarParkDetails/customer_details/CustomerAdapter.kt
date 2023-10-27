package com.cihancelik.CarParkDetails.customer_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    private var cstList : ArrayList<CustomerModel> = ArrayList()
    private var onClickItem: ((CustomerModel) -> Unit)? = null
    private var onClickDeleteItem: ((CustomerModel) -> Unit)? = null

    fun addItems(items:ArrayList<CustomerModel>){
        this.cstList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (CustomerModel) -> Unit){
        this.onClickItem = callback

    }
    fun setOnClickDeleteItem(callback: (CustomerModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  = CustomerViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_customers,parent,false)
    )



    override fun getItemCount(): Int {
       return cstList.size
    }



    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val cst = cstList[position]
        holder.bindView(cst)
        holder.itemView.setOnClickListener {onClickItem?.invoke(cst)}
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(cst) }


    }
    fun removeItem(position: Int) {
        if (position in 0 until cstList.size) {
            val deletedCustomer = cstList.removeAt(position)
            notifyDataSetChanged()

            // Silinen müşteriden sonraki müşterilerin ID'lerini güncelle
            val deletedCustomerId = deletedCustomer.id
            for (i in position until cstList.size) {
                val customer = cstList[i]
                customer.id = deletedCustomerId + i
            }
        }
    }
    fun updateCustomerList(customerList: List<CustomerModel>) {
        cstList.clear()
        cstList.addAll(customerList)
        notifyDataSetChanged()
    }


    class CustomerViewHolder(var view: View):RecyclerView.ViewHolder(view){

        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var lastname = view.findViewById<TextView>(R.id.tvLastName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        private var city = view.findViewById<TextView>(R.id.tvCity)
        private var carplate = view.findViewById<TextView>(R.id.tvCarPlate)

         var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(cst: CustomerModel){
            id.text = cst.id.toString()
            name.text = cst.name
            lastname.text = cst.lastName
            email.text = cst.email
            phone.text = cst.phone
            address.text = cst.address
            city.text = cst.city
            carplate.text = cst.carplate

        }
    }
}