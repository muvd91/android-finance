package com.mobile.finance.data

import androidx.room.TypeConverter
import com.mobile.finance.domain.EntryType
import java.math.BigDecimal
import java.time.Instant

private const val DECIMAL_PLACES: Long = 100

class TypeTransmogrifier {

  @TypeConverter
  fun fromInstant(date: Instant): Long = date.toEpochMilli()

  @TypeConverter
  fun toInstant(millisSinceEpoch: Long): Instant = Instant.ofEpochMilli(millisSinceEpoch)

  @TypeConverter
  fun fromEntryType(entryType: EntryType): String = entryType.name

  @TypeConverter
  fun toEntryType(entryType: String): EntryType = EntryType.valueOf(entryType)

  @TypeConverter
  fun fromBigDecimal(amount: BigDecimal): Long =
    amount.multiply(BigDecimal.valueOf(DECIMAL_PLACES)).longValueExact()

  @TypeConverter
  fun toBigDecimal(amount: Long): BigDecimal =
    BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(DECIMAL_PLACES))
}
