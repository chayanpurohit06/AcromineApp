package com.app.acromine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.acromine.databinding.AdapterAbbreviationBinding

import com.app.acromine.model.Lfs

class AbbreviationAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var LfsList = mutableListOf<Lfs>()
    var onItemClick: ((Lfs) -> Unit)? = null

    fun setAbbreviationsData(lfs: List<Lfs>) {
        this.LfsList = lfs.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterAbbreviationBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.binding.item = LfsList[position]
        holder.binding.cardView.setOnClickListener {
            onItemClick?.invoke(LfsList[position])

        }
    }

        override fun getItemCount(): Int {
            return LfsList.size
        }
    }

    class MainViewHolder(val binding: AdapterAbbreviationBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }