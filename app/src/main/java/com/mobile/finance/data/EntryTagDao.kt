package com.mobile.finance.data

import androidx.room.*
import com.mobile.finance.data.entity.EntryTagsCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryTagDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveEntryTag(entryTag: EntryTagsCrossRef): Long

  @Query("SELECT * FROM entry_tag WHERE entryId = :entryId")
  fun findEntryTag(entryId: Long): Flow<List<EntryTagsCrossRef?>>

  @Delete
  suspend fun delete(vararg entryTag: EntryTagsCrossRef?)
}