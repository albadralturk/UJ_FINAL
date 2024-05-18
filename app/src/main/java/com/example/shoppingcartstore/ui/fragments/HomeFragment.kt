package com.example.shoppingcartstore.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcartstore.databinding.FragmentHomeBinding
import com.example.shoppingcartstore.ui.activity.AddedListActivity
import com.example.shoppingcartstore.ui.activity.ShowListActivity
import com.example.shoppingcartstore.ui.adapter.HomeAdapter
import com.example.shoppingcartstore.ui.local.ListDataClass
import com.example.shoppingcartstore.ui.local.MainItemDataClass
import com.example.shoppingcartstore.utils.TITLE_NAME_INTENT_KEY
import com.example.shoppingcartstore.utils.Utils.showDeleteConfirmationDialog
import com.example.shoppingcartstore.utils.Utils.showDialog
import com.example.shoppingcartstore.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), HomeAdapter.CategoryClickInterface {

    private lateinit var binding: FragmentHomeBinding
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(this)
    }
    private lateinit var categoryList: ArrayList<ListDataClass>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Fetching List...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        categoryList = ArrayList()

        with(binding) {
            rvHome.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                hasFixedSize()
                adapter = homeAdapter
            }
            ivAddList.setOnClickListener {
                activity?.showDialog { title ->
                    val i = Intent(activity, AddedListActivity::class.java)
                    i.putExtra(TITLE_NAME_INTENT_KEY, title)
                    startActivity(i)
                }
            }
        }
        fetchListData()

    }

    private fun fetchListData() {
        progressDialog.show()

        val uid = auth.currentUser?.uid

        if (uid != null) {
            val userRef = database.reference.child("users").child(uid).child("list")
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    categoryList.clear()

                    for (listSnapshot in dataSnapshot.children) {
                        val itemCount = listSnapshot.child("itemCount").getValue(String::class.java)
                        val date = listSnapshot.child("date").getValue(String::class.java)
                        val pushedKey = listSnapshot.child("pushedKey").getValue(String::class.java)
                        val listName = listSnapshot.child("listName").getValue(String::class.java)

                        val mainItemList = ArrayList<MainItemDataClass>()
                        for (categorySnapshot in listSnapshot.child("categoryList").children) {
                            val categoryItem = categorySnapshot.getValue(MainItemDataClass::class.java)
                            categoryItem?.let {
                                mainItemList.add(it)
                            }
                        }

                        val listData = ListDataClass(itemCount.toString(), date.toString(), listName.toString(), mainItemList,pushedKey.toString())
                        categoryList.add(listData)
                    }
                    homeAdapter.updateList(categoryList)
                    progressDialog.dismiss()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    context?.showToast("Error fetching list data: ${databaseError.message}")
                    progressDialog.dismiss()
                }
            })
        }
    }

    override fun onParentCLick(position: Int, item: ListDataClass) {
        super.onParentCLick(position, item)
        val i = Intent(activity,ShowListActivity::class.java)
        i.putExtra("show_list",item)
        startActivity(i)
    }

    override fun onDeleteClick(position: Int, item: ListDataClass) {
        showDeleteConfirmationDialog(requireContext()){
            val uid = auth.currentUser?.uid
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid.toString())
                .child("list").child(item.pushedKey)
            userRef.removeValue()
                .addOnSuccessListener {
                    context?.showToast("Item deleted successfully")
                }
                .addOnFailureListener { exception ->
                    context?.showToast("Failed to delete item: $exception")
                }
        }
    }
}