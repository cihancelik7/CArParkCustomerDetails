package com.cihancelik.CarParkDetails.general.userUpdateScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cihancelik.carparkcustomerdetails.R

class UserAdapter :RecyclerView.Adapter<UserAdapter.UsersViewHolder>(){
    private var userList : ArrayList<UserModel> = ArrayList()
    private var onClickItem : ((UserModel) ->Unit )? = null
    private var onCLickDeleteItem : ((UserModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= UsersViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_users,parent,false)
    )

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val users = userList[position]
        holder.bindView(users)
        holder.itemView.setOnClickListener { onClickItem?.invoke(users) }
        holder.btnDelete.setOnClickListener { onCLickDeleteItem?.invoke(users) }
    }

    fun addItems(items:ArrayList<UserModel>){
        this.userList = items
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback: (UserModel) ->Unit){
        this.onClickItem = callback
    }
    fun setOnclickDeleteItem(callback: (UserModel) -> Unit){
        this.onCLickDeleteItem = callback
    }



    fun updateUsersList(userListForUpdate : List<UserModel>){
        userList.clear()
        userList.addAll(userListForUpdate)
    }
    class UsersViewHolder(var view : View):RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.userIdTv)
        private var username = view.findViewById<TextView>(R.id.userUsernameTv)
        private var password = view.findViewById<TextView>(R.id.userPasswordTv)
        private var email = view.findViewById<TextView>(R.id.userEmailTv)
        private var startDate = view.findViewById<TextView>(R.id.userStartDateTv)
        private var endDAte = view.findViewById<TextView>(R.id.userEndDateTv)
        private var updateDate = view.findViewById<TextView>(R.id.userUpdateDateTv)
        private var creationDate = view.findViewById<TextView>(R.id.userCreationDateTv)

        var btnDelete = view.findViewById<Button>(R.id.btnDeleteUsers)

        fun bindView(users:UserModel){
            id.text = users.userId.toString()
            username.text = users.username
            password.text = users.password
            email.text = users.email
            startDate.text = users.startDate
            endDAte.text = users.endDate
            updateDate.text = users.updateDate
            creationDate.text = users.creationDate
        }


    }


}