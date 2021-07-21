package com.mobile.finance.newentry

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mobile.finance.R
import com.mobile.finance.domain.EntryType
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

@BindingAdapter("entryCategory")
fun AutoCompleteTextView.setCategory(category: String) {
    setText(category)
}
@InverseBindingAdapter(attribute = "entryCategory")
fun AutoCompleteTextView.getCategory(): String {
    return text.toString()
}
@BindingAdapter("entryCategoryAttrChanged")
fun AutoCompleteTextView.setCategoryListeners(listener: InverseBindingListener) {
    onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
        listener.onChange()
    }
}

@BindingAdapter("entryAmount")
fun TextInputEditText.setEntryAmount(amount: String) {
    setText(amount)
}

@InverseBindingAdapter(attribute = "entryAmount")
fun TextInputEditText.getEntryAmount(): String {
    val value = checkEmptyString(text.toString())
    return value
}

@BindingAdapter("entryAmountAttrChanged")
fun TextInputEditText.setListeners(listener: InverseBindingListener) {
    onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
        val textView = focusedView as TextView
        if (hasFocus) {
            textView.text = ""
        } else {
            listener.onChange()
            val text = checkEmptyString(text.toString())
            textView.text = Converters.bigDecimalToString(BigDecimal(text))
        }
    }
}

@BindingAdapter("newEntryTypeHint")
fun TextInputLayout.setEntryTypeHint(type: EntryType) {
    hint = when(type) {
        EntryType.INCOME -> resources.getString(R.string.new_entry_fund_add)
        EntryType.OUTCOME -> resources.getString(R.string.new_entry_fund_deduct)
    }
    refreshDrawableState()
}

fun checkEmptyString(str: String) = if (str.isEmpty() || str.isBlank()) "0" else str

object Converters {
    @InverseMethod("stringToBigDecimal")
    @JvmStatic fun bigDecimalToString(value: BigDecimal): String {
        val df = NumberFormat.getNumberInstance() as DecimalFormat
        df.isParseBigDecimal = true
        df.minimumFractionDigits = 2
        df.maximumFractionDigits = 2
        return df.format(value)
    }
    @JvmStatic fun stringToBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }
}