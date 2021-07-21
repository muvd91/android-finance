package com.mobile.finance.entries

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mobile.finance.R
import com.mobile.finance.data.entity.CompositeEntry
import com.mobile.finance.domain.EntryType
import java.text.DecimalFormat
import java.text.NumberFormat

@BindingAdapter("entryTypeImg")
fun ImageView.setEntryType(item: CompositeEntry) {
  setImageResource(
    when (item.entry.type) {
      EntryType.INCOME -> R.drawable.ic_income
      EntryType.OUTCOME -> R.drawable.ic_outcome
    }
  )
}

@BindingAdapter("entryIdString")
fun TextView.setEntryId(item: CompositeEntry) {
  text = item.entry.entryId.toString()
}

@BindingAdapter("entryTypeString")
fun TextView.setEntryType(item: CompositeEntry) {
  text = item.entry.type.name
}

@BindingAdapter("entryAmountString")
fun TextView.setAmount(item: CompositeEntry) {
  val df = NumberFormat.getNumberInstance() as DecimalFormat
  df.isParseBigDecimal = true
  df.minimumFractionDigits = 2
  df.maximumFractionDigits = 2
  text = df.format(item.entry.amount)
}

@BindingAdapter("entryDescriptionString")
fun TextView.setDescription(item: CompositeEntry) {
  text = item.entry.description
}
