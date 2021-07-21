package com.mobile.finance.data.entity

import androidx.room.Entity

@Entity(tableName = "entry_tag", primaryKeys = ["entryId", "tagId"])
data class EntryTagsCrossRef(
  val entryId: Long,
  val tagId: String,
)