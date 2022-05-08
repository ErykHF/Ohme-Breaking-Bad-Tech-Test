package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.erykhf.android.ohmebreakingbadtechtest.databinding.FragmentCharacterBinding
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.util.Util
import com.erykhf.android.ohmebreakingbadtechtest.util.Util.loadImage

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

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val characterName: TextView = binding.characterName
        val image = binding.theImage
    }

    private var onItemClickListener: ((BreakingBadCharacterItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (BreakingBadCharacterItem) -> Unit){
        onItemClickListener = listener
    }



}