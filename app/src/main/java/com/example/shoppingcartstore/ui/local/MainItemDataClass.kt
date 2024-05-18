package com.example.shoppingcartstore.ui.local

import java.io.Serializable

data class MainItemDataClass @JvmOverloads constructor (
    var categoryName: String="",
    var itemName:String="",
    var itemDescription:String=""
):Serializable