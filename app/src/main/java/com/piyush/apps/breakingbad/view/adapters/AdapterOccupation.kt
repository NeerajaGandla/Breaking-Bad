package com.piyush.apps.breakingbad.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piyush.apps.breakingbad.R

/**
 * Created By Piyush Pandey on 04-07-2020
 * Apps@Piyush
 */
class AdapterOccupation(private val occupationsList : List<String>) : RecyclerView.Adapter<AdapterOccupation.Holder>(){

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView.rootView) {
        val tvOccupation: TextView = itemView.findViewById(R.id.tv_occupation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(LayoutInflater.from(parent.context).inflate(
        R.layout.list_occupation, parent, false))

    override fun getItemCount() = occupationsList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvOccupation.text = occupationsList[position]
    }
}