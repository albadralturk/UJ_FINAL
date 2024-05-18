package com.example.shoppingcartstore.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityShowReceiptBinding
import com.example.shoppingcartstore.ui.local.ReceiptItemDataClass

class ShowReceiptActivity : AppCompatActivity() {

    private val binding:ActivityShowReceiptBinding by lazy {
        ActivityShowReceiptBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val receipt = intent.extras?.getSerializable("receipt") as ReceiptItemDataClass
        with(binding){
            edtReceiptNumber.text=receipt.receiptNo
            edtReceiptAmount.text=receipt.receiptAmount
            edtStore.text=receipt.store
            Glide.with(this@ShowReceiptActivity)
                .load(receipt.imgUrl)
                .into(ivCameraSelector)

        }
    }
}