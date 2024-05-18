package com.example.shoppingcartstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ReceiptsItemBinding
import com.example.shoppingcartstore.ui.local.ReceiptItemDataClass

class ReceiptAdapter(private var onClick:OnClick) : RecyclerView.Adapter<ReceiptAdapter.MHolder>() {

    private var receiptList: ArrayList<ReceiptItemDataClass> = ArrayList()

    class MHolder(var binding: ReceiptsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        return MHolder(
            ReceiptsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return receiptList.size
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        val item = receiptList[position]
        with(holder.binding) {
            tvReceiptsNo.text = tvReceiptsNo.context.getString(R.string.receipt, item.receiptNo)
            tvReceiptsFrom.text = tvReceiptsFrom.context.getString(R.string.receipt_price, item.receiptAmount)
            tvReceiptsTime.text = item.date
            cardView.setOnClickListener {
                onClick.onParentClick(item,position)
            }
            ivDelete.setOnClickListener {
                onClick.onDeleteClick(item)
            }
        }
    }

    fun updateList(updatedList:ArrayList<ReceiptItemDataClass>){
        this.receiptList=updatedList
        notifyDataSetChanged()
    }

    interface OnClick{
        fun onParentClick(item:ReceiptItemDataClass,position: Int)
        fun onDeleteClick(item:ReceiptItemDataClass)
    }
}