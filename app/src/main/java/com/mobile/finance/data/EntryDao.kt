package com.mobile.finance.data

import androidx.room.*
import com.mobile.finance.data.entity.CompositeEntry
import com.mobile.finance.data.entity.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

  @Query("SELECT * FROM entries")
  fun all(): Flow<List<Entry>>

  @Transaction
  @Query("SELECT * FROM entries")
  fun allEntries(): Flow<List<CompositeEntry>>

  @Query("SELECT * FROM entries WHERE entryId = :id")
  fun find(id: Long): Flow<CompositeEntry?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveEntry(entry: Entry) : Long

  @Delete
  suspend fun delete(vararg entries: Entry)
}