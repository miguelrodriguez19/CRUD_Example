package com.utad.crudexample.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.utad.crudexample.R
import com.utad.crudexample.users.UsersResponse

class UsersAdapter(
    private val data: ArrayList<UsersResponse.User>,
    val onClick: (UsersResponse.User) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val card = v.findViewById<CardView>(R.id.itemCardLayout)
        val name = v.findViewById<TextView>(R.id.itemUsername)
        fun bind(item: UsersResponse.User) {
            name.text = item.name ?: "<!>unnamed"
            card.setCardBackgroundColor(generateColor().toInt())
            card.setOnClickListener {
                onClick(item)
            }
        }

        fun generateColor(): Long {
            val colors = arrayListOf(
                0xff90CAF9, 0xff64B5F6, 0xff42A5F5, 0xff2196F3,
                0xff1E88E5, 0xff1976D2, 0xff1565C0, 0xff0D47A1,
                0xffB3E5FC, 0xff81D4FA, 0xff4FC3F7, 0xff29B6F6,
                0xff03A9F4, 0xff00BCD4
            )
            return colors.random()
        }
    }
}