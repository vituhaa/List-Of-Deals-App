package com.example.listofdeals2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DealsDao {
    @Insert
    suspend fun insertDeal(deal: Deal)

    @Update
    suspend fun updateDeal(deal: Deal)

    @Delete
    suspend fun deleteDeal(deal: Deal)

    @Query("SELECT * FROM deals")
    suspend fun loadAllDeals(): List<Deal>
}