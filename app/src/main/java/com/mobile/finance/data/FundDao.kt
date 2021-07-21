package com.mobile.finance.data

import androidx.room.*
import com.mobile.finance.data.entity.Fund
import kotlinx.coroutines.flow.Flow

@Dao
interface FundDao {

  @Query("SELECT * FROM funds")
  fun all(): Flow<List<Fund>>

  @Query("SELECT * FROM funds WHERE fundId = :id")
  fun find(id: String): Flow<Fund?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveFund(fund: Fund): Long

  @Delete
  suspend fun delete(vararg funds: Fund)
}