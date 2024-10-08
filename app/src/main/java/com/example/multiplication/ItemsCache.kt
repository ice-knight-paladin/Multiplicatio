package com.example.multiplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table_multi")
data class ItemCacheMulti(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "correct") var correct: Int,
    @ColumnInfo(name = "incorrect") var incorrect: Int
)

@Entity(tableName = "item_table_div")
data class ItemCacheDiv(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "correct") var correct: Int,
    @ColumnInfo(name = "incorrect") var incorrect: Int
)

@Entity(tableName = "item_table_save")
data class ItemCacheSave(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "number") var number: Int,
)

@Entity(tableName = "item_table_plus")
data class ItemCachePlus(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "correct") var correct: Int,
    @ColumnInfo(name = "incorrect") var incorrect: Int
)

@Entity(tableName = "item_table_minus")
data class ItemCacheMinus(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "correct") var correct: Int,
    @ColumnInfo(name = "incorrect") var incorrect: Int
)