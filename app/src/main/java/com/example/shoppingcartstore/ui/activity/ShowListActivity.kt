package com.example.shoppingcartstore.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityShowListBinding
import com.example.shoppingcartstore.ui.adapter.ShowListAdapter
import com.example.shoppingcartstore.ui.local.ListDataClass
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class ShowListActivity : AppCompatActivity() {

    private val binding: ActivityShowListBinding by lazy {
        ActivityShowListBinding.inflate(layoutInflater)
    }
    lateinit var showListAdapter: ShowListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            includedToolbar.apply {
                tvToolbarTitle.text=getString(R.string.lists)
                ivBackPressTB.setOnClickListener { onBackPressed() }
            }
            val list = intent.extras?.getSerializable("show_list") as ListDataClass
            tvListName.text = getString(R.string.list_name)+list.listName
            tvListCount.text = getString(R.string.total_item)+list.itemCount
            tvListTime.text = list.date
            showListAdapter = ShowListAdapter(list.categoryList!!)
            rvListShow.apply {
                layoutManager = LinearLayoutManager(this@ShowListActivity)
                hasFixedSize()
                adapter = showListAdapter
            }


            btnShare.setOnClickListener {
                val jsonObject = JSONObject()
                jsonObject.put("listName", list.listName)
                jsonObject.put("date", list.date)

                val categoryArray = JSONArray()
                list.categoryList!!.forEach { itemData ->
                    val itemObject = JSONObject()
                    itemObject.put("categoryName", itemData.categoryName)
                    itemObject.put("itemName", itemData.itemName)
                    itemObject.put("itemDescription", itemData.itemDescription)
                    categoryArray.put(itemObject)
                }

                jsonObject.put("categories", categoryArray)

                val jsonString = jsonObject.toString()

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,jsonString)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }
}