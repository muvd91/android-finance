package com.mobile.finance.newentry

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.*
import com.mobile.finance.R
import com.mobile.finance.data.FinanceDatabase
import com.mobile.finance.data.entity.*
import com.mobile.finance.domain.EntryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class NewEntryViewModel @Inject constructor(
  database: FinanceDatabase,
  private val savedStateHandle: SavedStateHandle) : ViewModel() {

  val categoryDao = database.categoryDao()
  val tagDao = database.tagDao()
  val entryDao = database.entryDao()
  val fundDao = database.fundDao()
  val entryTagDao = database.entryTagDao()

  val categories = categoryDao.all()
  val funds = fundDao.all()
  val formTags = mutableMapOf<String, Tag>()

  // New Entry data binding
  val newEntry = EntryObservableViewModel(
    EntryType.INCOME,
    BigDecimal.valueOf(0L),
    "",
    "",
    "",
    emptyList()
  )

  fun setEntryType(radioGroup: RadioGroup, id: Int) {
    Log.i("myLogTag", "Entry amount is ${newEntry.amount}")
    Log.i("myLogTag", "Entry type is ${newEntry.type}")
    Log.i("myLogTag", "Entry category is ${newEntry.category}")
    Log.i("myLogTag", "Entry fund is ${newEntry.fund}")
    Log.i("myLogTag", "Entry description is ${newEntry.description}")
    Log.i("myLogTag", "Entry tags are ${formTags}")
    newEntry.type = when (id) {
      R.id.income_radio_button -> EntryType.INCOME
      R.id.outcome_radio_button -> EntryType.OUTCOME
      else -> EntryType.INCOME
    }
  }

  private val _doneSaving = MutableLiveData<Boolean>(false)
  val doneSaving: LiveData<Boolean>
    get() = _doneSaving

  fun entrySaved() {
    _doneSaving.value = false
  }

  fun onSave() {
    viewModelScope.launch {
      val category = Category(name = newEntry.category, type = newEntry.type)
      val fund = Fund(name = newEntry.fund)
      val tags = formTags.values.toList()
      val entry = Entry(type = newEntry.type,
        amount = newEntry.amount,
        categoryId = category.categoryId,
        description = newEntry.description,
        fundId = fund.fundId,
        timestamp = Instant.now())

      val savedEntryId = entryDao.saveEntry(entry)
      val categoryId = categoryDao.saveCategory(category)
      val fundId = fundDao.saveFund(fund)
      tags.forEach { tag ->
        tagDao.saveTag(tag)
        entryTagDao.saveEntryTag(EntryTagsCrossRef(savedEntryId, tag.tagId))
      }
      _doneSaving.value = true
    }
  }
}