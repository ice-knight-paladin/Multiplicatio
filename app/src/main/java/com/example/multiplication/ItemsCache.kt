package com.example.multiplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class ItemCache(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "correct") val correct: Int,
    @ColumnInfo(name = "incorrect") val incorrect: Int
)