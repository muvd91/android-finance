package com.mobile.finance.network

import com.mobile.finance.data.entity.Category
import com.mobile.finance.data.entity.CompositeEntry
import com.mobile.finance.data.entity.Fund
import com.mobile.finance.data.entity.Tag
import com.mobile.finance.domain.EntryType
import java.math.BigDecimal
import java.time.Instant

data class EntryRequestBody(
  val type: EntryType,
  val amount: BigDecimal,
  val category: Category,
  val description: String,
  val fund: Fund,
  val tags: List<Tag>,
  val timestamp: Instant
) {
  companion object {
    fun fromCompositeEntry(compositeEntry: CompositeEntry): EntryRequestBody {
      return EntryRequestBody(
        compositeEntry.entry.type,
        compositeEntry.entry.amount,
        compositeEntry.category,
        compositeEntry.entry.description,
        compositeEntry.fund,
        compositeEntry.tags,
        compositeEntry.entry.timestamp
      )
    }
  }
}
