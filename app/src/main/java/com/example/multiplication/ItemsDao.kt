package com.example.multiplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemsDaoMulti {
    @Query("SELECT * FROM items_table_multi")
    fun list(): List<ItemCacheMulti>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCacheMulti)

    @Update
    fun update(item: ItemCacheMulti)

    @Query("DELETE FROM items_table_multi WHERE text = :text")
    fun delete(text: String)

    @Query("SELECT * FROM items_table_multi WHERE id = :id")
    fun item(id: Long): ItemCacheMulti

    @Query("SELECT * FROM items_table_multi WHERE text = :text")
    fun item(text:String) :ItemCacheMulti?

    @Query("DELETE FROM items_table_multi")
    fun clear_table()
}

@Dao
interface ItemsDaoDiv {
    @Query("SELECT * FROM item_table_div")
    fun list(): List<ItemCacheDiv>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCacheDiv)

    @Update
    fun update(item: ItemCacheDiv)

    @Query("DELETE FROM item_table_div WHERE text = :text")
    fun delete(text: String)

    @Query("SELECT * FROM item_table_div WHERE id = :id")
    fun item(id: Long): ItemCacheDiv

    @Query("SELECT * FROM item_table_div WHERE text = :text")
    fun item(text:String) :ItemCacheDiv?

    @Query("DELETE FROM item_table_div")
    fun clear_table()
}

@Dao
interface ItemsDaoSave {
    @Query("SELECT * FROM item_table_save")
    fun list(): List<ItemCacheSave>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCacheSave)

    @Update
    fun update(item:ItemCacheSave)

    @Query("SELECT * FROM item_table_save WHERE id = :id")
    fun item(id: Long): ItemCacheSave

    @Query("SELECT * FROM item_table_save WHERE text = :text")
    fun item(text:String) :ItemCacheSave?

}