package com.mobile.finance.entries

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.finance.data.FinanceDatabase
import com.mobile.finance.data.entity.Entry
import com.mobile.finance.network.EntryRequestBody
import com.mobile.finance.network.FinanceApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject constructor(
  database: FinanceDatabase,
  private val apiService: FinanceApiService,
  private val savedStateHandle: SavedStateHandle): ViewModel() {

  val categoryDao = database.categoryDao()
  val tagDao = database.tagDao()
  val entryDao = database.entryDao()
  val fundDao = database.fundDao()
  val entryTagDao = database.entryTagDao()

  val entryList = entryDao.allEntries().asLiveData()

  fun deleteEntry(entry: Entry) {
    viewModelScope.launch {
      val entryTagList = entryTagDao.findEntryTag(entry.entryId).first()
      entryTagList.forEach {
        entryTagDao.delete(it)
      }
      entryDao.delete(entry)
    }
  }

  fun uploadEntries() {
    viewModelScope.launch {
      entryList.value?.forEach {
        try {
          apiService.postEntry(EntryRequestBody.fromCompositeEntry(it))
           entryDao.delete(it.entry)
        } catch (ex: ConnectException) {
          Log.i("network", "Connection failed")
        } catch (ex: HttpException) {
          Log.i("network", "Response was not 2xx")
        }
      }
    }
  }
}