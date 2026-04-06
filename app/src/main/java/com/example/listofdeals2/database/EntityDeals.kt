package com.example.listofdeals2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deals")
data class Deal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dealName: String
)
