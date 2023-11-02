package com.cihancelik.CarParkDetails.ledger.glAccountCombinations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R


class GLACAdapter:RecyclerView.Adapter<GLACAdapter.GLACViewHolder>() {
    private var GLACList : ArrayList<GLACModel> = ArrayList()
    private var onClickItem : ((GLACModel) -> Unit)? = null
    private var onClickDeleteItem : ((GLACModel) -> Unit)? = null

    fun addItems(items:ArrayList<GLACModel>){
        this.GLACList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(GLACModel) -> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (GLACModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= GLACAdapter.GLACViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_glac,parent,false)
    )

    override fun onBindViewHolder(holder: GLACAdapter.GLACViewHolder, position: Int) {
        val glac = GLACList[position]
        holder.bindView(glac)
        holder.itemView.setOnClickListener{onClickItem?.invoke(glac)}
        holder.itemView.setOnClickListener{onClickDeleteItem?.invoke(glac)}
    }

    override fun getItemCount(): Int {
        return GLACList.size
    }
    fun updateGLACList(glacListForUpdate : List<GLACModel>){
        GLACList.clear()
        GLACList.addAll(glacListForUpdate)
    }
    class GLACViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.glacIdCardItem)
        private var segment1 = view.findViewById<TextView>(R.id.glacSegment1)
        private var segment2 = view.findViewById<TextView>(R.id.glacSegment2)
        private var segment3 = view.findViewById<TextView>(R.id.glacSegment3)
        private var segment4 = view.findViewById<TextView>(R.id.glacSegment4)
        private var segment5 = view.findViewById<TextView>(R.id.glacSegment5)
        private var segmentCombination = view.findViewById<TextView>(R.id.glacSegmentCombination)
        private var updateDate = view.findViewById<TextView>(R.id.glacUpdateDate)
        private var creationDate = view.findViewById<TextView>(R.id.glacCreatonDate)


        var btnDelete = view.findViewById<Button>(R.id.btnDeleteGLAC)

        fun bindView(glac: GLACModel){
            id.text = glac.glCodComId.toString()
            segment1.text = glac.segment1
            segment2.text = glac.segment2
            segment3.text = glac.segment3
            segment4.text = glac.segment4
            segment5.text = glac.segment5
            segmentCombination.text = glac.segmentCombination
            updateDate.text = glac.updateDate
            creationDate.text = glac.creationDate
        }

    }

}