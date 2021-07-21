package com.mobile.finance.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mobile.finance.data.entity.Category
import com.mobile.finance.domain.EntryType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var db: FinanceDatabase
  private lateinit var categoryDao: CategoryDao

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = FinanceDatabase.newTestInstance(context)
    categoryDao = db.categoryDao()
  }

  @After
  fun close() {
    db.close()
  }

  @Test
  fun createAndReadCategory() = runBlocking {
    val foodCategory = Category("Food and snacks", EntryType.OUTCOME, Instant.now())
    categoryDao.saveCategory(foodCategory)

    assertThat(foodCategory.categoryId, equalTo("food_and_snacks"))
    assertThat(foodCategory.name, equalTo("Food and snacks"))

    var saved = categoryDao.find(foodCategory.categoryId).first()
    assertThat(saved?.categoryId, equalTo("food_and_snacks"))
    assertThat(saved?.name, equalTo("Food and snacks"))
    assertThat(saved?.type, equalTo(EntryType.OUTCOME))
  }
}