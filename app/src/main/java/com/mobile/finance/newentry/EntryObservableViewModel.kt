package com.mobile.finance.newentry

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.mobile.finance.domain.EntryType
import java.math.BigDecimal

class EntryObservableViewModel(
    type: EntryType,
    amount: BigDecimal,
    category: String,
    var description: String,
    var fund: String,
    var tags: List<String>
) : BaseObservable() {

    @get:Bindable
    var type: EntryType = type
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @get:Bindable
    var amount: BigDecimal = amount
        set(value) {
            if (value != field) field = value
        }

    @get:Bindable
    var category: String = category
}