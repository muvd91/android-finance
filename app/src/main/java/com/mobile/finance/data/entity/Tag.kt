package com.mobile.finance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.finance.domain.EntryType
import java.time.Instant
import java.util.*

private const val SPACE = " "
private const val UNDERSCORE = "_"

@Entity(tableName = "tags")
data class Tag(
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "type") val type: EntryType,
  @ColumnInfo(name = "last_used") val lastUsed: Instant = Instant.now(),
  @PrimaryKey val tagId: String = name.trim().lowercase(Locale.ROOT).replace(SPACE, UNDERSCORE)
)