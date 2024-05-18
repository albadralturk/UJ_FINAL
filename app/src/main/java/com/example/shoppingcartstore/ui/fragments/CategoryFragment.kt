package com.example.shoppingcartstore.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.FragmentCategoryBinding
import com.example.shoppingcartstore.ui.activity.CategoryItemListActivity
import com.example.shoppingcartstore.ui.adapter.HomeAdapter
import com.example.shoppingcartstore.ui.local.CategoryDataClass
import com.example.shoppingcartstore.ui.local.ListDataClass
import com.example.shoppingcartstore.utils.CATEGORY_SERI_INTENT_KEY

class CategoryFragment : Fragment() ,HomeAdapter.CategoryClickInterface {

    private lateinit var binding: FragmentCategoryBinding
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(this)
    }
    private lateinit var categoryList :ArrayList<CategoryDataClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryList= ArrayList()
        categoryList.add(CategoryDataClass(img = R.drawable.category_item1, categoryName = "Fruits & Vegetables"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item2, categoryName = "Breakfast"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item3, categoryName = "Meat & Fish"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item4, categoryName = "Snacks"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item5, categoryName = "Dairy"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item6, categoryName = "Beverages"))
        with(binding){
            rvCategoryFrag.apply {
                layoutManager= GridLayoutManager(requireActivity(),2)
                hasFixedSize()
                adapter=homeAdapter
            }
            //homeAdapter.updateList(categoryList)
        }
    }

    override fun onParentCLick(position: Int, item: ListDataClass) {
        val intent = Intent(activity, CategoryItemListActivity::class.java)
        //intent.putExtra(CATEGORY_SERI_INTENT_KEY,item)
        startActivity(intent)
        // activity?.openActivity(CategoryItemListActivity::class.java)
    }

    override fun onDeleteClick(position: Int, item: ListDataClass) {
        TODO("Not yet implemented")
    }

}