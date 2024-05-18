package com.example.shoppingcartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartstore.databinding.CategoryItemBinding
import com.example.shoppingcartstore.ui.local.CategoryDataClass
import com.example.shoppingcartstore.ui.local.ListDataClass

class HomeAdapter(private val categoryClickInterface: CategoryClickInterface) :
    RecyclerView.Adapter<HomeAdapter.MHolder>() {

    private var categoryList: ArrayList<ListDataClass> = ArrayList()

    class MHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        return MHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        val item = categoryList[position]
        with(holder.binding) {
            tvItemCount.text=item.itemCount
            tvCategoryTime.text = item.date
            tvListName.text=item.listName
            ccParentCategory.setOnClickListener {
                categoryClickInterface.onParentCLick(position, item)
            }
            ivDeleteList.setOnClickListener {
                categoryClickInterface.onDeleteClick(position,item)
            }
        }
    }

    interface CategoryClickInterface {
        fun onParentCLick(position: Int, item: ListDataClass){}
        fun onDeleteClick(position: Int,item: ListDataClass)
    }

    fun updateList(categoryList: ArrayList<ListDataClass>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }
}