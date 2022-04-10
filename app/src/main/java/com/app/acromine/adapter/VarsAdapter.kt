package com.app.acromine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.acromine.databinding.AdapterVarsBinding
import com.app.acromine.model.Lfs
import com.app.acromine.model.Vars

class VarsAdapter : RecyclerView.Adapter<VarsViewHolder>() {

    var VarsList = mutableListOf<Vars>()
    var onItemClick: ((Vars) -> Unit)? = null

    fun setVarsData(vars: List<Vars>) {
        this.VarsList = vars.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VarsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterVarsBinding.inflate(inflater, parent, false)
        return VarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VarsViewHolder, position: Int) {

        holder.binding.items = VarsList[position]

        holder.binding.cardView.setOnClickListener {
            onItemClick?.invoke(VarsList[position])

        }
    }

    override fun getItemCount(): Int {
        return VarsList.size
    }
}

class VarsViewHolder(val binding: AdapterVarsBinding) : RecyclerView.ViewHolder(binding.root) {

}