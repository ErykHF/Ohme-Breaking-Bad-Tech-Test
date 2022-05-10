package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterBinding
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.util.Util
import com.erykhf.android.ohmebreakingbadtechtest.util.Util.loadImage

class MyCharactersRecyclerViewAdapter :
    RecyclerView.Adapter<MyCharactersRecyclerViewAdapter.ViewHolder>(), Filterable {

    private var values: MutableList<BreakingBadCharacterItem> = ArrayList()
    private var filteredValues: MutableList<BreakingBadCharacterItem> = ArrayList()

    fun updateList(listOfCharacters: List<BreakingBadCharacterItem>?) {
        values = listOfCharacters as MutableList<BreakingBadCharacterItem>
        filteredValues = values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredValues[position]
        val progressDrawable = Util.getProgressDrawable(holder.itemView.context)
        holder.characterName.text = item.name
        holder.image.loadImage(item.img, progressDrawable)

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    override fun getItemCount(): Int = filteredValues.size

    inner class ViewHolder(binding: FragmentCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val characterName: TextView = binding.characterName
        val image = binding.theImage
    }

    private var onItemClickListener: ((BreakingBadCharacterItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (BreakingBadCharacterItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filteredValues = values else {
                    val filteredList = ArrayList<BreakingBadCharacterItem>()
                    val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                    values
                        .filter {
                            (it.name.toLowerCase().contains(filterPattern))
                        }
                        .forEach { filteredList.add(it) }
                    filteredValues = filteredList

                }
                return FilterResults().apply { values = filteredValues }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filteredValues = if (results?.values == null)
                    values
                else
                    results.values as MutableList<BreakingBadCharacterItem>
                notifyDataSetChanged()
            }
        }
    }

}