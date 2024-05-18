package com.example.shoppingcartstore.ui.local

import java.io.Serializable

data class ReceiptItemDataClass @JvmOverloads constructor(
    var receiptNo:String="",
    var receiptAmount:String="",
    var store:String="",
    var imgUrl:String="",
    var date:String="",
    var pushedKey:String=""
):Serializable