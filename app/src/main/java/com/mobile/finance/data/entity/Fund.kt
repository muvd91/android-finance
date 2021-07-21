package com.mobile.finance.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

private const val SPACE = " "
private const val UNDERSCORE = "_"

@Entity(tableName = "funds")
data class Fund(
  @ColumnInfo(name = "name") val name: String,
  @PrimaryKey val fundId: String = name.trim().lowercase(Locale.ROOT).replace(SPACE, UNDERSCORE)
)