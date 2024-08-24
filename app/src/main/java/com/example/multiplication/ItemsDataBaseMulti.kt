package com.example.multiplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemCacheMulti::class, ItemCacheDiv::class, ItemCacheSave::class], version = 3)
abstract class ItemsDataBaseMulti : RoomDatabase() {
    abstract fun itemsDaoMulti(): ItemsDaoMulti
    abstract fun itemsDaoDiv(): ItemsDaoDiv
    abstract fun itemsDaoSave():ItemsDaoSave
}