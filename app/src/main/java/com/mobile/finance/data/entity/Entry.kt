package com.mobile.finance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.finance.domain.EntryType
import java.math.BigDecimal
import java.time.Instant

@Entity(tableName = "entries")
data class Entry(
  @PrimaryKey(autoGenerate = true) val entryId: Long = 0L,
  @ColumnInfo(name = "type") val type: EntryType,
  @ColumnInfo(name = "amount") val amount: BigDecimal,
  @ColumnInfo(name = "categoryId") val categoryId: String,
  @ColumnInfo(name = "description") val description: String,
  @ColumnInfo(name = "fundId") val fundId: String,
  @ColumnInfo(name = "timestamp") val timestamp: Instant
)