package com.cihancelik.CarParkDetails.ledger.glJournals

import GLJournalsModel
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class GLJournalsAdapter : RecyclerView.Adapter<GLJournalsAdapter.GLJournalViewHolder>() {
    private var glJournalList: ArrayList<GLJournalsModel> = ArrayList()
    private var onClickItem: ((GLJournalsModel) -> Unit)? = null
    private var onClickDeleteItem: ((GLJournalsModel) -> Unit)? = null

    fun addItems(items: ArrayList<GLJournalsModel>) {
        this.glJournalList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (GLJournalsModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (GLJournalsModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GLJournalViewHolder {
        return GLJournalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item_gljournal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GLJournalViewHolder, position: Int) {
        val glJournal = glJournalList[position]
        holder.bindView(glJournal)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(glJournal)
            val intent = Intent(it.context, GLJournalsMainScreen::class.java)
            intent.putExtra("selectedGLJournalInfo", glJournal)
            it.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(glJournal) }
    }

    override fun getItemCount(): Int {
        return glJournalList.size
    }

    fun updateGLJournalLIst(glJournalListUpdate: List<GLJournalsModel>) {
        glJournalList.clear()
        glJournalList.addAll(glJournalListUpdate)
    }

    class GLJournalViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var journalId = view.findViewById<TextView>(R.id.journalIdTv)
        private var periodId = view.findViewById<TextView>(R.id.journalPeriodIdTv)
        private var journalDate = view.findViewById<TextView>(R.id.journalDateTv)
        private var status = view.findViewById<TextView>(R.id.journalStatusTv)
        private var amount = view.findViewById<TextView>(R.id.journalAmountTv)
        private var updateDate = view.findViewById<TextView>(R.id.journalUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.journalCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.journalDeleteBtn)

        fun bindView(journal: GLJournalsModel) {
            journalId.text = journal.journalId.toString()
            periodId.text = journal.periodId.toString()
            journalDate.text = journal.journalDate
            status.text = journal.status
            amount.text = journal.amount
            updateDate.text = journal.updateDate
            creationDate.text = journal.creationDate
        }
    }
}
