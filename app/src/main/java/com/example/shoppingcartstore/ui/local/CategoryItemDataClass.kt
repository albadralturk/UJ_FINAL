package com.example.shoppingcartstore.ui.local

import java.io.Serializable

data class CategoryItemDataClass(
    var img:Int,
    var itemName:String,
    var itemDescription:String=""
): Serializable