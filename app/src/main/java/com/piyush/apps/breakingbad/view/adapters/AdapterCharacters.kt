package com.piyush.apps.breakingbad.view.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.piyush.apps.breakingbad.R
import com.piyush.apps.breakingbad.model.Character
import com.piyush.apps.breakingbad.view.DetailActivity
import com.piyush.apps.breakingbad.view.HomeActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_character.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created By Piyush Pandey on 04-07-2020
 * Apps@Piyush
 */
class AdapterCharacters(private val charactersList : ArrayList<Character>, private val context: HomeActivity) : RecyclerView.Adapter<AdapterCharacters.Holder>(), Filterable {

    private var filteredList = ArrayList<Character>()
    init {
        filteredList = charactersList
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView.rootView) {

        fun bindData(character: Character) {
            Picasso.get().load(character.img)
                .fit().centerCrop().into(itemView.iv_char, object : Callback{
                    override fun onSuccess() {
                        itemView.tv_name.text = character.name
                        itemView.tv_actor.text = character.portrayed
                        itemView.iv_placeholder.visibility = View.GONE
                        itemView.ll_detail.visibility = View.VISIBLE
                        itemView.char_root_cl.background = null
                    }
                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                        Log.e("check_error", "Failed with : ${e.toString()}")
                    }
                })

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("character", character)
                context.startActivity(intent)
                context.overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(LayoutInflater.from(parent.context).inflate(
        R.layout.list_character, parent, false))

    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(filteredList[position])
    }

    override fun getFilter(): Filter {
        return object  : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                filteredList = if (constraint.isNullOrEmpty()) {
                    charactersList
                } else {
                    val list = ArrayList<Character>()
                    for (character in charactersList) {
                        if (character.name.toLowerCase(Locale.getDefault()).contains(constraint.toString().toLowerCase(Locale.getDefault()))){
                            list.add(character)
                        }
                    }
                    list
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Character>
                notifyDataSetChanged()
            }

        }
    }

    fun updateList(updatedList: List<Character>) {
        filteredList.clear()
        filteredList.addAll(updatedList)
        notifyDataSetChanged()
    }
}