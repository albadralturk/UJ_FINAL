package com.example.shoppingcartstore.ui.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityAddReceiptBinding
import com.example.shoppingcartstore.ui.local.ReceiptItemDataClass
import com.example.shoppingcartstore.utils.Utils.getCurrentTimeWithAmPm
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled
import com.example.shoppingcartstore.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class AddReceiptActivity : AppCompatActivity() {

    private val binding: ActivityAddReceiptBinding by lazy {
        ActivityAddReceiptBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri?=null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (!isDarkThemeEnabled()) {
            binding.ccParentActivity.setBackgroundResource(R.drawable.bg_img)
        } else {
            binding.ccParentActivity.setBackgroundColor(Color.BLACK)
        }
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.uploading))
        progressDialog.setCancelable(false)

        with(binding) {
            includedToolbar.apply {
                tvToolbarTitle.text = getString(R.string.add_to_receipts)
                ivBackPressTB.setOnClickListener { onBackPressed() }
            }

            ivCameraSelector.setOnClickListener {
                openGallery()
            }

            btnNext.setOnClickListener {
                if (edtReceiptNumber.text.isNotEmpty() && edtReceiptAmount.text.isNotEmpty() && edtStore.text.isNotEmpty())
                    if (selectedImageUri==null)
                        showToast(getString(R.string.please_select_image))
                    else
                        saveData()
                else
                    showToast(getString(R.string.enter_all_data))
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun ActivityAddReceiptBinding.saveData() {
        progressDialog.show()

        val uid = auth.currentUser?.uid

        if (uid != null) {
            val userRef = database.reference.child("users").child(uid).child("receipt")
            val imageByteArray = getImageByteArray(selectedImageUri!!)
            val storageRef = storage.reference.child("images").child("receipt").child("$uid.jpg")
            storageRef.putBytes(imageByteArray)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        val pushed=userRef.push()
                        val receiptDataClass = ReceiptItemDataClass(
                            receiptNo = edtReceiptNumber.text.toString(),
                            receiptAmount = edtReceiptAmount.text.toString(),
                            store = edtStore.text.toString(),
                            imgUrl = imageUrl.toString(),
                            date = getCurrentTimeWithAmPm(),
                            pushedKey =pushed.key.toString()
                        )

                        userRef.child(pushed.key.toString()).setValue(receiptDataClass)
                            .addOnSuccessListener {
                                showToast(getString(R.string.receipt_saved_successfully))
                                progressDialog.dismiss()
                                finish()
                            }
                            .addOnFailureListener {
                                showToast(
                                    getString(
                                        com.example.shoppingcartstore.R.string.error_saving_receipt,
                                        it.message
                                    ))
                                progressDialog.dismiss()
                            }
                    }
                }
                .addOnFailureListener {
                    showToast(getString(R.string.error_uploading_image, it.message))
                    progressDialog.dismiss()
                }
        }
    }

    private fun getImageByteArray(imageUri: Uri): ByteArray {
        val inputStream = contentResolver.openInputStream(imageUri)
        val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        inputStream?.close()
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data!!
            binding.ivCameraSelector.setImageURI(selectedImageUri)
        }
    }
}