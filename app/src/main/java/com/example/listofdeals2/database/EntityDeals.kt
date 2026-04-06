package com.example.listofdeals2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deals")
data class Deal(
    @PrimaryKey
    val id: Int,
    val dealName: String
)
