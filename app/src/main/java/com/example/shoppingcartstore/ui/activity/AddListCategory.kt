package com.example.shoppingcartstore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityAddedListBinding
import com.example.shoppingcartstore.ui.adapter.AddListAdapter
import com.example.shoppingcartstore.ui.local.CategoryItemDataClass

class AddListCategory : AppCompatActivity(), AddListAdapter.CategoryClickInterface {

    private val binding: ActivityAddedListBinding by lazy {
        ActivityAddedListBinding.inflate(layoutInflater)
    }
    private val addListAdapter: AddListAdapter by lazy {
        AddListAdapter(this)
    }
    private lateinit var categoryList: ArrayList<CategoryItemDataClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryList = ArrayList()
        categoryList.add(
            CategoryItemDataClass(
                img = R.drawable.category_item1,
                 "Fruits & Vegetables"
            )
        )
        categoryList.add(
            CategoryItemDataClass(
                img = R.drawable.category_item2,
                 "Breakfast"
            )
        )
        categoryList.add(
            CategoryItemDataClass(
                img = R.drawable.category_item3,
                 "Meat & Fish"
            )
        )
        categoryList.add(
            CategoryItemDataClass(
                img = R.drawable.category_item4,
                 "Snacks"
            )
        )
        categoryList.add(
            CategoryItemDataClass(
                img = R.drawable.category_item5,
                 "Dairy"
            )
        )
        categoryList.add(
            CategoryItemDataClass(
                img = R.drawable.category_item6,
                 "Beverages"
            )
        )
        with(binding) {
            includedToolbar.apply {
                tvToolbarTitle.text = "Select Category"
            }
            rvAddedList.apply {
                layoutManager = GridLayoutManager(this@AddListCategory, 2)
                hasFixedSize()
                adapter = addListAdapter
            }
            addListAdapter.updateList(categoryList)
        }
    }

    override fun onParentCLick(position: Int, item: CategoryItemDataClass) {

    }
}