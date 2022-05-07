package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterBinding
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyCharactersRecyclerViewAdapter(
) : RecyclerView.Adapter<MyCharactersRecyclerViewAdapter.ViewHolder>() {

    private val values = arrayListOf<BreakingBadCharacterItem>()

    fun updateList(listOfCharacters: List<BreakingBadCharacterItem>?) {
        values.clear()
        if (listOfCharacters != null) {
            values.addAll(listOfCharacters)
        }
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
        val item = values[position]
        holder.idView.text = item.name
        holder.contentView.text = item.portrayed
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

    }

}