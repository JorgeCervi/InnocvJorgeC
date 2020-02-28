package net.azarquiel.innocvjorgec.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*

import net.azarquiel.innocvjorgec.model.User

/**
 * Created by JorgeC on 22/02/2020.
 */
class CustomAdapter(val context: Context,
                    val layout: Int,
                    val listener: OnLongClickListenerUser
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item,listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setUsers(users: List<User>) {
        this.dataList = users
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(
            dataItem: User,
            listener: OnLongClickListenerUser
        ){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            var fecha:String = dataItem.birthdate.substring(0,10)
            val numeros: List<String>  = fecha.split ("-")

            var fecha2:String =""
            numeros.reversed().forEach {
                fecha2 += it
                fecha2 += "-"
            }
            fecha2 = fecha2.trim('-')

            itemView.tvBirthdate.text = fecha2

            itemView.tvId.text = dataItem.id.toString()
            itemView.tvName.text = dataItem.name

            itemView.tag = dataItem
            itemView.setOnLongClickListener{
                listener.OnLongClickUser(dataItem)
            }
        }
    }
    interface OnLongClickListenerUser {
        fun OnLongClickUser(user: User):Boolean{
            return true
        }
    }

}