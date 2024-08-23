package com.example.multiplication

data class Item(
    var id: Long,
    var text: String,
    var correct: Int,
    var incorrect: Int
)

data class Item_Save(
    var id:Long,
    var text: String,
    var number: Int,
)