package com.cihancelik.CarParkDetails.ledger.glJournalLines

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class GLJLAdapter:RecyclerView.Adapter<GLJLAdapter.GLJLViewHolder>() {
    private var gljlList : ArrayList<GLJLModel> = ArrayList()
    private var onClickItem : ((GLJLModel) -> Unit)? = null
    private var onClickDeleteItem : ((GLJLModel) -> Unit)? = null

    fun addItems(items:ArrayList<GLJLModel>){
        this.gljlList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (GLJLModel)-> Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback: (GLJLModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GLJLAdapter.GLJLViewHolder {
        return GLJLViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_gljl,parent,false)
        )
    }

    override fun onBindViewHolder(holder: GLJLAdapter.GLJLViewHolder, position: Int) {
       val gljl = gljlList[position]
        holder.bindView(gljl)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(gljl)
            val intent = Intent(it.context,GLJLMainScreen::class.java)
            intent.putExtra("selectedGLJLInfo",gljl)
            it.context.startActivity(intent)
        }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(gljl) }
    }

    override fun getItemCount(): Int {
        return gljlList.size
    }
    fun updateGLJLList(gljlListUpdate: List<GLJLModel>){
        gljlList.clear()
        gljlList.addAll(gljlListUpdate)
    }

    class GLJLViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        private var journalLineId = view.findViewById<TextView>(R.id.journalLineIdTv)
        private var journalId = view.findViewById<TextView>(R.id.journalIdForLinesTv)
        private var journalDate = view.findViewById<TextView>(R.id.gljlJournalDateTv)
        private var glCodComId = view.findViewById<TextView>(R.id.gljlGlCodComIdTv)
        private var accountCrAmount = view.findViewById<TextView>(R.id.gljlAccountedCrAmountTv)
        private var accountDrAmount = view.findViewById<TextView>(R.id.gljlAccountedDrAmountTv)
        private var updateDate = view.findViewById<TextView>(R.id.gljlUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.gljlCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.gljlDeleteBtn)

        fun bindView(gljl:GLJLModel){
            journalLineId.text = gljl.journalLineId.toString()
            journalId.text = gljl.journalId.toString()
            journalDate.text = gljl.journalDate
            glCodComId.text = gljl.glCodComId.toString()
            accountCrAmount.text = gljl.accountedCrAmount.toString()
            accountDrAmount.text = gljl.accountedDrAmount.toString()
            updateDate.text = gljl.updateDate
            creationDate.text = gljl.creationDate

        }
    }
}