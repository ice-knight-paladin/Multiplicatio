package com.example.multiplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemCacheMulti::class, ItemCacheDiv::class], version = 2)
abstract class ItemsDataBaseMulti : RoomDatabase() {
    abstract fun itemsDaoMulti(): ItemsDaoMulti
    abstract fun itemsDaoDiv(): ItemsDaoDiv
}

//@Database(entities = [ItemCacheMulti::class, ItemCacheDiv::class], version = 1)
//abstract class ItemsDataBaseDiv : RoomDatabase() {
//    abstract fun itemsDaoDiv(): ItemsDaoDiv
//}