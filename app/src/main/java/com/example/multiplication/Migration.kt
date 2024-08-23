package com.example.multiplication

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `item_table_div` (`id` INTEGER NOT NULL, `text` TEXT NOT NULL, `correct` INTEGER NOT NULL, `incorrect` INTEGER NOT NULL, PRIMARY KEY(`id`))")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `item_table_save` (`id` INTEGER NOT NULL, `text` TEXT NOT NULL, `number` INTEGER NOT NULL, PRIMARY KEY(`id`))")
    }
}