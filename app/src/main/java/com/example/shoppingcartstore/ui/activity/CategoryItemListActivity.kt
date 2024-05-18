package com.example.shoppingcartstore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityCategoryItemListBinding
import com.example.shoppingcartstore.ui.adapter.CategoryItemListAdapter
import com.example.shoppingcartstore.ui.local.CategoryDataClass
import com.example.shoppingcartstore.ui.local.MainItemDataClass
import com.example.shoppingcartstore.utils.CATEGORY_SERI_INTENT_KEY
import com.example.shoppingcartstore.utils.Utils.addedCategoryList
import com.example.shoppingcartstore.utils.Utils.getCategoryItemList
import com.example.shoppingcartstore.utils.Utils.showDescriptionDialog
import com.example.shoppingcartstore.utils.Utils.showToast

class CategoryItemListActivity : AppCompatActivity(),
    CategoryItemListAdapter.CategoryClickInterface {

    private val binding: ActivityCategoryItemListBinding by lazy {
        ActivityCategoryItemListBinding.inflate(layoutInflater)
    }
    private val categoryItemListAdapter: CategoryItemListAdapter by lazy {
        CategoryItemListAdapter(this)
    }
    lateinit var category:CategoryDataClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         category =
            intent.extras?.getSerializable(CATEGORY_SERI_INTENT_KEY) as CategoryDataClass
        with(binding) {
            includedToolbar.apply {
                tvToolbarTitle.text = category.categoryName
                ivBackPressTB.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            rvCategoryItem.apply {
                layoutManager = GridLayoutManager(this@CategoryItemListActivity, 2)
                hasFixedSize()
                adapter = categoryItemListAdapter
            }
            categoryItemListAdapter.updateList(getCategoryItemList(category.categoryName))


        }
    }

    override fun onParentCLick(position: Int, item: CategoryDataClass) {

    }

    override fun onAddToListCLick(position: Int, item: CategoryDataClass) {
        showDescriptionDialog {
            addedCategoryList.add(MainItemDataClass(category.categoryName, itemName = item.categoryName, itemDescription = it))
            showToast(getString(R.string.added_to_the_list))
        }
    }
}