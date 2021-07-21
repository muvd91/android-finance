package com.mobile.finance.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mobile.finance.data.entity.*
import com.mobile.finance.domain.EntryType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var db: FinanceDatabase
  private lateinit var entryDao: EntryDao
  private lateinit var categoryDao: CategoryDao
  private lateinit var tagDao: TagDao
  private lateinit var fundDao: FundDao
  private lateinit var entryTagDao: EntryTagDao

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = FinanceDatabase.newTestInstance(context)
    entryDao = db.entryDao()
    categoryDao = db.categoryDao()
    tagDao = db.tagDao()
    fundDao = db.fundDao()
    entryTagDao = db.entryTagDao()
  }

  @After
  fun close() {
    db.close()
  }

  @Test
  fun createAndReadEntry() = runBlocking {
    val foodCategory = Category("Food and snacks", EntryType.OUTCOME, Instant.now())
    categoryDao.saveCategory(foodCategory)

    val holidayTag = Tag("holiday", EntryType.OUTCOME, Instant.now())
    tagDao.saveTag(holidayTag)

    val mainFund = Fund("main")
    fundDao.saveFund(mainFund)

    val entry = Entry(fundId = "main",
      amount = BigDecimal(100),
      type = EntryType.OUTCOME,
      categoryId = "food_and_snacks",
      description = "M&Ms and Coke",
      timestamp = Instant.now())
    val entryId = entryDao.saveEntry(entry)

    val entryTag = EntryTagsCrossRef(entryId, holidayTag.tagId)
    entryTagDao.saveEntryTag(entryTag)

    var savedEntry = entryDao.all().first()
    var compositeEntry = entryDao.allEntries().first()

    println(savedEntry)
    println(compositeEntry)
  }

}