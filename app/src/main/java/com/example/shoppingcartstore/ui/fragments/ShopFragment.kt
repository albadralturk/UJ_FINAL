package com.example.shoppingcartstore.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcartstore.databinding.FragmentShopBinding
import com.example.shoppingcartstore.ui.activity.AddReceiptActivity
import com.example.shoppingcartstore.ui.activity.ShowReceiptActivity
import com.example.shoppingcartstore.ui.adapter.ReceiptAdapter
import com.example.shoppingcartstore.ui.local.ReceiptItemDataClass
import com.example.shoppingcartstore.utils.Utils
import com.example.shoppingcartstore.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class ShopFragment : Fragment(),ReceiptAdapter.OnClick {

    private lateinit var binding: FragmentShopBinding
    private val receiptAdapter: ReceiptAdapter by lazy {
        ReceiptAdapter(this)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Fetching Receipt...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        with(binding) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                hasFixedSize()
                adapter = receiptAdapter
            }
            ivAddReceipt.setOnClickListener {
                startActivity(Intent(activity, AddReceiptActivity::class.java))
            }
            fetchReceiptData()
        }
    }

    private fun fetchReceiptData() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userRef = database.reference.child("users").child(uid).child("receipt")

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val receiptList = ArrayList<ReceiptItemDataClass>()

                    for (receiptSnapshot in snapshot.children) {
                        val receiptDataClass =
                            receiptSnapshot.getValue(ReceiptItemDataClass::class.java)
                        receiptDataClass?.let {
                            receiptList.add(it)
                        }
                    }
                    receiptAdapter.updateList(receiptList)
                    progressDialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                    context?.showToast("Error fetching receipt data: ${error.message}")
                    progressDialog.dismiss()
                }
            })
        }
    }

    override fun onParentClick(item: ReceiptItemDataClass, position: Int) {
        val intent = Intent(activity,ShowReceiptActivity::class.java)
        intent.putExtra("receipt",item)
        startActivity(intent)
    }

    override fun onDeleteClick(item: ReceiptItemDataClass) {
        Utils.showDeleteConfirmationDialog(requireContext()) {
            val uid = auth.currentUser?.uid
            val userRef = database.reference.child("users").child(uid!!).child("receipt").child(item.pushedKey)

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