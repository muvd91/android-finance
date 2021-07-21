package com.mobile.finance.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CompositeEntry(
  @Embedded val entry: Entry,
  @Relation(
    parentColumn = "categoryId",
    entityColumn = "categoryId"
  )
  val category: Category,
  @Relation(
    parentColumn = "fundId",
    entityColumn = "fundId"
  )
  val fund: Fund,
  @Relation(
    parentColumn = "entryId",
    entityColumn = "tagId",
    associateBy = Junction(EntryTagsCrossRef::class)
  )
  val tags: List<Tag>
)