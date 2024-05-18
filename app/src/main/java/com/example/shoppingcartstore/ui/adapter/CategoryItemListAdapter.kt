package com.example.shoppingcartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartstore.databinding.CategoryItemListViewBinding
import com.example.shoppingcartstore.ui.local.CategoryDataClass
import com.example.shoppingcartstore.ui.local.CategoryItemDataClass

class CategoryItemListAdapter(private val clickInterface: CategoryClickInterface,private val hideBtn:Boolean=false) : RecyclerView.Adapter<CategoryItemListAdapter.MHolder>() {

    private var list: ArrayList<CategoryDataClass> = ArrayList()

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
        return list.size
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            ivCategory.setImageResource(item.img)
            tvCategoryTitle.text = item.categoryName
            cvCategoryParent.setOnClickListener { clickInterface.onParentCLick(position,item) }
            btnAddToCategory.setOnClickListener { clickInterface.onAddToListCLick(position,item) }
            if (hideBtn){
                btnAddToCategory.isVisible=false
            }
        }
    }


    fun updateList(categoryList: ArrayList<CategoryDataClass>) {
        this.list = categoryList
        notifyDataSetChanged()
    }

    interface CategoryClickInterface {
        fun onParentCLick(position: Int, item: CategoryDataClass){}
        fun onAddToListCLick(position: Int, item: CategoryDataClass){}
    }
}