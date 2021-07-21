package com.mobile.finance.data

import androidx.room.*
import com.mobile.finance.data.entity.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

  @Query("SELECT * FROM tags")
  fun all(): Flow<List<Tag>>

  @Query("SELECT * FROM tags WHERE tagId = :id")
  fun find(id: String): Flow<Tag?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveTag(tag: Tag): Long

  @Delete
  suspend fun delete(vararg tags: Tag)
}