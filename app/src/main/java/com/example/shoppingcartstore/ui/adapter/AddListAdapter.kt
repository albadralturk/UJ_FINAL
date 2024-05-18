package com.example.shoppingcartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartstore.databinding.CategoryItemListViewBinding
import com.example.shoppingcartstore.ui.local.CategoryItemDataClass

class AddListAdapter(private val categoryClickInterface: CategoryClickInterface) :
    RecyclerView.Adapter<AddListAdapter.MHolder>() {

    private var categoryList: ArrayList<CategoryItemDataClass> = ArrayList()

    class MHolder(var binding: CategoryItemListViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        return MHolder(
            CategoryItemListViewBinding.inflate(
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
            tvCategoryTitle.text = item.itemName
            ivCategory.setImageResource(item.img)
            btnAddToCategory.setOnClickListener {
                categoryClickInterface.onParentCLick(position, item)
            }
        }
    }

    interface CategoryClickInterface {
        fun onParentCLick(position: Int, item: CategoryItemDataClass)
    }

    fun updateList(categoryList: ArrayList<CategoryItemDataClass>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }
}