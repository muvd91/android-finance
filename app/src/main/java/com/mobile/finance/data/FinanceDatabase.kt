package com.mobile.finance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobile.finance.data.entity.*
import javax.inject.Singleton

private const val DB_NAME = "finance.db"

@Singleton
@Database(entities = [Entry::class, Category::class, Fund::class, Tag::class, EntryTagsCrossRef::class],
  version = 1,
  exportSchema = false)
@TypeConverters(TypeTransmogrifier::class)
abstract class FinanceDatabase : RoomDatabase() {
  abstract fun entryDao(): EntryDao
  abstract fun categoryDao(): CategoryDao
  abstract fun tagDao(): TagDao
  abstract fun fundDao(): FundDao
  abstract fun entryTagDao(): EntryTagDao

  companion object {
    fun newInstance(context: Context) =
      Room.databaseBuilder(context, FinanceDatabase::class.java, DB_NAME).build()

    fun newTestInstance(context: Context) =
      Room.inMemoryDatabaseBuilder(context, FinanceDatabase::class.java).build()
  }
}