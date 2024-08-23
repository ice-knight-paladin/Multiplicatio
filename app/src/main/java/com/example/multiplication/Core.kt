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
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    fun daomulti() = dataBase.itemsDaoMulti()
    fun daodiv() = dataBase.itemsDaoDiv()
    fun daosave() = dataBase.itemsDaoSave()
}
