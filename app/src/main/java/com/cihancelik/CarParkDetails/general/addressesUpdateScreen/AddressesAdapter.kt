package com.cihancelik.CarParkDetails.general.addressesUpdateScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R
import org.w3c.dom.Text

class AddressesAdapter : RecyclerView.Adapter<AddressesAdapter.AddressesViewHolder>() {
    private var addressesList: ArrayList<AddressessModel> = ArrayList()
    private var onClickItem: ((AddressessModel) -> Unit)? = null
    private var onClickDeleteItem: ((AddressessModel) -> Unit)? = null

    fun addItems(items: ArrayList<AddressessModel>) {
        this.addressesList = items
        notifyDataSetChanged()
    }

    fun setOnclickItem(callback: (AddressessModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (AddressessModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AddressesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_addresses, parent, false)
    )

    override fun onBindViewHolder(holder: AddressesAdapter.AddressesViewHolder, position: Int) {
        val addresses = addressesList[position]
        holder.bindView(addresses)
        holder.itemView.setOnClickListener { onClickItem?.invoke(addresses) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(addresses) }
    }

    override fun getItemCount(): Int {
        return addressesList.size
    }

    fun updateAddressesList(addressesListForUpdate: List<AddressessModel>) {
        addressesList.clear()
        addressesList.addAll(addressesListForUpdate)
    }

    class AddressesViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvAddressId)
        private var address = view.findViewById<TextView>(R.id.tvAddresses)
        private var startDateAddresses = view.findViewById<TextView>(R.id.tvStartDateAddessess)
        private var endDateAddresses = view.findViewById<TextView>(R.id.tvEndDateAddresses)
        private var countryAddresses = view.findViewById<TextView>(R.id.tvCountryAddresses)
        private var regionAddresses = view.findViewById<TextView>(R.id.tvRegionAddresses)
        private var postalCodeAddresses = view.findViewById<TextView>(R.id.tvPostalCodeAddresses)
        private var addressLine = view.findViewById<TextView>(R.id.tvAddressLineAddresses)
        private var updateDate = view.findViewById<TextView>(R.id.tvUpdateDateAddresses)
        private var creationDate = view.findViewById<TextView>(R.id.tvCreationDate)

        var btnDelete = view.findViewById<Button>(R.id.btnDeleteAddresses)

        fun bindView(addresses: AddressessModel) {
            id.text = addresses.addressId.toString()
            address.text = addresses.address
            startDateAddresses.text = addresses.startDate
            endDateAddresses.text = addresses.endDAte
            countryAddresses.text = addresses.country
            regionAddresses.text = addresses.region
            postalCodeAddresses.text = addresses.postalCode
            addressLine.text = addresses.addressLine
            updateDate.text = addresses.updateDate
            creationDate.text = addresses.creationDate

        }
    }
}