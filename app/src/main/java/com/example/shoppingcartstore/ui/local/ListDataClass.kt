package com.example.shoppingcartstore.ui.local

import java.io.Serializable

data class ListDataClass(
    var itemCount: String,
    var date: String,
    var listName: String,
    var categoryList: ArrayList<MainItemDataClass>? = null,
    var pushedKey:String="",
):Serializable