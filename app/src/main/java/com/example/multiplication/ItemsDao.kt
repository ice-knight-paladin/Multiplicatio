package com.example.multiplication

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemsDao {
    @Query("SELECT * FROM items_table")
    fun list(): List<ItemCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCache)

    @Update
    fun update(item: ItemCache)

    @Query("DELETE FROM items_table WHERE text = :text")
    fun delete(text: String)

    @Query("SELECT * FROM items_table WHERE id = :id")
    fun item(id: Long): ItemCache

    @Query("SELECT * FROM items_table WHERE text = :text")
    fun item(text:String) :ItemCache?

    @Query("DELETE FROM items_table")
    fun clear_table()
}