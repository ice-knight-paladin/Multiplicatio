package com.example.multiplication

import android.content.Context
import androidx.room.Room

class Core(
    private val context: Context
) {
    private val dataBase by lazy {
        Room.databaseBuilder(
            context,
            ItemsDataBaseMulti::class.java,
            "items_database_multi"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    fun daomulti() = dataBase.itemsDaoMulti()
    fun daodiv() = dataBase.itemsDaoDiv()
}

//class CoreDiv(
//    private val context: Context
//) {
//    private val dataBase by lazy {
//        Room.databaseBuilder(
//            context,
//            ItemsDataBaseDiv::class.java,
//            "items_database_multi"
//        ).build()
//    }
//
//    fun dao() = dataBase.itemsDaoDiv()
//}