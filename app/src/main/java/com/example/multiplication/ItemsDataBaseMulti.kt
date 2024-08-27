package com.example.multiplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemCacheMulti::class, ItemCacheDiv::class, ItemCacheSave::class, ItemCacheMinus::class, ItemCachePlus::class], version = 4)
abstract class ItemsDataBaseMulti : RoomDatabase() {
    abstract fun itemsDaoMulti(): ItemsDaoMulti
    abstract fun itemsDaoDiv(): ItemsDaoDiv
    abstract fun itemsDaoSave():ItemsDaoSave
    abstract fun itemsDaoPlus(): ItemsDaoPlus
    abstract fun itemsDaoMinus(): ItemsDaoMinus
}