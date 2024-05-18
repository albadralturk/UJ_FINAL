package com.example.shoppingcartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ListShowViewBinding
import com.example.shoppingcartstore.ui.local.MainItemDataClass

class ShowListAdapter(var list:ArrayList<MainItemDataClass>) : RecyclerView.Adapter<ShowListAdapter.MHolder>() {

    class MHolder(var binding:ListShowViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
      return MHolder(ListShowViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        val item = list[position]
        with(holder.binding){
            tvItemName.text= tvItemName.context.getString(R.string.item_name)+item.itemName
            tvItemDesc.text=
                tvItemDesc.context.getString(R.string.item_description)+item.itemDescription
        }
    }
}