package com.cihancelik.CarParkDetails.ledger.glPeriods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class GLPeriodsAdapter:RecyclerView.Adapter<GLPeriodsAdapter.GLPeriodViewHolder>() {
    private var GLPeriodList : ArrayList<GLPeriodsModel> = ArrayList()
    private var onClickItem : ((GLPeriodsModel) -> Unit)? = null
    private var onClickDeleteItem : ((GLPeriodsModel)-> Unit)? = null

    fun addItems(items:ArrayList<GLPeriodsModel>){
        this.GLPeriodList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(GLPeriodsModel)-> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (GLPeriodsModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GLPeriodViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_glperiods,parent,false)
    )
    override fun onBindViewHolder(holder: GLPeriodViewHolder, position: Int) {
        val glPeriod = GLPeriodList[position]
        holder.bindView(glPeriod)
        holder.itemView.setOnClickListener { onClickItem?.invoke(glPeriod)
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(glPeriod) }}
    }
    override fun getItemCount(): Int {
        return GLPeriodList.size
    }
    fun updateGLPeriodList(glPeriodListUpdate:List<GLPeriodsModel>){
        GLPeriodList.clear()
        GLPeriodList.addAll(glPeriodListUpdate)
    }
    class GLPeriodViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.periodIdTv)
        private var periodName = view.findViewById<TextView>(R.id.glPeriodNameTv)
        private var periodYear = view.findViewById<TextView>(R.id.glPeriodYearTv)
        private var updateDate = view.findViewById<TextView>(R.id.glPeriodUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.glperiodCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.btnDeleteGLPeriod)

        fun bindView(glPeriod:GLPeriodsModel){
            id.text = glPeriod.periodId.toString()
            periodName.text = glPeriod.periodName
            periodYear.text = glPeriod.periodYear
            updateDate.text = glPeriod.updateDate
            creationDate.text = glPeriod.creationDate
        }
    }
}