package com.example.listofdeals2.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Deal::class],
    version = 1
)
abstract class DealsDatabase : RoomDatabase() {
    abstract fun dealDao(): DealsDao
}
