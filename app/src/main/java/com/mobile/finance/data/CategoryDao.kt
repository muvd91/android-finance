package com.mobile.finance.data

import androidx.room.*
import com.mobile.finance.data.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories ORDER BY last_used")
  fun all(): Flow<List<Category>>

  @Query("SELECT * FROM categories WHERE categoryId = :id")
  fun find(id: String): Flow<Category?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveCategory(category: Category): Long

  @Delete
  suspend fun delete(vararg categories: Category)

}
