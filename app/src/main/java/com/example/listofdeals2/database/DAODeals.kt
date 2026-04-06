package com.example.listofdeals2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DealsDao {
    @Insert
    suspend fun insert(deal: Deal)

    @Update
    suspend fun updateUsers(deal: Deal)

    @Delete
    suspend fun deleteUsers(deal: Deal)

    @Query("SELECT * FROM deals")
    suspend fun loadAllDeals(): Array<Deal>
}