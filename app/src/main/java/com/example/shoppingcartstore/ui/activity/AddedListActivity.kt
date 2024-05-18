package com.example.shoppingcartstore.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityAddedListBinding
import com.example.shoppingcartstore.ui.adapter.CategoryItemListAdapter
import com.example.shoppingcartstore.ui.local.CategoryDataClass
import com.example.shoppingcartstore.ui.local.ListDataClass
import com.example.shoppingcartstore.utils.CATEGORY_SERI_INTENT_KEY
import com.example.shoppingcartstore.utils.TITLE_NAME_INTENT_KEY
import com.example.shoppingcartstore.utils.Utils
import com.example.shoppingcartstore.utils.Utils.getCurrentTimeWithAmPm
import com.example.shoppingcartstore.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddedListActivity : AppCompatActivity(),CategoryItemListAdapter.CategoryClickInterface {

    private val binding:ActivityAddedListBinding by lazy {
        ActivityAddedListBinding.inflate(layoutInflater)
    }
    private val categoryItemListAdapter: CategoryItemListAdapter by lazy {
        CategoryItemListAdapter(this,true)
    }

    private lateinit var categoryList :ArrayList<CategoryDataClass>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var progressDialog: ProgressDialog
    private var listName:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.uploading))
        progressDialog.setCancelable(false)

         listName = intent.extras?.getString(TITLE_NAME_INTENT_KEY,"")
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        categoryList= ArrayList()
        categoryList.add(CategoryDataClass(img = R.drawable.category_item1, "Fruits & Vegetables"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item2, "Breakfast"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item4, "Meat & Fish"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item5, "Snacks"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item6, "Dairy"))
        categoryList.add(CategoryDataClass(img = R.drawable.category_item3, "Beverages"))
        with(binding){
            includedToolbar.apply {
                tvToolbarTitle.text = listName
                ivBackPressTB.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            rvAddedList.apply {
                layoutManager = GridLayoutManager(this@AddedListActivity, 2)
                hasFixedSize()
                adapter = categoryItemListAdapter
            }
            categoryItemListAdapter.updateList(categoryList)
            btnNext.setOnClickListener {
                saveListData()
            }
        }
    }

    private fun saveListData(){
        progressDialog.show()

        val uid = auth.currentUser?.uid

        if (uid != null) {
            val userRef = database.reference.child("users").child(uid).child("list")
            val pushed=userRef.push()
            val list = ListDataClass(
                listName = listName.toString(),
                date = getCurrentTimeWithAmPm(),
                itemCount = Utils.addedCategoryList.size.toString(),
                categoryList = Utils.addedCategoryList,
                pushedKey = pushed.key.toString()
            )
            userRef.child(pushed.key.toString()).setValue(list).addOnSuccessListener {
                showToast(getString(R.string.list_added_successfully))
                progressDialog.dismiss()
                finish()
            }.addOnFailureListener {
                showToast(getString(R.string.error_saving_list)+it.message)
                progressDialog.dismiss()
                finish()
            }
        }
    }

    override fun onParentCLick(position: Int, item: CategoryDataClass) {
        super.onParentCLick(position, item)
        val intent = Intent(this, CategoryItemListActivity::class.java)
        intent.putExtra(CATEGORY_SERI_INTENT_KEY,item)
        startActivity(intent)
    }
}